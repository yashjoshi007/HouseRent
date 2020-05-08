package com.example.houserent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextInputLayout fullName, email, phoneNo, password;
    TextView fullNameLabel,usernameLabel;

    String  user_username,user_name,user_email,user_phoneNo,user_password;

    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fullName =findViewById(R.id.full_name_profile);
        email =findViewById(R.id.email_profile);
        phoneNo =findViewById(R.id.phone_no_profile);
        password =findViewById(R.id.password_profile);
        fullNameLabel = findViewById(R.id.fullname_field);
        usernameLabel = findViewById(R.id.username_field);

        reference = FirebaseDatabase.getInstance().getReference("users");

        // show all Data
        showAllUserData();


    }

    private void showAllUserData() {

        Intent intent = getIntent();
         user_username = intent.getStringExtra("username");
         user_name = intent.getStringExtra("name");
         user_email = intent.getStringExtra("email");
         user_phoneNo = intent.getStringExtra("phoneNo");
         user_password = intent.getStringExtra("password");

        fullNameLabel.setText(user_name);
        usernameLabel.setText(user_username);
        fullName.getEditText().setText(user_name);
        email.getEditText().setText(user_email);
        phoneNo.getEditText().setText(user_phoneNo);
        password.getEditText().setText(user_password);

    }

    public void update(View view){

        if(isNameChanged() || isPasswordChanged()  ){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();

        }
        else Toast.makeText(this,"Data is same and cannot be updated",Toast.LENGTH_LONG).show();

    }
    private Boolean isPasswordChanged(){
        if(!user_password.equals(password.getEditText().getText().toString()))
        {
            reference.child(user_username).child("password").setValue(password.getEditText().getText().toString());
            user_password=password.getEditText().getText().toString();
            return true;
        }else{
            return false;
        }
    }


    private  Boolean isNameChanged(){
        if(!user_name.equals(fullName.getEditText().getText().toString())){
            reference.child(user_username).child("name").setValue(fullName.getEditText().getText().toString());
            user_name=fullName.getEditText().getText().toString();
            return true;
        }else{
            return false;

    }


}





}
