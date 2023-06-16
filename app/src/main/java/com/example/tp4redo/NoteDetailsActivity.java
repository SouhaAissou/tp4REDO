package com.example.tp4redo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class NoteDetailsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;
    private String noteContent;
    private String noteTitle;
    private String noteCreationDate;
    private TextView noteContentTextView;
    private TextView noteTitleTextView;
    private TextView noteDateTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        noteContent = getIntent().getStringExtra("noteContent");
        noteTitle = getIntent().getStringExtra("noteTitle");
        noteCreationDate = getIntent().getStringExtra("noteCreationDate");

        textToSpeech = new TextToSpeech(this, this);

        noteContentTextView = findViewById(R.id.noteContentTextView);
        noteContentTextView.setText(noteContent);


        noteDateTextView = findViewById(R.id.noteDateTextView);
        noteDateTextView.setText(noteCreationDate);

        Button readButton = findViewById(R.id.readButton);
        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readNoteContent();
            }
        });
    }

    private void readNoteContent() {
        if (textToSpeech != null) {
            textToSpeech.speak(noteContent, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Language not supported, handle the error
            }
        } else {
            // Initialization failed, handle the error
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
