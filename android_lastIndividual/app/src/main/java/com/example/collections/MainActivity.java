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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.collections.adapters.StudentListAdapter;
import com.example.collections.dao.DBHelper;
import com.example.collections.models.Action;
import com.example.collections.models.Student;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/*
 * Новый проект. При открытие появляется список студентов. Дбавить crud операции для студентов в меню (правый верхний угол)
 * Перенести добавление/изменение данных о студенте в новом активити
 * CRUD дисциплины сделать в диалоговом окне
 *
 */

public class MainActivity extends AppCompatActivity {
    private List<Student> studentList;
    private StudentListAdapter studentListAdapter;
    private Action action = Action.none;

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private Integer position;
    private DBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        studentList = new ArrayList<>();


        this.dbHelper = new DBHelper(this);
        new AsyncTask<Object, Object, List<Student>>() {
            @Override
            protected List<Student> doInBackground(Object[] objects) {
                return dbHelper.getAllStudents();
            }

            @Override
            protected void onPostExecute(List<Student> students) {
                super.onProgressUpdate(students);
                studentList.addAll(students);
            }
        }.execute();
//        studentList.addAll(dbHelper.getAllStudents());


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
                                new AsyncTask<Object, Student, Student>() {
                                    @Override
                                    protected Student doInBackground(Object[] objects) {
                                        return dbHelper.saveStudent(student);
                                    }

                                    @Override
                                    protected void onPostExecute(Student student) {
                                        super.onProgressUpdate(student);
                                        studentList.add(student);
                                        studentListAdapter.notifyDataSetChanged();
                                    }
                                }.execute();
                                studentList.add(student);
                                position = null;
                            } else {
                                new AsyncTask<Student, Student, Object>() {
                                    @Override
                                    protected Student doInBackground(Student[] objects) {
                                        return dbHelper.saveStudent(objects[0]);
                                    }
                                }.execute(student);
                                studentList.set(position, student);
                                studentListAdapter.notifyDataSetChanged();
                                action = Action.none;
                                position = null;
                            }
                            studentListAdapter.setChoosePosition(null);
                            studentListAdapter.notifyDataSetChanged();
                            action = Action.none;
                            position = null;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            case R.id.miDeleteStudent: {
                if (position != null) {
                    action = Action.delete;
                    deleteStudent(position);
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
                if (position != null && position == i) {
                    position = null;
                } else {
                    position = i;
                }
                studentListAdapter.setChoosePosition(position);
                studentListAdapter.notifyDataSetChanged();
            }
        };
//        AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
//                if (!studentList.isEmpty()) {
//                    Student student = studentList.get(index);
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", student.getPhone(), null));
//                    startActivity(intent);
//                }
//                return true;
//            }
//        };
        listView.setOnItemClickListener(clickListener);
//        listView.setOnItemLongClickListener(longClickListener);
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

    private void deleteStudent(int index) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
        deleteDialog.setTitle("Delete?");
        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Student student = studentList.get(index);
                new AsyncTask() {


                    @Override
                    protected Object doInBackground(Object[] objects) {
                        dbHelper.deleteStudent(student.getId());
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Object value) {
                        super.onProgressUpdate(value);
                        studentList.remove(index);
                        studentListAdapter.notifyDataSetChanged();
                    }
                }.execute();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        deleteDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDestroy() {
        if (studentList != null) {
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] objects) {
                    studentList.forEach(dbHelper::saveStudent);
                    return null;
                }
            }.execute();
        }
        super.onDestroy();
    }
}