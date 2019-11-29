package com.example.letsmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class profileupdate extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageView imageView;
    TextView name,email,phone,creditcard,expiry,cvv;
    ArrayList<String> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileupdate);

        mAuth = FirebaseAuth.getInstance();
        imageView=(ImageView) findViewById(R.id.profilepic);


//        loadUserInformation();










    }

//    protected void onStart() {
//        super.onStart();
//
//        if (mAuth.getCurrentUser() == null){
//            finish();
//            startActivity(new Intent(this,Login.class));
//        }
//
//
//    }
//    private void loadUserInformation() {
//
//        FirebaseUser user = mAuth.getCurrentUser();
//
//        if (user!=null) {
//
//
//            if (user.getPhotoUrl() != null) {
//
//                Glide.with(this).load(user.getPhotoUrl().toString()).into(imageView);
//
//            }
//
//            if (user.getDisplayName() != null) {
//                name.setText(user.getDisplayName());
//
//
//            }
//            if (user.getPhoneNumber() != null) {
//                String displayPhone = user.getPhoneNumber();
//            }
//            if (user.getEmail() != null) {
//                String displaycredicard = user.getEmail();
//
//            }
//        }
//        }
}
