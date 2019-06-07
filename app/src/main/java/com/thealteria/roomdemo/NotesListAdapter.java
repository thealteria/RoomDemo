package com.thealteria.roomdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.NotesViewHolder> {
    private final LayoutInflater inflater;
    private Context mContext;
    private List<Note> mNotes;

    NotesListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        mContext = context;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.list_item, viewGroup, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        if (mNotes != null) {
            Note note = mNotes.get(position);
            holder.setData(note.getNote(), position);
            holder.setListener();
        } else {
            holder.textView.setText("No note");
        }

    }

    @Override
    public int getItemCount() {
        if (mNotes != null) {
            return mNotes.size();
        } else {
            return 0;
        }
    }

    void setNotes(List<Note> notes) {
        mNotes = notes;
        notifyDataSetChanged();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView textView;
        private int mPosition;
        private ImageView deleteNote;

        NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            textView = itemView.findViewById(R.id.txvNote);
            deleteNote = itemView.findViewById(R.id.ivRowDelete);
        }

        void setData(String note, int position) {
            textView.setText(note);
            mPosition = position;
        }

        void setListener() {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NewNoteActivity.class);
                    intent.putExtra("note_id", mNotes.get(mPosition).getId());
                    ((Activity) mContext).startActivityForResult(intent, MainActivity.UPDATE_NOTE_ACTIVITY_REQUEST_CODE);
                }
            });

            deleteNote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
