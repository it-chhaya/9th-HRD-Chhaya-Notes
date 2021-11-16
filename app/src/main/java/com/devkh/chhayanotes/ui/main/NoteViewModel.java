package com.devkh.chhayanotes.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.devkh.chhayanotes.data.local.NoteRepositoryImpl;
import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepositoryImpl noteRepositoryImpl;
    private MutableLiveData<List<Note>> liveData;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepositoryImpl = new NoteRepositoryImpl(application.getApplicationContext());
        liveData = noteRepositoryImpl.findNotes();
    }

    public MutableLiveData<List<Note>> getLiveData() {
        return liveData;
    }

}
