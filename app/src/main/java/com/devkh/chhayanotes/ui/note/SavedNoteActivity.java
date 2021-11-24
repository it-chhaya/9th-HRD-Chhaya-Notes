package com.devkh.chhayanotes.ui.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.model.local.Note;
import com.devkh.chhayanotes.ui.main.MainActivity;
import com.devkh.chhayanotes.utils.KeyboardUtils;
import com.google.android.material.appbar.MaterialToolbar;

public class SavedNoteActivity extends AppCompatActivity {

    private final static String TAG = SavedNoteActivity.class.getName();

    private MaterialToolbar mToolbar;
    private EditText mEditTextTitle;
    private EditText mEditTextContent;

    private SavedNoteViewModel mSavedNoteViewModel;

    private boolean mIsEditingAction;
    private boolean mIsCreateAction;

    private Note mNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_note);

        mEditTextTitle = findViewById(R.id.edit_text_note_title);
        mEditTextContent = findViewById(R.id.edit_text_note_content);

        onInitToolbar();

        mSavedNoteViewModel = new ViewModelProvider(this).get(SavedNoteViewModel.class);
        subscriberLiveData();

        // Invoke method to check user focus
        checkUserFocus();

        // Invoke on new intent come
        onNewIntent(getIntent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        if (mIsEditingAction) {
            inflater.inflate(R.menu.menu_saved_note, menu);
        } else {
            inflater.inflate(R.menu.menu_delete_note, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                if (mIsCreateAction) {
                    createNote();
                } else {
                    editNote();
                }
                return true;
            case R.id.menu_item_delete:
                deleteNote();
                return true;
            default:
                return false;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent: " + intent);
        if (intent.hasExtra(MainActivity.EXTRA_NOTE)) {
            mNote = (Note) intent.getSerializableExtra(MainActivity.EXTRA_NOTE);
            mEditTextTitle.setText(mNote.getNoteTitle());
            mEditTextContent.setText(mNote.getNoteContent());
            mIsCreateAction = false;
        } else {
            // Request focus on edit text title
            mEditTextTitle.requestFocus();
            // Show keyboard
            KeyboardUtils.showKeyboard(this);

            mIsCreateAction = true;
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

    private void subscriberLiveData() {
        mSavedNoteViewModel.getNote().observe(this,
                new Observer<Note>() {
                    @Override
                    public void onChanged(Note note) {
                        if (note != null) {
                            KeyboardUtils.hideKeyboard(SavedNoteActivity.this);
                            clearInputFocus();

                            mIsEditingAction = false;

                            invalidateOptionsMenu();

                            mNote = note;
                            mIsCreateAction = false;
                        }
                    }
                });
    }

    private void clearInputFocus() {
        mEditTextTitle.clearFocus();
        mEditTextContent.clearFocus();
    }

    private void checkUserFocus() {
        mEditTextTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused)
                    mIsEditingAction = true;
                invalidateOptionsMenu();
            }
        });
        mEditTextContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused)
                    mIsEditingAction = true;
                invalidateOptionsMenu();
            }
        });
    }

    private void createNote() {
        mNote = new Note();
        mNote.setNoteTitle(mEditTextTitle.getText().toString());
        mNote.setNoteContent(mEditTextContent.getText().toString());
        mNote.setNoteSavedDate(System.currentTimeMillis());
        mSavedNoteViewModel.createNewNote(mNote);
    }

    private void deleteNote() {
        mSavedNoteViewModel.deleteNote(mNote);
        finish();
    }

    private void editNote() {
        mNote.setNoteTitle(mEditTextTitle.getText().toString());
        mNote.setNoteContent(mEditTextContent.getText().toString());
        mNote.setNoteSavedDate(System.currentTimeMillis());
        mSavedNoteViewModel.editNote(mNote);
    }

}