package com.devkh.chhayanotes.ui.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.model.local.Note;
import com.google.android.material.appbar.MaterialToolbar;

public class SavedNoteActivity extends AppCompatActivity {

    private final static String TAG = SavedNoteActivity.class.getName();

    private MaterialToolbar mToolbar;
    private EditText mEditTextTitle;
    private EditText mEditTextContent;

    private SavedNoteViewModel mSavedNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_note);

        mEditTextTitle = findViewById(R.id.edit_text_note_title);
        mEditTextContent = findViewById(R.id.edit_text_note_content);

        onInitToolbar();

        mSavedNoteViewModel = new ViewModelProvider(this).get(SavedNoteViewModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_saved_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                Note note = new Note();
                note.setNoteTitle(mEditTextTitle.getText().toString());
                note.setNoteContent(mEditTextContent.getText().toString());
                note.setNoteSavedDate(System.currentTimeMillis());
                mSavedNoteViewModel.createNewNote(note);
                return true;
            default:
                return false;
        }
    }

    private void onInitToolbar() {
        mToolbar = findViewById(R.id.saved_note_toolbar);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("");

        mToolbar.setNavigationIcon(R.drawable.ic_baseline_keyboard_backspace_24);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}