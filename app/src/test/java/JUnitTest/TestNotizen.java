package JUnitTest;

import Control.NotizenHandler;

import org.junit.Assert;
import org.junit.Test;

import Model.Notizen;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TestNotizen {

    @Test
    public void notizTest() {
        Notizen notizen = new Notizen();

        // keine Notizen, deswegen 0
        Assert.assertEquals(notizen.getNotizId(), 0);
        Assert.assertNull(notizen.getTitel());
        Assert.assertNull(notizen.getText());

        // Notiz Objekt erstellen und Werte testen
        Notizen notizen1 = new Notizen(1, "Prog", "Keine Ahnung wie ich das Modul bestehen soll");
        Assert.assertEquals(notizen1.getNotizId(), 1);
        Assert.assertEquals(notizen1.getTitel(), "Prog");
        Assert.assertTrue(notizen1.getText().contains("Modul"));
        // toString Testen
        Assert.assertTrue(notizen1.toString().contains("bestehen"));
        Assert.assertTrue(notizen1.toString().contains("Prog"));
        Assert.assertTrue(notizen1.toString().contains("1"));
        Assert.assertFalse(notizen1.toString().contains("ahnung"));

    }

    @Test
    public void notizenHandlerTest() throws IOException {
        NotizenHandler notizenHandler = new NotizenHandler();

        // Erste Notiz erstellen
        Notizen n = notizenHandler.notizHinzufügen("Elefanten", "Elefanten sind sehr schlaue Tiere");
        Assert.assertEquals(n.getTitel(), "Elefanten");
        Assert.assertEquals(n.getNotizId(), 1);
        Assert.assertTrue(n.getText().contains("schlau"));

        //Zweite Notiz erstellen und bearbeiten
        Notizen n2 = notizenHandler.notizHinzufügen("Tee", "Tee ist am meisten beliebt in England, Japan und Türkei");
        // Text ändern
        notizenHandler.notizTextBearbeiten(n2.getNotizId(), "Tee ist am meisten beliebt in England, Japan und Indien");
        // Titel ändern
        notizenHandler.notizTitelBearbeiten(n2.getNotizId(), "Heißer Tee");
        Assert.assertEquals(n2.getNotizId(), 2);
        // alter Text nicht mehr vorhanden
        Assert.assertFalse(n2.getText().contains("Indien"));

        // Notiz anzeigen
        Notizen notiz = notizenHandler.getNotiz(2);
        Assert.assertEquals(notiz, n2);

        // Notiz löschen
        notizenHandler.notizLöschen(1);
        Assert.assertNull(notizenHandler.getNotiz(1));

    }

    @Test
    public void getNotizenMapTest() throws IOException {
        // Erstelle eine Instanz der AufgabenHandler-Klasse
        NotizenHandler notizenHandler = new NotizenHandler();

        // Füge einige Aufgaben zur AufgabenHandler-Instanz hinzu
        Notizen notiz1 = notizenHandler.notizHinzufügen("Keine Ahnung", "ibcubeuibwuc");
        Notizen notiz2 = notizenHandler.notizHinzufügen("Essen", "brauche ich gerade");

        // Erstelle die erwartete Map mit den Aufgaben
        Map<Integer, Notizen> erwarteteNotizenMap = new HashMap<>();
        erwarteteNotizenMap.put(notiz1.getNotizId(), notiz1);
        erwarteteNotizenMap.put(notiz2.getNotizId(), notiz2);

        // Rufe die getAufgabenMap-Methode auf
        Map<Integer, Notizen> tatsächlicheNotizenMap = notizenHandler.getNotizenMap();

        // Vergleiche die erwartete Map mit der tatsächlichen Map
        Assert.assertEquals(erwarteteNotizenMap, tatsächlicheNotizenMap);
    }
}
