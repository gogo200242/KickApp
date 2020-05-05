package com.hugo.kick;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
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
    TextView Bienvenue, logodesc, textclose, textKick, textLeMagazine, textmessage;
    TextInputLayout logEmail, logPassword;
    SignInButton googlesignin;
    CheckBox checkBox;
    FirebaseAuth mAuth;
    Dialog erreurDialog;

    private static final int RC_SIGN_IN = 1;
    private GoogleApiClient mGoogleSignInClient;
    private static final String TAG = "LoginActivity";


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        erreurDialog = new Dialog(this);

        callSignUp = findViewById(R.id.signup_button);
        Go = findViewById(R.id.go_button);
        lostPassword = findViewById(R.id.lost_password);
        logo = findViewById(R.id.logo);
        Bienvenue = findViewById(R.id.logo_name);
        logodesc = findViewById(R.id.logo_desc);
        logEmail = findViewById(R.id.email);
        logPassword = findViewById(R.id.mdp);
        checkBox = findViewById(R.id.checkbox);
        googlesignin = findViewById(R.id.sign_in_button);

        final LoadingDialog loadingDialog = new LoadingDialog(LoginActivity.this);

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

                if (TextUtils.isEmpty(email)) {
                    logEmail.setError("Le champs ne peut pas être vide");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    logPassword.setError("Le champs ne peut pas être vide");
                    return;
                }

                if (password.length() < 6) {
                    logPassword.setError("Le mot de passe doit contenir au moins 6 caractères");
                    return;
                }


                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loadingDialog.loadingAlertDialog();
                            lancementActivity();
                        } else {
                            openErreurDialog();
                        }
                    }
                });
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(LoginActivity.this, "La connexion a Google à échoué", Toast.LENGTH_SHORT).show();
            }
        })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


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

        googlesignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }

    private void lancementActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void SendUserToLogin() {
        Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(LoginActivity.this, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseWithAuthGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithCredential:success");
                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("checkbox", "true");
                            editor.apply();
                            lancementActivity();
                        } else {
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("checkbox", "false");
                            editor.apply();
                            String message = task.getException().toString();
                            SendUserToLogin();
                            Toast.makeText(LoginActivity.this, "Une erreur est survenue : " + message, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void openErreurDialog() {

        erreurDialog.setContentView(R.layout.custom_erreur_wrongmdp);

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

