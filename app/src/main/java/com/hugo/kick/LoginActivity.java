package com.hugo.kick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button callSignUp, Go, lostPassword;
    ImageView logo;
    TextView Bienvenue, logodesc;
    TextInputLayout pseudo, password;
    CheckBox checkBox;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.signup_button);
        Go = findViewById(R.id.go_button);
        lostPassword = findViewById(R.id.lost_password);
        logo = findViewById(R.id.logo);
        Bienvenue = findViewById(R.id.logo_name);
        logodesc = findViewById(R.id.logo_desc);
        pseudo = findViewById(R.id.username);
        password = findViewById(R.id.mdp);
        checkBox = findViewById(R.id.checkbox);

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("checkbox", "");
        if (checkbox.equals("true")) {
            Intent intent8 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent8);
        } else if (checkbox.equals("false")) {
            Toast.makeText(this, "Connecte-toi", Toast.LENGTH_SHORT).show();
        }


        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("users");

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePseudo() | !validatePassword()) {
                    return;
                } else {
                    isUser();
                }
            }

            private boolean validatePseudo() {
                String val = pseudo.getEditText().getText().toString();

                if (val.isEmpty()) {
                    pseudo.setError("Le champs ne peut être vide");
                    return false;
                } else {
                    pseudo.setError(null);
                    pseudo.setErrorEnabled(false);
                    return true;
                }
            }

            private boolean validatePassword() {
                String val = password.getEditText().getText().toString();

                if (val.isEmpty()) {
                    password.setError("Le champs ne peut être vide");
                    return false;
                } else {
                    password.setError(null);
                    password.setErrorEnabled(false);
                    return true;
                }
            }

            private void isUser() {
                final String userEnteredPseudo = pseudo.getEditText().getText().toString().trim();
                final String userEnteredPassword = password.getEditText().getText().toString().trim();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

                Query checkUser = reference.orderByChild("pseudo").equalTo(userEnteredPseudo);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {

                            pseudo.setError(null);
                            pseudo.setErrorEnabled(false);

                            String passwordFromDB = dataSnapshot.child(userEnteredPseudo).child("password").getValue(String.class);

                            if (passwordFromDB.equals(userEnteredPassword)) {

                                password.setErrorEnabled(false);
                                password.setError(null);

                                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent2);
                                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                            } else {
                                password.setError("Mauvais mot de passe");
                                password.requestFocus();
                            }
                        } else {
                            pseudo.setError("Ce pseudo n'existe pas");
                            pseudo.requestFocus();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        callSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checkbox", "true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();

                } else if (!compoundButton.isChecked()) {

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checkbox", "false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }
}

