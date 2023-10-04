package com.example.messengerfirebase.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.messengerfirebase.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersViewModel extends AndroidViewModel {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference usersReferance;

    private final MutableLiveData<FirebaseUser> user = new MutableLiveData<>();
    private final MutableLiveData<List<User>> userList = new MutableLiveData<>();

    public UsersViewModel(@NonNull Application application) {
        super(application);
        auth = FirebaseAuth.getInstance();
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user.setValue(firebaseAuth.getCurrentUser());
            }
        });
        database = FirebaseDatabase.getInstance();
        usersReferance = database.getReference("Users");
        usersReferance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser curentUser = auth.getCurrentUser(); //юзер под которым авторизовались
                if (curentUser == null) return;
                List<User> usersFromDB = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    var user = dataSnapshot.getValue(User.class); //очередной юзер из БД
                    if (user == null) return;
                    if (!user.getId().equals(curentUser.getUid())) {
                        usersFromDB.add(user);
                    }
                }
                userList.setValue(usersFromDB);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("UsersViewModel_TAG", error.getMessage());
            }
        });
    }

    public void setUserOnline(boolean isOnline) {
        var currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            usersReferance.child(currentUser.getUid()).child("online").setValue(isOnline);
        }
    }

    public LiveData<List<User>> getUserList() {
        return userList;
    }

    public LiveData<FirebaseUser> getUser() {
        return user;
    }

    public void logout() {
        setUserOnline(false);
        auth.signOut();
    }

}
