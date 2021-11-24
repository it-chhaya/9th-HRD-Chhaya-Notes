package com.devkh.chhayanotes.ui.main;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.devkh.chhayanotes.R;
import com.devkh.chhayanotes.data.model.local.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> mDataSet;
    private final LayoutInflater mInflater;

    private NoteAdapterListener mListener;

    public NoteAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        try {
            mListener = (NoteAdapterListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
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
        holder.mTextViewNoteTitle.setText(mDataSet.get(position).getNoteTitle());
        holder.mTextViewNoteContent.setText(mDataSet.get(position).getNoteContent());
        holder.mTextViewNoteSavedDate.setText(mDataSet.get(position).getNoteSavedDate() + "");
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ? 0 : mDataSet.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTextViewNoteTitle;
        TextView mTextViewNoteContent;
        TextView mTextViewNoteSavedDate;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            mTextViewNoteTitle = itemView.findViewById(R.id.note_title);
            mTextViewNoteContent = itemView.findViewById(R.id.note_content);
            mTextViewNoteSavedDate = itemView.findViewById(R.id.note_saved_date);

        }

        @Override
        public void onClick(View view) {
            mListener.onItemSelected(mDataSet.get(getAdapterPosition()));
        }

    }

    public interface NoteAdapterListener {
        void onItemSelected(Note note);
    }


}
