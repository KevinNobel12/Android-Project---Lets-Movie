package com.example.letsmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mAuth;

    EditText email, password;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        email = (EditText) findViewById(R.id.emaliLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
         findViewById(R.id.loginbutton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {

        switch (view.getId())
        {
            case R.id.loginbutton:
                userLogin();
                break;
        }

    }

    private void userLogin() {
        String Email = email.getText().toString();

        String Password = password.getText().toString();

 if (Email.isEmpty()){
            email.setError("Email cant be Empty");
            email.requestFocus();
            return;
        }
 else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
     email.setError("Enter a valid Email");
     email.requestFocus();
     return;

 }
 else if (Password.isEmpty()){
     password.setError("Password is required");
     password.requestFocus();
     return;
 }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(Email,Password ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE );
                if (task.isSuccessful()){
                    finish();
                    Intent i  = new Intent(Login.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }


    }
}
