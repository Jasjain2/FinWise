package com.example.finwise;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tvInput;
    String currentInput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvInput = findViewById(R.id.tvInput);

        int[] numberBtnIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3,
                R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7,
                R.id.btn8, R.id.btn9, R.id.btnDot
        };

        int[] operatorBtnIds = {
                R.id.btnPlus, R.id.btnMinus, R.id.btnMultiply,
                R.id.btnDivide, R.id.btnMod
        };

        // Number and dot buttons
        View.OnClickListener numberClickListener = v -> {
            Button b = (Button) v;
            currentInput += b.getText().toString();
            tvInput.setText(currentInput);
        };

        for (int id : numberBtnIds) {
            findViewById(id).setOnClickListener(numberClickListener);
        }

        // Operator buttons
        View.OnClickListener operatorClickListener = v -> {
            Button b = (Button) v;
            if (!currentInput.isEmpty() && !endsWithOperator(currentInput)) {
                currentInput += " " + b.getText().toString() + " ";
                tvInput.setText(currentInput);
            }
        };

        for (int id : operatorBtnIds) {
            findViewById(id).setOnClickListener(operatorClickListener);
        }

        // Equals button
        findViewById(R.id.btnEquals).setOnClickListener(v -> {
            try {
                String result = evaluate(currentInput);
                tvInput.setText(result);
                currentInput = result;
            } catch (Exception e) {
                tvInput.setText("Error");
                currentInput = "";
            }
        });

        // Clear button
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            currentInput = "";
            tvInput.setText("0");
        });

        // Delete button
        findViewById(R.id.btnDel).setOnClickListener(v -> {
            if (!currentInput.isEmpty()) {
                currentInput = currentInput.trim();
                if (currentInput.endsWith(" ")) {
                    currentInput = currentInput.substring(0, currentInput.length() - 3);
                } else {
                    currentInput = currentInput.substring(0, currentInput.length() - 1);
                }
                tvInput.setText(currentInput.isEmpty() ? "0" : currentInput);
            }
        });
    }

    private boolean endsWithOperator(String input) {
        return input.endsWith(" + ") || input.endsWith(" - ") ||
                input.endsWith(" × ") || input.endsWith(" ÷ ") || input.endsWith(" % ");
    }

    private String evaluate(String input) {
        input = input.replaceAll("×", "*").replaceAll("÷", "/");
        String[] tokens = input.split(" ");
        if (tokens.length < 3) return input;

        double result = Double.parseDouble(tokens[0]);

        for (int i = 1; i < tokens.length; i += 2) {
            String op = tokens[i];
            double num = Double.parseDouble(tokens[i + 1]);

            switch (op) {
                case "+": result += num; break;
                case "-": result -= num; break;
                case "*": result *= num; break;
                case "/": result /= num; break;
                case "%": result %= num; break;
            }
        }

        if (result == (long) result)
            return String.valueOf((long) result);
        else
            return String.valueOf(result);
    }
}
