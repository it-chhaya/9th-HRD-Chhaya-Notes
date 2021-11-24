package com.devkh.chhayanotes.ui.note;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.local.NoteRepositoryImpl;
import com.devkh.chhayanotes.data.model.local.Note;
import com.devkh.chhayanotes.utils.Event;

public class SavedNoteViewModel extends AndroidViewModel {

    public boolean isEditing = false;

    private NoteRepositoryImpl mNoteRepositoryImpl;

    private LiveData<Note> mNote;

    private MutableLiveData<Event<String>> mEventMessage;

    public LiveData<Note> getNote() {
        return mNote;
    }

    public LiveData<Event<String>> getStatusMessage() {
        if (mEventMessage == null)
            mEventMessage = new MutableLiveData<>();
        return mEventMessage;
    }

    public SavedNoteViewModel(@NonNull Application application) {
        super(application);
        mNoteRepositoryImpl = new NoteRepositoryImpl(application);
        mNote = mNoteRepositoryImpl.getObservableSavedNote();

    }

    public void createNewNote(Note note) {
        if (isTitleValid(note.getNoteTitle())) {
            mNoteRepositoryImpl.createNewNote(note);
        }
    }

    public void editNote(Note note) {
        if (isTitleValid(note.getNoteTitle()))
            mNoteRepositoryImpl.editNote(note);
    }

    public void deleteNote(Note note) {
        if (note != null)
            mNoteRepositoryImpl.deleteNote(note);
    }

    public boolean isTitleValid(String title) {
        if (TextUtils.isEmpty(title)) {
            mEventMessage.setValue(new Event<>(getApplication().getString(R.string.invalid_title)));
            return false;
        }
        return true;
    }

}
