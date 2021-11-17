package com.devkh.chhayanotes.ui.note;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.devkh.chhayanotes.data.local.NoteRepositoryImpl;
import com.devkh.chhayanotes.data.model.local.Note;

public class SavedNoteViewModel extends AndroidViewModel {

    private NoteRepositoryImpl mNoteRepositoryImpl;

    private LiveData<Boolean> mIsCreated;

    public LiveData<Boolean> getIsCreated() {
        return mIsCreated;
    }

    public SavedNoteViewModel(@NonNull Application application) {
        super(application);
        mNoteRepositoryImpl = new NoteRepositoryImpl(application);
    }

    public void createNewNote(Note note) {
        mNoteRepositoryImpl.createNewNote(note);
    }

}
