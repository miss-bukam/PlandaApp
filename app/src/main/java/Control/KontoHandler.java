package Control;

import Model.Konto;
import Model.SpeicherInterface;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Klasse KontoHandler zur Verwaltung von mehreren Konten
 * und zur Kommunikation mit dem Server
 */
public class KontoHandler implements SpeicherInterface {

    private Map<Integer, Konto> kontenMap;
    private int nextId;
    private static final String KONTEN_SERVER = "/Users/gizembiliktue/Desktop/HTW/3.Semester/MobBetNet/kontendatei.txt";

    /**
     * Konstruktor, der eine kontenMap und immer die nächste ID bereithält
     * die gespeicherten Daten werden bei jedem Abrufen aus der Datei geladen
     *
     * @throws IOException wenn in die Datei (Speicher) nicht reingelesen werden kann
     */
    public KontoHandler() throws IOException {
        kontenMap = new HashMap<>();
        ladenAusDatei();
        nextId = kontenMap.size() + 1;
    }

    /**
     * registriert ein neues Konto mit angegebener E-Mail-Adresse und Passwort
     *
     * @param email    E-mail-Adresse
     * @param passwort Passwort
     * @return ein neues Konto
     * @throws IOException              wenn in die Datei (Speicher) nicht reingeschrieben werden kann
     * @throws IllegalArgumentException falls ein Konto mit der E-Mail schon besteht
     */
    public Konto registriereKonto(String email, String passwort) throws IOException, IllegalArgumentException {
        boolean bereitsRegistriert = false;
        for (Konto k : kontenMap.values()) {
            if (k.getEmail().equals(email)) {
                bereitsRegistriert = true;
                break;
            }
        }
        if (!bereitsRegistriert) {
            Konto neuesKonto = new Konto(nextId, email, passwort);
            kontenMap.put(nextId, neuesKonto);
            nextId++;
            speichernInDatei();
            System.out.println("Konto mit E-Mail " + email + " wurde erfolgreich registriert.");
            return neuesKonto;
        } else {
            throw new IllegalArgumentException("Es existiert bereits ein Konto mit der E-Mail " + email + ".");
        }

    }

    /**
     * löscht das Konto mit der angegebenen E-Mail-Adresse
     *
     * @param email E-Mail-Adresse
     * @throws IllegalStateException Wenn das Konto nicht existiert
     */
    public void loescheKonto(String email) throws IllegalStateException {
        Konto konto = sucheKonto(email);
        if (konto != null && konto.isAngemeldet() == true) {
            kontenMap.remove(konto);
            System.out.println("Konto mit E-Mail " + email + " wurde gelöscht.");
        } else {
            throw new IllegalStateException("Konto mit E-Mail " + email + " wurde nicht gefunden.");
        }
    }

    /**
     * meldet das Konto mit der angegebenen E-Mail-Adresse und dem Passwort an
     *
     * @param email    Email-Adresse
     * @param passwort Passwort
     * @throws IllegalStateException    wenn es kein Konto mit den Eingaben gibt
     * @throws IllegalArgumentException wenn E-Mail oder Passwort falsch geschrieben ist
     */
    public void anmelden(String email, String passwort) throws IllegalStateException {
        Konto konto = sucheKonto(email);
        if (konto != null && konto.getPasswort().equals(passwort)) {
            konto.anmelden();
        } else {
            throw new IllegalStateException("Anmeldung fehlgeschlagen. Überprüfen Sie Ihre E-Mail und Ihr Passwort.");
        }
    }

    /**
     * meldet das Konto mit der angegebenen E-Mail-Adresse ab
     *
     * @param email EMail-Adresse
     * @throws IllegalStateException Wenn Konto nicht existiert
     */
    public void abmelden(String email) throws IllegalStateException {
        Konto konto = sucheKonto(email);
        if (konto != null) {
            konto.abmelden();
        } else {
            throw new IllegalStateException("Konto mit E-Mail " + email + " wurde nicht gefunden.");
        }
    }

    /**
     * sucht ein Konto mit einer E-Mail als Eingabe
     *
     * @param email E-Mail
     * @return das gewünschte Konto
     */
    private Konto sucheKonto(String email) {
        for (Konto konto : kontenMap.values()) {
            if (konto.getEmail().equals(email)) {
                return konto;
            }
        }
        return null;
    }

    /**
     * liefert ein Konto aus mit einer ID als Eingabe
     *
     * @param id ID
     * @return ein gewünschtes Konto
     */
    public Konto getKonto(int id) {
        return kontenMap.get(id);
    }

    /**
     * Liefert alle erstellten Konten als Map in lesbarer Form aus
     *
     * @return lesbare Map
     */
    public String getAlleKonten() {
        /*Map<Integer, String[]> konten = new HashMap<>();
        for (Konto konto : kontenMap.values()) {
            String[] kontoDaten = new String[3];
            kontoDaten[0] = String.valueOf(konto.getKontoId());
            kontoDaten[1] = konto.getEmail();
            kontoDaten[2] = konto.getPasswort();
            konten.put(konto.getKontoId(), kontoDaten);
        }
        return konten;*/

        String string = "";
        Set<Integer> keySet = kontenMap.keySet();

        for (Konto k : kontenMap.values()) {
            string += "Kontonr: " + k.getKontoId() + ", \tEmail: " + k.getEmail() + ", \tPasswort: " + k.getPasswort() + "\n";
        }

        return string;
    }

    /**
     * gibt die aktuelle KontenMap aus
     *
     * @return aktuelle KontenMap
     */
    public Map<Integer, Konto> getKontenMap() {
        return kontenMap;
    }

    /**
     * schreibt die Daten aus der Map in eine Textdatei und behält sie dort
     *
     * @throws IOException wenn in die Datei (Speicher) nicht reingeschrieben werden kann
     */
    @Override
    public void speichernInDatei() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(KONTEN_SERVER))) {
            for (Konto k : kontenMap.values()) {
                writer.write("ID: " + k.getKontoId() + ", Email: " + k.getEmail() + ", Passwort: " + k.getPasswort());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Schreiben der Kontextdatei: " + e.getMessage());
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
        try (BufferedReader reader = new BufferedReader(new FileReader(KONTEN_SERVER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String email = parts[1];
                String passw = parts[2];
                Konto konto = new Konto(id, email, passw);
                kontenMap.put(id, konto);
                nextId = Math.max(nextId, id + 1);
            }
        } catch (IOException e) {
            System.out.println("Fehler beim Lesen der Kontendatei: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ungültiges Format in der Kontendatei: " + e.getMessage());
        }
    }
}
