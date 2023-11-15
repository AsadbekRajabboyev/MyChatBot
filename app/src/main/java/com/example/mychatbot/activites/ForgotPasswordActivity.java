package com.example.mychatbot.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mychatbot.R;
import com.example.mychatbot.databinding.ActivityForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ActivityForgotPasswordBinding binding;
    private FirebaseAuth mAuth;
    private String emailPattern = "[a-zA-Z0-9_-]+@[a-z]+\\.[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.btnForgotPasswordBack.setOnClickListener(v -> {
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginOrSignUpActivity.class);
            startActivity(intent);
        });

        binding.btnReset.setOnClickListener(v -> {
            String strEmail = binding.edtForgotPasswordEmail.getText().toString().trim();
            if (isValidate(strEmail)) {
                resetPassword(strEmail);
            }
        });
    }

    private void resetPassword(String email) {
        binding.btnReset.setVisibility(View.INVISIBLE);
        binding.forgetPasswordProgressbar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                Toast.makeText(ForgotPasswordActivity.this, "Reset password link has been forwarded on the registered Email ID!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ForgotPasswordActivity.this, "Error -" + e.getMessage(), Toast.LENGTH_SHORT).show();
                binding.btnReset.setVisibility(View.VISIBLE);
                binding.forgetPasswordProgressbar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private boolean isValidate(String email) {
        if (TextUtils.isEmpty(email)) {
            binding.edtForgotPasswordEmail.setError("Iltimos email kiriting!");
            return false;
        }
        if (!email.matches(emailPattern)) {
            binding.edtForgotPasswordEmail.setError("Iltimos to'g'ri email kiriting!");
            return false;
        }
        return true;
    }
}
