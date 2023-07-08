package MockTest;
/*
import junit.framework.Assert;
import org.example.Control.AufgabenHandler;
import org.example.Control.KontoHandler;
import org.example.Control.NotizenHandler;
import org.example.Control.TerminenHandler;
import org.example.Model.*;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import Control.AufgabenHandler;

public class mockTest {


    @BeforeEach
    void setUp() throws IOException, ParseException {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    public void testAufgabeErstellen() throws IOException {
        AufgabenHandler mockAufgabe = mock(AufgabenHandler.class);

        Mockito.when(mockAufgabe.erstelleAufgabe(Mockito.anyString(),anyInt(), anyBoolean())).thenReturn(new Aufgaben(1, "Titel", 1, false));
        mockAufgabe.erstelleAufgabe("Test", 2, true);
        verify(mockAufgabe).erstelleAufgabe("Test", 2,true);

        mockAufgabe.aufgabeBeschreibungBearbeiten(1,"Neuer Text");
        verify(mockAufgabe).aufgabeBeschreibungBearbeiten(1, "Neuer Text");

        mockAufgabe.aufgabePrioBearbeiten(1,3);
        verify(mockAufgabe).aufgabePrioBearbeiten(1,3);

        mockAufgabe.aufgabeErledigen(1);
        verify(mockAufgabe).aufgabeErledigen(1);

        mockAufgabe.aufgabeLöschen(1);
        verify(mockAufgabe).aufgabeLöschen(1);
    }

    @Test
    public void testTerminErstellen() throws IOException {

        TerminenHandler mockTermin = mock(TerminenHandler.class);
        LocalDateTime now = LocalDateTime.now();

        when(mockTermin.terminErstellen(Mockito.anyString(),Mockito.anyString(), Mockito.eq(now))).thenReturn(new Termin(1, "Titel", "Ein langer Text", now));
        mockTermin.terminErstellen("Test", "Ein Text", now);
        verify(mockTermin).terminErstellen("Test", "Ein Text", now);

        mockTermin.titelBearbeiten(1,"Neuer Titel");
        verify(mockTermin).titelBearbeiten(1, "Neuer Titel");

        mockTermin.textBearbeiten(1,"Neuer Text");
        verify(mockTermin).textBearbeiten(1,"Neuer Text");

        mockTermin.terminLöschen(1);
        verify(mockTermin).terminLöschen(1);
    }

    @Test
    public void testKonto() throws IOException {
       // Arrange
        String email = "test@example.com";
        String passwort = "password";

        KontoHandler mockKonto = Mockito.mock(KontoHandler.class);

        Mockito.when(mockKonto.registriereKonto(anyString(),anyString())).thenReturn(new Konto(1, "Email", "Passwort"));

        mockKonto.registriereKonto(email,passwort);
        verify(mockKonto).registriereKonto(email,passwort);

        mockKonto.getKonto(1);
        verify(mockKonto).getKonto(1);

        mockKonto.anmelden("Email", "Passwort");
        verify(mockKonto).anmelden("Email", "Passwort");

        mockKonto.abmelden(email);
        verify(mockKonto).abmelden(email);

        mockKonto.loescheKonto(email);
        verify(mockKonto).loescheKonto(email);


    }

    @Test
    public void testNotizHinzufügen() throws IOException {

        NotizenHandler mockNotiz = Mockito.mock(NotizenHandler.class);

        Mockito.when(mockNotiz.notizHinzufügen(Mockito.anyString(),Mockito.anyString())).thenReturn(new Notizen(1, "Titel", "Ein langer Text"));
        mockNotiz.notizHinzufügen("Test", "Ein Text");
        Mockito.verify(mockNotiz).notizHinzufügen("Test", "Ein Text");

        mockNotiz.notizTextBearbeiten(1,"Es funktioniert");
        Mockito.verify(mockNotiz).notizTextBearbeiten(1, "Es funktioniert");

        mockNotiz.notizTitelBearbeiten(1,"Neuer Titel");
        Mockito.verify(mockNotiz).notizTitelBearbeiten(1,"Neuer Titel");

        mockNotiz.notizLöschen(1);
        Mockito.verify(mockNotiz).notizLöschen(1);


    }

    @Test
    public void speicherTest() throws IOException {
        // Verwende eine spezielle Testdatei für die Datenpersistenz
        NotizenHandler notizenHandler = new NotizenHandler();

        // Füge eine Testnotiz hinzu
        Notizen testNotiz = notizenHandler.notizHinzufügen("Test-Notiz", "Dies ist ein Test");

        // Speichere die Notizen in der Testdatei
        notizenHandler.speichernInDatei();

        // Erstelle einen neuen NotizenHandler und lade die Notizen aus der Testdatei
        NotizenHandler neuerHandler = new NotizenHandler();
        neuerHandler.ladenAusDatei();

        // Überprüfe, ob die Testnotiz erfolgreich geladen wurde
        Notizen geladeneNotiz = neuerHandler.getNotiz(testNotiz.getNotizId());
        Assert.assertEquals(testNotiz, geladeneNotiz);
    }
}
*/