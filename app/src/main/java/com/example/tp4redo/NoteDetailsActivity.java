package com.example.tp4redo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteDetailsActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;
    private String noteContent;
    private String noteTitle;
    private String noteCreationDate;
    private TextView noteContentTextView;
    private TextView noteTitleTextView;
    private TextView noteDateTextView;
    private int notePosition;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);


        noteContent = getIntent().getStringExtra("noteContent");
        noteTitle = getIntent().getStringExtra("noteTitle");
        noteCreationDate = getIntent().getStringExtra("noteCreationDate");
        notePosition = getIntent().getIntExtra("notePosition", -1);

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

        Button editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditNoteDialog(new Note(noteTitle, noteContent, noteCreationDate, noteContent));
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

    private void showEditNoteDialog(final Note noteToEdit) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Note");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(noteToEdit.getNoteText()); // Set the initial text to the existing note content
        builder.setView(input);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String editedNoteText = input.getText().toString(); // Get the edited note text from the input field
                noteToEdit.setNoteText(editedNoteText); // Update the note text in the Note object
                noteToEdit.setContent(editedNoteText);
                // Update the displayed note content
                noteContentTextView.setText(editedNoteText);

                // Update the note content variable with the edited text
                noteContent = editedNoteText;

                // Pass the edited note back to the MainActivity2 activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("editedNote", noteToEdit);
                resultIntent.putExtra("notePosition", notePosition);
                setResult(Activity.RESULT_OK, resultIntent);

                // Read the updated note content
//                readNoteContent();
            }
        });

        // Set the negative button action
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show the dialog
        builder.show();
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
