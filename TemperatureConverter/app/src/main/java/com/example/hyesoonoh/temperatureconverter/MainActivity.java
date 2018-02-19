package com.example.hyesoonoh.temperatureconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView history;
    private ScrollView scrollV;
    private double v1, answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        history = (TextView)findViewById(R.id.textView);
        scrollV = (ScrollView)findViewById(R.id.scrollView3);
    }

    public void doConvert(View v) {
        //Button
        RadioGroup radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        RadioButton BFC = (RadioButton)findViewById(R.id.ButtonFC);
        RadioButton BCF = (RadioButton)findViewById(R.id.ButtonCF);

        EditText FC = findViewById(R.id.editText);
        v1 = Double.parseDouble(FC.getText().toString());
        TextView answerText = findViewById(R.id.answer);
        String newText = null;
        String historyText = history.getText().toString();
        //F->C
        if(BFC.isChecked()){
            answer = ((v1 - 32.0) * (5.0 / 9.0));
            answer = Math.round(answer*10)/10.0;
            answerText.setText(answer + "");
            newText = "F to C: " + v1 + "->" + answer;
        }
        //C->F
        if(BCF.isChecked()){
            answer = ((v1 * (9.0 / 5.0)) + 32.0);
            answer = Math.round(answer*10)/10.0;
            answerText.setText(answer + "");
            newText = "C to F: " + v1 + "->" + answer;
        }
        history.setText(newText + "\n" + historyText);
    }
    //history
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("HISTORY", history.getText().toString());
        outState.putDouble("VALUE", v1);
        // Call super last
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Call super first
        super.onRestoreInstanceState(savedInstanceState);
        history.setText(savedInstanceState.getString("HISTORY"));
        v1 = savedInstanceState.getDouble("VALUE");
    }
}