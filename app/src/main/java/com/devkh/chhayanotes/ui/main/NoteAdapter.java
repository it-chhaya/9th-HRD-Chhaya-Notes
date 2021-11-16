package com.devkh.chhayanotes.ui.main;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> mDataSet;
    private LayoutInflater inflater;

    public NoteAdapter(Context context, List<Note> dataSet) {
        this.mDataSet = dataSet;
        this.inflater = LayoutInflater.from(context);
    }

    public void setDataSet(List<Note> dataSet) {
        if (mDataSet == null) {
            mDataSet = dataSet;
            notifyItemRangeChanged(0, dataSet.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mDataSet.size();
                }

                @Override
                public int getNewListSize() {
                    return dataSet.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mDataSet.get(oldItemPosition).getNoteId().equals(dataSet.get(newItemPosition).getNoteId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Note newNote = dataSet.get(newItemPosition);
                    Note oldNote = mDataSet.get(oldItemPosition);
                    return newNote.getNoteId().equals(oldNote.getNoteId())
                            && TextUtils.equals(newNote.getNoteTitle(), oldNote.getNoteTitle());
                }
            });
            mDataSet = dataSet;
            result.dispatchUpdatesTo(this);
        }

    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.textViewNoteTitle.setText(mDataSet.get(position).getNoteTitle());
        holder.textViewNoteContent.setText(mDataSet.get(position).getNoteContent());
        holder.textViewNoteSavedDate.setText(mDataSet.get(position).getNoteSavedDate() + "");
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
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
