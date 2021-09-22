package com.example.a4it_1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainActivity extends AppCompatActivity {
    private Calculate calculate;
    private Resources resources;
    private List<Button> operationButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ll);
        resources = getResources();
        initOperationButtons();
        calculate = new Calculate();
    }

    public void numberButtonsClick(View view) {
        String editText = ((EditText) findViewById(R.id.edit1)).getText().toString();
        ((TextView) findViewById(R.id.edit1)).setText(editText + ((Button) view).getText());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void operationButtonsClick(View view) {
        Button clickedButton = (Button) view;
        clickedButton.setBackgroundColor(resources.getColor(R.color.active_operation));

        operationButtons.forEach(button -> {
            if (!button.equals(clickedButton))
                clickedButton.setBackgroundColor(resources.getColor(R.color.main_buttons));
        });

        Double newOperand = Double.parseDouble(((EditText) findViewById(R.id.edit1)).getText().toString());
        ((TextView) findViewById(R.id.edit1)).setText(String.valueOf(calculate.addNumber(newOperand, clickedButton.getText().toString())));
    }

    private void initOperationButtons() {
        operationButtons = new ArrayList<>(4);
        // Иницируем кнопки операций, чтобы было проще к ним обращаться
        operationButtons.add((Button) findViewById(R.id.bPlus));
        operationButtons.add((Button) findViewById(R.id.bMinus));
        operationButtons.add((Button) findViewById(R.id.bDivision));
        operationButtons.add((Button) findViewById(R.id.bMultip));
    }
}