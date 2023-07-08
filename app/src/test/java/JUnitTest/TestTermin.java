package JUnitTest;

import Control.TerminenHandler;

import org.junit.Assert;
import org.junit.Test;

import Model.Termin;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class TestTermin {

    @Test
    public void terminTest() {

        Termin termin = new Termin();

        // Attribute testen, wenn es keine Aufgabe gibt
        Assert.assertEquals(termin.getTerminId(), 0);
        Assert.assertNull(termin.getTitel());
        Assert.assertNull(termin.getBeschreibung());
        Assert.assertEquals(termin.getDatum(), null);

        // erste Aufgabe testen
        LocalDateTime datum = LocalDateTime.of(2023, 6, 27, 10, 03);
        Termin termin1 = new Termin(1, "Geburtstag", "Im Park in Alexa", datum);

        Assert.assertEquals(termin1.getTerminId(), 1);
        Assert.assertTrue(termin1.getTitel().contains("Geburtstag"));
        Assert.assertTrue(termin1.getBeschreibung().contains("Park"));
        Assert.assertEquals(termin1.getDatum(), datum);

    }

    @Test
    public void terminErstellenTest() throws IOException, ParseException {

        TerminenHandler terminenHandler = new TerminenHandler();
        LocalDateTime datum = LocalDateTime.of(2023, 7, 11, 8, 0);
        Termin termin1 = terminenHandler.terminErstellen("Präsentation", "Gliederung fertig machen", datum);

        // Ersten Termin erstellen
        Assert.assertEquals(termin1.getTerminId(), 1);
        Assert.assertTrue(termin1.getTitel().contains("Präse"));
        Assert.assertTrue(termin1.getBeschreibung().contains("Gliederung"));
        Assert.assertEquals(termin1.getDatum(), datum);

        // Zweite erstellen
        LocalDateTime datum2 = LocalDateTime.of(2023, 11, 23, 12, 54);
        Termin termin2 = terminenHandler.terminErstellen("Termin", "Muss noch planen", datum2);
        Assert.assertEquals(termin2.getTerminId(), 2);
        Assert.assertTrue(termin2.getTitel().contains("Termin"));
        Assert.assertTrue(termin2.getBeschreibung().contains("planen"));
        Assert.assertEquals(termin2.getDatum(), datum2);

    }

    @Test
    public void terminBearbeitenTest() throws IOException, ParseException {
        TerminenHandler terminenHandler = new TerminenHandler();
        LocalDateTime datum = LocalDateTime.of(2023, 7, 11, 8, 0);
        Termin termin1 = terminenHandler.terminErstellen("Präsentation", "Gliederung fertig machen", datum);

        // Titel ändern
        termin1.setTitel("Kein Plan");
        Assert.assertTrue(termin1.getTitel().contains("Plan"));
        terminenHandler.titelBearbeiten(1, "Presi");
        Assert.assertFalse(termin1.getTitel().contains("Plan"));
        Assert.assertTrue(termin1.getTitel().contains("Presi"));

        // Test bearbeiten
        termin1.setBeschreibung("Irgendein Text");
        Assert.assertTrue(termin1.getBeschreibung().contains("Text"));
        terminenHandler.textBearbeiten(1, "Ich weiß nicht");
        Assert.assertFalse(termin1.getBeschreibung().contains("Text"));
        Assert.assertTrue(termin1.getBeschreibung().contains("weiß"));

        // Datum
        LocalDateTime datum2 = LocalDateTime.of(2023, 11, 23, 12, 54);
        termin1.setDatum(datum2);
        Assert.assertEquals(termin1.getDatum(), datum2);
        terminenHandler.datumBearbeiten(1, datum);
        Assert.assertEquals(termin1.getDatum(), datum);

    }

    @Test
    public void terminLöschenTest() throws IOException, ParseException {
        TerminenHandler terminenHandler = new TerminenHandler();
        LocalDateTime datum2 = LocalDateTime.of(2023, 11, 23, 12, 54);

        // Termin erstellen
        Termin t = terminenHandler.terminErstellen("Indisch", "abcuibcujv", datum2);
        Assert.assertEquals(terminenHandler.getTermin(1), t);
        // Termin löschen
        terminenHandler.terminLöschen(1);
        // Termin nach Existenz prüfen
        Assert.assertEquals(terminenHandler.getTermin(1), null);

        // Aufgabe löschen, die nicht existiert
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            terminenHandler.terminLöschen(4);
        });
    }

    @Test
    public void getTermineMapTest() throws IOException, ParseException {
        TerminenHandler terminenHandler = new TerminenHandler();
        LocalDateTime datum = LocalDateTime.of(2023, 7, 11, 8, 0);
        LocalDateTime datum2 = LocalDateTime.of(2023, 11, 23, 12, 54);


        // Füge einigen Terminen zur TerminenHandler-Instanz hinzu
        Termin termin1 = terminenHandler.terminErstellen("Indisch", "abcuibcujv", datum);
        Termin termin2 = terminenHandler.terminErstellen("Termin", "Muss noch planen", datum2);

        // Erstelle die erwartete Map mit den Terminen
        Map<Integer, Termin> erwarteteTerminenMap = new HashMap<>();
        erwarteteTerminenMap.put(termin1.getTerminId(), termin1);
        erwarteteTerminenMap.put(termin2.getTerminId(), termin2);

        // Rufe die getTermineMap-Methode auf
        Map<Integer, Termin> tatsächlicheTerminenMap = terminenHandler.getTerminMap();

        // Vergleiche die erwartete Map mit der tatsächlichen Map
        Assert.assertEquals(erwarteteTerminenMap, tatsächlicheTerminenMap);
    }
}
