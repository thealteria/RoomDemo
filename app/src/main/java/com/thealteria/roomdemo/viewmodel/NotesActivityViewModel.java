package com.thealteria.roomdemo.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thealteria.roomdemo.model.Note;
import com.thealteria.roomdemo.interfaces.NoteDao;
import com.thealteria.roomdemo.database.NoteRoomDatabase;

public class NotesActivityViewModel extends AndroidViewModel {
    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;

    public NotesActivityViewModel(@NonNull Application application) {
        super(application);

        NoteRoomDatabase database = NoteRoomDatabase.getNoteRoomDatabase(application);
        noteDao = database.noteDao();
    }

    public LiveData<Note> getNote(String noteId) {
        return noteDao.getNote(noteId);
    }
}
