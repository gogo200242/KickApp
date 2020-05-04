package com.hugo.kick;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class SendEmailActivity extends AppCompatActivity {

    TextView email_to;
    EditText sujet, message;
    Button btn_send;
    ImageButton back;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_send_email);

        email_to = findViewById(R.id.email_to);
        sujet = findViewById(R.id.sujet);
        message = findViewById(R.id.message);
        btn_send = findViewById(R.id.btn_send);
        back = findViewById(R.id.imageButton2);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + email_to.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT, sujet.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, message.getText().toString());
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendEmailActivity.this, ContactActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }
}
