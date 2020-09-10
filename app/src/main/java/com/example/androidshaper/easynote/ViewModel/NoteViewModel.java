package com.example.androidshaper.easynote.ViewModel;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {
    public Repository repository;
    LiveData<List<NoteDataObject>> listLiveData;
    LiveData<List<NoteDataObject>> listLiveDataFavorite;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository=new Repository(application);
        this.listLiveData=repository.allNotes;
        this.listLiveDataFavorite=repository.allFavoriteNotes;
    }

    public LiveData<List<NoteDataObject>> getListLiveData() {
        return listLiveData;
    }

    public LiveData<List<NoteDataObject>> getListLiveDataFavorite() {
        return listLiveDataFavorite;
    }

    public void insertNote(NoteDataObject noteDataObject)
    {
        repository.insertNote(noteDataObject);
    }

    public void updateNote(NoteDataObject noteDataObject)
    {
        repository.updateNote(noteDataObject);
    }
    public void deleteNote(NoteDataObject noteDataObject)
    {
        repository.deleteNote(noteDataObject);
    }
}
