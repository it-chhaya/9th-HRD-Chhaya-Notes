package com.devkh.chhayanotes.ui.main;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.model.local.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> mDataSet;
    private LayoutInflater mInflater;
    private OnItemClickListener mListener;

    public NoteAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mListener = (OnItemClickListener) context;
    }

    public void setDataSet(List<Note> dataSet) {
        if (mDataSet == null) {
            Log.i("TAG", "setDataSet: New");
            mDataSet = dataSet;
            notifyItemRangeChanged(0, dataSet.size());
        } else {
            Log.i("TAG", "setDataSet: Old");
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
                    return mDataSet.get(oldItemPosition).getNoteId()
                            .equals(dataSet.get(newItemPosition).getNoteId());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    return mDataSet.get(oldItemPosition).getNoteId()
                            .equals(dataSet.get(newItemPosition).getNoteId())
                            && TextUtils.equals(mDataSet.get(oldItemPosition).getNoteTitle(), dataSet.get(newItemPosition).getNoteTitle())
                            && TextUtils.equals(mDataSet.get(oldItemPosition).getNoteContent(), dataSet.get(newItemPosition).getNoteContent());
                }
            });
            mDataSet = dataSet;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_note, parent, false);
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
        return mDataSet == null ? 0 : mDataSet.size();
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // trigger event
                    mListener.onItemClicked(mDataSet.get(getAdapterPosition()));
                }
            });

        }

    }

    public interface OnItemClickListener {
        void onItemClicked(Note note);
    }

}
