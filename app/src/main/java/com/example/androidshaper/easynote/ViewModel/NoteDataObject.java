package com.example.androidshaper.easynote.ViewModel;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NoteDataObject {

    @PrimaryKey(autoGenerate = true)
    int id;
    String noteTitle;
    String noteDescription;
    String noteDate;
    boolean noteLike;

    public NoteDataObject( String noteTitle, String noteDescription, String noteDate, boolean noteLike) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.noteDate = noteDate;
        this.noteLike = noteLike;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    public boolean isNoteLike() {
        return noteLike;
    }

    public void setNoteLike(boolean noteLike) {
        this.noteLike = noteLike;
    }
}
