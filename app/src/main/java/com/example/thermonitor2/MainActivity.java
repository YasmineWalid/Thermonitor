package com.example.thermonitor2;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    FirebaseAuth mAuth;
    EditText editTextEmail,editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword1);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.textViewSignUp).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();




    }
public void userLogin(){




    String email= editTextEmail.getText().toString().trim();
    String password=editTextPassword.getText().toString().trim();
    if (email.isEmpty()){
        editTextEmail.setError("Email is required");
        editTextEmail.requestFocus();
        return;
    }
    if (password.isEmpty()){
        editTextPassword.setError("Password is required");
        editTextPassword.requestFocus();
        return;
    }
    if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
        editTextEmail.setError("Please enter a valid email");
        editTextEmail.requestFocus();
        return;
    }
    if(password.length()<6){
        editTextPassword.setError("Minimum length of password should be 6");
        editTextPassword.requestFocus();
        return;
    }


      mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful()){
                  Intent intent= new Intent(MainActivity.this,ListActivity.class);
                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                  startActivity(intent);
                  finish();

              }
              else {
                  Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
              }
          }
      });
}

    public void onClick(View view){
        switch (view.getId()){
            case R.id.textViewSignUp:
                startActivity(new Intent(this,RegisterActivity.class));
                finish();
                break;

            case R.id.buttonLogin:
                userLogin();

                break;
        }



    }
}
