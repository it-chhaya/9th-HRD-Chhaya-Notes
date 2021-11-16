package com.devkh.chhayanotes.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.model.local.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvNote;
    private MainViewModel mMainViewModel;
    private List<Note> dataSet;
    private NoteAdapter noteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init RecyclerView Note
        rcvNote = findViewById(R.id.rcv_note);

        // init view model
        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        mMainViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Log.i("TAG", "onChanged: " + notes);
                noteAdapter.setDataSet(notes);
            }
        });

        // create layout manager obj
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);

        rcvNote.setLayoutManager(layoutManager);

        // create adapter obj
        dataSet = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, dataSet);
        rcvNote.setAdapter(noteAdapter);

        Note note = new Note();
        note.setNoteTitle("New note qwer qwer qwer");
        note.setNoteContent("Hello qwer qwer qwer");
        note.setNoteSavedDate(System.currentTimeMillis());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMainViewModel.createNewNote(note);
            }
        }, 10000);

    }
}