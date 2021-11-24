package com.devkh.chhayanotes.data.model.local;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.devkh.chhayanotes.utils.AppConstants;

import java.io.Serializable;
import java.sql.Timestamp;


@Entity(tableName = AppConstants.TB_NOTES)
public class Note implements Serializable {

    public Note() {}

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    private Integer noteId;

    @ColumnInfo(name = "title")
    private String noteTitle;

    @ColumnInfo(name = "content")
    private String noteContent;

    @ColumnInfo(name = "saved_date")
    private Long noteSavedDate;

    public Integer getNoteId() {
        return noteId;
    }

    public void setNoteId(Integer noteId) {
        this.noteId = noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Long getNoteSavedDate() {
        return noteSavedDate;
    }

    public void setNoteSavedDate(Long noteSavedDate) {
        this.noteSavedDate = noteSavedDate;
    }

    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                "noteId=" + noteId +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteContent='" + noteContent + '\'' +
                ", noteSavedDate=" + noteSavedDate +
                '}';
    }

}
