package com.thealteria.roomdemo.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.thealteria.roomdemo.R;
import com.thealteria.roomdemo.adapter.NotesListAdapter;
import com.thealteria.roomdemo.model.Note;
import com.thealteria.roomdemo.viewmodel.NoteViewModel;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements NotesListAdapter.OnDeleteClickListener {
    private String TAG = this.getClass().getSimpleName();
    private NoteViewModel noteViewModel;
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_NOTE_ACTIVITY_REQUEST_CODE = 2;
    private NotesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new NotesListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        manager.setStackFromEnd(true);
        recyclerView.setLayoutManager(manager);

        FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }
        });

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            final String note_id = UUID.randomUUID().toString();
            if (data != null) {
                Note note = new Note(note_id, data.getStringExtra(NotesActivity.NOTE_ADDED));
                noteViewModel.insert(note);
            }
            Toast.makeText(getApplicationContext(), "Note saved", Toast.LENGTH_LONG).show();
        }
        if (requestCode == UPDATE_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                Note note = new Note(data.getStringExtra(NotesActivity.NOTE_ID),
                        data.getStringExtra(NotesActivity.NOTE_ADDED));
                noteViewModel.update(note);
            }
            Toast.makeText(getApplicationContext(), "Note updated", Toast.LENGTH_LONG).show();
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "Nothing is saved", Toast.LENGTH_LONG).show();
        }
        Log.i(TAG, "onActivityResult: " + requestCode + " " + resultCode);
    }

    @Override
    public void onDeleteClickListener(Note note) {
        noteViewModel.delete(note);
        Toast.makeText(getApplicationContext(), "Note deleted", Toast.LENGTH_LONG).show();
    }
}
