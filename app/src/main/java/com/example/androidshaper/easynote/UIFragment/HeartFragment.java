package com.example.androidshaper.easynote.UIFragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidshaper.easynote.Adapter.NoteAdapter;
import com.example.androidshaper.easynote.R;
import com.example.androidshaper.easynote.UI.NoteDetails;
import com.example.androidshaper.easynote.ViewModel.NoteDataObject;
import com.example.androidshaper.easynote.ViewModel.NoteViewModel;

import java.util.ArrayList;
import java.util.List;


public class HeartFragment extends Fragment implements NoteAdapter.OnRecyclerItemClickInterface {

    View viewNote;
    RecyclerView recyclerViewNoteItem;
    NoteAdapter noteAdapter;
    List<NoteDataObject> noteDataObjectList;
    NoteAdapter.OnRecyclerItemClickInterface onRecyclerItemClickInterface;
    NoteViewModel noteViewModel;
    SearchView searchView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        viewNote=inflater.inflate(R.layout.fragment_heart, container, false);
        recyclerViewNoteItem=viewNote.findViewById(R.id.noteRecyclerView);


        searchView=viewNote.findViewById(R.id.searchView);

        searchView.setFocusable(false);

        noteDataObjectList=new ArrayList<>();
        onRecyclerItemClickInterface=HeartFragment.this;
        recyclerViewNoteItem.setHasFixedSize(true);
        recyclerViewNoteItem.setLayoutManager(new LinearLayoutManager(viewNote.getRootView().getContext()));

        noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);

        loadNotes();
        swipeDelete();
        return viewNote;
    }
    private void swipeDelete() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT
                | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                DeleteStudent(viewHolder.getAdapterPosition());


            }
        }).attachToRecyclerView(recyclerViewNoteItem);
    }

    private void DeleteStudent(final int adapterPosition) {

        new AlertDialog.Builder(getContext())
                .setMessage("Do you want to delete this Note?")
                .setTitle("WARNING!!!")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //delete function called

                        NoteDataObject noteDataObject=noteDataObjectList.get(adapterPosition);

                        noteViewModel.deleteNote(noteDataObject);
                        noteAdapter.notifyDataSetChanged();
                        loadNotes();

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loadNotes();

                    }
                })
                .show();

    }

    private void loadNotes() {

        noteViewModel.getListLiveDataFavorite().observe(this, new Observer<List<NoteDataObject>>() {
            @Override
            public void onChanged(List<NoteDataObject> noteDataObjects) {
                noteDataObjectList=noteDataObjects;
                noteAdapter=new NoteAdapter(onRecyclerItemClickInterface,noteDataObjectList);
                recyclerViewNoteItem.setAdapter(noteAdapter);
            }
        });




    }



    @Override
    public void OnItemClick(int position) {

        Intent intent=new Intent(getContext(), NoteDetails.class);
        Activity activity=(Activity) getContext();
        intent.putExtra("position",position);
        activity.startActivity(intent);

    }
}