package com.devkh.chhayanotes.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> dataSet;
    private LayoutInflater inflater;

    public NoteAdapter(Context context, List<Note> dataSet) {
        this.dataSet = dataSet;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.textViewNoteTitle.setText(dataSet.get(position).getNoteTitle());
        holder.textViewNoteContent.setText(dataSet.get(position).getNoteContent());
        holder.textViewNoteSavedDate.setText(dataSet.get(position).getNoteSavedDate() + "");
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNoteTitle;
        TextView textViewNoteContent;
        TextView textViewNoteSavedDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNoteTitle = itemView.findViewById(R.id.note_title);
            textViewNoteContent = itemView.findViewById(R.id.note_content);
            textViewNoteSavedDate = itemView.findViewById(R.id.note_saved_date);

        }

    }

}
