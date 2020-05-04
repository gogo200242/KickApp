package com.hugo.kick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.wifi.hotspot2.PasspointConfiguration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
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
    TextInputLayout logEmail, logPassword;
    CheckBox checkBox;
    FirebaseAuth mAuth;



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
        logEmail = findViewById(R.id.email);
        logPassword = findViewById(R.id.mdp);
        checkBox = findViewById(R.id.checkbox);

        mAuth = FirebaseAuth.getInstance();


        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("checkbox", "");
        if (checkbox.equals("true")) {
            Intent intent8 = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent8);
        } else if (checkbox.equals("false")) {

        }

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = logEmail.getEditText().getText().toString().trim();
                String password = logPassword.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(email)) {
                    logEmail.setError("Le champs ne peut pas être vide");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    logPassword.setError("Le champs ne peut pas être vide");
                    return;
                }

                if(password.length() < 6) {
                    logPassword.setError("Le mot de passe doit contenir au moins 6 caractères");
                    return;
                }

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            lancementActivity();
                        } else {
                            //faire popup mauvais mot de passe ou adresse mail
                            Toast.makeText(LoginActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                        }
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

                } else if (!compoundButton.isChecked()) {

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("checkbox", "false");
                    editor.apply();
                }
            }

        });

        lostPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                startActivity(intent2);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });


    }

    private void lancementActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        Toast.makeText(LoginActivity.this, "Connecter avec succès", Toast.LENGTH_SHORT).show();
    }


}

