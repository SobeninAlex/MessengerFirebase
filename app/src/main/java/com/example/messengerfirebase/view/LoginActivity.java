package com.example.messengerfirebase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.viewModel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textViewForgotPassword;
    private TextView textViewRegister;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        setupClickListeners();
    }

    private void setupClickListeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var email = editTextEmail.getText().toString().trim();
                var password = editTextPassword.getText().toString().trim();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, R.string.write_in_all_fields, Toast.LENGTH_SHORT).show();
                }
                else {
                    viewModel.login(email, password);
                }
            }
        });

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var email = editTextEmail.getText().toString().trim();
                var intent = ResetPasswordActivity.newIntent(LoginActivity.this, email);
                startActivity(intent);
            }
        });

        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var intent = RegistrationActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });
    }

    private void observeViewModel() {
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    var intent = UsersActivity.newIntent(LoginActivity.this, firebaseUser.getUid());
                    startActivity(intent);
                    finish();

                    //метод finish() убивает текущюю активити
                    //в данном случае LoginActivity
                    //при клике по кнопке назад на телефоне, этой активити уже не существует, приложение свернется
                }
            }
        });

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null) {
                    Toast.makeText(
                            LoginActivity.this,
                            message,
                            Toast.LENGTH_LONG
                    ).show();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    private void initViews() {
        editTextEmail = findViewById(R.id.editTextEmailReg);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
    }
}