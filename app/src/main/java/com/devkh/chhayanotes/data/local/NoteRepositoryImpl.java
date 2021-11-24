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
    private MediatorLiveData<Note> mObservableSavedNote;

    // Expose live data for UI subscribe
    public LiveData<List<Note>> getObservableNotes() {
        return mObservableNotes;
    }

    public LiveData<Note> getObservableSavedNote() {
        if (mObservableSavedNote == null)
            mObservableSavedNote = new MediatorLiveData<>();
        return mObservableSavedNote;
    }

    public NoteRepositoryImpl(Application application) {
        AppDatabase appDb = AppDatabase.getInstance(application);
        mNoteDao = appDb.noteDao();

        // init observableNotes
        mObservableNotes = new MediatorLiveData<>();

        // add data into observable notes
        mObservableNotes.addSource(mNoteDao.select(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                mObservableNotes.setValue(notes);
            }
        });
    }

    @Override
    public LiveData<List<Note>> findAllNotes() {
        mObservableNotes.addSource(mNoteDao.select(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                mObservableNotes.setValue(notes);
            }
        });
        return mObservableNotes;
    }

    @Override
    public void createNewNote(Note note) {
        int newId = (int) mNoteDao.insert(note);
        if (mObservableSavedNote == null)
            mObservableSavedNote = new MediatorLiveData<>();
        mObservableSavedNote.addSource(mNoteDao.select(newId), new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                mObservableSavedNote.setValue(note);
            }
        });
    }

    @Override
    public void editNote(Note note) {
        mNoteDao.update(note);
        mObservableSavedNote.addSource(mNoteDao.select(note.getNoteId()), new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                mObservableSavedNote.setValue(note);
            }
        });
    }

    @Override
    public void deleteNote(Note note) {
        mNoteDao.delete(note);
        mObservableSavedNote.setValue(null);
    }

    @Override
    public LiveData<List<Note>> searchNotesByTitle(String title) {
        mObservableNotes.addSource(mNoteDao.select(title), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                mObservableNotes.setValue(notes);
            }
        });
        return mObservableNotes;
    }
}
