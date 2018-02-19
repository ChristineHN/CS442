package com.example.hyesoonoh.quicknotes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.JsonReader;
import android.util.JsonWriter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.util.Date;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends AppCompatActivity {

    private String current;
    private TextView timestamp;
    private EditText inputText;
    private notePad notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        current = DateFormat.getDateTimeInstance().format(new Date());
        timestamp = (TextView) findViewById(R.id.dateTime);
        timestamp.setText(current);
        inputText = (EditText) findViewById(R.id.notepad);
        inputText.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    protected void onResume() {
        notes = loadNote();
        if (notes != null) {
            timestamp.setText(notes.getTimeStamp());
            inputText.setText(notes.getInputText());
        }
        super.onResume();
    }

    private notePad loadNote() {

        notes = new notePad();
        try {
            InputStream is = getApplicationContext().openFileInput(getString(R.string.file_name));
            JsonReader reader = new JsonReader(new InputStreamReader(is, getString(R.string.encoding)));

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("dateTime")) {
                    notes.setTimeStamp(reader.nextString());
                } else if (name.equals("notepad")) {
                    notes.setInputText(reader.nextString());
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();

        } catch (FileNotFoundException e) {
            notes.setTimeStamp(current);
            Toast.makeText(this, getString(R.string.no_file), Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return notes;
    }

    @Override
    protected void onPause(){
        notes.setTimeStamp(timestamp.getText().toString());
        notes.setInputText(inputText.getText().toString());
        super.onPause();
    }

    @Override
    protected void onStop() {
        saveNotePad();
        super.onStop();
    }

    private void saveNotePad() {
        //notes.setTimeStamp(timestamp.getText().toString());
        notes.setTimeStamp(current);

        try {
            FileOutputStream fos = getApplicationContext().openFileOutput(getString(R.string.file_name), Context.MODE_PRIVATE);
            JsonWriter writer = new JsonWriter(new OutputStreamWriter(fos, getString(R.string.encoding)));
            writer.setIndent("  ");
            writer.beginObject();
            writer.name("dateTime").value(notes.getTimeStamp());
            writer.name("notepad").value(notes.getInputText());
            writer.endObject();
            writer.close();
            Toast.makeText(this, getString(R.string.saved), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}