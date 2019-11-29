package com.example.letsmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class First extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
    }

    public void signup(View view) {
        Intent i = new Intent(First.this,SignUp.class);
        startActivity(i);
    }

    public void login(View view) {
        Intent i = new Intent(First.this,Login.class);
        startActivity(i);
    }
}
