package Control;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plandaneu.Activities.MainActivity;
import com.example.plandaneu.Activities.erzeugeEreignisActivity;
import com.example.plandaneu.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class speicherungDerDaten extends AppCompatActivity {

    // -----  Notizen Attribute ------
    private static final String FILE_NAME_NOTIZEN = "NotizenSpeicherung";

    private ArrayList<String> notizenListe;  // ArrayList zum Speichern der Notizen
    private ArrayAdapter<String> notizenAdapter;
    private ListView listViewNotiz;

    // --------- AUFGABEN Attribute ------

    private static final String FILE_NAME_AUFGABEN = "AufgabenSpeicherung";

    private ArrayList<String> aufgabenListe;  // ArrayList zum Speichern der Aufgaben
    private ArrayAdapter<String> aufgabenAdapter;
    private ListView listViewAufgabe;

    // -------- Ereignisse Attribute ------
    private ArrayList<String> ereignissnListe;  // ArrayList zum Speichern der Ereignisse
    private ArrayAdapter<String> ereignissAdapter;
    private ListView listViewEreignisse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speicherung);

        //    ----------------------------------------------
        //    --------          Notizen             --------
        //    ----------------------------------------------

        listViewNotiz = findViewById(R.id.notizenListView);
        notizenListe = new ArrayList<>();
        notizenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notizenListe);
        listViewNotiz.setAdapter(notizenAdapter);

        // -----------  Löschung -----------

        //Ermögliche das klicken auf die Listen
        listViewNotiz.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //um die bestimmt Notiz auszuwählen
        listViewNotiz.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listViewNotiz.setItemChecked(position, true);
            }
        });

        //Wähle den Button aus um die folgende Notiz zu löschen
        ImageButton deleteButton = findViewById(R.id.notizenloescheButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listViewNotiz.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    String notiz = notizenAdapter.getItem(position);
                    deleteNotizFromStorage(notiz);
                    notizenAdapter.remove(notiz);
                    notizenAdapter.notifyDataSetChanged();
                    Toast.makeText(speicherungDerDaten.this, "Notiz wurde endgültig gelöscht.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadNotiz();


        //    ----------------------------------------------
        //    --------          Aufgaben            --------
        //    ----------------------------------------------

        listViewAufgabe = findViewById(R.id.aufgabenListView);
        aufgabenListe = new ArrayList<>();
        aufgabenAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aufgabenListe);
        listViewAufgabe.setAdapter(aufgabenAdapter);

        // ----------   Löschung --------------

        //Ermögliche das klicken auf die Listen
        listViewAufgabe.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //um die bestimmt Aufgabe auszuwählen
        listViewAufgabe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listViewAufgabe.setItemChecked(position, true);
            }
        });

        //Wähle den Button aus um die folgende Aufgabe zu löschen
        ImageButton deleteButtonAufgabe = findViewById(R.id.loescheAufgabenView);
        deleteButtonAufgabe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listViewAufgabe.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    String aufgabe = aufgabenAdapter.getItem(position);
                    deleteAufgabenFromStorage(aufgabe);
                    aufgabenAdapter.remove(aufgabe);
                    aufgabenAdapter.notifyDataSetChanged();
                    Toast.makeText(speicherungDerDaten.this, "Aufgabe wurde endgültig gelöscht.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        loadAufgabe();

        //    ----------------------------------------------
        //    --------          Ereignisse          --------
        //    ----------------------------------------------
        listViewEreignisse = findViewById(R.id.ereignisseListView);
        ereignissnListe = new ArrayList<>();
        ereignissAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ereignissnListe);
        listViewEreignisse.setAdapter(ereignissAdapter);

        // -----------  Löschung -----------

        //Ermögliche das klicken auf die Listen
        listViewEreignisse.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //um die bestimmte Ereignise auszuwählen
        listViewEreignisse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listViewEreignisse.setItemChecked(position, true);
            }
        });

        //Wähle den Button aus um die folgende Ereignisse zu löschen
        ImageButton deleteButtonErg = findViewById(R.id.ereignisseloescheButton);
        deleteButtonErg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = listViewEreignisse.getCheckedItemPosition();
                if (position != ListView.INVALID_POSITION) {
                    String erg = ereignissAdapter.getItem(position);
                    deleteErgFromStorage(erg);
                    ereignissAdapter.remove(erg);
                    ereignissAdapter.notifyDataSetChanged();
                    Toast.makeText(speicherungDerDaten.this, "Ereignis wurde endgültig gelöscht.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadErg();

        // ----------------------------------------------------------------------------------------------------------------------------------

        // zurück zum Home Button
        ImageButton homebutton = findViewById(R.id.homeButton);
        homebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(speicherungDerDaten.this, MainActivity.class);
                startActivity(intent);
            }
        });



    }

    //    --------------------------------------------------------------------------
    //    --------          Notizen    SPEICHERUNG / LÖSCHUNG              --------
    //    --------------------------------------------------------------------------

    /**
     * Gibt die gespeicherten Notizen aus
     * (in erzeugeNotizActivty ist die Speicherung)
     */
    public void loadNotiz() {
        File[] filesNotiz = getFilesDir().listFiles();

        for (File file : filesNotiz) {
            if (file.getName().startsWith("Notiz_")) {
                try {
                    FileInputStream fileInputStream = openFileInput(file.getName());
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;

                    StringBuilder aufgabeBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        aufgabeBuilder.append(line).append("\n");
                    }

                    notizenAdapter.add(aufgabeBuilder.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        notizenAdapter.notifyDataSetChanged();
    }

    /**
     *  Führt die Löschung der Notizen durch
      */
    public void deleteNotizFromStorage(String notiz) {
        File[] files = getFilesDir().listFiles();
        for (File file : files) {
            if (file.getName().startsWith("Notiz_")) {
                try {
                    FileInputStream fileInputStream = openFileInput(file.getName());
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;

                    StringBuilder notizBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        notizBuilder.append(line).append("\n");
                    }

                    String existingNotiz = notizBuilder.toString();
                    if (existingNotiz.equals(notiz)) {
                        boolean deleted = file.delete();
                        if (!deleted) {
                            Log.e("speicherungDerDaten", "Fehler beim Löschen der Notiz: " + file.getName());
                        }
                        break;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //    --------------------------------------------------------------------------
    //    --------          Aufgaben    SPEICHERUNG / LÖSCHUNG              --------
    //    --------------------------------------------------------------------------


    /**
     * Gibt die gespeicherten Aufgaben aus
     * (in erzeugeAufgabeActivty ist die Speicherung)
     */
    public void loadAufgabe() {
        File[] files = getFilesDir().listFiles();
        aufgabenAdapter.clear(); // Clear the adapter before adding the tasks

        for (File file : files) {
            if (file.getName().startsWith("Aufgabe_")) {
                try {
                    FileInputStream fileInputStream = openFileInput(file.getName());
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    String line;

                    StringBuilder aufgabeBuilder = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        aufgabeBuilder.append(line).append("\n");
                    }

                    aufgabenAdapter.add(aufgabeBuilder.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        aufgabenAdapter.notifyDataSetChanged();
    }

    /**
     *  Führt die Löschung der Aufgaben durch
     */
    public void deleteAufgabenFromStorage(String aufgabe){
        File[] fileAufgabe = getFilesDir().listFiles();

        for (File file : fileAufgabe) {
        if (file.getName().startsWith("Aufgabe_")) {
            try {
                FileInputStream fileInputStream = openFileInput(file.getName());
                InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;

                StringBuilder aufgabenBuilder = new StringBuilder();
                while ((line = bufferedReader.readLine()) != null) {
                    aufgabenBuilder.append(line).append("\n");
                }

                String aufgabeExist = aufgabenBuilder.toString();
                if (aufgabeExist.equals(aufgabe)) {
                    boolean deleted = file.delete();
                    if (!deleted) {
                        Log.e("speicherungDerDaten", "Fehler beim Löschen der Aufgabe: " + file.getName());
                    }
                    break;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    }

    //    --------------------------------------------------------------------------
    //    --------          Ereignisse  SPEICHERUNG / LÖSCHUNG              --------
    //    --------------------------------------------------------------------------


    /**
     * Gibt Ereignisse aus
     */
    public void loadErg() {
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
                    ereignissAdapter.add(ergBuilder.toString());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ereignissAdapter.notifyDataSetChanged();
    }

    /**
     *  Führt die Löschung der Ereignisse durch
     */
    public void deleteErgFromStorage(String erg) {
        File[] files = getFilesDir().listFiles();
        for (File file : files) {
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

                    String existingErg = ergBuilder.toString();
                    if (existingErg.equals(erg)) {
                        boolean deleted = file.delete();
                        if (!deleted) {
                            Log.e("speicherungDerDaten", "Fehler beim Löschen dem Ereigniss: " + file.getName());
                        }
                        break;
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }





}
