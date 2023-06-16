package com.example.tp4redo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private RecyclerView notesRecyclerView;
    private NotesAdapter notesAdapter;
    private List<Note> notesList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesList = new ArrayList<>();

        if (savedInstanceState != null) {
            List<Note> savedNotesList = savedInstanceState.<Note>getParcelableArrayList("notesList");
            if (savedNotesList != null) {
                notesList.addAll(savedNotesList);
            }
        }

        notesAdapter = new NotesAdapter(notesList, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        notesRecyclerView.setLayoutManager(layoutManager);
        notesRecyclerView.setAdapter(notesAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("notesList", new ArrayList<>(notesList));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        List<Note> savedNotesList = savedInstanceState.getParcelableArrayList("notesList");
        if (savedNotesList != null) {
            notesList.clear();
            notesList.addAll(savedNotesList);
            notesAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_note) {
            showAddNoteDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAddNoteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Note");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String noteText = input.getText().toString().trim();
                if (!noteText.isEmpty()) {
                    String noteTextDisplay = null;
                    if (noteText.length() > 15) {
                        noteTextDisplay = noteText.substring(0, 15) + "...";
                    }
                    else{
                        noteTextDisplay = noteText;
                    }


                    Date currentDate = new Date();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                    String formattedDate = dateFormat.format(currentDate);
                    notesList.add(new Note("Aissou Souha", noteTextDisplay, formattedDate,noteText));
                    notesAdapter.notifyDataSetChanged();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }



}