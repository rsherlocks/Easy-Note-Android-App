package com.example.androidshaper.easynote.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import com.example.androidshaper.easynote.RoomDbService.NoteDao;
import com.example.androidshaper.easynote.RoomDbService.NoteDatabase;
import com.example.androidshaper.easynote.ViewModel.NoteDataObject;

import java.util.List;

import androidx.lifecycle.LiveData;

public class Repository {

    public NoteDao noteDao;
    LiveData<List<NoteDataObject>> allNotes;
    LiveData<List<NoteDataObject>> allFavoriteNotes;

    Repository(Application application)
    {
        NoteDatabase noteDatabase=NoteDatabase.getDatabase(application);
        noteDao=noteDatabase.noteDao();
        allNotes=noteDao.getAllNote();
        allFavoriteNotes=noteDao.getAllFavoriteNote(true);
    }

    public LiveData<List<NoteDataObject>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<NoteDataObject>> getAllFavoriteNotes() {
        return allFavoriteNotes;
    }

    public void insertNote(NoteDataObject noteDataObject)
    {
        new InsertTask(noteDao).execute(noteDataObject);

    }
    public class InsertTask extends AsyncTask<NoteDataObject,Void,Void>
    {
        private NoteDao noteDao;

        public InsertTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteDataObject... noteDataObjects) {
            noteDao.noteInsert(noteDataObjects[0]);
            return null;
        }
    }
    public void deleteNote(NoteDataObject noteDataObject)
    {
        new DeleteTask(noteDao).execute(noteDataObject);

    }
    public class DeleteTask extends AsyncTask<NoteDataObject,Void,Void>
    {
        private NoteDao noteDao;

        public DeleteTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteDataObject... noteDataObjects) {
            noteDao.noteDelete(noteDataObjects[0]);
            return null;
        }
    }

    public  void updateNote(NoteDataObject noteDataObject)
    {
        new UpdateTask(noteDao).execute(noteDataObject);

    }
    public class UpdateTask extends AsyncTask<NoteDataObject,Void,Void>
    {
        private NoteDao noteDao;

        public UpdateTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteDataObject... noteDataObjects) {
            noteDao.noteUpdate(noteDataObjects[0]);
            return null;
        }
    }
}
