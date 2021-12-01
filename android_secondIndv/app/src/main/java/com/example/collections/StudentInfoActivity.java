package com.example.collections;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.collections.adapters.SubjectListAdapter;
import com.example.collections.models.Student;
import com.example.collections.models.Subject;


public class StudentInfoActivity extends AppCompatActivity {
    private SubjectListAdapter subjectListAdapter;
    private Student student;
    private Integer selectedSubject = null;
    private Integer position;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.subject_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miAddSubject: {
                addSubject();
                return true;
            }
            case R.id.miUpdateSubject: {
                updateSubject();
                return true;
            }
            default: {
            }
        }
        return super.onOptionsItemSelected(item);
    }

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
        if (this.student != null) {
            ((EditText) findViewById(R.id.editStudentFio)).setText(student.getFio());
            ((EditText) findViewById(R.id.editStudentFaculty)).setText(student.getFacultet());
            ((EditText) findViewById(R.id.editStudentGroup)).setText(student.getGroup());


            subjectListAdapter = new SubjectListAdapter(student.getSubjects(), StudentInfoActivity.this);
            ((ListView) findViewById(R.id.lvAsiSubjects)).setAdapter(subjectListAdapter);
        } else {
            this.student = new Student();

            subjectListAdapter = new SubjectListAdapter(student.getSubjects(), StudentInfoActivity.this);
            ((ListView) findViewById(R.id.lvAsiSubjects)).setAdapter(subjectListAdapter);
        }
        this.position = getIntent().getIntExtra("position", -1);

        ((ListView) findViewById(R.id.lvAsiSubjects)).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(StudentInfoActivity.this);
                deleteDialog.setTitle("Delete?");
                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        student.getSubjects().remove(position);
                        subjectListAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                deleteDialog.show();
                return false;
            }
        });

        ((ListView) findViewById(R.id.lvAsiSubjects)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSubject = i;
                subjectListAdapter.setSelectedSubject(selectedSubject);
                subjectListAdapter.notifyDataSetChanged();
            }
        });

//        ((ListView) findViewById(R.id.lvAsiSubjects)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                showPopupMenu(view.findViewById(R.id.tvSubjectMark), position);
//            }
//        });
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
                if ((student != null) && (position < student.getSubjects().size())) {
                    student.getSubjects().get(position).setMark(mark);
                    subjectListAdapter.notifyDataSetChanged();
                }
                return true;
            }
        });
        popupMenu.show();
    }

    public void addSubject() {
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
                student.addSubject(new Subject(name.getText().toString(),
                        Integer.parseInt(mark.getSelectedItem().toString())));
                subjectListAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("Cancel", null);
        inputDialog.show();
//        student.addSubject(new Subject(((EditText) findViewById(R.id.editAsiSubjectName)).getText().toString(),
//                Integer.parseInt(((Spinner) findViewById(R.id.sAsiMark)).getSelectedItem().toString())));
//        subjectListAdapter.notifyDataSetChanged();
    }

    private int getIndex(Spinner spinner, String myString) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                return i;
            }
        }
        return 0;
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
            ((EditText) inputView.findViewById(R.id.editDialogSubjectName)).setText(student.getSubjects().get(selectedSubject).getName());
            ((Spinner) inputView.findViewById(R.id.sDialogMark))
                    .setSelection(getIndex(mark, student.getSubjects().get(selectedSubject).getMark().toString()));

            inputDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (!name.getText().toString().isEmpty())
                        student.addSubject(new Subject(name.getText().toString(),
                                Integer.parseInt(mark.getSelectedItem().toString())));
                    subjectListAdapter.notifyDataSetChanged();
                }
            }).setNegativeButton("Cancel", null);
            inputDialog.show();
        }
    }

    public void saveState(View view) {
        Intent intent = new Intent();
        if (student == null)
            student = Student.builder()
                    .fio(((EditText) findViewById(R.id.editFIO)).getText().toString())
                    .facultet(((EditText) findViewById(R.id.editFaculty)).getText().toString())
                    .group(((EditText) findViewById(R.id.editStudentGroup)).getText().toString())
                    .build();
        intent.putExtra("student", student);
        intent.putExtra("position", position);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void exit(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        String fio = ((EditText) findViewById(R.id.editStudentFio)).getText().toString();
        String faculty = ((EditText) findViewById(R.id.editStudentFaculty)).getText().toString();
        String group = ((EditText) findViewById(R.id.editStudentGroup)).getText().toString();
        if (fio.isEmpty() || faculty.isEmpty() || group.isEmpty()) {
            exit(null);
        }


        AlertDialog.Builder quitDialog = new AlertDialog.Builder(this);
        quitDialog.setTitle("Save changes?");
        quitDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveState(null);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                exit(null);
            }
        });
        quitDialog.show();
    }

    public void updateStudentInfo(View view) {
        student.setFio(((EditText) findViewById(R.id.editStudentFio)).getText().toString());
        student.setFacultet(((EditText) findViewById(R.id.editStudentFaculty)).getText().toString());
        student.setGroup(((EditText) findViewById(R.id.editStudentGroup)).getText().toString());
    }
}