package com.devkh.chhayanotes.ui.note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.model.local.Note;
import com.devkh.chhayanotes.utils.Event;
import com.devkh.chhayanotes.utils.KeyboardUtils;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class SavedNoteActivity extends AppCompatActivity {

    private final static String TAG = SavedNoteActivity.class.getName();

    private MaterialToolbar mToolbar;
    private EditText mEditTextTitle;
    private EditText mEditTextContent;

    private SavedNoteViewModel mSavedNoteViewModel;

    private Note mNote;

    private boolean mIsEditAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_note);

        mEditTextTitle = findViewById(R.id.edit_text_note_title);
        mEditTextContent = findViewById(R.id.edit_text_note_content);

        onInitToolbar();

        /*
        * Init view model
        * */
        mSavedNoteViewModel = new ViewModelProvider(this).get(SavedNoteViewModel.class);
        /*
        * Subscribe to live data
        * */
        subscribeToLiveData();

        /*
        * Subscribe message event
        * */
        subscribeMessageEvent();

        /*
        * On new intent
        * */
        onNewIntent(getIntent());

        /*
        * Check user action focus or not
        * */
        checkUserActions();

        // Request focus on edit title
        mEditTextTitle.requestFocus();
        // Show keyboard
        KeyboardUtils.showKeyboard(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        MenuInflater inflater = getMenuInflater();
        if (mSavedNoteViewModel.isEditing) {
            inflater.inflate(R.menu.menu_saved_note, menu);
        } else {
            inflater.inflate(R.menu.menu_view_note, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save:
                createNewNote();
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
        if (intent.hasExtra("extra_note")) {
            Log.i(TAG, "extra note: " + (Note) intent.getSerializableExtra("extra_note"));
            mIsEditAction = true;
            mNote = (Note) intent.getSerializableExtra("extra_note");
            mEditTextTitle.setText(mNote.getNoteTitle());
            mEditTextContent.setText(mNote.getNoteContent());
        }
    }

    // Feature create new note
    private void createNewNote() {
        String title = mEditTextTitle.getText().toString();
        String content = mEditTextContent.getText().toString();
        if (mIsEditAction) {
            mNote.setNoteTitle(title);
            mNote.setNoteContent(content);
            mNote.setNoteSavedDate(System.currentTimeMillis());
            mSavedNoteViewModel.editNote(mNote);
        } else {
            mNote = new Note();
            mNote.setNoteTitle(title);
            mNote.setNoteContent(content);
            mNote.setNoteSavedDate(System.currentTimeMillis());
            mSavedNoteViewModel.createNewNote(mNote);
        }
    }

    // Feature delete note
    private void deleteNote() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_dialog_content)
                .setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mSavedNoteViewModel.deleteNote(mNote);
                    }
                })
                .setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
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

    private void clearFocus() {
        mEditTextTitle.clearFocus();
        mEditTextContent.clearFocus();
    }

    private void checkUserActions() {
        mEditTextTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mSavedNoteViewModel.isEditing = true;
                invalidateOptionsMenu();
            }
        });
        mEditTextContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mSavedNoteViewModel.isEditing = true;
                invalidateOptionsMenu();
            }
        });
    }

    private void subscribeToLiveData() {
        mSavedNoteViewModel.getNote().observe(this, note -> {
            // Update UI
            if (note != null) {
                KeyboardUtils.hideKeyboard(this);
                clearFocus();
                invalidateOptionsMenu();
                mSavedNoteViewModel.isEditing = false;
            } else {
                finish();
            }
        });
    }

    private void subscribeMessageEvent() {
        mSavedNoteViewModel.getStatusMessage().observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> event) {
                if (event.getContentIfNotHandled() != null)
                    Toast.makeText(SavedNoteActivity.this, event.getContentIfNotHandled(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}