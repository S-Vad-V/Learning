package com.example.collections;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentListAdapter extends BaseAdapter {

    private List<Student> students;
    private Context context;
    private LayoutInflater layoutInflater;

    public StudentListAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int index) {
        return students.get(index);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.student_element, viewGroup, false);
        if (students.isEmpty())
            return view;

        ((TextView) view.findViewById(R.id.tvElementFIO)).setText(students.get(index).getFio());
        ((TextView) view.findViewById(R.id.tvElementFaculty)).setText(students.get(index).getFacultet());
        ((TextView) view.findViewById(R.id.tvElementGroup)).setText(students.get(index).getGroup());

        if (index %2 == 1){
            view.findViewById(R.id.llElement).setBackgroundColor(
                    context.getResources().getColor(R.color.oddElement)
            );
        }

        return view;

    }
}
