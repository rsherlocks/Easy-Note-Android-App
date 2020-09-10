package com.example.androidshaper.easynote.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidshaper.easynote.ViewModel.NoteDataObject;
import com.example.androidshaper.easynote.ViewModel.NoteViewModel;
import com.example.androidshaper.easynote.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteDetails extends AppCompatActivity {

    TextView textViewTitle,textViewDescription,textViewDate;
    Toolbar toolbar;
    NoteViewModel noteViewModel;
    List<NoteDataObject> noteDataObjectList;
    int position;
    LinearLayout linearLayout;


    String noteDate,noteTitle,noteDescription=null;
    boolean noteStatus=false;


    EditText editTextTitle,editTextDescription;
    Switch aSwitch;

    Button buttonAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        toolbar=findViewById(R.id.toolbar);
        textViewTitle=findViewById(R.id.noteTitle);
        textViewDescription=findViewById(R.id.noteDescription);
        textViewDate=findViewById(R.id.noteDate);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        position=intent.getExtras().getInt("position");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        noteDataObjectList=new ArrayList<>();

        noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);

        setNote();

    }

    private void setData(List<NoteDataObject> noteDataObjects) {
        textViewTitle.setText(noteDataObjectList.get(position).getNoteTitle());
        textViewDescription.setText(noteDataObjectList.get(position).getNoteDescription());
        textViewDate.setText(noteDataObjectList.get(position).getNoteDate());
    }

    private void setNote() {

        noteViewModel.getListLiveData().observe(this, new Observer<List<NoteDataObject>>() {
            @Override
            public void onChanged(List<NoteDataObject> noteDataObjects) {
                noteDataObjectList=noteDataObjects;
                setData(noteDataObjects);


            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            finish();

        }
        else if (item.getItemId()==R.id.deleteId)
        {
            new AlertDialog.Builder(this)
                    .setMessage("Do you want to delete this Note?")
                    .setTitle("WARNING!!!")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //delete function called

                            NoteDataObject noteDataObject=noteDataObjectList.get(position);

                            noteViewModel.deleteNote(noteDataObject);
                            finish();



                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    })
                    .show();

        }

        else if (item.getItemId()==R.id.editId)
        {

            updateNote();

        }
        else if (item.getItemId()==R.id.addId)
        {
            addNote();
        }

        return true;
    }

    private void addNote() {
        Date date=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
        noteDate=dateFormat.format(date);
        Log.d("MyTime", "onClick: "+noteDate);

        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this,R.style.BottomSheet);
        View bottomSheet= LayoutInflater.from(this).inflate(R.layout.bottom_sheet,(LinearLayout)findViewById(R.id.bottomSheetAddNote));
        bottomSheetDialog.setContentView(bottomSheet);

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

                    bottomSheetDialog.dismiss();


                }

                else{
                    Toast.makeText(getApplicationContext(),"Please at first add note",Toast.LENGTH_LONG).show();

                }



            }
        });
    }

    private void updateNote() {

        Date date=new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
        noteDate=dateFormat.format(date);
        Log.d("MyTime", "onClick: "+noteDate);

        final BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this,R.style.BottomSheet);
        View bottomSheet= LayoutInflater.from(this).inflate(R.layout.bottom_sheet,(LinearLayout)findViewById(R.id.bottomSheetAddNote));
        bottomSheetDialog.setContentView(bottomSheet);

        bottomSheetDialog.show();
        bottomSheetDialog.setCanceledOnTouchOutside(true);


        editTextTitle=bottomSheetDialog.findViewById(R.id.editTextTitle);
        editTextDescription=bottomSheetDialog.findViewById(R.id.editTextDescription);
        linearLayout=bottomSheetDialog.findViewById(R.id.bottomSheetAddNote);
        aSwitch=bottomSheetDialog.findViewById(R.id.switchFavorite);
        buttonAdd=bottomSheetDialog.findViewById(R.id.addNoteButton);
        linearLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.bottom_sheet_edit));

        buttonAdd.setText("Edit Note");
        editTextTitle.setText(noteDataObjectList.get(position).getNoteTitle());
        editTextDescription.setText(noteDataObjectList.get(position).getNoteDescription());

            aSwitch.setChecked(noteDataObjectList.get(position).isNoteLike());


        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                noteTitle=editTextTitle.getText().toString();
                noteDescription=editTextDescription.getText().toString();




                    if (aSwitch.isChecked())
                    {
                        noteStatus=true;
                        NoteDataObject noteDataObject=noteDataObjectList.get(position);
                        noteDataObject.setNoteTitle(noteTitle);
                        noteDataObject.setNoteDescription(noteDescription);
                        noteDataObject.setNoteDate(noteDate);
                        noteDataObject.setNoteLike(noteStatus);
                        noteViewModel.updateNote(noteDataObject);
                        bottomSheetDialog.dismiss();
                    }
                    else
                    {
                        NoteDataObject noteDataObject=noteDataObjectList.get(position);
                        noteDataObject.setNoteTitle(noteTitle);
                        noteDataObject.setNoteDescription(noteDescription);
                        noteDataObject.setNoteDate(noteDate);
                        noteDataObject.setNoteLike(noteStatus);
                        noteViewModel.updateNote(noteDataObject);
                        bottomSheetDialog.dismiss();

                        finish();
                    }








            }
        });
    }
}