package com.example.letsmovie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    ImageView imageView;
EditText name, phonenumber, creditcard, expiry, cvv;
    Uri uriProfileImage;
    ProgressBar progressBar;
    String profileImageurl;

    DatabaseReference databseUser;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        databseUser = FirebaseDatabase.getInstance().getReference("user");
        mAuth = FirebaseAuth.getInstance();
        name = (EditText)findViewById(R.id.namesave);
        phonenumber= (EditText)findViewById(R.id.phonesave);
        creditcard= (EditText)findViewById(R.id.creditcardsave);
        expiry=(EditText)findViewById(R.id.expirysave);
        cvv = (EditText)findViewById(R.id.cvvsave);
        imageView=(ImageView) findViewById(R.id.profilepic);

        progressBar = findViewById(R.id.profileprogress);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showImageChooser();


            }
        });

        findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveUserInformation();

            }
        });
    }

    private void saveUserInformation() {

        String displayname = name.getText().toString();
        String displayphone = phonenumber.getText().toString();
        String displaycreditcard = creditcard.getText().toString();
        String displayexpiry = expiry.getText().toString();
        String displaycvv = cvv.getText().toString();

        FirebaseUser user = mAuth.getCurrentUser();

        if (!TextUtils.isEmpty(displayname)){

            String id = databseUser.push().getKey();
            user Users = new user( id,displayname,displayphone, displaycreditcard );
            databseUser.child(id).setValue(Users);

            Toast.makeText(this,"User added",Toast.LENGTH_SHORT).show();

        }



        else {
            Toast.makeText(this,"Enter all details",Toast.LENGTH_SHORT).show();

        }






        if (user != null && profileImageurl != null)
        {
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayname)
                    .setPhotoUri(Uri.parse(profileImageurl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (!task.isSuccessful())
                            {
                                Toast.makeText(Profile.this,"Profile not  Updated",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                String savename = name.getText().toString();
                                String savephone = phonenumber.getText().toString();
                                String savecreditcard = creditcard.getText().toString();
                                String saveexpiry = expiry.getText().toString();
                                String savecvv = cvv.getText().toString();

                                String user_id = mAuth.getCurrentUser().getUid();
                                DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                                Map newPost = new HashMap();
                                newPost.put("name", savename);
                                newPost.put("phone", savephone);
                                newPost.put("credicard", savecreditcard);
                                newPost.put("expiry", saveexpiry);
                                newPost.put("cvv", savecvv);

                                current_user_db.setValue(newPost );
                                Toast.makeText(Profile.this,"Profile  Updated",Toast.LENGTH_SHORT).show();


                            }
                        }
                    });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null)
        {
            uriProfileImage=data.getData();
            uploadImageToFirebaseStorage();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfileImage);
                imageView.setImageBitmap(bitmap);


            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    private void  uploadImageToFirebaseStorage(){

        final StorageReference profileImageRef=
                FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");


        if (uriProfileImage != null)
        {
            progressBar.setVisibility(View.VISIBLE);
            profileImageRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressBar.setVisibility(View.GONE);

                    profileImageurl = taskSnapshot.getStorage().getDownloadUrl().toString();

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Profile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void showImageChooser(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Profile Image"), CHOOSE_IMAGE);
    }
}
