package com.example.collections;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collections.adapters.StudentListAdapter;
import com.example.collections.models.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Новый проект. При открытие появляется список студентов. Дбавить crud операции для студентов в меню (правый верхний угол)
 * Перенести добавление/изменение данных о студенте в новом активити
 * CRUD дисциплины сделать в диалоговом окне
 *
 */

public class MainActivity extends AppCompatActivity {
    private List<String> list;
    private List<Student> studentList;
    private StudentListAdapter studentListAdapter;

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        findViewById(R.id.llInput).setVisibility(
                findViewById(R.id.bAddStudent).getVisibility()
        );
        list = new ArrayList<>();
        studentList = new ArrayList<>();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        int size = sharedPreferences.getInt("count", 0);
        if (size > 0) {
            Gson gson = (new GsonBuilder()).create();
            for (int i = 0; i < size; i++) {
                String studentJson = sharedPreferences.getString("student" + i, "");
                if (!studentJson.isEmpty()) {
                    Student student = gson.fromJson(studentJson, Student.class);
                    studentList.add(student);
                }
            }
        }
        createStudentList(null);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        Intent intent = result.getData();
                        Student student = intent.getParcelableExtra("student");
                        studentList.set(position, student);
                        Toast.makeText(getApplicationContext(),
                                "Student :" + student.toString() + "\n Success saved",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAbout: {
                AlertDialog.Builder infoDialog = new AlertDialog.Builder(MainActivity.this);
                infoDialog.setTitle("About program");
                infoDialog.setMessage("This program wrote Vadim Storchak");
                infoDialog.setCancelable(false);
                infoDialog.setPositiveButton("Readied", null);

                infoDialog.show();
                return true;
            }
            case R.id.miExit: {
                finish();
                return true;
            }
            default: {
            }
        }
        return super.onOptionsItemSelected(item);
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
                position = i;
                activityResultLauncher.launch(intent);
            }
        };
        listView.setOnItemClickListener(clickListener);
    }

    public void addStudent(View view) {
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.editFIO)).getText().toString())) {
            ((EditText) findViewById(R.id.editFIO)).setError("Enter your FIO");
            return;
        }
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.editGroup)).getText().toString())) {
            ((EditText) findViewById(R.id.editGroup)).setError("Enter your Group");
            return;
        }
        if (TextUtils.isEmpty(((EditText) findViewById(R.id.editFaculty)).getText().toString())) {
            ((EditText) findViewById(R.id.editFaculty)).setError("Enter your Faculty");
            return;
        }

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

    private boolean checkField(EditText editText) {
        boolean isEmpty = TextUtils.isEmpty(editText.getText().toString());
        if (isEmpty) {
            ((EditText) findViewById(R.id.editFIO)).setError("Enter value");
        }
        return isEmpty;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDestroy() {
        if (studentList != null) {
            SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            editor.putInt("count", studentList.size());
            AtomicInteger counter = new AtomicInteger(0);
            studentList.forEach(student -> {
                String jsonStudent = gson.toJson(student);
                editor.putString("student" + counter.getAndIncrement(), jsonStudent);
            });
            editor.commit();
        }
        super.onDestroy();
    }
}