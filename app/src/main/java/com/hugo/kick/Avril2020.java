package com.hugo.kick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.navigation.NavigationView;

public class Avril2020 extends AppCompatActivity  {

    ImageButton back,download;


    PDFView avril2020;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avril2020);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        avril2020 = (PDFView) findViewById(R.id.pdf_avril2020);
        back = findViewById(R.id.imageButton2);
        download = findViewById(R.id.imageButton);

        avril2020.fromAsset("avril2020.pdf").load();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Avril2020.this,Avril2020Resume.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
    }

}