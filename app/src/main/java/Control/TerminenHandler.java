package Control;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import Model.Aufgaben;
import Model.SpeicherInterface;
import Model.Termin;

import java.io.*;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * verwaltet alle Termine und lässt den User auf die Methoden zugreifen
 */
public class TerminenHandler implements SpeicherInterface {
    private Context context;
    private int nextId;
    private Map<Integer, Termin> terminMap;
    private static final String TERMINEN_SERVER = "/Users/gizembiliktue/Desktop/HTW/3.Semester/MobBetNet/terminendatei.txt";

    /**
     * Konstruktor, der eine Map und die nächste ID benutzt
     * und gleichzeitig die Termine aus der Datei ladet
     *
     * @throws IOException    wenn Daten aus Datei nicht gelesen werden können
     * @throws ParseException wenn beim Umwandeln von Daten in ein bestimmtes Format Probleme gibt
     */
    public TerminenHandler() throws IOException, ParseException {
        this.terminMap = new HashMap<>();
        ladenAusDatei();
        nextId = terminMap.size() + 1;

    }


    /**
     * Konstruktor der einen Context als Parameter akzeptiert um
     * getFilesDir() aufzurufen zu können.
     * @param context
     */
    public TerminenHandler(Context context) {
        this.context = context;
    }
    /**
     * erstellt Termin mit Titel, Beschreibung und Datum,
     * tut sie in die Map und speichert sie in die Datei
     *
     * @param titel Titel
     * @param b     Beschreibung
     * @param datum Datum
     * @return neuer Termin
     * @throws IOException wenn in die Datei nicht reingeschrieben werden kann
     */
    public Termin terminErstellen(String titel, String b, LocalDateTime datum) throws IOException {
        Termin termin = new Termin(nextId, titel, b, datum);
        terminMap.put(nextId, termin);
        speichernInDatei();
        nextId++;
        System.out.println("Sie haben den Termin erfolgreich hinzugefügt.");
        return termin;
    }

    /**
     * sucht den gewünschten Termin mit der ID in der Map,
     * ändert Titel und gibt den aktualisierten Termin aus
     *
     * @param id ID
     * @param t  Titel
     * @return aktualisierter Termin
     * @throws IOException              wenn in die Datei nicht reingeschrieben werden kann
     * @throws IllegalArgumentException wenn Termin nicht gefunden wurde
     */
    public Termin titelBearbeiten(int id, String t) throws IOException, IllegalArgumentException {
        Termin termin = terminMap.get(id);
        if (termin != null) {
            termin.setTitel(t);
            System.out.println("Sie haben den Titel des Termins erfolgreich bearbeitet.");
            return termin;
        } else {
            throw new IllegalArgumentException("Termin wurde nicht gefunden.");
        }
    }

    /**
     * sucht den gewünschten Termin mit der ID in der Map,
     * ändert Beschreibung und gibt den aktualisierten Termin aus
     *
     * @param id ID
     * @param b  Beschreibung
     * @return aktualisierter Termin
     * @throws IOException              wenn in die Datei nicht reingeschrieben werden kann
     * @throws IllegalArgumentException wenn Termin nicht gefunden wurde
     */
    public Termin textBearbeiten(int id, String b) throws IOException, IllegalArgumentException {
        Termin termin = terminMap.get(id);
        if (termin != null) {
            termin.setBeschreibung(b);
            System.out.println("Sie haben den Text zum Termin erfolgreich bearbeitet.");
            return termin;
        } else {
            throw new IllegalArgumentException("Termin wurde nicht gefunden.");
        }
    }

    /**
     * sucht den gewünschten Termin mit der ID in der Map,
     * ändert Datum und gibt den aktualisierten Termin aus
     *
     * @param id    ID
     * @param datum Datum
     * @return aktualisierter Termin
     * @throws IOException              wenn in die Datei nicht reingeschrieben werden kann
     * @throws IllegalArgumentException wenn Termin nicht gefunden wurde
     */
    public Termin datumBearbeiten(int id, LocalDateTime datum) throws IOException, IllegalArgumentException {
        Termin termin = terminMap.get(id);
        if (termin != null) {
            termin.setDatum(datum);
            System.out.println("Sie haben das Datum des Termins erfolgreich bearbeitet.");
            return termin;
        } else {
            throw new IllegalArgumentException("Termin wurde nicht gefunden.");
        }
    }

    /**
     * löscht den Termin durch die Eingabe der ID aus der Map
     * und schreibt es in die Datei ein
     *
     * @param id ID
     * @throws IllegalArgumentException wenn Termin nicht gefunden wurde
     * @throws IOException              wenn in die Datei nicht reingeschrieben werden kann
     */
    public void terminLöschen(int id) throws IllegalArgumentException, IOException {
        Termin termin = terminMap.remove(id);
        if (termin != null) {
            speichernInDatei();
            System.out.println("Sie haben den Termin erfolgreich gelöscht.");
        } else {
            throw new IllegalArgumentException("Termin nicht gefunden.");
        }
    }

    /**
     * liefert den gewünschten Termin aus der Map
     *
     * @param id ID
     * @return gewünschter Termin
     */
    public Termin getTermin(int id) {
        return terminMap.get(id);
    }

    /**
     * gibt alle Termine im Form einer lesbaren Map aus
     *
     * @return Termine als Map
     */
    public Map<Integer, String[]> getAlleTermine() {
        Map<Integer, String[]> termine = new HashMap<>();
        for (Termin t : terminMap.values()) {
            String[] terminDaten = new String[3];
            terminDaten[0] = String.valueOf(t.getTerminId());
            terminDaten[1] = t.getTitel();
            terminDaten[2] = t.getBeschreibung();
            terminDaten[3] = String.valueOf(t.getDatum());
            termine.put(t.getTerminId(), terminDaten);
        }
        return termine;
    }

    /**
     * gibt die TerminenMap aus
     *
     * @return TerminenMap
     */
    public Map<Integer, Termin> getTerminMap() {
        return terminMap;
    }

    public void erinnerungAnzeigen() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (Termin t : terminMap.values()) {
            if (t.getDatum().isAfter(now)) {
                System.out.println("Erinnerung: " + t);
            }
        }
    }

    /**
     * speichert die AufgabenDaten in eine Textdatei
     *
     * @throws IOException wenn in die Datei nicht rein geschreiben werden kann
     */
  /*  @Override
    public void speichernInDatei() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TERMINEN_SERVER))) {
            for (Termin t : terminMap.values()) {
                writer.write("ID: " + t.getTerminId() + ", Titel: " + t.getTitel() + ", Beschreibung: "
                        + t.getBeschreibung() + ", Datum: " + t.getDatum());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern des Termins in die Datei: " + e.getMessage());
        }
    }*/


    @Override
    public void speichernInDatei() throws IOException {

    }

    /**
     * liest die geschriebenen Daten aus der Textdatei aus
     *
     * @throws IOException           wenn in die Datei (Speicher) nicht reingelesen werden kann
     * @throws NumberFormatException falls ein ungültiges Format in der Kontendatei besteht
     */
    @Override
    public void ladenAusDatei() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(TERMINEN_SERVER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String t = parts[1];
                String b = parts[2];
                LocalDateTime d = LocalDateTime.parse(parts[3]);
                Termin termin = new Termin(id, t, b, d);
                terminMap.put(id, termin);
                nextId = Math.max(nextId, id + 1);
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Termindatei: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ungültiges Format in der Termindatei: " + e.getMessage());
        }
    }
}
