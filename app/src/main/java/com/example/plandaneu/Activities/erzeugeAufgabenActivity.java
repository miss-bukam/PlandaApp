package com.example.plandaneu.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Control.AufgabenHandler;

import com.example.plandaneu.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Diese Klasse erzeugt Aufgaben
 */
public class erzeugeAufgabenActivity extends AppCompatActivity {

    //Private Attribute
    private EditText titelAufgabe;
    private TextView aufgabenText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erzeuge_aufgabenactivity);

        //Erstelle NotizTitel und EingabetextNotiz
        titelAufgabe = findViewById(R.id.aufgabenTitel);
        aufgabenText = findViewById(R.id.TextEingabe);

        //Clicke auf den button um aufgaben zu speichern
        Button createTaskButton = findViewById(R.id.buttonAufgaben);
        createTaskButton.setOnClickListener(new View.OnClickListener() {

            //Das passiert nachdem ich auf diesen Button geklickt habe
            @Override
            public void onClick(View v) {
                save(v);
                finish();

            }
        });

        // zurück Home Button
        ImageButton homebutton = findViewById(R.id.homeButton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mit der Erzeugung eines Intent wird zur Home Page zurücj geleitet
                Intent intent = new Intent(erzeugeAufgabenActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Diese Methode speichert Daten in einem File
     * Inspirtation : https://www.youtube.com/watch?v=EcfUkjlL9RI
     * @param view
     */
    public void save(View view) { // Speichern der Aufgabe
        String aufgabe = titelAufgabe.getText().toString() + aufgabenText.getText().toString();
        String fileName = "Aufgabe_" + System.currentTimeMillis(); // Verwenden Sie einen eindeutigen Dateinamen für jede Aufgabe
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            String speicherung = aufgabe;
            fileOutputStream.write(speicherung.getBytes());
            Toast.makeText(this, "Aufgabe wurde gespeichert.", Toast.LENGTH_SHORT).show();
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