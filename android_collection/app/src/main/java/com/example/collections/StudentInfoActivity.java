package com.example.collections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.collections.adapters.SubjectListAdapter;
import com.example.collections.models.Student;
import com.example.collections.models.Subject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class StudentInfoActivity extends AppCompatActivity {
    private SubjectListAdapter subjectListAdapter;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);

//        ((TextView) findViewById(R.id.tvElementFIO)).setText(getIntent().getStringExtra("fio"));
//        ((TextView) findViewById(R.id.tvElementFaculty)).setText(getIntent().getStringExtra("facultet"));
//        ((TextView) findViewById(R.id.tvElementGroup)).setText(getIntent().getStringExtra("group"));

//        Bundle bundle = getIntent().getExtras();
//        ((TextView) findViewById(R.id.tvElementFIO)).setText(bundle.getString("fio"));
//        ((TextView) findViewById(R.id.tvElementFaculty)).setText(bundle.getString("facultet"));
//        ((TextView) findViewById(R.id.tvElementGroup)).setText(bundle.getString("group"));

        this.student = getIntent().getParcelableExtra("student");
        ((TextView) findViewById(R.id.tvAsiFIO)).setText(student.getFio());
        ((TextView) findViewById(R.id.tvAsiFaculty)).setText(student.getFacultet());
        ((TextView) findViewById(R.id.tvAsiGroup)).setText(student.getGroup());

        subjectListAdapter = new SubjectListAdapter(student.getSubjects(), StudentInfoActivity.this);
        ((ListView) findViewById(R.id.lvAsiSubjects)).setAdapter(subjectListAdapter);
    }

    public void addSubject(View view) {
        student.addSubject(new Subject(((EditText) findViewById(R.id.editAsiSubjectName)).getText().toString(),
                Integer.parseInt(((EditText) findViewById(R.id.editAsiSubjectMark)).getText().toString())));
        subjectListAdapter.notifyDataSetChanged();
    }

    public void saveState(View view) {
        Intent intent = new Intent();
        intent.putExtra("student", student);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }
}