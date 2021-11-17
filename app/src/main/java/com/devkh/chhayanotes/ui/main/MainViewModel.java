package com.devkh.chhayanotes.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.devkh.chhayanotes.data.local.NoteRepositoryImpl;
import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private NoteRepositoryImpl mNoteRepositoryImpl;

    private LiveData<List<Note>> mNotes;

    public LiveData<List<Note>> getNotes() {
        return mNotes;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        mNoteRepositoryImpl = new NoteRepositoryImpl(application);

        mNotes = mNoteRepositoryImpl.getObservableNotes();

    }

    public void reloadNotes() {
        mNoteRepositoryImpl.findAllNotes();
    }

    public void searchNotesByTitle(String title) {
        mNoteRepositoryImpl.searchNotesByTitle(title);
    }

}
