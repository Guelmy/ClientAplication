package com.company.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void AddUser(View view){
        Intent intent = new Intent(MainActivity.this, AddUserActivity.class);
        startActivity(intent);
    }

    public void UpdateUser(View view){
        Intent intent = new Intent(MainActivity.this, UpdateUserActivity.class);
        startActivity(intent);
    }

    public void ListUser(View view){
        Intent intent = new Intent(MainActivity.this, ListUserActivity.class);
        startActivity(intent);
    }

    public void DeleteeUser(View view){
        Intent intent = new Intent(MainActivity.this, DeleteUserActivity.class);
        startActivity(intent);
    }
}