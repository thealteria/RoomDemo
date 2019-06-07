package com.thealteria.roomdemo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "notes")
class Note {
    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getNote() {
        return this.mNote;
    }

    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    @ColumnInfo(name = "note") // change column name for a particular field
    private String mNote;

    Note(@NonNull String id, @NonNull String mNote) {
        this.id = id;
        this.mNote = mNote;
    }


}
