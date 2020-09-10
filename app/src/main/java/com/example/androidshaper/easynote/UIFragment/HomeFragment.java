package com.example.androidshaper.easynote.UIFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidshaper.easynote.R;
import com.example.androidshaper.easynote.ViewModel.NoteDataObject;
import com.example.androidshaper.easynote.ViewModel.NoteViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;


public class HomeFragment extends Fragment {

    MaterialCardView materialCardView;

    Fragment fragment;

    TextView textViewNotesNumber,textViewName;

    NoteViewModel noteViewModel;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);

        materialCardView=view.findViewById(R.id.cardNotes);
        textViewNotesNumber=view.findViewById(R.id.notesNumber);
        textViewName=view.findViewById(R.id.textViewName);
        noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);
        String name = getColoredSpanned("Welcome", "#000000");
        String surName = getColoredSpannedBold("John Doe!","#CC0C52");
        textViewName.setText(Html.fromHtml(name+" "+surName));
        noteViewModel.getListLiveData().observe(this, new Observer<List<NoteDataObject>>() {
            @Override
            public void onChanged(List<NoteDataObject> noteDataObjects) {
                int size=noteDataObjects.size();
                textViewNotesNumber.setText(String.valueOf(size));
            }
        });
        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                fragment=new AssignmentFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        return view ;
    }

    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";

        return input;
    }
    private String getColoredSpannedBold(String text, String color) {
        String input = "<font color=" + color + ">"+"<b>" + text+"</b>" + "</font>";
        return input;
    }
}