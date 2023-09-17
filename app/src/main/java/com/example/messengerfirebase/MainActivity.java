package com.example.messengerfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTag";

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();


//        TODO -> test user
//        auth.signOut();

//        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = auth.getCurrentUser();
//                if (user == null) {
//                    Log.d(TAG, "Not authorized");
//                }
//                else {
//                    Log.d(TAG, "Authorized " + user.getUid());
//                }
//            }
//        });
//
//        auth.signInWithEmailAndPassword("email@email.com", "123456")
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, e.getMessage());
//                    }
//                });

//                auth.createUserWithEmailAndPassword("email@email.com", "123456")
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        FirebaseUser user = auth.getCurrentUser();
//                        if (user == null) {
//                            Log.d(TAG, "Not authorized");
//                        }
//                        else {
//                            Log.d(TAG, "Authorized");
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, e.getMessage());
//                        Toast.makeText(
//                                MainActivity.this,
//                                e.getMessage(),
//                                Toast.LENGTH_SHORT
//                        ).show();
//                    }
//                });


//        TODO -> sobeninalex@yandex.ru
//        auth.signInWithEmailAndPassword("sobeninalex@yandex.ru", "902021")
//                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                    @Override
//                    public void onSuccess(AuthResult authResult) {
//                        FirebaseUser user = authResult.getUser();
//                        Log.d(TAG, "Authorized " + user.getUid() + " " + user.getEmail());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, e.getMessage());
//                    }
//                });

//        auth.sendPasswordResetEmail("sobeninalex@yandex.ru")
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        FirebaseUser user = auth.getCurrentUser();
//                        Log.d(TAG, "Send on " + user.getEmail());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.d(TAG, e.getMessage());
//                    }
//                });

        auth.createUserWithEmailAndPassword("sobeninalex@yandex.ru", "529440")
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user == null) {
                            Log.d(TAG, "Not authorized");
                        }
                        else {
                            Log.d(TAG, "Authorized " + user.getUid());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, e.getMessage());
                    }
                });

    }
}