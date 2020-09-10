package com.example.androidshaper.easynote.RoomDbService;


import com.example.androidshaper.easynote.ViewModel.NoteDataObject;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert
    public void noteInsert(NoteDataObject noteDataObject);

    @Query("select * From NoteDataObject ")
    LiveData<List<NoteDataObject>> getAllNote();

    @Query("select * From NoteDataObject WHERE noteLike LIKE :status")
    LiveData<List<NoteDataObject>> getAllFavoriteNote(boolean status);

    @Delete
    public void noteDelete(NoteDataObject noteDataObject);

    @Update
    public void noteUpdate(NoteDataObject noteDataObject);

}
