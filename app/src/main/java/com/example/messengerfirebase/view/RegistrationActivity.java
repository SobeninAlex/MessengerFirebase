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
import com.example.messengerfirebase.viewModel.RegistrationViewModel;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextEmailReg;
    private EditText editTextPasswordReg;
    private EditText editTextNameReg;
    private EditText editTextLastNameReg;
    private EditText editTextAgeReg;
    private Button buttonSignUp;

    private RegistrationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initViews();
        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        setupClickListener();
        observeViewModel();
    }

    private void setupClickListener() {
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var email = getTrimmedValue(editTextEmailReg);
                var password = getTrimmedValue(editTextPasswordReg);
                var name = getTrimmedValue(editTextNameReg);
                var lastName = getTrimmedValue(editTextLastNameReg);
                var age = getTrimmedValue(editTextAgeReg);
                if (email.isEmpty() || password.isEmpty() || name.isEmpty() || lastName.isEmpty() || age.isEmpty()) {
                    Toast.makeText(
                            RegistrationActivity.this,
                            "All fields is required",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                else {
                    viewModel.registration(email, password, name, lastName, Integer.parseInt(age));
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
                            RegistrationActivity.this,
                            message,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
        viewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser user) {
                if (user != null) {
                    var intent = UsersActivity.newIntent(RegistrationActivity.this);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, RegistrationActivity.class);
    }

    private void initViews() {
        editTextEmailReg = findViewById(R.id.editTextEmailReg);
        editTextPasswordReg = findViewById(R.id.editTextPasswordReg);
        editTextNameReg = findViewById(R.id.editTextNameReg);
        editTextLastNameReg = findViewById(R.id.editTextLastNameReg);
        editTextAgeReg = findViewById(R.id.editTextAgeReg);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }

    private String getTrimmedValue(EditText editText) {
        return editText.getText().toString();
    }
}