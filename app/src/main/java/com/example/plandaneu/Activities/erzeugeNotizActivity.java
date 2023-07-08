package com.example.plandaneu.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Control.NotizenHandler;

import com.example.plandaneu.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Klasse um Notizen zu erzeugen
 */
public class erzeugeNotizActivity extends AppCompatActivity {
    //private NotizenHandler notizenHandler;
    private static final String FILE_NAME = "NotizenSpeicherung";

    private TextView titelEdit;
    private TextView notizEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erzeuge_notizactivity);

            //Erstelle NotizTitel und EingabetextNotiz
        titelEdit = findViewById(R.id.notizenTitel);
        notizEdit = findViewById(R.id.notizTextEingabe);

            //Speicherung der Notiz
        Button createTaskButton = findViewById(R.id.buttonNotizen);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Notizen notiz = notizenHandler.notizHinzufügen("Beschreibung", "Notiz :");
                //Toast.makeText(erzeugeNotizActivity.this, "Erstellte Notiz: " + notiz.getNotizId(), Toast.LENGTH_SHORT).show();
                save(v);
                finish();

            }
        });


        // zurück zum Home Menue
        ImageButton homebutton = findViewById(R.id.homeButton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(erzeugeNotizActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Diese Methode speichert Daten in einem File in dem Internen Speicher
     * Inspirtation : https://www.youtube.com/watch?v=EcfUkjlL9RI
     * @param view
     */
    public void save(View view) {
        String notiz = titelEdit.getText().toString() + notizEdit.getText().toString();
        String fileName = "Notiz_" + System.currentTimeMillis(); // Verwenden Sie einen eindeutigen Dateinamen für jede Aufgabe
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            String speicherung = notiz;
            fileOutputStream.write(speicherung.getBytes());
            Toast.makeText(this, "Notiz wurde gespeichert.", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}