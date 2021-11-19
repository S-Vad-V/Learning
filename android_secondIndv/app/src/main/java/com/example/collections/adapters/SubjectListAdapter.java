package com.example.collections.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collections.R;
import com.example.collections.models.Subject;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectListAdapter extends BaseAdapter {
    private List<Subject> subjectList;
    private Context context;
    private LayoutInflater layoutInflater;
    private Integer selectedSubject;

    public SubjectListAdapter(List<Subject> subjectList, Context context) {
        this.subjectList = subjectList;
        this.context = context;
        this.layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.selectedSubject = null;
    }

    @Override
    public int getCount() {
        return subjectList.size();
    }

    @Override
    public Object getItem(int i) {
        return subjectList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int index, View view, ViewGroup viewGroup) {
        view = layoutInflater.inflate(R.layout.subject_element, viewGroup, false);
        if (subjectList.isEmpty())
            return view;

        ((TextView) view.findViewById(R.id.tvSubjectName)).setText(subjectList.get(index).getName());
        ((TextView) view.findViewById(R.id.tvSubjectMark)).setText(subjectList.get(index).getMark().toString());

        ((TextView) view.findViewById(R.id.tvSubjectMark)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (selectedSubject != null && index == selectedSubject) {
            ((TextView) view.findViewById(R.id.tvSubjectName)).setTextColor(
                    context.getResources().getColor(R.color.teal_200)
            );
        }

        return view;
    }

    public void removeSubject(int index) {
        subjectList.remove(index);
    }
}
