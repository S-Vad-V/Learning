package com.example.collections;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.collections.models.Student;

public class StudentInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_element);

//        ((TextView) findViewById(R.id.tvElementFIO)).setText(getIntent().getStringExtra("fio"));
//        ((TextView) findViewById(R.id.tvElementFaculty)).setText(getIntent().getStringExtra("facultet"));
//        ((TextView) findViewById(R.id.tvElementGroup)).setText(getIntent().getStringExtra("group"));

//        Bundle bundle = getIntent().getExtras();
//        ((TextView) findViewById(R.id.tvElementFIO)).setText(bundle.getString("fio"));
//        ((TextView) findViewById(R.id.tvElementFaculty)).setText(bundle.getString("facultet"));
//        ((TextView) findViewById(R.id.tvElementGroup)).setText(bundle.getString("group"));

        Student student = getIntent().getParcelableExtra("student");
        ((TextView) findViewById(R.id.tvElementFIO)).setText(student.getFio());
        ((TextView) findViewById(R.id.tvElementFaculty)).setText(student.getFacultet());
        ((TextView) findViewById(R.id.tvElementGroup)).setText(student.getGroup());
    }


}