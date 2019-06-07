package com.thealteria.roomdemo.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.thealteria.roomdemo.R;
import com.thealteria.roomdemo.model.Note;
import com.thealteria.roomdemo.viewmodel.NotesActivityViewModel;

public class NotesActivity extends AppCompatActivity {
    public static final String NOTE_ID = "note_id";
    private static final String UPDATE_NOTE = "update_note";
    private String TAG = this.getClass().getSimpleName();
    public static final String NOTE_ADDED = "new_note";
    private EditText editText;

    NotesActivityViewModel viewModel;

    Bundle bundle;
    private String noteId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        editText = findViewById(R.id.etNewNote);
        Button save = findViewById(R.id.bSave);
        Button cancel = findViewById(R.id.bCancel);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            noteId = bundle.getString("note_id");
        }

        viewModel = ViewModelProviders.of(this).get(NotesActivityViewModel.class);
        LiveData<Note> noteLiveData = viewModel.getNote(noteId);

        noteLiveData.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                if (note != null) {
                    editText.setText(note.getNote());
                }
            }
        });
    }

    public void saveNote(View view) {
        addNewNote();
        Log.i(TAG, "onClick");
    }

    public void cancelNote(View view) {
        finish();
    }

    public void addNewNote() {
        Intent result = new Intent();
        if (TextUtils.isEmpty(editText.getText())) {
            setResult(RESULT_CANCELED, result);
            Log.i(TAG, "not saved");
        } else {
            String note = editText.getText().toString();
            result.putExtra(NOTE_ID, noteId);
            result.putExtra(NOTE_ADDED, note);
            result.putExtra(UPDATE_NOTE, note);
            setResult(RESULT_OK, result);
            Log.i(TAG, "Note Saved \n EditText: " + note);
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        addNewNote();
        Log.i(TAG, "onBackPressed");
        super.onBackPressed();
    }
}
