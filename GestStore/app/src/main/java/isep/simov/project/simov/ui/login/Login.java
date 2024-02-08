package isep.simov.project.simov.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import isep.simov.project.simov.MainActivity;
import isep.simov.project.simov.R;
import isep.simov.project.simov.ui.db.ItemsBD;
import isep.simov.project.simov.ui.db.StoreManagementBD;
import isep.simov.project.simov.ui.items.Items;
import isep.simov.project.simov.ui.items.ItemsDetail;

public class Login extends AppCompatActivity {


    EditText et_mail;
    EditText et_pass;
    Button loginBtn;

    public ProgressBar mProgressBar;

    public void setProgressBar(ProgressBar progressBar) {
        mProgressBar = progressBar;
    }

    public void showProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    private static final String TAG = "Login";

    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        mAuth = FirebaseAuth.getInstance();

        et_mail = findViewById(R.id.editTextTextEmailAddress);
        et_pass = findViewById(R.id.editTextTextPassword);
        loginBtn = findViewById(R.id.login);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String email = et_mail.getText().toString().trim();
                String password = et_pass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    et_mail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)){
                    et_pass.setError("Password is Required");
                    return;
                }

                showProgressBar();

                signIn(email, password);
            }
        });
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void signIn(String email, String password){
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressBar();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            hideProgressBar();
                        }

                        // ...
                    }
                });
    }

    @Override
    public void onBackPressed() {
        // empty so nothing happens
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = et_mail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            et_mail.setError("Required.");
            valid = false;
        } else {
            et_mail.setError(null);
        }

        String password = et_pass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            et_pass.setError("Required.");
            valid = false;
        } else {
            et_pass.setError(null);
        }

        return valid;
    }

    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.login) {
            signIn(et_mail.getText().toString(), et_pass.getText().toString());
        }
    }
}
