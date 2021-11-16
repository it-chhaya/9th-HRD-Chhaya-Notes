package com.devkh.chhayanotes.data.local;

import androidx.lifecycle.MutableLiveData;

import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public interface NoteRepository {

    MutableLiveData<List<Note>> findNotes();

}
