package com.hugo.kick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout regPrenom, regNom, regPseudo, regEmail, regPassword;
    Button login_button, btn_go, btn_connecte;
    Dialog myDialog, erreurDialog;
    TextView textclose, textKick, textLeMagazine, textmessage;

    FirebaseAuth mAuth;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sign_up);

        myDialog = new Dialog(this);
        erreurDialog = new Dialog(this);

        login_button = findViewById(R.id.btn_login);
        btn_go = findViewById(R.id.reg_go_btn);
        regPrenom = findViewById(R.id.reg_prenom);
        regNom = findViewById(R.id.reg_nom);
        regPseudo = findViewById(R.id.reg_pseudo);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);

        mAuth = FirebaseAuth.getInstance();

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = regEmail.getEditText().getText().toString().trim();
                String password = regPassword.getEditText().getText().toString().trim();
                String prenom = regPrenom.getEditText().getText().toString().trim();
                String nom = regNom.getEditText().getText().toString().trim();
                String pseudo = regPseudo.getEditText().getText().toString().trim();

                if(TextUtils.isEmpty(prenom)) {
                    regPrenom.setError("Le champs ne peut pas être vide");
                    return;
                }

                if(TextUtils.isEmpty(nom)) {
                    regNom.setError("Le champs ne peut pas être vide");
                    return;
                }

                if(TextUtils.isEmpty(pseudo)) {
                    regPseudo.setError("Le champs ne peut pas être vide");
                    return;
                }

                if(TextUtils.isEmpty(email)) {
                    regEmail.setError("Le champs ne peut pas être vide");
                    return;
                }

                if(TextUtils.isEmpty(password)) {
                    regPassword.setError("Le champs ne peut pas être vide");
                    return;
                }

                if(password.length() < 6) {
                    regPassword.setError("Le mot de passe doit contenir au moins 6 caractères");
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            openDialog();
                        } else {
                            openErreurDialog();
                        }
                    }
                });


            }
        });



        login_button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View v){
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    });
}

    public void openDialog() {
        myDialog.setContentView(R.layout.custom_popup);

        textclose = myDialog.findViewById(R.id.textview17);
        textKick = myDialog.findViewById(R.id.Kick);
        textLeMagazine = myDialog.findViewById(R.id.LeMagazine);
        textmessage = myDialog.findViewById(R.id.TextView18);
        btn_connecte = myDialog.findViewById(R.id.btn_connecte);

        textclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });

        btn_connecte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent5);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    public void openErreurDialog() {

        erreurDialog.setContentView(R.layout.custom_popup_erreur);

        textclose = erreurDialog.findViewById(R.id.textview17);
        textKick = erreurDialog.findViewById(R.id.Kick);
        textLeMagazine = erreurDialog.findViewById(R.id.LeMagazine);
        textmessage = erreurDialog.findViewById(R.id.TextView18);

        textclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                erreurDialog.dismiss();
            }
        });
        erreurDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        erreurDialog.show();

    }


}


