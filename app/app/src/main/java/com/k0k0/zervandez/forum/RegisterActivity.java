package com.k0k0.zervandez.forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Allows the user to create an account via their email address.
 *
 * @author Ariel Halilaj
 * @since  10.12.2020
 *
 */

public class RegisterActivity extends AppCompatActivity {

    private EditText emailET, passET;
    private Button registerBtn;

    private FirebaseAuth firebaseAuth;     //  we declare an instance of Firebase Auth

    /**
     * Interact with the Register-interface.
     * <p> The Main components of the Register-interface are:
     * <ul>
     *  <li> A block for the user's email address
     *  <li> A block for the password
     *  <li> A button to finish the registration
     * </ul>
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailET = findViewById(R.id.reg_emailEditText);
        passET = findViewById(R.id.reg_editTextPassword);
        registerBtn = findViewById(R.id.register_button);

        firebaseAuth = FirebaseAuth.getInstance();      // we initialise the instance

        registerBtn.setOnClickListener(new View.OnClickListener() {
            /**
             * Get the required information from the user (email, password).
             * <p> If the user hasn't filled the email block, he/she will get a notification
             * <p> If the user hasn't filled the password block, he/she will get a notification
             * <p> If the two required information have been given, they will be passed to the registerUser(email, pass)-Method to perform the authentication
             * @param view
             */
            @Override
            public void onClick(View view) {
                String emailStr = emailET.getText().toString().trim();
                String passStr = passET.getText().toString();
                if (TextUtils.isEmpty(emailStr)) {
                    Toast.makeText(getApplicationContext(), "PLEASE ENTER EMAIL", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(emailStr)) {
                    Toast.makeText(getApplicationContext(), "PASSWORD PLEASE", Toast.LENGTH_LONG).show();
                } else {
                    registerUser(emailStr, passStr);
                }
            }
        });
    }

    /**
     * Perform the authentication.
     * <p>
     *     If the user has provided correct information, the registration process will be successful and he/she will be directed to the Login-interface
     *     so that he/she can login to the system
     * <p>
     *     If the user has provided false information, the registration process will be failed and he/she will stay in the Register-Interface until he/she
     *     provides correct information
     *
     * @param email The email address of the user
     * @param pass  A Password
     */

    private void registerUser(String email, String pass) {
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "LOGED IN", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();       // we type finish so the user can't come back by hitting 'back'
                }
                else Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_LONG).show();
            }
        });
    }
}