package com.devkh.chhayanotes.data.local;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {

    private AppDatabase appDatabase;

    public NoteRepositoryImpl(Context context) {
        appDatabase = AppDatabase.getInstance(context);
    }

    @Override
    public MutableLiveData<List<Note>> findNotes() {

        MutableLiveData<List<Note>> liveData = new MutableLiveData<>();

        List<Note> notes = appDatabase.noteDao().select();

        liveData.setValue(notes);

        return liveData;
    }

}
