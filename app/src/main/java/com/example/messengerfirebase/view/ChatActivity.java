package com.example.messengerfirebase.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.messengerfirebase.R;
import com.example.messengerfirebase.adapters.MessagesAdapter;
import com.example.messengerfirebase.model.Message;
import com.example.messengerfirebase.model.User;
import com.example.messengerfirebase.viewModel.ChatViewModel;
import com.example.messengerfirebase.viewModel.ChatViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final String USER_EXTRA = "user";
    private static final String EXTRA_CURRENT_USER_ID = "currentUserID";

    private TextView textViewTitle;
    private View onlineStatus;
    private RecyclerView recyclerViewMessages;
    private EditText editTextMessage;
    private ImageView imageViewSendMessage;

    private MessagesAdapter adapter;

    private String currentUserID;
    private User otherUser;

    private ChatViewModel viewModel;
    private ChatViewModelFactory viewModelFactory;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initViews();

        otherUser = (User) getIntent().getSerializableExtra(USER_EXTRA);
        currentUserID = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);

        viewModelFactory = new ChatViewModelFactory(currentUserID, otherUser.getId());
        viewModel = new ViewModelProvider(this, viewModelFactory).get(ChatViewModel.class);

        adapter = new MessagesAdapter(currentUserID);
        recyclerViewMessages.setAdapter(adapter);

        observeViewModel();
        imageViewSendMessage.setOnClickListener(view -> {
            var textMessage = editTextMessage.getText().toString().trim();
            if (!textMessage.isEmpty()) {
                var message = new Message(textMessage, currentUserID, otherUser.getId());
                viewModel.sendMessage(message);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.setUserOnline(false);
    }



    private void observeViewModel() {
        viewModel.getMessagesList().observe(this, messages -> {
            adapter.setMessages(messages);
        });

        viewModel.getErrorMessage().observe(this, errorMessage -> {
            if (errorMessage != null) {
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getMessageSend().observe(this, isSend -> {
            if (isSend) {
                editTextMessage.setText("");
            }
        });

        viewModel.getOtherUser().observe(this, user -> {
            var userInfo = String.format("%s %s, %d", user.getName(), user.getLastNeme(), user.getAge());
            textViewTitle.setText(userInfo);

            int backgroundID;
            if (user.isOnline()) {
                backgroundID = R.drawable.circle_green;
            }
            else {
                backgroundID = R.drawable.circle_red;
            }
            var drawable = ContextCompat.getDrawable(this, backgroundID);
            onlineStatus.setBackground(drawable);
        });

    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        onlineStatus = findViewById(R.id.onlineStatus);
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }

    public static Intent newIntent(Context context, User user, String currentUserID) {
        var intent = new Intent(context, ChatActivity.class);
        intent.putExtra(USER_EXTRA, user);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserID);
        return intent;
    }
}