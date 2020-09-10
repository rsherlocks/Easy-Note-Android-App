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

import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.androidshaper.easynote.Adapter.NoteAdapter;
import com.example.androidshaper.easynote.R;
import com.example.androidshaper.easynote.UI.NoteDetails;
import com.example.androidshaper.easynote.ViewModel.NoteDataObject;
import com.example.androidshaper.easynote.ViewModel.NoteViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AssignmentFragment extends Fragment implements View.OnClickListener, NoteAdapter.OnRecyclerItemClickInterface {

    View viewNote;
    RecyclerView recyclerViewNoteItem;
    ImageView imageViewButton;
    NoteAdapter noteAdapter;
    List<NoteDataObject> noteDataObjectList;
    NoteAdapter.OnRecyclerItemClickInterface onRecyclerItemClickInterface;
    NoteViewModel noteViewModel;
    SearchView searchView;



    String noteDate,noteTitle,noteDescription=null;
    boolean noteStatus=false;

    EditText editTextTitle,editTextDescription;
    Switch aSwitch;
    Button buttonAdd;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         viewNote=inflater.inflate(R.layout.fragment_assignement, container, false);
         recyclerViewNoteItem=viewNote.findViewById(R.id.noteRecyclerView);
         imageViewButton=viewNote.findViewById(R.id.noteAddButton);

         searchView=viewNote.findViewById(R.id.searchView);

         searchView.setFocusable(false);
         imageViewButton.setOnClickListener(this);
         noteDataObjectList=new ArrayList<>();
         onRecyclerItemClickInterface=AssignmentFragment.this;
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


        noteViewModel.getListLiveData().observe(this, new Observer<List<NoteDataObject>>() {
            @Override
            public void onChanged(List<NoteDataObject> noteDataObjects) {

                noteDataObjectList=noteDataObjects;
                noteAdapter=new NoteAdapter(onRecyclerItemClickInterface,noteDataObjectList);
                recyclerViewNoteItem.setAdapter(noteAdapter);

            }
        });

    }

    @Override
    public void onClick(View view) {

        if (view.getId()==R.id.noteAddButton)
        {

            Date date=new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
            noteDate=dateFormat.format(date);
            Log.d("MyTime", "onClick: "+noteDate);

//            String noteTitle="office work";
//            String noteDescription="Now I working office work";
//            String noteDate="09";
//            String noteMonth="APR";
//            boolean noteStatus=true;
//            NoteDataObject noteDataObject=new NoteDataObject(noteTitle,noteDescription,noteDate, true);
//
//            noteViewModel.insertNote(noteDataObject);
//            Toast.makeText(getContext(),"Insert Note",Toast.LENGTH_LONG).show();
            final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(getContext(),R.style.BottomSheet);
            View bottomSheet= LayoutInflater.from(getContext()).inflate(R.layout.bottom_sheet,(LinearLayout)getView().findViewById(R.id.bottomSheetAddNote));
            bottomSheetDialog.setContentView(bottomSheet);

            searchView.setFocusable(false);
            bottomSheetDialog.show();
            bottomSheetDialog.setCanceledOnTouchOutside(true);



            editTextTitle=bottomSheetDialog.findViewById(R.id.editTextTitle);
            editTextDescription=bottomSheetDialog.findViewById(R.id.editTextDescription);
            aSwitch=bottomSheetDialog.findViewById(R.id.switchFavorite);

            buttonAdd=bottomSheetDialog.findViewById(R.id.addNoteButton);

            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    noteTitle=editTextTitle.getText().toString();
                    noteDescription=editTextDescription.getText().toString();



                    if (noteDescription.length()>0 || noteTitle.length()>0)
                    {
                        if (aSwitch.isChecked())
                        {
                            noteStatus=true;
                        }
                        NoteDataObject noteDataObject=new NoteDataObject(noteTitle,noteDescription,noteDate,noteStatus);
                        noteViewModel.insertNote(noteDataObject);

                        Time today = new Time(Time.getCurrentTimezone());
                        today.setToNow();
                        Log.d("MyTime", "onClick: "+today);
                        bottomSheetDialog.dismiss();

                        searchView.clearFocus();
                    }

                    else{
                        Toast.makeText(getContext(),"Please at first add note",Toast.LENGTH_LONG).show();

                    }



                }
            });





        }

    }

    @Override
    public void OnItemClick(int position) {

//        Toast.makeText(getContext(),String.valueOf(position),Toast.LENGTH_SHORT).show();

        Intent intent=new Intent(getContext(), NoteDetails.class);
        Activity activity=(Activity) getContext();
        intent.putExtra("position",position);
        activity.startActivity(intent);


    }
}