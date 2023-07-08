package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Termin {

    private int terminId;
    private String titel;
    private String beschreibung;
    private LocalDateTime datum;

    public Termin(int terminid, String titel, String beschreibung, LocalDateTime datum) {
        this.terminId = terminid;
        this.titel = titel;
        this.beschreibung = beschreibung;
        this.datum = datum;
    }

    public Termin() {

    }

    public int getTerminId() {
        return terminId;
    }

    public void setTerminId(int terminId) {
        this.terminId = terminId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        return "ID: " + terminId + "\nTitel: " + titel
                + "\nBeschreibung: " + beschreibung
                + "\nDatum: " + datum.format(formatter);
    }
}
