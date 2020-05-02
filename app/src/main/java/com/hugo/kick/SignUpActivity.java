package com.hugo.kick;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout regPrenom, regNom, regPseudo, regEmail, regPassword;
    Button login_button, btn_go;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sign_up);

        login_button = findViewById(R.id.btn_login);
        btn_go = findViewById(R.id.reg_go_btn);
        regPrenom = findViewById(R.id.reg_prenom);
        regNom = findViewById(R.id.reg_nom);
        regPseudo = findViewById(R.id.reg_pseudo);
        regEmail = findViewById(R.id.reg_email);
        regPassword = findViewById(R.id.reg_password);

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validatePassword() | !validatePseudo() | !validateEmail() | !validatePrenom() | !validateNom()) {
                    return;
                } else {
                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("users");

                    String prenom = regPrenom.getEditText().getText().toString();
                    String nom = regNom.getEditText().getText().toString();
                    String pseudo = regPseudo.getEditText().getText().toString();
                    String email = regEmail.getEditText().getText().toString();
                    String password = regPassword.getEditText().getText().toString();

                    UserHelperClass helperClass = new UserHelperClass(prenom, nom, pseudo, email, password);

                    reference.child(pseudo).setValue(helperClass);
                }
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private Boolean validatePseudo() {
        String val = regPseudo.getEditText().getText().toString();
        String noSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            regPseudo.setError("Le champs ne peut être vide");
            return false;
        } else if (val.length()>= 20) {
            regPseudo.setError("Pseudo trop long");
            return false;
        } else if (!val.matches(noSpace)) {
            regPseudo.setError("Il ne doit pas y avoir d'espaces");
            return false;
        } else {
            regPseudo.setError(null);
            regPseudo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordValidation = "^" +
                "(?=.*[0-9])" +              //au moins 1 chiffre
                "(?=.*[a-z])" +              //au moins une lettre
                "(?=.*[A-Z])" +              //au moins une majuscule
                "(?=.*[a-zA-Z])" +             //toutes les lettres
                //"(?=.*[@#$%^&+=])" +           //au moins 1 caractère special
                "(?=\\S+$)" +                  //pas d'espace
                ".{4,}" +                      //au moins 4 caractères
                "$";

        if (val.isEmpty()) {
            regPassword.setError("Le champs ne peut être vide");
            return false;
        } else if (!val.matches(passwordValidation)) {
            regPassword.setError("Le mot de passe doit contenir au moins une majuscule, une minuscule et un chiffre");
            return false;
        }
        else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePrenom() {
        String val = regPrenom.getEditText().getText().toString();

        if (val.isEmpty()) {
            regPrenom.setError("Le champs ne peut être vide");
            return false;
        } else {
            regPrenom.setError(null);
            regPrenom.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateNom() {
        String val = regNom.getEditText().getText().toString();

        if (val.isEmpty()) {
            regNom.setError("Le champs ne peut être vide");
            return false;
        } else {
            regNom.setError(null);
            regNom.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            regEmail.setError("Le champs ne peut être vide");
            return false;
        } else if (!val.matches(emailPattern)) {
            regEmail.setError("Email invalide");
            return false;
        } else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
}


