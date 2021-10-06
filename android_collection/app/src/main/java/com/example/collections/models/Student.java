package com.example.collections.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student implements Parcelable {
    private String fio;
    private String facultet;
    private String group;
    private List<Subject> subjects;


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
}
