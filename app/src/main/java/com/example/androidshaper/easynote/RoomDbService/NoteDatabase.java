package com.example.androidshaper.easynote.RoomDbService;


import android.content.Context;

import com.example.androidshaper.easynote.ViewModel.NoteDataObject;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NoteDataObject.class},version = 1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();
    private static  volatile NoteDatabase INSTANCE;
    public static NoteDatabase getDatabase(final Context context)
    {

        if (INSTANCE==null)
        {
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .build();



        }

        return INSTANCE;
    }



}
