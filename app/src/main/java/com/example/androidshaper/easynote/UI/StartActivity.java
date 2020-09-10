package com.example.androidshaper.easynote.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidshaper.easynote.R;

public class StartActivity extends AppCompatActivity {
    Button button;
    TextView textView;
    Spannable wordEasy,wordNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        button=findViewById(R.id.buttonStart);
        textView=findViewById(R.id.textViewStart);





        wordEasy = new SpannableString("Easy ");
        wordNote = new SpannableString("Note");

        wordEasy.setSpan(new ForegroundColorSpan(Color.rgb(204,12,82)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordNote.setSpan(new ForegroundColorSpan(Color.rgb(204,12,82)), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setText(wordEasy);
        textView.append(wordNote);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(StartActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}