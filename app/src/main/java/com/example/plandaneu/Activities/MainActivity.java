package com.example.plandaneu.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.example.plandaneu.R;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import Control.speicherungDerDaten;

/**
 * MainActvity führt alle anderen Activities aus
 * TODO: Ereignisse die gelöscht werden,werden nicht direkt aus der TextView gelöscht (müsste
 *  eigentlich synchron passieren)
 */
public class MainActivity extends AppCompatActivity {
    //Private Attribute
    private TextView aktuTagView;

    //TODO: deadline

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //-----------------------------------------
        //------        Kalenderansicht     -------
        // ----------------------------------------

        aktuTagView = findViewById(R.id.aktuTagView);

        // Aktuelles Datum abrufen
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        // Das aktuelle Datum in den TextView einfügen
        aktuTagView.setText(currentDate);

        // Speicherung ausgabe im Calender
        //https://www.youtube.com/watch?v=knpSbtbPz3o
        CalendarView calendarView = findViewById(R.id.calendarViewMain);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                showEventsForSelectedDate(year, month, dayOfMonth);
            }
        });

        //-----------------------------------------
        //--        Speicherung deadline    -------
        // ----------------------------------------
        // TODO: Deadline


        //------------------------------------------------------------------------------

        //Hier ist der Button für das plus unten rechts hinzuügen von Ereignissen
        ImageButton hinzufuegen = findViewById(R.id.hinzufuegeButton);

        hinzufuegen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ruft meine Methode auf mit dem PopUp-Fenster
                showPopupMenu(v);
            }
        });

        //-----------------------------------------------------------------------------

        //Hier für den Button für das icon unten links ausgabe von Ereignissen
        ImageButton listeNotizen = findViewById(R.id.speicherungListe);
        listeNotizen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent itent = new Intent(MainActivity.this, speicherungDerDaten.class);
                startActivity(itent);

            }
        });
    }


    // -----------------------
    // --     POPUP MENU   ---
    // -----------------------

    /**
     * Es zeigt das Menu an (unten rechts das +)
     * der Aufruf kommt natürlich von meiner popup_menu.xml datei
     * daduch laufen dann erezugeAufgabenActivity, erzeugeNotizenActivity
     * und ereignisActivity.
     *
     * @param view
     */
    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                //Hier für die Notizen Erzeugung
                if (item.getItemId() == R.id.NotizErzeugung ) {
                    Intent intent = new Intent(MainActivity.this, erzeugeNotizActivity.class);
                    startActivity(intent);
                    return true;
                }

                //Hier für die Aufgaben Erzeugung
                if (item.getItemId() == R.id.AufgabenErzeugung) {
                    Intent intent = new Intent(MainActivity.this, erzeugeAufgabenActivity.class);
                    startActivity(intent);
                    return true;
                }

                //Hier für die Ereignisse Erzeugung
                if (item.getItemId() == R.id.EreignissErzeugung) {
                    Intent intent = new Intent(MainActivity.this, erzeugeEreignisActivity.class);
                    startActivity(intent);

                    return true;
                }


                return true;
            }
        });
        popupMenu.show();
    }


    // ------------------------------------
    // --     Datum Speicherungsmenue   ---
    // ------------------------------------

    /***
     * Zeigt die gespeicherten Ereignisse
     * @param year
     * @param month
     * @param dayOfMonth
     */
    private void showEventsForSelectedDate(int year, int month, int dayOfMonth) {
        // Erstellen Sie das ausgewählte Datum
        LocalDate selectedDate = LocalDate.of(year, month + 1, dayOfMonth);
        //Calendar calendar = Calendar.getInstance();

        // Formatierung Datum für den Vergleich
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(Date.from(selectedDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));

        // Erg speichern und aus dem Internen Speicher ausgeben
        StringBuilder eventsText = new StringBuilder();
        File[] filesErg = getFilesDir().listFiles();
        for (File file : filesErg) {
            if (file.getName().startsWith("Ereignisse_")) {
                try {
                    FileInputStream fileInputStream = openFileInput(file.getName());
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;

                    StringBuilder ergBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        ergBuilder.append(line).append("\n");
                    }

                    // entnehme Titel und Zeit und Datum aus dem ergBuilder
                    String[] eventData = ergBuilder.toString().split("\n");
                    String tab = "\t";
                    if (eventData.length >= 4) {
                        //Daten die so aus dem FILE gelesen werden
                        String title = eventData[1];
                        String date = eventData[0];
                        String time = eventData[3];

                        // Überprüfe, ob das Datum mit dem ausgewählten Datum übereinstimmt
                        //gebe dann zeit und Titel der Ereignisse aus
                        if (date.equals(formattedDate)) {
                            eventsText.append(title);
                            eventsText.append("\t \t \t \t\t\t\t");
                            eventsText.append(time + "\n");


                          /*  String eventText = String.format("%-20s %20s", title, time);
                            eventsText.append(eventText).append("\n");*/
                        }
                    }

                    bufferedReader.close();
                    inputStreamReader.close();
                    fileInputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Anzeigen der Ereignisse im TextView
        if (eventsText.length() > 0) {
            aktuTagView.setText(eventsText.toString());
        } else {
            aktuTagView.setText("Keine Ereignisse");
        }
    }
}