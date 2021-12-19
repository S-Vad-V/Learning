package com.example.collections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.collections.adapters.LessonsListAdapter;
import com.example.collections.dao.DBHelper;
import com.example.collections.models.Lessons;
import com.example.collections.models.Student;

import java.util.ArrayList;
import java.util.List;


public class StudentInfoActivity extends AppCompatActivity {
    private LessonsListAdapter subjectListAdapter;
    private Student student;
    private Integer selectedSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        selectedSubject = null;

        this.student = getIntent().getParcelableExtra("student");
        if (student != null) {
            ((TextView) findViewById(R.id.editAsiFIO)).setText(student.getFio());
            ((TextView) findViewById(R.id.editAsiFaculty)).setText(student.getFacultet());
            ((TextView) findViewById(R.id.editAsiGroup)).setText(student.getGroup());
            ((TextView) findViewById(R.id.editAsiPhone)).setText(student.getPhone());


            new AsyncTask<Student, Object, List<Lessons>>() {
                DBHelper dbHelper;
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    dbHelper = new DBHelper(StudentInfoActivity.this);
                }

                @Override
                protected List<Lessons> doInBackground(Student[] students) {
                    return dbHelper.getAllStudentLessonsByStudentId(students[0].getId());
                }

                @Override
                protected void onPostExecute(List<Lessons> lessons) {
                    super.onProgressUpdate(lessons);
                    student.setLessons(lessons);
                    subjectListAdapter = new LessonsListAdapter(student.getLessons(), StudentInfoActivity.this);
                    ((ListView) findViewById(R.id.lvAsiSubjects)).setAdapter(subjectListAdapter);

                    ((ListView) findViewById(R.id.lvAsiSubjects)).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                            deleteSubject(position);
                            return false;
                        }
                    });

                    ((ListView) findViewById(R.id.lvAsiSubjects)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            if (selectedSubject != null && selectedSubject == position) {
                                selectedSubject = null;
                            } else {
                                selectedSubject = position;
                            }
                            subjectListAdapter.setSelectedSubjectPosition(selectedSubject);
                            subjectListAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }.execute(student);
//            student.setLessons(dbHelper.getAllStudentLessonsByStudentId(student.getId()));
        } else {
            student = new Student();
            student.setLessons(new ArrayList<>());
            subjectListAdapter = new LessonsListAdapter(student.getLessons(), StudentInfoActivity.this);

        }
        ((ListView) findViewById(R.id.lvAsiSubjects)).setAdapter(subjectListAdapter);

        ((ListView) findViewById(R.id.lvAsiSubjects)).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                deleteSubject(position);
                return false;
            }
        });

        ((ListView) findViewById(R.id.lvAsiSubjects)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (selectedSubject != null && selectedSubject == position) {
                    selectedSubject = null;
                } else {
                    selectedSubject = position;
                }
                subjectListAdapter.setSelectedSubjectPosition(selectedSubject);
                subjectListAdapter.notifyDataSetChanged();
            }
        });
    }

    private void deleteSubject(int position) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(StudentInfoActivity.this);
        deleteDialog.setTitle("Delete?");
        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                student.getLessons().remove(position);
                subjectListAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        deleteDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subject_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAddSubject: {
                addSubject(null);
                return true;
            }
            case R.id.miUpdateSubject: {
                if (selectedSubject != null) {
                    updateSubject();
                }
                return true;
            }

            default: {
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateSubject() {
        if (selectedSubject != null) {
            AlertDialog.Builder inputDialog = new AlertDialog.Builder(StudentInfoActivity.this);
            inputDialog.setTitle("Subject Title");
            inputDialog.setCancelable(false);
            View inputView = (LinearLayout) getLayoutInflater().inflate(R.layout.subject_input, null);
            inputDialog.setView(inputView);
            final EditText name = inputView.findViewById(R.id.editDialogSubjectName);
            final Spinner mark = inputView.findViewById(R.id.sDialogMark);
            ((EditText) inputView.findViewById(R.id.editDialogSubjectName)).setText(student.getLessons().get(selectedSubject).getName());
            ((Spinner) inputView.findViewById(R.id.sDialogMark))
                    .setSelection(getIndex(mark, student.getLessons().get(selectedSubject).getMark().toString()));

            inputDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    student.getLessons().set(selectedSubject, new Lessons(name.getText().toString(),
                            Integer.parseInt(mark.getSelectedItem().toString())));
                    subjectListAdapter.notifyDataSetChanged();
                }
            }).setNegativeButton("Cancel", null);
            inputDialog.show();
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
    }

    public void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Integer mark;
                switch (menuItem.getItemId()) {
                    case R.id.popup1: {
                        mark = 1;
                        break;
                    }
                    case R.id.popup2: {
                        mark = 2;
                        break;
                    }
                    case R.id.popup3: {
                        mark = 3;
                        break;
                    }
                    case R.id.popup4: {
                        mark = 4;
                        break;
                    }
                    case R.id.popup5: {
                        mark = 5;
                        break;
                    }
                    default:
                        return false;
                }
                if ((student != null) && (position < student.getLessons().size())) {
                    student.getLessons().get(position).setMark(mark);
                    subjectListAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void addSubject(View view) {
        AlertDialog.Builder inputDialog = new AlertDialog.Builder(StudentInfoActivity.this);
        inputDialog.setTitle("Subject Title");
        inputDialog.setCancelable(false);
        View inputView = (LinearLayout) getLayoutInflater().inflate(R.layout.subject_input, null);
        inputDialog.setView(inputView);
        final EditText name = inputView.findViewById(R.id.editDialogSubjectName);
        final Spinner mark = inputView.findViewById(R.id.sDialogMark);

        inputDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                student.addSubject(new Lessons(name.getText().toString(),
                        Integer.parseInt(mark.getSelectedItem().toString())));
                subjectListAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("Cancel", null);
        inputDialog.show();
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

    private void updateStudentState() {
        student.setFio(((TextView) findViewById(R.id.editAsiFIO)).getText().toString());
        student.setFacultet(((TextView) findViewById(R.id.editAsiFaculty)).getText().toString());
        student.setGroup(((TextView) findViewById(R.id.editAsiGroup)).getText().toString());
        student.setPhone(((TextView) findViewById(R.id.editAsiPhone)).getText().toString());
//        student.setLessons(subjectListAdapter.getSubjectList());
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        try {
            AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
            quitDialog.setTitle("Save changes?");
            quitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    updateStudentState();
                    if (student.getFacultet().isEmpty() || student.getFio().isEmpty() || student.getGroup().isEmpty()) {
                        AlertDialog.Builder inputAllFields = new AlertDialog.Builder(quitDialog.getContext());
                        inputAllFields.setTitle("Pls input all Fields");
                        inputAllFields.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        });
                        inputAllFields.show();
                        return;

                    }
                    saveState(null);
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    exit(null);
                }
            });
            quitDialog.show();
        } catch (RuntimeException e) {

        }
        return super.onKeyDown(keyCode, event);
    }
}