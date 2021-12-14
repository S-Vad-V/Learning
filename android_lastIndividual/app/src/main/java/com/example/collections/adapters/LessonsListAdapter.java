package com.example.collections.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collections.R;
import com.example.collections.models.Lessons;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonsListAdapter extends BaseAdapter {
    private List<Lessons> lessonsList;
    private Context context;
    private LayoutInflater layoutInflater;

    public LessonsListAdapter(List<Lessons> lessonsList, Context context) {
        this.lessonsList = lessonsList;
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lessonsList.size();
    }

    @Override
    public Object getItem(int i) {
        return lessonsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.subject_element, viewGroup, false);
        if (lessonsList.isEmpty())
            return view;

        ((TextView) view.findViewById(R.id.tvSubjectName)).setText(lessonsList.get(index).getName());
        ((TextView) view.findViewById(R.id.tvSubjectMark)).setText(lessonsList.get(index).getMark().toString());

        ((TextView) view.findViewById(R.id.tvSubjectMark)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
