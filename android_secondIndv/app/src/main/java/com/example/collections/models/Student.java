package com.example.collections.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
public class Student implements Parcelable {
    private String fio;
    private String facultet;
    private String group;
    private List<Subject> subjects;

    public String getFio() {
        return fio;
    }

    public Student() {
        this.subjects = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Student{" +
                "fio='" + fio + '\'' +
                ", facultet='" + facultet + '\'' +
                ", group='" + group + '\'' +
                ", subjects=" + subjects +
                '}';
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getFacultet() {
        return facultet;
    }

    public void setFacultet(String facultet) {
        this.facultet = facultet;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Student(String fio, String facultet, String group, List<Subject> subjects) {
        this.fio = fio;
        this.facultet = facultet;
        this.group = group;
        this.subjects = new ArrayList<>();
    }

    protected Student(Parcel in) {
        fio = in.readString();
        facultet = in.readString();
        group = in.readString();
        subjects = in.createTypedArrayList(Subject.CREATOR);
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fio);
        parcel.writeString(facultet);
        parcel.writeString(group);
        parcel.writeTypedList(subjects);
    }

    public int addSubject(Subject subject) {
        subjects.add(subject);
        return subjects.size();
    }
}
