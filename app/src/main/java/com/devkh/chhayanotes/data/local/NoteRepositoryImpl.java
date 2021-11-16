package com.devkh.chhayanotes.data.local;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {

    private final NoteDao mNoteDao;

    private MediatorLiveData<List<Note>> mObservableNotes;

    public NoteRepositoryImpl(Application application) {
        AppDatabase mAppDb = AppDatabase.getInstance(application);
        mNoteDao = mAppDb.noteDao();
        mObservableNotes = new MediatorLiveData<>();

        mObservableNotes.addSource(mNoteDao.select(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                mObservableNotes.setValue(notes);
            }
        });

    }

    public LiveData<List<Note>> getNotes() {
        return mObservableNotes;
    }

    @Override
    public LiveData<List<Note>> getAllNotes() {
        return mNoteDao.select();
    }

    @Override
    public void createNewNote(Note note) {
        mNoteDao.insert(note);
    }


}
