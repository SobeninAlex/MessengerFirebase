package com.example.messengerfirebase.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.viewModel.ResetPasswordViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextEmailReset;
    private Button buttonResetPassword;

    private ResetPasswordViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        viewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        initViews();

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var email = editTextEmailReset.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(ResetPasswordActivity.this, "You are stupid", Toast.LENGTH_SHORT).show();
                }
                else {
                    viewModel.resetPassword(email);
                }
            }
        });



        viewModel.getIsSuccess().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(
                        ResetPasswordActivity.this,
                        s,
                        Toast.LENGTH_SHORT
                ).show();
            }
        });



    }

    public static Intent newIntent(Context context) {
        return new Intent(context, ResetPasswordActivity.class);
    }

    public void initViews() {
        editTextEmailReset = findViewById(R.id.editTextEmailReset);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
    }
}