package com.example.collections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collections.models.Student;

import java.util.ArrayList;
import java.util.List;

/*
 * Если нажать на любого студента, выделить всех студентов этого факультета одним цветом.
 */

public class MainActivity extends AppCompatActivity {
    private List<String> list;
    private List<Student> studentList;
    private StudentListAdapter studentListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.llInput).setVisibility(
                findViewById(R.id.bAddStudent).getVisibility()
        );
        list = new ArrayList<>();
        studentList = new ArrayList<>();
    }

    public void createList(View view) {
        list = new ArrayList<>();
        list.add("String1");
        list.add("String2");
        list.add("String3");
        list.add("String4");
        list.add("String5");
        list.add("String6");
        list.add("String7");
        ListView listView = (ListView) findViewById(R.id.lvList1);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);


        // Toast - маленькая всплывашка
        listView.setOnItemClickListener((adapterView, view1, i, l) -> Toast.makeText(getApplicationContext(),
                "Нажато '" + ((TextView) view1).getText() + "'",
                Toast.LENGTH_SHORT).show());
    }

    public void createStudentList(View view) {
        ListView listView = findViewById(R.id.lvList2);
        studentListAdapter = new StudentListAdapter(studentList, this);
        listView.setAdapter(studentListAdapter);

        findViewById(R.id.llInput).setVisibility(View.VISIBLE);
        findViewById(R.id.bAddStudent).setVisibility(View.VISIBLE);
        findViewById(R.id.bCreateStudentList).setVisibility(View.GONE);

        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                studentListAdapter.setChooseFaculty(((TextView) view.findViewById(R.id.tvElementFaculty)).getText().toString().trim());
                studentListAdapter.notifyDataSetChanged();

                // При клике, передадим управление активности в element
                Intent intent = new Intent(MainActivity.this, StudentInfoActivity.class);

//                intent.putExtra("fio", ((TextView)view.findViewById(R.id.tvElementFIO)).getText().toString());
//                intent.putExtra("facultet", ((TextView)view.findViewById(R.id.tvElementFaculty)).getText().toString());
//                intent.putExtra("group", ((TextView)view.findViewById(R.id.tvElementGroup)).getText().toString());

//                Bundle bundle = new Bundle();
//                bundle.putString("fio", studentList.get(i).getFio());
//                bundle.putString("group", studentList.get(i).getGroup());
//                bundle.putString("facultet", studentList.get(i).getFacultet());
//
//                intent.putExtras(bundle);

                intent.putExtra("student", studentList.get(i));

                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(clickListener);
    }


    public void addStudent(View view) {
        studentList.add(Student.builder()
                .fio(((EditText) findViewById(R.id.editFIO)).getText().toString())
                .facultet(((EditText) findViewById(R.id.editFaculty)).getText().toString())
                .group(((EditText) findViewById(R.id.editGroup)).getText().toString())
                .build());

        ((EditText) findViewById(R.id.editFIO)).setText("");
        ((EditText) findViewById(R.id.editFaculty)).setText("");
        ((EditText) findViewById(R.id.editGroup)).setText("");
        studentListAdapter.notifyDataSetChanged();
    }
}