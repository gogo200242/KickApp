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
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class ContactActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BottomNavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    androidx.appcompat.widget.Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Button btn_mail;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        btn_mail = findViewById(R.id.btn_mail);

        bottomNavigationView.setSelectedItemId(R.id.contact);
        Menu menu = navigationView.getMenu();
        Menu menu2 = bottomNavigationView.getMenu();
        menu.findItem(R.id.nav_profil).setVisible(false);
        menu.findItem(R.id.nav_login).setVisible(false);
        menu.findItem(R.id.nav_signup).setVisible(false);
        menu2.findItem(R.id.contact).setVisible(false);

        btn_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, SendEmailActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle;
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.magazine:
                Intent intent4 = new Intent(ContactActivity.this, MainActivity.class);
                startActivity(intent4);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            case R.id.boutique:
                Intent intent = new Intent(ContactActivity.this, BoutiqueActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            case R.id.nav_contact:
                return true;
            case R.id.sortiedumois:
                Intent intent3 = new Intent(ContactActivity.this, SortiedumoisActivity.class);
                startActivity(intent3);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            case R.id.nav_logout:
                Intent intent7 = new Intent(ContactActivity.this, LogoutActivity.class);
                startActivity(intent7);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
            case R.id.nav_share:
                Intent intent6 = new Intent(Intent.ACTION_SEND);
                intent6.setType("text/plain");
                String ShareBody = ("Lien");
                String ShareSub = ("Texte");
                intent6.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
                intent6.putExtra(Intent.EXTRA_TEXT, ShareBody);
                startActivity(Intent.createChooser(intent6, "Partage avec"));
                return true;
            case R.id.nav_rateus:
                Toast.makeText(this, "Note nous !", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.nav_don:
                Intent intent9 = new Intent(ContactActivity.this, DonateActivity.class);
                startActivity(intent9);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                return true;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {


    }
}