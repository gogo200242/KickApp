package com.hugo.kick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    TextView texte;
    EditText email_reset;
    Button btn_sendreset;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        texte = findViewById(R.id.textview17);
        email_reset = findViewById(R.id.reset_mail);
        btn_sendreset = findViewById(R.id.button_reset);
        mAuth = FirebaseAuth.getInstance();

        btn_sendreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email_reset.getText().toString();

                if (TextUtils.isEmpty(userEmail)) {
                    //faire le text input layer comme le login
                    Toast.makeText(ResetPasswordActivity.this, "Le champ ne peut pas être vide", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //faire popup reussite et redirection vers login
                                Toast.makeText(ResetPasswordActivity.this, "Regarde ta boite mail", Toast.LENGTH_SHORT).show();
                            } else {
                                //faire popup erreur meme que erreur connexion
                                Toast.makeText(ResetPasswordActivity.this, "Erreur", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }
}
