package JUnitTest;

import Control.KontoHandler;
/*
import org.testng.Assert;
import org.testng.annotations.Test;

import Model.Konto;
import java.io.IOException;


public class TestKonto {

    @Test
    public void kontoTest() {
        Konto konto = new Konto();
        Konto konto1 = new Konto(1,"gizembiliktu@gmail.com","Gizem2002");
        Konto konto2 = new Konto(2,"blablablub@gmail.com","bibidi");
        Konto konto3 = new Konto(12,"dbjbd@dw.de","lol");

        // Testen, dass es kein Konto gibt
        Assert.assertEquals(konto.getKontoId(),0);
        Assert.assertNull(konto.getEmail());
        Assert.assertNull(konto.getPasswort());

        // Angemeldet Testen
        Assert.assertFalse(konto1.isAngemeldet());
        Assert.assertFalse(konto1.isAbgemeldet());

        // Getter Testen
        int id = konto1.getKontoId();
        Assert.assertEquals(id, 1);
        String email = konto1.getEmail();
        Assert.assertEquals(email,"gizembiliktu@gmail.com");
        String passw = konto1.getPasswort();
        Assert.assertEquals(passw,"Gizem2002");

        // mehrere Konten anmelden
        konto1.anmelden();
        Assert.assertTrue(konto1.isAngemeldet());
        konto2.anmelden();
        Assert.assertTrue(konto2.isAngemeldet());
        konto1.abmelden();
        Assert.assertFalse(konto1.isAngemeldet());
        konto3.anmelden();
        Assert.assertTrue(konto3.isAngemeldet());

    }

    @Test
    public void kontoHandlerTest() throws IOException {
        KontoHandler kontoHandler = new KontoHandler();
        String email = "gizembiliktu@gmail.com";
        String passwort = "Gizem2002";
        String email2 = "keineAhnung@hotmail.de";
        String passw2 = "Blub";

        // Exceptions, wenn kein Konto existiert
        Assert.assertThrows(IllegalStateException.class, () -> {
            kontoHandler.anmelden("random","passw");
        });
        Assert.assertThrows(IllegalStateException.class, () -> {
            kontoHandler.abmelden("random");
        });
        Assert.assertThrows(IllegalStateException.class, () -> {
            kontoHandler.loescheKonto("random");
        });


        // Konto registrieren und nach den Daten und Statuse checken
        Konto k = kontoHandler.registriereKonto(email, passwort);
        Assert.assertEquals(k.getKontoId(),1);
        Assert.assertEquals(k.getPasswort(),passwort);
        Assert.assertFalse(k.isAngemeldet());
        Assert.assertFalse(k.isAbgemeldet());
        k.anmelden();
        Assert.assertTrue(k.isAngemeldet());
        Assert.assertTrue(k.toString().contains("gizem"));


        // Testen, ob man gleiche Konto nochmal registrieren kann
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            kontoHandler.registriereKonto(email,passwort);
        });
        // Testen, ob man mit gleicher Email und anderem Passwort registrieren kann
        Assert.assertThrows(IllegalArgumentException.class, () -> {
            kontoHandler.registriereKonto(email,passw2);
        });
        // Testen, ob man mit neuer Email und mit schon benutzten Passwort registrieren kann
        Konto k2 = kontoHandler.registriereKonto(email2,passwort);
        Assert.assertEquals(k2.getKontoId(),2);
        // Testen, ob man mit gleicher Email und anderem Passwort anmelden kann
        Assert.assertThrows(IllegalStateException.class, () -> {
            kontoHandler.anmelden(email,passw2);
        });
        // Testen, ob man ohne Anmeldung sich abmelden kann
        kontoHandler.registriereKonto("eineEmail","mitPasswort");
        Assert.assertThrows(IllegalStateException.class, () -> {
            kontoHandler.abmelden("eineEmail");
        });
        // Testen, ob ein abgemeldetes Konto sich wieder anmelden kann
        Konto k4 = kontoHandler.registriereKonto("EineEmail@.de","mitPasswort");
        kontoHandler.anmelden("EineEmail@.de","mitPasswort");
        kontoHandler.abmelden("EineEmail@.de");
        kontoHandler.anmelden("EineEmail@.de","mitPasswort");
        Assert.assertEquals(k4.getKontoId(),4);

        // Konto ohne Anmeldung kann nicht sein Konto löschen
        Konto k5 = kontoHandler.registriereKonto("binmüde@123.com", "jogurt");
        Assert.assertFalse(k5.isAngemeldet());
        Assert.assertThrows(IllegalStateException.class, () -> {
            kontoHandler.loescheKonto("binmüde@123.com");
        });
        // Konto mit Anmeldung löschen
        kontoHandler.anmelden("binmüde@123.com", "jogurt");
        Assert.assertTrue(k5.isAngemeldet());
        kontoHandler.loescheKonto("binmüde@123.com");
        System.out.println("--------------------");
        assert kontoHandler.getKontenMap().containsKey(k5.getKontoId());

        // getAlleKonten

        System.out.println(kontoHandler.getAlleKonten());
    }

}*/
