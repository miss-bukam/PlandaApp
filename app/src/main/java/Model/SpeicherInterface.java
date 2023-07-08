package Model;

import java.io.IOException;

public interface SpeicherInterface {

    void speichernInDatei() throws IOException;

    void ladenAusDatei() throws IOException;
}
