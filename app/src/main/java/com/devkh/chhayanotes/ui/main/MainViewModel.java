package com.devkh.chhayanotes.ui.main;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.devkh.chhayanotes.data.local.NoteRepositoryImpl;
import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Data Repository
    private final NoteRepositoryImpl mNoteRepositoryImpl;

    // LiveData
    private LiveData<List<Note>> mNotes;

    public MainViewModel(@NonNull Application application) {
        super(application);

        // Init data repository
        mNoteRepositoryImpl = new NoteRepositoryImpl(application);

        mNotes = mNoteRepositoryImpl.getNotes();
    }

    // Expose the LiveData Notes, so the UI can observe it.
    public LiveData<List<Note>> getNotes() {
        return mNotes;
    }

    public void createNewNote(Note note) {
        mNoteRepositoryImpl.createNewNote(note);
    }

}
