package com.example.a4it_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Operations lastOperation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_ll);

    }

    public void numberButtonsClick(View view) {
        String editText = ((EditText) findViewById(R.id.edit1)).getText().toString();
        if (!editText.equalsIgnoreCase("0"))
            ((TextView) findViewById(R.id.edit1)).setText(editText + ((Button) view).getText());
        else
            ((TextView) findViewById(R.id.edit1)).setText(((Button) view).getText());
    }

    public void removeOneDigit(View view) {
        String editText = ((EditText) findViewById(R.id.edit1)).getText().toString();
        if (editText.length() > 0)
            ((TextView) findViewById(R.id.edit1)).setText(editText.substring(0, editText.length() - 1));
    }

    public void operationButtonsClick(View view) {
        String numTextView = String.valueOf(((TextView) findViewById(R.id.tv1)).getText());
        String operationTextView = String.valueOf(((TextView) findViewById(R.id.operationTextView)).getText());
        String editText = String.valueOf(((EditText) findViewById(R.id.edit1)).getText());
        if (operationTextView.isEmpty() || lastOperation == Operations.equal) {
            ((TextView) findViewById(R.id.tv1)).setText(editText);
            ((EditText) findViewById(R.id.edit1)).setText("");
        } else {
            if (!numTextView.isEmpty() && !operationTextView.isEmpty() && !editText.isEmpty()) {
                double num1 = Double.parseDouble(numTextView);
                double num2 = Double.parseDouble(editText);
                double result = Double.NaN;

                switch (operationTextView) {
                    case "/":
                        if (num2 == 0d) {
                            if (num1 > 0)
                                result = Double.POSITIVE_INFINITY;
                            else if (num1 > 0)
                                result = Double.NEGATIVE_INFINITY;
                            else
                                result = Double.NaN;
                        }
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                }
                ((TextView) findViewById(R.id.tv1)).setText(String.valueOf(result));
                ((EditText) findViewById(R.id.edit1)).setText("");
            }
        }

        ((TextView) findViewById(R.id.operationTextView)).setText(((Button) view).getText());
        lastOperation = convertStringToOperation(String.valueOf(((Button) view).getText()));
    }

    public void clearAll(View view) {
        ((TextView) findViewById(R.id.tv1)).setText("");
        ((TextView) findViewById(R.id.operationTextView)).setText("");
        ((EditText) findViewById(R.id.edit1)).setText("");
    }

    public void dotClick(View view) {
        String editText = String.valueOf(((EditText) findViewById(R.id.edit1)).getText());
        int countOfDot = editText.indexOf(".");
        if (countOfDot == -1) {
            ((EditText) findViewById(R.id.edit1)).setText(editText + ((Button) view).getText());
        }
    }

    public void equalButton(View view) {
        String numTextView = String.valueOf(((TextView) findViewById(R.id.tv1)).getText());
        String operationTextView = String.valueOf(((TextView) findViewById(R.id.operationTextView)).getText());
        String editText = String.valueOf(((EditText) findViewById(R.id.edit1)).getText());
        if (!numTextView.isEmpty() && !operationTextView.isEmpty() && !editText.isEmpty()) {
            double num1 = Double.parseDouble(numTextView);
            double num2 = Double.parseDouble(editText);
            double result = Double.NaN;

            switch (operationTextView) {
                case "/":
                    if (num2 == 0d) {
                        if (num1 > 0)
                            result = Double.POSITIVE_INFINITY;
                        else if (num1 > 0)
                            result = Double.NEGATIVE_INFINITY;
                        else
                            result = Double.NaN;
                    } else
                        result = num1 / num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
            }


            if (lastOperation != Operations.equal) {
                ((TextView) findViewById(R.id.tv1)).setText(String.valueOf(num2));

            }
            ((EditText) findViewById(R.id.edit1)).setText(String.valueOf(result));
            lastOperation = convertStringToOperation(String.valueOf(((Button) view).getText()));
        }
    }


    private Operations convertStringToOperation(String operation) {
        switch (operation) {
            case "+":
                return Operations.plus;
            case "-":
                return Operations.minus;
            case "*":
                return Operations.multpl;
            case "/":
                return Operations.division;
            case "=":
                return Operations.equal;
        }
        return Operations.plus;
    }

    enum Operations {
        plus, minus, multpl, division, equal
    }

}
