package Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Klasse Notizen, der Notizen erstellt
 */
public class Notizen implements NotizenInterface {

    private int notizId;
    private String titel;
    private String text;

    public Notizen(int id, String titel, String text) {
        this.notizId = id;
        this.titel = titel;
        this.text = text;
    }

    public Notizen() {

    }

    public int getNotizId() {
        return notizId;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ID: " + notizId + "\nTitel: " + titel + "\nText: " + text;
    }

    /*@Override
    public void NotizErstellen(String titel, String text) {
        System.out.println("Die Notiz wurde erfolgreich erstellt");
    }*/
}
