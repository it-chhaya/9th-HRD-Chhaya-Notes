package com.devkh.chhayanotes.data.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Query(value = "SELECT * FROM notes ORDER BY id DESC")
    LiveData<List<Note>> select();

    @Query(value = "SELECT * FROM notes WHERE id = :id")
    LiveData<Note> select(Integer id);

    @Query(value = "SELECT * FROM notes WHERE title LIKE '%' || :title || '%'")
    LiveData<List<Note>> select(String title);

    @Insert
    long insert(Note note);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);

}
