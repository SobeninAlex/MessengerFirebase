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
import android.widget.Toast;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.viewModel.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity {

    private static final String EXTRA_EMAIL = "email";

    private EditText editTextEmailReset;
    private Button buttonResetPassword;

    private ResetPasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        initViews();
        userEmail();
        setupClickListeners();
        observeViewModel();
    }

    private void setupClickListeners() {
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var email = editTextEmailReset.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            R.string.write_email_field,
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else {
                    viewModel.resetPassword(email);
                }
            }
        });
    }

    private void observeViewModel() {
        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String message) {
                if (message != null) {
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            message,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        viewModel.getIsSuccess().observe(this, isSuccess -> {
            if (isSuccess) {
                startActivity(LoginActivity.newIntent(this));
                finish();
            }
        });
    }

    private void userEmail() {
        var userEmail = getIntent().getStringExtra(EXTRA_EMAIL);
        editTextEmailReset.setText(userEmail);
    }

    public static Intent newIntent(Context context, String email) {
        var intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra(EXTRA_EMAIL, email);
        return intent;
    }

    public void initViews() {
        editTextEmailReset = findViewById(R.id.editTextEmailReset);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
    }
}