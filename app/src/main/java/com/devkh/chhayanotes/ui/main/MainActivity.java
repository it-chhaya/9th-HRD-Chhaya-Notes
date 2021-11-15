package com.devkh.chhayanotes.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.local.AppDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        AppDatabase appDatabase = AppDatabase.getInstance(this);
//
//        appDatabase.noteDao().select();

    }
}