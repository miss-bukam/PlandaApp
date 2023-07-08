package JUnitTest;

import Control.AufgabenHandler;

import org.junit.Assert;
import org.junit.Test;

import Model.Aufgaben;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;


public class TestAufgaben {

    @Test
    public void aufgabenTest() throws ParseException {

        Aufgaben aufgabe = new Aufgaben();

        // Attribute testen, wenn es keine Aufgabe gibt
        Assert.assertEquals(aufgabe.getPriorität(), 0);
        Assert.assertNull(aufgabe.getBeschreibung());
        Assert.assertEquals(aufgabe.getAufgabenId(), 0);
        Assert.assertFalse(aufgabe.isAufgabeErledigt());

        // erste Aufgabe testen
        Aufgaben aufgabe1 = new Aufgaben(1, "Heute gehe ich früher schlafen", 1, false);
        Assert.assertEquals(aufgabe1.getAufgabenId(), 1);
        Assert.assertTrue(aufgabe1.getBeschreibung().contains("schlafen"));
        Assert.assertEquals(aufgabe1.getPriorität(), 1);
        Assert.assertFalse(aufgabe1.isAufgabeErledigt());
        aufgabe1.setAufgabeErledigt();
        Assert.assertTrue(aufgabe1.isAufgabeErledigt());

    }

    @Test
    public void aufgabeErstellenTest() throws IOException, ParseException {

        AufgabenHandler aufgabenHandler = new AufgabenHandler();

        // Erste Aufgabe erstellen
        Aufgaben aufgabe1 = aufgabenHandler.erstelleAufgabe("Prog3 machen", 1, false);
        Assert.assertEquals(aufgabe1.getAufgabenId(), 1);
        Assert.assertTrue(aufgabe1.getBeschreibung().contains("Prog3"));
        Assert.assertEquals(aufgabe1.getPriorität(), 1);
        Assert.assertFalse(aufgabe1.isAufgabeErledigt());
        Assert.assertEquals(aufgabe1, aufgabenHandler.getAufgabe(1));

        // Zweite erstellen
        Aufgaben aufgabe2 = aufgabenHandler.erstelleAufgabe("AlgoDat", 2, true);
        Assert.assertEquals(aufgabe2.getAufgabenId(), 2);
        Assert.assertTrue(aufgabe2.getBeschreibung().contains("Algo"));
        Assert.assertEquals(aufgabe2.getPriorität(), 2);
        Assert.assertTrue(aufgabe2.isAufgabeErledigt());
        Assert.assertEquals(aufgabe2, aufgabenHandler.getAufgabe(2));

    }

    @Test
    public void aufgabeBearbeitenTest() throws IOException, ParseException {
        AufgabenHandler aufgabenHandler = new AufgabenHandler();
        Aufgaben aufgabe1 = aufgabenHandler.erstelleAufgabe("Prog3 machen", 1, false);

        // Beschreibung ändern und Attribute prüfen
        aufgabenHandler.aufgabeBeschreibungBearbeiten(1, "Software");
        Assert.assertFalse(aufgabe1.getBeschreibung().contains("Algo"));
        Assert.assertTrue(aufgabe1.getBeschreibung().contains("Soft"));
        Assert.assertEquals(aufgabe1.getAufgabenId(), 1);
        Assert.assertEquals(aufgabe1.getPriorität(), 1);

        // Priorität ändern und Attribute prüfen
        aufgabenHandler.aufgabePrioBearbeiten(1, 3);
        Assert.assertEquals(aufgabe1.getAufgabenId(), 1);
        Assert.assertTrue(aufgabe1.getBeschreibung().contains("Soft"));
        Assert.assertNotEquals(aufgabe1.getAufgabenId(), 2);
        Assert.assertEquals(aufgabe1.getPriorität(), 3);
    }


    @Test
    public void aufgabeLöschenTest() throws IOException, ParseException {
        AufgabenHandler aufgabenHandler = new AufgabenHandler();

        // Aufgabe erstellen
        aufgabenHandler.erstelleAufgabe("Kein Plan", 4, true);
        // Aufgabe löschen
        aufgabenHandler.aufgabeLöschen(1);
        // Aufgabe nach Existenz prüfen
        Assert.assertEquals(aufgabenHandler.getAufgabe(1), null);
        // Aufgabe löschen, die nicht existiert
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            aufgabenHandler.aufgabeLöschen(4);
        });

    }

    @Test
    public void getAufgabenMapTest() throws IOException, ParseException {
        // Erstelle eine Instanz der AufgabenHandler-Klasse
        AufgabenHandler aufgabenHandler = new AufgabenHandler();

        // Füge einige Aufgaben zur AufgabenHandler-Instanz hinzu
        Aufgaben aufgabe1 = aufgabenHandler.erstelleAufgabe("Prog3 machen", 1, true);
        Aufgaben aufgabe2 = aufgabenHandler.erstelleAufgabe("Algorithmen und Datenstrukturen", 2, false);

        // Erstelle die erwartete Map mit den Aufgaben
        Map<Integer, Aufgaben> erwarteteAufgabenMap = new HashMap<>();
        erwarteteAufgabenMap.put(aufgabe1.getAufgabenId(), aufgabe1);
        erwarteteAufgabenMap.put(aufgabe2.getAufgabenId(), aufgabe2);

        // Rufe die getAufgabenMap-Methode auf
        Map<Integer, Aufgaben> tatsächlicheAufgabenMap = aufgabenHandler.getAufgabenMap();

        // Vergleiche die erwartete Map mit der tatsächlichen Map
        Assert.assertEquals(erwarteteAufgabenMap, tatsächlicheAufgabenMap);
    }
}
