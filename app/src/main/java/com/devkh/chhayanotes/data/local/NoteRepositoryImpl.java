package com.devkh.chhayanotes.data.local;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public class NoteRepositoryImpl implements NoteRepository {

    private NoteDao mNoteDao;
    private MediatorLiveData<List<Note>> mObservableNotes;
    private MediatorLiveData<Note> mObservableNote;

    // Expose live data for UI subscribe
    public LiveData<List<Note>> getObservableNotes() {
        return mObservableNotes;
    }

    public LiveData<Note> getObservableNote() {
        if (mObservableNote == null)
            mObservableNote = new MediatorLiveData<>();
        return mObservableNote;
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
        int generatedId = (int) mNoteDao.insert(note);
        mObservableNote.addSource(mNoteDao.select(generatedId), new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                mObservableNote.setValue(note);
            }
        });
    }

    @Override
    public void deleteNote(Note note) {
        mNoteDao.delete(note);
    }

    @Override
    public void editNote(Note note) {
        mNoteDao.update(note);
        mObservableNote.addSource(mNoteDao.select(note.getNoteId()), new Observer<Note>() {
            @Override
            public void onChanged(Note note) {
                mObservableNote.setValue(note);
            }
        });
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
