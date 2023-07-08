package com.example.plandaneu.Activities;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plandaneu.R;

import Control.TerminenHandler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;

import Model.Termin;

/**
 * Diese Klasse erzeugt Ereignisse, und in der aktuTagView(TextView)
 * werden Ereignisse wiedergegeben
 * FÜR CALENDERVIEW DAILY https://www.youtube.com/watch?v=Aig99t-gNqM
 */
public class erzeugeEreignisActivity extends AppCompatActivity {

    //Privtae Attribute

    //private TerminenHandler terminenHandler;
    private DatePickerDialog dpd;
    private EditText titelErg;
    private EditText textErg;
    private Button zeitErg;
    private LocalDate ausgewaehltesDatum;

    private String selectedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.erzeuge_ereignisseactivity);

            //Erstelle TitelEingabeEreignis und TextEingabeErg
        titelErg = findViewById(R.id.TitelEingabeEreignis);
        textErg =findViewById(R.id.TextEingabeErg);

            //Datum auswählen für das Ereignis
        Button createTaskButton = findViewById(R.id.bottonEreigniss);
        createTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

            //Ereigniss Erstellen und speichern
        Button createEreignis = findViewById(R.id.speicherungEreignis);
        createEreignis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });

            //Zeit feststellen für das Ereignis
        zeitErg = findViewById(R.id.btnPick);
        zeitErg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });



            //zurück zum Home Menue
        ImageButton homebutton = findViewById(R.id.homeButton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(erzeugeEreignisActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Diese Methode ist vordefiniert und ermöglicht uns ein Datum auszuwählen
     */
    private void showDatePicker() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        dpd = new DatePickerDialog(erzeugeEreignisActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                ausgewaehltesDatum = LocalDate.of(year, monthOfYear + 1, dayOfMonth);
            }
        }, year, month, day);
        dpd.show();
    }

    /**
     * Speichert die Ereignisse in einem File
     * @param view
     */
    public void save(View view) {
        String titel = titelErg.getText().toString();
        String text = textErg.getText().toString();
        String zeit = selectedTime;
        String datum = ausgewaehltesDatum != null ? ausgewaehltesDatum.toString() : "";

        String ereignis = datum + "\n"+ titel + "\n" + text + "\n" + zeit ;
        String fileName = "Ereignisse_" + System.currentTimeMillis(); // Verwenden Sie einen eindeutigen Dateinamen für jede Aufgabe
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = openFileOutput(fileName, MODE_PRIVATE);
            String speicherung = ereignis;
            fileOutputStream.write(speicherung.getBytes());
            Toast.makeText(this, "Ereignis wurde gespeichert.", Toast.LENGTH_SHORT).show();
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

    /**
     * Zeigt durch den Button die Uhrzeit mit einem TimePickerDialog
     */
    private void showTimePickerDialog() {
        // Aktuelle Zeit abrufen
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

}
