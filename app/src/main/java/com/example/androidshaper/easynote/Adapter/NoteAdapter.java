package com.example.androidshaper.easynote.Adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.androidshaper.easynote.R;
import com.example.androidshaper.easynote.ViewModel.NoteDataObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    OnRecyclerItemClickInterface itemClickInterface;
    List<NoteDataObject> myList;

    public NoteAdapter(OnRecyclerItemClickInterface itemClickInterface, List<NoteDataObject> myList) {
        this.itemClickInterface = itemClickInterface;
        this.myList = myList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false);

        return new NoteViewHolder(view,itemClickInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        NoteDataObject noteDataObjectPosition=myList.get(position);

        if (!myList.isEmpty())
        {

            String noteDate=noteDataObjectPosition.getNoteDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM/dd/yyyy");
            Date convertedDate = null;
            try {
                convertedDate = dateFormat.parse(noteDate);
                String month = new SimpleDateFormat("MMM").format(convertedDate);
                String day = new SimpleDateFormat("dd").format(convertedDate);
                holder.textViewDate.setText(day);
                holder.textViewMonth.setText(month.toUpperCase());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            holder.textViewTitle.setText(noteDataObjectPosition.getNoteTitle());
            holder.textViewDescription.setText(noteDataObjectPosition.getNoteDescription());
            holder.viewCardColor.setBackgroundColor(getRandomColor());
            Log.d("MyCheck", "NoteViewHolder: "+noteDataObjectPosition.isNoteLike());


        }



    }
    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewDate,textViewMonth,textViewTitle,textViewDescription;
        View viewCardColor;
        OnRecyclerItemClickInterface onRecyclerItemClickInterface;

        public NoteViewHolder(@NonNull View itemView,OnRecyclerItemClickInterface interFace) {
            super(itemView);

            textViewDate=itemView.findViewById(R.id.textViewDate);
            textViewMonth=itemView.findViewById(R.id.textViewMonth);
            textViewTitle=itemView.findViewById(R.id.textViewNoteTitle);
            textViewDescription=itemView.findViewById(R.id.textViewNoteDescription);
            viewCardColor=itemView.findViewById(R.id.viewCardColor);



            itemView.setOnClickListener(this);
            this.onRecyclerItemClickInterface=interFace;

        }

        @Override
        public void onClick(View view) {

            onRecyclerItemClickInterface.OnItemClick(getAdapterPosition());
        }
    }
    public interface  OnRecyclerItemClickInterface{
        void OnItemClick(int position);
    }
}
