package Model;

import java.util.Date;

/**
 * erstellt Aufgaben Objekte
 */
public class Aufgaben {

    private int aufgabenId;
    private String beschreibung;
    private int priorität;
    private boolean aufgabeErledigt;

    /**
     * Konstruktor für Aufgaben, der eine ID, Beschreibung,
     * Datum und eine Priorität hat
     *
     * @param aufgabenId   ID
     * @param beschreibung Beschreibung
     * @param priorität    Priorität
     */
    public Aufgaben(int aufgabenId, String beschreibung, int priorität, boolean aufgabeErledigt) {
        this.aufgabenId = aufgabenId;
        this.beschreibung = beschreibung;
        this.priorität = priorität;
        this.aufgabeErledigt = aufgabeErledigt;
    }

    /**
     * parameterloser Konstruktor
     */
    public Aufgaben() {

    }

    /**
     * Liefert die AufgabenID als Integer zurück
     *
     * @return ID
     */
    public int getAufgabenId() {
        return aufgabenId;
    }

    /**
     * Liefert die Beschreibung als String zurück
     *
     * @return Beschreibung
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Ersetzt Beschreibung mit neuem String
     *
     * @param beschreibung neue Beschreibung
     */
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    /**
     * Gibt die Priorität als Integer aus
     *
     * @return Priorität
     */
    public int getPriorität() {
        return priorität;
    }

    /**
     * Ersetzt Priorität mit neuem Integer
     *
     * @param priorität neue Priorität
     */
    public void setPriorität(int priorität) {
        this.priorität = priorität;
    }

    /**
     * Gibt mit Boolean zurück, ob eine Aufgabe erledigt ist
     *
     * @return true, wenn erledigt; false, wenn nicht erledigt
     */
    public boolean isAufgabeErledigt() {
        return aufgabeErledigt;
    }

    /**
     * Setzt die Aufgabe mit Boolean auf erledigt
     */
    public void setAufgabeErledigt() {
        this.aufgabeErledigt = true;
    }
}
