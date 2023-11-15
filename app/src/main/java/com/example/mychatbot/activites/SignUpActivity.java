package com.example.mychatbot.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.example.mychatbot.MainActivity;
import com.example.mychatbot.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        binding.txtSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        binding.btnSignUp.setOnClickListener(v -> {
            String fullName = Objects.requireNonNull(binding.edtSignUpFullName.getText()).toString();
            String strEmail = Objects.requireNonNull(binding.edtSignUpEmail.getText()).toString().trim();
            String strMobile = Objects.requireNonNull(binding.edtSignUpMobile.getText()).toString().trim();
            String strPass = Objects.requireNonNull(binding.edtSignUpPassword.getText()).toString();
            String strConfirmPass = Objects.requireNonNull(binding.edtSignUpConfirmPassword.getText()).toString();

            if (isValidate(fullName, strEmail, strMobile, strPass, strConfirmPass)) {
                signUp(fullName, strEmail, strMobile, strPass);
            }
        });
    }

    private void signUp(String fullName, String strEmail, String strMobile, String strPass) {
        binding.btnSignUp.setVisibility(View.INVISIBLE);
        binding.signUpProgressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(strEmail, strPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String, Object> user = new HashMap<>();
                user.put("FullName", fullName);
                user.put("Email", strEmail);
                user.put("Mobile", strMobile);

                db.collection("Users")
                        .document(strEmail)
                        .set(user)
                        .addOnSuccessListener(unused -> {
                            Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                binding.btnSignUp.setVisibility(View.VISIBLE);
                                binding.signUpProgressBar.setVisibility(View.INVISIBLE);
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this, "Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                binding.btnSignUp.setVisibility(View.VISIBLE);
                binding.signUpProgressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private boolean isValidate(String fullName, String strEmail, String strMobile, String strPass, String strConfirmPass) {
        if (TextUtils.isEmpty(fullName)) {
            binding.edtSignUpFullName.setError("Ismingizni kiriting !");
            return false;
        }

        if (TextUtils.isEmpty(strEmail)) {
            binding.edtSignUpEmail.setError("Iltimos email kiriting !");
            return false;
        }
        if (!strEmail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            binding.edtSignUpEmail.setError("Iltimos to'g'ri email kiriting !");
            return false;
        }
        if (TextUtils.isEmpty(strMobile)) {
            binding.edtSignUpMobile.setError("Iltimos raqamingizni kiriting !");
            return false;
        }
        if (TextUtils.isEmpty(strPass)) {
            binding.edtSignUpPassword.setError("Iltimos parol kiriting !");
            return false;
        }
        if (TextUtils.isEmpty(strConfirmPass)) {
            binding.edtSignUpConfirmPassword.setError("Iltimos tasdiqlovchi parolni kiriting !");
            return false;
        }
        if (!strPass.equals(strConfirmPass)) {
            binding.edtSignUpConfirmPassword.setError("Parol bir biriga mos kelmadi !");
            return false;
        }
        return true;
    }
}
