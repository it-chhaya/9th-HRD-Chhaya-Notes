package com.devkh.chhayanotes.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.local.AppDatabase;
import com.devkh.chhayanotes.data.model.local.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvNote;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        // init RecyclerView Note
        rcvNote = findViewById(R.id.rcv_note);

        // create layout manager obj
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);

        rcvNote.setLayoutManager(layoutManager);

        List<Note> dataSet = new ArrayList<>();

        Note note1 = new Note();
        note1.setNoteTitle("My note 1");
        note1.setNoteContent("My note content 1");
        note1.setNoteSavedDate((long)12341234);

        Note note2 = new Note();
        note2.setNoteTitle("My note 2");
        note2.setNoteContent("Lionel Andrés Messi, also known as Leo Messi, is an Argentine professional footballer who plays as a forward for Ligue 1 club Paris Saint-Germain and capta...");
        note2.setNoteSavedDate((long)12341234);

        Note note3 = new Note();
        note3.setNoteTitle("My note 3");
        note3.setNoteContent("Lionel Andrés Messi, also known as Leo Messi, is an Argentine professional footballer who plays as a forward for Ligue 1 club Paris Saint-Germain and capta...");
        note3.setNoteSavedDate((long)12341234);

        dataSet.add(note1);
        dataSet.add(note2);
        dataSet.add(note3);

        // create adapter obj
        NoteAdapter noteAdapter = new NoteAdapter(this, dataSet);
        rcvNote.setAdapter(noteAdapter);

    }
}