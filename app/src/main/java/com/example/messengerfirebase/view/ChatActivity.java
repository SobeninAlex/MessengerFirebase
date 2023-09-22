package com.example.messengerfirebase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.model.User;

public class ChatActivity extends AppCompatActivity {

    private static final String USER_EXTRA = "user";

    private TextView textViewTitle;
    private View onlineStatus;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView imageViewSendMessage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();

        var user = (User) getIntent().getSerializableExtra(USER_EXTRA);
        var userInfo = String.format("%s %s, %d", user.getName(), user.getLastNeme(), user.getAge());

        textViewTitle.setText(userInfo);

    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        onlineStatus = findViewById(R.id.onlineStatus);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }

    public static Intent newIntent(Context context, User user) {
        var intent = new Intent(context, ChatActivity.class);
        intent.putExtra(USER_EXTRA, user);
        return intent;
    }
}