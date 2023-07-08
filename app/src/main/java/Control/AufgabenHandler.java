package Control;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import Model.Aufgaben;
import Model.Notizen;
import Model.SpeicherInterface;

/**
 * verwaltet alle Aufgaben und lässt den User auf die Methoden zugreifen
 */

public class AufgabenHandler {


    private int nextId;
    private Map<Integer, Aufgaben> aufgabenMap;
    private static final String AUFGABEN_SERVER = "/Users/gizembiliktue/Desktop/HTW/3.Semester/MobBetNet/aufgabendatei.txt";


    /**
     * Konstruktor, der eine Map und die nächste ID benutzt
     * und gleichzeitig die Aufgaben aus der Datei ladet
     *
     * @throws IOException wenn Daten aus Datei nicht gelesen werden können
     */
    public AufgabenHandler() throws IOException {
        aufgabenMap = new HashMap<>();
        //ladenAusDatei();
        nextId = aufgabenMap.size() + 1;

    }

    /**
     * erstellt Aufgabe mit Beschreibung und Priorität,
     * tut sie in die Map und speichert sie in die Datei
     *
     * @param b    Beschreibung
     * @param prio Priorität
     * @return neue Aufgabe
     * @throws IOException wenn in die Datei nicht reingeschrieben werden kann
     */
    public Aufgaben erstelleAufgabe(String b, int prio, boolean erledigt) throws IOException {
        Aufgaben aufgabe = new Aufgaben(nextId, b, prio, erledigt);
        aufgabenMap.put(nextId, aufgabe);
        // speichernInDatei();
        nextId++;
        System.out.println("Sie haben die Aufgabe erfolgreich hinzugefügt.");
        return aufgabe;
    }

    /**
     * sucht die gewünschte Aufgabe mit der ID in der Map,
     * ändert die Priorität von einer erstellten Aufgabe
     * und gibt die aktualisierte Aufgabe aus
     *
     * @param id       ID
     * @param neuePrio neue Priorität
     * @return aktualisierte Aufgabe
     * @throws IOException              wenn in die Datei nicht reingeschrieben werden kann
     * @throws IllegalArgumentException wenn Aufgabe nicht gefunden wurde
     */
    public Aufgaben aufgabePrioBearbeiten(int id, int neuePrio) throws IOException, IllegalArgumentException {
        Aufgaben aufgabe = aufgabenMap.get(id);
        if (aufgabe != null) {
            aufgabe.setPriorität(neuePrio);
            // speichernInDatei();
            System.out.println("Sie haben die Aufgabe erfolgreich bearbeitet.");
            return aufgabe;
        } else {
            throw new IllegalArgumentException("Aufgabe wurde nicht gefunden.");
        }
    }

    /**
     * sucht die gewünschte Aufgabe mit der ID in der Map,
     * ändert die Beschreibung von einer erstellten Aufgabe
     * und gibt die aktualisierte Aufgabe aus
     *
     * @param id    ID
     * @param neueB neue Beschreibung
     * @return aktualisierte Aufgabe
     * @throws IOException              wenn in die Datei nicht reingeschrieben werden kann
     * @throws IllegalArgumentException wenn Aufgabe nicht gefunden wurde
     */
    public Aufgaben aufgabeBeschreibungBearbeiten(int id, String neueB) throws IOException, IllegalArgumentException {
        Aufgaben aufgabe = aufgabenMap.get(id);
        if (aufgabe != null) {
            aufgabe.setBeschreibung(neueB);
            // speichernInDatei();
            System.out.println("Sie haben die Aufgabe erfolgreich bearbeitet.");
            return aufgabe;
        } else {
            throw new IllegalArgumentException("Aufgabe wurde nicht gefunden.");
        }
    }

    /**
     * löscht die Aufgabe durch die Eingabe der ID aus der Map
     * und schreibt es in die Datei ein
     *
     * @param id ID
     * @throws IllegalArgumentException wenn Aufgabe nicht gefunden wurde
     * @throws IOException              wenn in die Datei nicht reingeschrieben werden kann
     */
    public void aufgabeLöschen(int id) throws IllegalArgumentException, IOException {
        Aufgaben aufgabe = aufgabenMap.remove(id);
        if (aufgabe != null) {
            // speichernInDatei();
            System.out.println("Sie haben die Aufgabe erfolgreich gelöscht.");
        } else {
            throw new IllegalArgumentException("Aufgabe nicht gefunden.");
        }
    }

    /**
     * kennzeichnet eine Aufgabe als erledigt
     *
     * @param id ID
     * @throws IllegalArgumentException wenn Aufgabe nicht gefunden wurde
     */
    public void aufgabeErledigen(int id) throws IllegalArgumentException {
        Aufgaben aufgabe = aufgabenMap.get(id);
        if (aufgabe != null) {
            aufgabe.setAufgabeErledigt();
        } else {
            throw new IllegalArgumentException("Aufgabe nicht gefunden.");
        }
    }

    /**
     * liefert die gewünschte Aufgabe aus der Map
     *
     * @param id ID
     * @return gewünschte Aufgabe
     */
    public Aufgaben getAufgabe(int id) {
        return aufgabenMap.get(id);
    }

    /**
     * gibt alle Aufgaben im Form einer lesbaren Map aus
     *
     * @return Aufgaben als Map
     */
    public Map<Integer, String[]> getAlleAufgaben() {
        Map<Integer, String[]> aufgaben = new HashMap<>();
        for (Aufgaben aufgabe : aufgabenMap.values()) {
            String[] aufgabenDaten = new String[3];
            aufgabenDaten[0] = String.valueOf(aufgabe.getAufgabenId());
            aufgabenDaten[1] = aufgabe.getBeschreibung();
            aufgabenDaten[2] = String.valueOf(aufgabe.getPriorität());
            aufgaben.put(aufgabe.getAufgabenId(), aufgabenDaten);
        }
        return aufgaben;
    }

    /**
     * gibt die AufgabenMap aus
     *
     * @return AufgabenMap
     */
    public Map<Integer, Aufgaben> getAufgabenMap() {
        return aufgabenMap;
    }

    /**
     * speichert die AufgabenDaten in eine Textdatei
     * @throws IOException wenn in die Datei nicht rein geschreiben werden kann
     * TODO: File reinschreiben mit getexternalildir
     */


    /**
     * liest die geschriebenen Daten aus der Textdatei aus
     * @throws IOException wenn in die Datei (Speicher) nicht reingelesen werden kann
     * @throws NumberFormatException falls ein ungültiges Format in der Kontendatei besteht

    // @Override
    public void ladenAusDatei() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(AUFGABEN_SERVER))) {
    String line;
    while ((line = reader.readLine()) != null) {
    String[] parts = line.split(",");
    int id = Integer.parseInt(parts[0]);
    String b = parts[1];
    int prio = Integer.parseInt(parts[2]);
    boolean e = Boolean.parseBoolean(parts[3]);
    Aufgaben aufgabe = new Aufgaben(id,b,prio,e);
    aufgabenMap.put(id, aufgabe);
    nextId = Math.max(nextId, id + 1);
    }
    } catch (IOException e) {
    System.out.println("Fehler beim Lesen der Aufgabendatei: " + e.getMessage());
    } catch (NumberFormatException e) {
    System.out.println("Ungültiges Format in der Aufgabendatei: " + e.getMessage());
    }
    }
     */


    //Speichern
}

