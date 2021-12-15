package com.example.collections.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.collections.models.Lessons;
import com.example.collections.models.Student;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "student.db";

    // Table name: Student.
    private static final String TABLE_STUDENT = "students";

    private static final String COLUMN_STUDENT_ID = "student_id";
    private static final String COLUMN_STUDENT_FIO = "student_fio";
    private static final String COLUMN_STUDENT_FACULTY = "student_faculty";
    private static final String COLUMN_STUDENT_GROUP = "student_group";
    private static final String COLUMN_STUDENT_PHONE = "student_phone";

    private static final String createStudentTable =
            "create table if not exists students" +
                    " (" +
                    " student_id      INTEGER not null " +
                    "constraint students_pk " +
                    "primary key autoincrement," +
                    "student_fio     text    not null," +
                    "student_faculty text," +
                    "student_group   text," +
                    "student_phone   text" +
                    "); ";

    // Table name: Lessons
    private static final String TABLE_LESSONS = "lessons";

    private static final String COLUMN_LESSONS_ID = "lesson_id";
    private static final String COLUMN_LESSONS_TITLE = "lesson_title";

    private static final String createLessonsTable =
            "create table if not exists lessons" +
                    " (" +
                    "  lesson_id    INTEGER not null " +
                    "          constraint lessons_pk  " +
                    "              primary key autoincrement," +
                    "lesson_title text    not null" +
                    "); ";

    // Table name: student_lessons
    private static final String TABLE_STUDENT_LESSONS = "student_lessons";

    private static final String COLUMN_STUDENT_LESSONS_LESSON_ID = "lesson_id";
    private static final String COLUMN_STUDENT_LESSONS_STUDENT_ID = "student_id";
    private static final String COLUMN_STUDENT_LESSONS_MARK = "lesson_mark";

    private static final String createStudentLessonsTable =
            "create table if not exists student_lessons" +
                    " (" +
                    "student_id  integer not null " +
                    "                   references students" +
                    "                    on update cascade on delete cascade," +
                    " lesson_id   integer not null" +
                    "                   references lessons " +
                    "                 on update cascade on delete cascade," +
                    "lesson_mark integer not null" +
                    "); ";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Execute Script.
        db.execSQL(createStudentTable);
        db.execSQL(createLessonsTable);
        db.execSQL(createStudentLessonsTable);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);

        // Create tables again
        onCreate(db);
    }

    public List<Student> getAllStudents() {
        List<Student> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from students;", null);
        if (cursor.moveToFirst()) {
            do {
                result.add(Student.builder()
                        .id(cursor.getString(0))
                        .fio(cursor.getString(1))
                        .facultet(cursor.getString(2))
                        .group(cursor.getString(3))
                        .phone(cursor.getString(4))
                        .build());
            } while (cursor.moveToNext());
        }
        return result;
    }

    public List<Lessons> getAllLessons() {
        List<Lessons> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from lessons;", null);
        if (cursor.moveToFirst()) {
            do {
                result.add(Lessons.builder()
                        .id(cursor.getString(0))
                        .name(cursor.getString(1))
                        .build());
            } while (cursor.moveToNext());
        }
        return result;
    }

    public List<String> getStudentLessons() {
        List<String> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from student_lessons;", null);
        if (cursor.moveToFirst()) {
            do {
                result.add(String.format(" student_id: %s \n lesson_id: %s\n lesson_mark: %s",
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                ));
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void addStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_STUDENT_FIO, student.getFio().isEmpty() ? "" : student.getFio());
        values.put(COLUMN_STUDENT_FACULTY, student.getFacultet().isEmpty() ? "" : student.getFacultet());
        values.put(COLUMN_STUDENT_GROUP, student.getGroup().isEmpty() ? "" : student.getGroup());
        values.put(COLUMN_STUDENT_PHONE, student.getPhone().isEmpty() ? "" : student.getPhone());

        // Inserting Row
        db.insert(TABLE_STUDENT, null, values);
    }

    public void deleteStudent(String student_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Student> list1 = getAllStudents();
        db.delete(TABLE_STUDENT, "student_id = ?", new String[]{String.valueOf(student_id)});
        List<Student> list = getAllStudents();
    }


    public List<Lessons> getAllStudentLessonsByStudentId(String student_id) {
        List<Lessons> result = new ArrayList<>();

        String query = String.format("select lessons.lesson_id, lesson_title, lesson_mark\n" +
                        "from lessons,\n" +
                        "     student_lessons\n" +
                        "where lessons.lesson_id in (select lesson_id\n" +
                        "                            from student_lessons\n" +
                        "                            where student_id = %s)\n" +
                        "  and student_lessons.student_id = %s\n" +
                        "  and lessons.lesson_id = student_lessons.lesson_id;",
                student_id,
                student_id
        );

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                result.add(Lessons.builder()
                        .id(cursor.getString(0))
                        .name(cursor.getString(1))
                        .mark(cursor.getInt(2))
                        .build());
            } while (cursor.moveToNext());
        }
        return result;
    }

    public void addLesson(Lessons lesson, String student_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues lessonContent = new ContentValues();
        lessonContent.put(COLUMN_LESSONS_TITLE, lesson.getName());

        // Inserting Row
        db.insert(TABLE_LESSONS, null, lessonContent);

        String query = String.format("select * from lessons\n" +
                "where lesson_title = '%s'", lesson.getName());
        Lessons lessons = null;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                lessons = Lessons.builder()
                        .id(cursor.getString(0))
                        .name(cursor.getString(1))
                        .mark(cursor.getInt(2))
                        .build();
            } while (cursor.moveToNext());
        }
        if (lessons != null) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_STUDENT_LESSONS_MARK, lesson.getMark());
            values.put(COLUMN_STUDENT_LESSONS_STUDENT_ID, student_id);
            values.put(COLUMN_STUDENT_LESSONS_LESSON_ID, lessons.getId());
            // Inserting Row
            db.insert(TABLE_LESSONS, null, values);
        }

        // Closing database connection
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void saveStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        String updateStudentQuery;
        if (student.getId() != null) {
            updateStudentQuery = String.format("insert or\n" +
                            "replace into students (student_id, student_fio, student_faculty, student_group, student_phone)\n" +
                            "values ('%s', '%s','%s','%s','%s');",
                    student.getId(),
                    student.getFio(),
                    student.getFacultet(),
                    student.getGroup(),
                    student.getPhone()
            );
        } else {
            updateStudentQuery = String.format("insert or\n" +
                            "replace into students (student_fio, student_faculty, student_group, student_phone)\n" +
                            "values ('%s','%s','%s','%s');",
                    student.getFio(),
                    student.getFacultet(),
                    student.getGroup(),
                    student.getPhone()
            );
        }

        db.execSQL(updateStudentQuery);

        List<Student> studentList = getAllStudents();

        String deleteStudentLessonsQuery = String.format("delete from student_lessons\n" +
                        "where student_id = '%s';",
                student.getId()
        );
        db.execSQL(deleteStudentLessonsQuery);
        if (student.getLessons() != null) {
            student.getLessons().forEach(lesson -> {
                List<Lessons> lessons = getAllLessons();
                List<String> studentLessons = getStudentLessons();
                String saveLessonQuery;
                String updateStudentLessonsQuery;
                if (lesson.getId() != null) {
                    saveLessonQuery = String.format("insert or\n" +
                                    "replace into lessons (lesson_id, lesson_title)\n" +
                                    "values ('%s', '%s');",
                            lesson.getId(),
                            lesson.getName());
                    updateStudentLessonsQuery = String.format("insert or\n" +
                                    "replace into student_lessons (student_id, lesson_id, lesson_mark)\n" +
                                    "values ('%s', '%s', '%s');",
                            student.getId(),
                            lesson.getId(),
                            lesson.getMark());
                } else {
                    saveLessonQuery = String.format("insert or\n" +
                                    "replace into lessons (lesson_title)\n" +
                                    "values ('%s');",
                            lesson.getName());
                    db.execSQL(saveLessonQuery);
                    String query = String.format("select * from lessons\n" +
                            "where lesson_title = '%s'", lesson.getName());
                    Lessons lastLesson = null;
                    Cursor cursor = db.rawQuery(query, null);
                    if (cursor.moveToFirst()) {
                        do {
                            lastLesson = Lessons.builder()
                                    .id(cursor.getString(0))
                                    .name(cursor.getString(1))
                                    .build();
                        } while (cursor.moveToNext());
                    }

                    updateStudentLessonsQuery = String.format("insert or\n" +
                                    "replace into student_lessons (student_id, lesson_id, lesson_mark)\n" +
                                    "values ('%s', '%s', '%s');",
                            student.getId(),
                            lastLesson.getId(),
                            lesson.getMark());
                }
                db.execSQL(saveLessonQuery);
                db.execSQL(updateStudentLessonsQuery);
                studentLessons = getStudentLessons();
                System.out.println(studentLessons);
            });
        }
    }

}
