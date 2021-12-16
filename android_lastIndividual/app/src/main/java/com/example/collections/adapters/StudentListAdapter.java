package com.example.collections.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collections.R;
import com.example.collections.models.Student;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentListAdapter extends BaseAdapter {

    private List<Student> students;
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer choosePosition;

    public void setChoosePosition(Integer choosePosition) {
        if (choosePosition == this.choosePosition) {
            this.choosePosition = null;
        } else {
            this.choosePosition = choosePosition;
        }
    }


    public StudentListAdapter(List<Student> students, Context context) {
        this.students = students;
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.choosePosition = null;
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
        ((TextView) view.findViewById(R.id.textElementPhone)).setText(students.get(index).getPhone());

//        ♥if (index % 2 == 1) {
//            view.findViewById(R.id.llElement).setBackgroundColor(
//                    context.getResources().getColor(R.color.oddElement)
//            );
//        }♥

        if (choosePosition != null && index == choosePosition) {
            ((TextView) view.findViewById(R.id.tvElementFIO)).setTextColor(
                    context.getResources().getColor(R.color.chooseFaculty)
            );
            ((TextView) view.findViewById(R.id.tvElementFaculty)).setTextColor(
                    context.getResources().getColor(R.color.chooseFaculty)
            );
            ((TextView) view.findViewById(R.id.tvElementGroup)).setTextColor(
                    context.getResources().getColor(R.color.chooseFaculty)
            );
            ((TextView) view.findViewById(R.id.textElementPhone)).setTextColor(
                    context.getResources().getColor(R.color.chooseFaculty)
            );
        }

        return view;
    }
}
