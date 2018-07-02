package com.example.android.journalapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteAdapterViewHolder>{
    public List<NoteObject> noteArray = new ArrayList<>();
    private final ListItemOnClickListener listItemOnClickListener;

    public NoteAdapter(ListItemOnClickListener listItemOnClickListener){
        this.listItemOnClickListener = listItemOnClickListener;
    }
    public  interface ListItemOnClickListener{
        void onListItemClickListener(int clickedIndex);
    }
    @NonNull
    @Override
    public NoteAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int noteXml = R.layout.note_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(noteXml,parent,false);
        return new NoteAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapterViewHolder holder, int position) {
        Time time = new Time(Time.getCurrentTimezone());
        time.setToNow();
        String day = String.valueOf(time.monthDay);
        NoteObject currentText = noteArray.get(position);
        holder.mBodyTextview.setText(currentText.getmBody());
        holder.mTitleTextview.setText(currentText.getmTitle());
        if (!day.equals(currentText.getDay())) {
            holder.textView.setText(currentText.getDay() + "/" + currentText.getMonth() + "/" + currentText.getYear());

        }else {
            holder.textView.setText("Today");
        }
    }

    @Override
    public int getItemCount() {
        if (noteArray != null){
            return noteArray.size();
        }
        return 0;
    }

    class NoteAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView,mBodyTextview, mTitleTextview;

        public NoteAdapterViewHolder(View itemView) {
            super(itemView);
            mTitleTextview = itemView.findViewById(R.id.tv_title_display);
            mBodyTextview = itemView.findViewById(R.id.tv_note_body);
            textView = itemView.findViewById(R.id.tv_display_time);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int adapterIndex = getAdapterPosition();
            listItemOnClickListener.onListItemClickListener(adapterIndex);
        }
    }
}
