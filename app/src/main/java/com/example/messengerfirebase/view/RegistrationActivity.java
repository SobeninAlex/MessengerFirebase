package com.example.messengerfirebase.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.messengerfirebase.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextEmailReg;
    private EditText editTextPasswordReg;
    private EditText editTextNameReg;
    private EditText editTextLastNameReg;
    private EditText editTextAgeReg;
    private Button buttonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                var email = getTrimmedValue(editTextEmailReg);
                var password = getTrimmedValue(editTextPasswordReg);
                var name = getTrimmedValue(editTextNameReg);
                var lastName = getTrimmedValue(editTextLastNameReg);
                var age = Integer.parseInt(getTrimmedValue(editTextAgeReg));
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