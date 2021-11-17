package com.devkh.chhayanotes.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ImageViewCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.local.AppDatabase;
import com.devkh.chhayanotes.data.model.local.Note;
import com.devkh.chhayanotes.ui.note.SavedNoteActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rcvNote;
    private FloatingActionButton mFabCreateNewNote;
    private MaterialToolbar mToolbar;

    private MainViewModel mMainViewModel;
    private List<Note> mDataSet;
    private NoteAdapter mNoteAdapter;

    private final static String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFabCreateNewNote = findViewById(R.id.fab_create_new_note);
        mToolbar = findViewById(R.id.toolbar);

        mNoteAdapter = new NoteAdapter(this);

        // init view model
        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // UI subscribes live data (Observable)
        mMainViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                Log.i(TAG, "onChanged: " + notes);
                mNoteAdapter.setDataSet(notes);
            }
        });

        // init RecyclerView Note
        rcvNote = findViewById(R.id.rcv_note);

        // create layout manager obj
        StaggeredGridLayoutManager layoutManager =
                new StaggeredGridLayoutManager(2,
                        StaggeredGridLayoutManager.VERTICAL);

        rcvNote.setLayoutManager(layoutManager);
        rcvNote.setAdapter(mNoteAdapter);
        
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_item_search:
                        SearchView searchView = (SearchView) item.getActionView();

                        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
                            @Override
                            public void onFocusChange(View view, boolean isFocused) {
                                if (!isFocused)
                                    mMainViewModel.reloadNotes();
                            }
                        });

                        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                Log.i(TAG, "onQueryTextSubmit: ");
                                mMainViewModel.searchNotesByTitle(query);
                                return true;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {
                                Log.i(TAG, "onQueryTextChange: ");
                                mMainViewModel.searchNotesByTitle(newText);
                                return true;
                            }

                        });
                        return true;
                    default:
                        return false;
                }
            }
        });

        // Bind events
        onFabCreateNewNoteClicked();

    }

    private void onFabCreateNewNoteClicked() {
        mFabCreateNewNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SavedNoteActivity.class);
                startActivity(intent);
            }
        });
    }
    
}