package com.example.messengerfirebase.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.messengerfirebase.model.Message;
import com.example.messengerfirebase.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatViewModel extends ViewModel {

    private MutableLiveData<List<Message>> messagesList = new MutableLiveData<>();
    private MutableLiveData<Boolean> messageSend = new MutableLiveData<>();
    private MutableLiveData<String> errorMessage = new MutableLiveData<>();
    private MutableLiveData<User> otherUser = new MutableLiveData<>();

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference referenceUser = database.getReference("Users");
    private DatabaseReference referenceMessages = database.getReference("Messages");

    private String currentUserID;
    private String otherUserID;

    public ChatViewModel(String currentUserID, String otherUserID) {
        this.currentUserID = currentUserID;
        this.otherUserID = otherUserID;

        //TODO -> возможно не пригодится
        referenceUser.child(otherUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                var user = snapshot.getValue(User.class);
                otherUser.setValue(user);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //

        referenceMessages.child(currentUserID).child(otherUserID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Message> messages = new ArrayList<>();
                for ( DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    var message = dataSnapshot.getValue(Message.class);
                    messages.add(message);
                }
                messagesList.setValue(messages);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setUserOnline(boolean isOnline) {
        referenceUser.child(currentUserID).child("online").setValue(isOnline);
    }

    public void sendMessage(Message message) {
        //сохраняем сообщение в БД у отправителя и у получателя
        referenceMessages
                .child(message.getSenderID())
                .child(message.getReceiverID())
                .push()
                .setValue(message)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        referenceMessages
                                .child(message.getReceiverID())
                                .child(message.getSenderID())
                                .push()
                                .setValue(message)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        messageSend.setValue(true);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        errorMessage.setValue(e.getMessage());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        errorMessage.setValue(e.getMessage());
                    }
                });
    }

    public LiveData<User> getOtherUser() {
        return otherUser;
    }
    public LiveData<List<Message>> getMessagesList() {
        return messagesList;
    }
    public LiveData<Boolean> getMessageSend() {
        return messageSend;
    }
    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

}
