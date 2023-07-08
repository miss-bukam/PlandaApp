package Model;

/**
 * Konto Klasse zur Erstellung eines Kontos
 * mit Anmeldung und Abmeldung
 */
public class Konto implements KontoInterface {

    private int kontoId;
    private String email;
    private String passwort;
    private boolean angemeldet, abgemeldet;

    /**
     * Konstruktor, das die ID, E-Mail, Passwort bereithält
     *
     * @param kontoId  ID
     * @param email    E-Mail
     * @param passwort Passwort
     */
    public Konto(int kontoId, String email, String passwort) {
        this.kontoId = kontoId;
        this.email = email;
        this.passwort = passwort;
        this.angemeldet = false;
    }

    /**
     * parameterloser Konstruktor
     */
    public Konto() {
        kontoId = 0;
        email = null;
        passwort = null;
        angemeldet = false;
    }

    /**
     * liefert die KontoID zurück
     *
     * @return
     */
    public int getKontoId() {
        return kontoId;
    }

    /**
     * gibt die E-Mail zurück
     *
     * @return E-Mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * gibt das Passwort zurück
     *
     * @return Passwort
     */
    public String getPasswort() {
        return passwort;
    }

    /**
     * gibt zurück, ob man angemeldet ist
     *
     * @return true, wenn angemeldet; false, wenn nicht angemeldet
     */
    public boolean isAngemeldet() {
        return angemeldet;
    }

    /**
     * gibt zurück, ob man abgemeldet ist
     *
     * @return true, wenn abgemeldet; false, wenn nicht abgemeldet
     */
    public boolean isAbgemeldet() throws IllegalStateException {
        return abgemeldet;
    }

    /**
     * setzt angemeldet auf true und gibt eine String message zurück
     */
    public void anmelden() {
        angemeldet = true;
        System.out.println("Konto " + kontoId + " wurde angemeldet.");
    }

    /**
     * setzt angemeldet auf false und gibt eine String message zurück
     *
     * @throws IllegalStateException wenn Konto nicht angemeldet ist
     */
    public void abmelden() throws IllegalStateException {
        if (isAngemeldet() == false) {
            throw new IllegalStateException("Konto ist nicht angemeldet");
        } else {
            angemeldet = false;
            System.out.println("Konto " + kontoId + " wurde abgemeldet.");
        }
    }

    /**
     * gibt ein Konto als String zurück
     *
     * @return Konto als String
     */
    @Override
    public String toString() {
        return "ID: " + getKontoId() + "\nEmail: " + getEmail() + "\nPasswort: " + getPasswort();
    }
}
