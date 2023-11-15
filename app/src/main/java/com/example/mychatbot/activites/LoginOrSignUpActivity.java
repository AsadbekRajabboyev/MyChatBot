package com.example.mychatbot.activites;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import com.example.mychatbot.databinding.ActivityLoginOrSignUpBinding;

public class LoginOrSignUpActivity extends AppCompatActivity {
    private ActivityLoginOrSignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginOrSignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnMainLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginOrSignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnMainSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginOrSignUpActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
