package com.example.houserent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    TextInputLayout regName,regUsername,regEmail,regPhoneNo,regPassword;
    Button regBtn,regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // hooks
        regName = findViewById(R.id.name);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhoneNo = findViewById(R.id.phoneNo);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.reg_login_btn);



        regToLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this,login.class));

            }
        });


        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                   if (!validateName() | !validateUsername() | !validateEmail() | !validatePhoneNo() | !validatePassword())
                       return;


                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                //get all values
                String name = regName.getEditText().getText().toString();
                String username = regUsername.getEditText().getText().toString();
                String phoneNo =  regPhoneNo.getEditText().getText().toString();
                String email = regEmail.getEditText().getText().toString();
                String password =  regPassword.getEditText().getText().toString();

                Intent intent = new Intent(getApplicationContext(),verify_phone_no.class);
                intent.putExtra("phoneNo",phoneNo);
                startActivity(intent);

                UserHelperClass  helperClass = new UserHelperClass(name,username,phoneNo,email,password);

                reference.child(username).setValue(helperClass);

            }
        });
    }
    private Boolean validateName(){
        String val = regName.getEditText().getText().toString();
        if(val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        }
            else{
                regName.setError(null);
                regName.setErrorEnabled(false);
                return true;
            }

    }

    private Boolean validateUsername(){
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";



        if(val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        }
        else if(val.length()>=15){
            regUsername.setError("Username too long");
            return false;
        }
        else if(! val.matches(noWhiteSpace)){
            regUsername.setError(" White Spaces  are not allowed");
            return false;
        }
        else{
            regUsername.setError(null);
            return true;
        }

    }

    private Boolean validateEmail(){
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        }
        else if(!val.matches(emailPattern)){
            regEmail.setError("Invalid Email address");
            return false;
        }
        else{
            regEmail.setError(null);
            return true;
        }

    }

    private Boolean validatePhoneNo(){
        String val = regPhoneNo.getEditText().getText().toString();
        if(val.isEmpty()) {
            regPhoneNo.setError("Field cannot be empty");
            return false;
        }
        else{
            regPhoneNo.setError(null);
            return true;
        }

    }

    private Boolean validatePassword(){
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^"+
                "(?=.*[a-zA-Z])"+
                "(?=.[@#$%^&+=])"+
                "(?=\\s+$)"+
                ".{4,}"+
                "$";
        if(val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        }

        else{
            regPassword.setError(null);
            return true;
        }

    }

}
