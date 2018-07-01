package com.gbolissimo.androidnotes;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    //UI controls
    private Button mSignin, mSignup, mForgotPassword;
    private EditText mEmail, mPassword;
    private ProgressDialog mProgress;

    //Firebase Auth class
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //UI components
        mSignin = (Button) findViewById(R.id.signin);
        mSignup = (Button) findViewById(R.id.signup);
        mForgotPassword = (Button) findViewById(R.id.forgot);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

                //OnClickListeners
        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Signin();
            }
        });

        mSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signup = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(signup);

            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent forgot = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(forgot);

            }
        });


    }

    private void Signin() {

        //Getting Email and password from users
        String email = mEmail.getText().toString().trim();
        String password = mPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(LoginActivity.this,"Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)){
            Toast.makeText(LoginActivity.this,"Please enter Password", Toast.LENGTH_LONG).show();
            return;
        }

        //If email and password provided display progress dialog
        mProgress.setMessage("Signing in please wait.....");
        mProgress.show();


        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Successfully signed in", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,"Error! Please check your connection.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
