package com.example.mychatbot.activites;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import com.example.mychatbot.databinding.ActivityLoginBinding;
import com.example.mychatbot.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    private String emailPattern = "[a-zA-Z0-9_-]+@[a-z]+\\.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.txtSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

        binding.txtForgotPassword.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnSignIn.setOnClickListener(v -> {
            String strEmail = binding.edtSignInEmail.getText().toString().trim();
            String strPassword = binding.edtSignInPassword.getText().toString().trim();

            if (isValidate(strEmail, strPassword)) {
                signIn(strEmail, strPassword);
            }
        });
    }

    private void signIn(String email, String password) {
        binding.btnSignIn.setVisibility(View.INVISIBLE);
        binding.signInProgressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }).addOnFailureListener(e -> {
                    binding.btnSignIn.setVisibility(View.VISIBLE);
                    binding.signInProgressBar.setVisibility(View.INVISIBLE);
                    // Handle failure
                });
    }

    private boolean isValidate(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            binding.edtSignInEmail.setError("Iltimos email kiriting!");
            return false;
        }

        if (!email.matches(emailPattern)) {
            binding.edtSignInEmail.setError("Iltimos to'g'ri email kiriting!");
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            binding.edtSignInPassword.setError("Parolni yozing!");
            return false;
        }

        return true;
    }
}
