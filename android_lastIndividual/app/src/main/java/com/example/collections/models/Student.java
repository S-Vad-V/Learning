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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Parcelable {
    private String id;
    private String fio;
    private String facultet;
    private String group;
    private String phone;
    private List<Lessons> lessons;

    public String getFio() {
        return fio;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fio='" + fio + '\'' +
                ", facultet='" + facultet + '\'' +
                ", group='" + group + '\'' +
                ", lessons=" + lessons +
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

    public List<Lessons> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lessons> lessons) {
        this.lessons = lessons;
    }

    public Student(String fio, String facultet, String group, List<Lessons> lessons) {
        this.fio = fio;
        this.facultet = facultet;
        this.group = group;
        this.lessons = new ArrayList<>();
    }

    protected Student(Parcel in) {
        id = in.readString();
        fio = in.readString();
        facultet = in.readString();
        group = in.readString();
        phone = in.readString();
        lessons = in.createTypedArrayList(Lessons.CREATOR);
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
        parcel.writeString(id);
        parcel.writeString(fio);
        parcel.writeString(facultet);
        parcel.writeString(group);
        parcel.writeString(phone);
        parcel.writeTypedList(lessons);
    }

    public int addSubject(Lessons lessons) {
        this.lessons.add(lessons);
        return this.lessons.size();
    }
}
