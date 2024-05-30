package com.example.speech;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.speech.RecognizerIntent;
import android.widget.Toast;
import android.content.ActivityNotFoundException;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;

public class MainActivity extends AppCompatActivity {
    protected static final int RESULT_SPEECH = 1;
    private TextView tvText;

    @SuppressLint({"MissingInflateParams", "MissingInflateParam", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvText = findViewById(R.id.tvText);
        ImageButton btnSpeak = findViewById(R.id.btnSpeak);
        btnSpeak.setOnClickListener(v -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            try {
                startActivityForResult(intent, RESULT_SPEECH);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Your device doesn't support Speech to Text", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        tvText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = tvText.getText().toString().trim();
                if (!text.isEmpty()) {
                    try {
                        // Evaluate the expression
                        int result = evaluateExpression(text);
                        tvText.setText(String.valueOf(result));
                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), "Invalid expression", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private int evaluateExpression(String expression) {
        String[] parts = expression.split("[+\\-*/]");
        int operand1 = Integer.parseInt(parts[0]);
        int operand2 = Integer.parseInt(parts[1]);
        char operator = expression.charAt(parts[0].length());

        int result = 0;
        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
        }
        return result;
    }
}