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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    TextView texte, textclose, textKick, textLeMagazine, textmessage;
    EditText email_reset;
    Button btn_sendreset, btn_connecte;
    ImageButton back;
    Dialog erreurDialog, myDialog;
    FirebaseAuth mAuth;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_reset_password);

        erreurDialog = new Dialog(this);
        myDialog = new Dialog(this);

        texte = findViewById(R.id.textview17);
        email_reset = findViewById(R.id.reset_mail);
        btn_sendreset = findViewById(R.id.button_reset);
        back = findViewById(R.id.imageButton2);
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
                                openDialog();
                            } else {
                                openErreurDialog();
                            }
                        }
                    });
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

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

    public void openDialog() {
        myDialog.setContentView(R.layout.custom_popup_resetmdp);

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
                Intent intent5 = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                startActivity(intent5);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
}
