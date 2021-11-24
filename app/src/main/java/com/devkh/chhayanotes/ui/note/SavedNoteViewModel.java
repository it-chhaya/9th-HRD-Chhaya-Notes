package com.devkh.chhayanotes.ui.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devkh.chhayanotes.data.local.NoteRepositoryImpl;
import com.devkh.chhayanotes.data.model.local.Note;

public class SavedNoteViewModel extends AndroidViewModel {

    private NoteRepositoryImpl mNoteRepositoryImpl;

    private LiveData<Note> mNote;

    public LiveData<Note> getNote() {
        if (mNote == null)
            mNote = mNoteRepositoryImpl.getObservableNote();
        return mNote;
    }

    public SavedNoteViewModel(@NonNull Application application) {
        super(application);
        mNoteRepositoryImpl = new NoteRepositoryImpl(application);
    }

    public void createNewNote(Note note) {
        mNoteRepositoryImpl.createNewNote(note);
        mNote = mNoteRepositoryImpl.getObservableNote();
    }

    public void deleteNote(Note note) {
        mNoteRepositoryImpl.deleteNote(note);
    }

    public void editNote(Note note) {
        mNoteRepositoryImpl.editNote(note);
    }

}
