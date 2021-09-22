package com.example.a4it_1;

import android.graphics.Path;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Calculate {
    @Getter
    private Double firstOperand;
    private Double secondOperand;
    private Double result;
    private Operations operation;

    public Double addNumber(Double newOperand, String operation) {
        if (firstOperand == null) {
            firstOperand = newOperand;
            this.operation = convertStringToOperation(operation);
            return 0d;
        } else {
            if (secondOperand == null) {
                secondOperand = newOperand;
                return madeOperation(this.operation);
            } else {
                if (operation != null) {
                    result = madeOperation(this.operation);
                    secondOperand = newOperand;
                    this.operation = convertStringToOperation(operation);
                    firstOperand = result;
                    return result;
                }
            }
        }
        return null;
    }

    public double madeOperation(Operations operation) {
        switch (operation) {
            case plus:
                return firstOperand + secondOperand;
            case minus:
                return firstOperand - secondOperand;
            case multpl:
                return firstOperand * secondOperand;
            case division:
                if (secondOperand == 0d)
                    return Double.POSITIVE_INFINITY;
                else
                    return firstOperand / secondOperand;
        }
        return result;
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
        }
        return Operations.plus;
    }

    public void clear() {
        firstOperand = 0d;
        secondOperand = null;
        operation = null;
        result = null;
    }

}

enum Operations {
    plus, minus, multpl, division
}
