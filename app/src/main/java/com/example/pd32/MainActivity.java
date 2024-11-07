package com.example.pd32;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private TextView txtDisplay;
    private StringBuilder currentInput;
    private String currentOperator;
    private double firstOperand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtDisplay = findViewById(R.id.txtDisplay);
        currentInput = new StringBuilder();
        currentOperator = "";
        firstOperand = 0;

        // Skaitmenų mygtukai
        setButtonClickListener(R.id.btn0);
        setButtonClickListener(R.id.btn1);
        setButtonClickListener(R.id.btn2);
        setButtonClickListener(R.id.btn3);
        setButtonClickListener(R.id.btn4);
        setButtonClickListener(R.id.btn5);
        setButtonClickListener(R.id.btn6);
        setButtonClickListener(R.id.btn7);
        setButtonClickListener(R.id.btn8);
        setButtonClickListener(R.id.btn9);
        setButtonClickListener(R.id.btnDot);

        // Operatoriai
        setOperatorClickListener(R.id.btnAdd, "+");
        setOperatorClickListener(R.id.btnSubtract, "-");
        setOperatorClickListener(R.id.btnMultiply, "×");
        setOperatorClickListener(R.id.btnDivide, "÷");
        setOperatorClickListener(R.id.btnPercent, "%");
        setOperatorClickListener(R.id.btnOneOverX, "1/x");

        // Kvadratinė šaknis
        Button btnSqrt = findViewById(R.id.btnSqrt);
        btnSqrt.setOnClickListener(v -> {
            double result = Math.sqrt(getCurrentNumber());
            currentInput.setLength(0);
            currentInput.append(result);
            txtDisplay.setText(currentInput.toString());
        });

        // Ženklo keitimas
        Button btnPlusMinus = findViewById(R.id.btnPlusMinus);
        btnPlusMinus.setOnClickListener(v -> {
            double currentNumber = getCurrentNumber();
            currentNumber = -currentNumber;
            currentInput.setLength(0);
            currentInput.append(currentNumber);
            txtDisplay.setText(currentInput.toString());
        });

        // Išvalymas
        Button btnC = findViewById(R.id.btnC);
        btnC.setOnClickListener(v -> {
            currentInput.setLength(0);
            currentOperator = "";
            firstOperand = 0;
            txtDisplay.setText("0");
        });

        // Gryninimas (nustato 0)
        Button btnCE = findViewById(R.id.btnCE);
        btnCE.setOnClickListener(v -> {
            currentInput.setLength(0);
            txtDisplay.setText("0");
        });

        // Atgal (trinama paskutinis simbolis)
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            if (currentInput.length() > 0) {
                currentInput.deleteCharAt(currentInput.length() - 1);
                txtDisplay.setText(currentInput.length() > 0 ? currentInput.toString() : "0");
            }
        });

        // Apskaičiavimas "=" mygtuko paspaudimu
        Button btnEquals = findViewById(R.id.btnEquals);
        btnEquals.setOnClickListener(v -> {
            double secondOperand = getCurrentNumber();
            double result = 0;

            switch (currentOperator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "×":
                    result = firstOperand * secondOperand;
                    break;
                case "÷":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        txtDisplay.setText("Error");
                        return;
                    }
                    break;
                case "%":
                    result = firstOperand * (secondOperand / 100);
                    break;
                case "1/x":
                    if (secondOperand != 0) {
                        result = 1 / secondOperand;
                    } else {
                        txtDisplay.setText("Error");
                        return;
                    }
                    break;
            }

            DecimalFormat df = new DecimalFormat("#.##########");
            currentInput.setLength(0);
            currentInput.append(df.format(result));
            txtDisplay.setText(currentInput.toString());
            currentOperator = "";
            firstOperand = 0;
        });
    }

    // Mygtukų skaitmenims apdoroti
    private void setButtonClickListener(int buttonId) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            String text = button.getText().toString();
            if (currentInput.length() == 1 && currentInput.charAt(0) == '0') {
                currentInput.setLength(0); // Pašalina pradinį 0
            }
            currentInput.append(text);
            txtDisplay.setText(currentInput.toString());
        });
    }

    // Operatoriams apdoroti
    private void setOperatorClickListener(int buttonId, String operator) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            firstOperand = getCurrentNumber();
            currentOperator = operator;
            currentInput.setLength(0);
        });
    }

    // Dabartinio skaičiaus gavimas iš įvesties
    private double getCurrentNumber() {
        try {
            return Double.parseDouble(currentInput.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
