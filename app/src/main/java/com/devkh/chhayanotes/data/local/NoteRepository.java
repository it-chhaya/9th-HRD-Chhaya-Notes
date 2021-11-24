package com.devkh.chhayanotes.data.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public interface NoteRepository {

    LiveData<List<Note>> findAllNotes();

    void createNewNote(Note note);

    void deleteNote(Note note);

    void editNote(Note note);

    LiveData<List<Note>> searchNotesByTitle(String title);

}
