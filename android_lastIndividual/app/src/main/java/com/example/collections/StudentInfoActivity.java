package com.example.collections;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.collections.models.Student;
import com.example.collections.models.Lessons;


public class StudentInfoActivity extends AppCompatActivity {
    private LessonsListAdapter lessonsListAdapter;
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

        lessonsListAdapter = new LessonsListAdapter(student.getLessons(), StudentInfoActivity.this);
        ((ListView) findViewById(R.id.lvAsiSubjects)).setAdapter(lessonsListAdapter);

        ((ListView) findViewById(R.id.lvAsiSubjects)).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(StudentInfoActivity.this);
                deleteDialog.setTitle("Delete?");
                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        student.getLessons().remove(position);
                        lessonsListAdapter.notifyDataSetChanged();
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
                if ((student != null) && (position < student.getLessons().size())) {
                    student.getLessons().get(position).setMark(mark);
                    lessonsListAdapter.notifyDataSetChanged();
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
                lessonsListAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("Cancel", null);
        inputDialog.show();
//        student.addSubject(new Subject(((EditText) findViewById(R.id.editAsiSubjectName)).getText().toString(),
//                Integer.parseInt(((Spinner) findViewById(R.id.sAsiMark)).getSelectedItem().toString())));
//        subjectListAdapter.notifyDataSetChanged();
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

    @Override
    public void onBackPressed() {
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
}