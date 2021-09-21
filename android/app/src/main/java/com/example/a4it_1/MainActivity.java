package com.example.a4it_1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ll);

        View.OnClickListener listener = new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String editText = ((EditText) findViewById(R.id.edit1)).getText().toString();
                ((TextView) findViewById(R.id.edit1)).setText(editText + ((Button) view).getText());
            }
        };

        ((Button) findViewById(R.id.b1)).setOnClickListener(listener);
        ((Button) findViewById(R.id.b2)).setOnClickListener(listener);
        ((Button) findViewById(R.id.b3)).setOnClickListener(listener);
        ((Button) findViewById(R.id.b4)).setOnClickListener(listener);
        ((Button) findViewById(R.id.b5)).setOnClickListener(listener);
    }

    public void copyClick(View view) {
        String editText = ((EditText) findViewById(R.id.edit1)).getText().toString();
        ((TextView) findViewById(R.id.edit1)).setText(editText);
    }

    public void plusOne(View view) {
        String editText = ((EditText) findViewById(R.id.edit1)).getText().toString();
        Integer editTextNumber;
        if (editText.isEmpty())
            editTextNumber = 0;
        else
            editTextNumber = Integer.parseInt(editText);
        editTextNumber++;
        ((TextView) findViewById(R.id.edit1)).setText(String.valueOf(editTextNumber));
    }
}