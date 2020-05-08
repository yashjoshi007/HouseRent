package com.example.houserent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {

    Button callSignup,login_btn;
    ImageView image;
    TextView logotext, slogantext;
    TextInputLayout username,password;
    ProgressBar bar;
    private Object isUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callSignup = findViewById(R.id.signup);
        image = findViewById(R.id.imageView2);
        logotext = findViewById(R.id.textView);
        slogantext = findViewById(R.id.textView2);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login_btn = findViewById(R.id.Login_btn);
        bar = findViewById(R.id.pb);

        bar.setVisibility(View.GONE);

        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,Signup.class);

                Pair[] pairs = new Pair[7];

                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logotext,"logo_text");
                pairs[2] = new Pair<View,String>(slogantext,"logo_desc");
                pairs[3] = new Pair<View,String>(username,"username_tran");
                pairs[4] = new Pair<View,String>(password,"password_tran");
                pairs[5] = new Pair<View,String>(login_btn,"button_tran");
                pairs[6] = new Pair<View,String>(callSignup,"login_signup_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(login.this,pairs);
                startActivity(intent,options.toBundle());

            }
        });
    }


    private Boolean validateUsername(){
        String val = username.getEditText().getText().toString();


        if(val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        }
        else{
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean validatePassword(){
        String val = password.getEditText().getText().toString();
        String passwordVal = "^"+
                "(?=.*[a-zA-Z])"+
                "(?=.[@#$%^&+=])"+
                "(?=\\s+$)"+
                ".{4,}"+
                "$";
        if(val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        }

        else{
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }


    }

    public void  loginUser(View view){
        if(!validateUsername() | !validatePassword()){
            return;

        }
        else{
            isUser();
            bar.setVisibility(View.VISIBLE);
        }
    }
    private void isUser() {

        final String userEnteredUsername  = username.getEditText().getText().toString().trim();
        final String userEnteredPassword  = password.getEditText().getText().toString().trim();

        DatabaseReference  reference =  FirebaseDatabase.getInstance().getReference("users");

        Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    username.setError(null);
                    username.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)) {

                        username.setError(null);
                        username.setErrorEnabled(false);


                        String nameFromDB = dataSnapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phoneNoFromDB = dataSnapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(userEnteredUsername).child("email").getValue(String.class);





                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                        intent.putExtra("name", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNo", phoneNoFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);

                    } else {
                        password.setError("Wrong Password");
                        password.requestFocus();

                    }
                }
                    else{
                        username.setError("No such User exist");
                        username.requestFocus();
                    }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }





}
