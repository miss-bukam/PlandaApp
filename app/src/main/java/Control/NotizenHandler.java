package Control;

import Model.Notizen;
import Model.SpeicherInterface;

import java.io.*;
import java.util.*;

/**
 * verwaltet alle Notizen Objekte und interagiert mit ihnen
 */
public class NotizenHandler implements SpeicherInterface {

    private int nextId;
    private Map<Integer, Notizen> notizenMap;
    private static final String NOTIZEN_SERVER = "/Users/gizembiliktue/Desktop/HTW/3.Semester/MobBetNet/notizendatei.txt";

    /**
     * NotizenHandler-Konstruktor, der eine Map und die nächste ID definiert
     * gleichzeitig ladet sie gespeicherte Daten aus dem Server
     */
    public NotizenHandler() throws IOException {
        notizenMap = new HashMap<>();
        this.nextId = 1;
        ladenAusDatei();
    }

    /**
     * Erstellt eine Notiz mit zwei String zu Titel
     * und Text Eingabe und gibt eine neue Notiz aus
     *
     * @param titel Titel
     * @param text  Text
     * @return neue Notiz
     * @throws IOException wenn in den Server nicht reingeschrieben werden kann
     */

    public Notizen notizHinzufügen(String titel, String text) throws IOException {
        Notizen notiz = new Notizen(nextId, titel, text);
        notizenMap.put(nextId, notiz);
        speichernInDatei();
        nextId++;
        System.out.println("Sie haben die Notiz '" + notiz.getTitel() + "' erfolgreich hinzugefügt.");
        return notiz;
    }

    /**
     * Bearbeitet eine Notiz, indem es die ID in der NotizenMap sucht
     * und nur den Inhalt von Text mit Setter-Methoden ändert.
     * Gibt neue Notiz wieder aus und speichert sie im Server
     *
     * @param id        ID
     * @param neuerText neuer Text
     * @return neue Notiz
     * @throws IOException wenn in den Server nicht reingeschrieben werden kann
     */

    public Notizen notizTextBearbeiten(int id, String neuerText) throws IOException, IllegalArgumentException {
        Notizen notiz = notizenMap.get(id);
        if (notiz != null) {
            notiz.setText(neuerText);
            speichernInDatei();
            System.out.println("Sie haben die Notiz erfolgreich bearbeitet.");
            return notiz;
        } else {
            throw new IllegalArgumentException("Notiz wurde nicht gefunden.");
        }
    }

    /**
     * Bearbeitet eine Notiz, indem es die ID in der NotizenMap sucht
     * und nur den Inhalt von Titel mit Setter-Methoden ändert.
     * Gibt neue Notiz wieder aus und speichert sie im Server
     *
     * @param id         ID
     * @param neuerTitel neuer Titel
     * @return neue Notiz
     * @throws IOException wenn in den Server nicht reingeschrieben werden kann
     */

    public Notizen notizTitelBearbeiten(int id, String neuerTitel) throws IOException, IllegalArgumentException {
        Notizen notiz = notizenMap.get(id);
        if (notiz != null) {
            notiz.setText(neuerTitel);
            speichernInDatei();
            System.out.println("Sie haben die Notiz erfolgreich bearbeitet.");
            return notiz;
        } else {
            throw new IllegalArgumentException("Notiz wurde nicht gefunden.");
        }
    }

    /**
     * Löscht eine Notiz mit der ID als Eingabe
     * aus der Map und aktualisiert es im Server
     *
     * @param id NotizenID
     * @throws IllegalArgumentException wenn die Notiz nicht gefunden wurde
     */

    public void notizLöschen(int id) throws IllegalArgumentException, IOException {
        Notizen notiz = notizenMap.remove(id);
        if (notiz != null) {
            speichernInDatei();
            System.out.println("Sie haben die Notiz: " + notiz.getTitel() + " erfolgreich gelöscht.");
        } else {
            throw new IllegalArgumentException("Notiz nicht gefunden.");
        }
    }

    /**
     * gibt die gewünschte Notiz mit Eingabe als ID aus
     *
     * @param id ID
     * @return Notiz
     */

    public Notizen getNotiz(int id) {
        return notizenMap.get(id);
    }

    /**
     * gibt alle Notizen im Form einer lesbaren Map aus
     *
     * @return Notizen als Map
     */

    public Map<Integer, String[]> getAlleNotizen() {
        Map<Integer, String[]> notizen = new HashMap<>();
        for (Notizen notiz : notizenMap.values()) {
            String[] notizenDaten = new String[3];
            notizenDaten[0] = String.valueOf(notiz.getNotizId());
            notizenDaten[1] = notiz.getTitel();
            notizenDaten[2] = notiz.getText();
            notizen.put(notiz.getNotizId(), notizenDaten);
        }
        return notizen;
    }

    public Map<Integer, Notizen> getNotizenMap() {
        return notizenMap;
    }

    /**
     * speichert die NotizenDaten in eine Textdatei
     *
     * @throws IOException wenn in die Datei nicht rein geschreiben werden kann
     */

    public void speichernInDatei() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTIZEN_SERVER))) {
            for (Notizen n : notizenMap.values()) {
                writer.write("NotizenID: " + n.getNotizId() + ", Titel: " + n.getTitel() + ", Text: " + n.getText());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Speichern der Notizen in die Datei: " + e.getMessage());
        }
    }

    /**
     * liest die geschriebenen Daten aus der Textdatei aus
     *
     * @throws IOException           wenn in die Datei (Speicher) nicht reingelesen werden kann
     * @throws NumberFormatException falls ein ungültiges Format in der Kontendatei besteht
     */
    @Override
    public void ladenAusDatei() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(NOTIZEN_SERVER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String titel = parts[1];
                String inhalt = parts[2];
                Notizen notiz = new Notizen(id, titel, inhalt);
                notizenMap.put(id, notiz);
                nextId = Math.max(nextId, id + 1);
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Notizdatei: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ungültiges Format in der Notizdatei: " + e.getMessage());
        }
    }
}
