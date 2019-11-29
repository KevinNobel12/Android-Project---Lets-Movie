package com.example.letsmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity implements View.OnClickListener{

ProgressBar progressBar;
    EditText name, email,password, confirmPass;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();

        name =(EditText) findViewById(R.id.namesignup);
        email = (EditText) findViewById(R.id.emailsignup);
        password = (EditText) findViewById(R.id.passwordsignup);
        confirmPass = (EditText) findViewById(R.id.confirmpass);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        findViewById(R.id.signupbutton).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signupbutton:
                registerUser();
                break;
        }
    }

    private void registerUser() {

        String Name =name.getText().toString();
        String Email = email.getText().toString();

        String Password = password.getText().toString();
        String ConfirmPassword = confirmPass.getText().toString();

            if (Name.isEmpty()){
                name.setError("Name cant be Empty");
                name.requestFocus();
                return;
            }
       else if (Email.isEmpty()){
            email.setError("Email cant be Empty");
            email.requestFocus();
            return;
        }
    else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()){
                email.setError("Enter a valid Email");
                email.requestFocus();
                return;

            }
    else if (Password.length()<6){
                password.setError("Minimum length of password should be 6 ");
                password.requestFocus();
                return;
            }
        else if (Password.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

       else if (ConfirmPassword.isEmpty()){
            confirmPass.setError("Confirm password is required");
            confirmPass.requestFocus();
            return;
        }
       else if (!Password.equals(ConfirmPassword)){



                confirmPass.setError("Password should be matched");
                confirmPass.requestFocus();
                return;
            }


       progressBar.setVisibility(View.VISIBLE);
       mAuth.createUserWithEmailAndPassword(Email , Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task)
           {
                if (task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(),"User Registered successsfully",Toast.LENGTH_LONG).show();
                    Intent i = new Intent(SignUp.this,MainActivity.class);
                    startActivity(i);

                }
                else
                    {

                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {

                            Toast.makeText(getApplicationContext(), "Email is already taken", Toast.LENGTH_LONG).show();

                        } else
                            {

                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();


                             }


                    }


           }
       });

    }
}
