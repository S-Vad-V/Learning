package com.example.collections;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.collections.adapters.StudentListAdapter;
import com.example.collections.models.Action;
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
    private Action action = Action.none;

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Integer position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


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
                        try {
                            Student student = intent.getParcelableExtra("student");
                            if (action == Action.add) {
                                studentList.add(student);
                            } else {
                                studentList.set(position, student);
                                studentListAdapter.notifyDataSetChanged();
                                action = Action.none;
                            }
                            studentListAdapter.setChoosePosition(null);
                            studentListAdapter.notifyDataSetChanged();
                            action = Action.none;
//                            Toast.makeText(getApplicationContext(),
//                                    "Student :" + student.toString() + "\n Success saved",
//                                    Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {

                        }
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
            case R.id.miAddStudent: {
                action = Action.add;
                Intent intent = new Intent(MainActivity.this, StudentInfoActivity.class);
                activityResultLauncher.launch(intent);
                return true;
            }
            case R.id.miUpdateStudent: {
                if (position != null) {
                    action = Action.update;
                    updateStudent(position);
                }
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

    public void createStudentList(View view) {
        ListView listView = findViewById(R.id.lvList2);
        studentListAdapter = new StudentListAdapter(studentList, this);
        listView.setAdapter(studentListAdapter);


        AdapterView.OnItemClickListener clickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                studentListAdapter.setChoosePosition(i);
                position = i;
                studentListAdapter.notifyDataSetChanged();

            }
        };
        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {

                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                deleteDialog.setTitle("Delete?");
                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        studentList.remove(index);
                        studentListAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                deleteDialog.show();

                return true;
            }
        };
        listView.setOnItemClickListener(clickListener);
        listView.setOnItemLongClickListener(longClickListener);
    }

    private void updateStudent(int i) {
        studentListAdapter.setChoosePosition(i);
        studentListAdapter.notifyDataSetChanged();

        // При клике, передадим управление активности в element
        Intent intent = new Intent(MainActivity.this, StudentInfoActivity.class);

        intent.putExtra("student", studentList.get(i));
        position = i;
        activityResultLauncher.launch(intent);
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