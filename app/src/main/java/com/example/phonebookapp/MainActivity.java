package com.example.phonebookapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.phonebookapp.databinding.ActivityMainBinding;
import com.google.firebase.BuildConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> users;
    private MyAdapter userAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        // Enable StrictMode for debugging
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }
        // Initialize Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        // Initialize RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users = new ArrayList<>();
        userAdapter = new MyAdapter(this, users);
        binding.recyclerView.setAdapter(userAdapter);

        // Show loading indicator
        binding.loading.setVisibility(View.VISIBLE);

        // Fetch data from Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
          //  @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<User> newUsers =  new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        newUsers.add(user);
                    }else{
                        Log.w("MainActivity", "Null user at key: " + dataSnapshot.getKey());
                    }
                }
                userAdapter.notifyDataSetChanged();
                binding.loading.setVisibility(View.GONE); // Hide loading
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("MainActivity", "Firebase error: " + error.getMessage());
                binding.loading.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Handle window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}