
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import android.os.SystemClock;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.action.ViewActions;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;


import com.example.plandaneu.Activities.MainActivity;
import com.example.plandaneu.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);


    // ----------------------------------------------
    // ------            Notizen             --------
    // ----------------------------------------------


    // Testet die Erstellung und Ausgaben von einer Notiz
    @Test
    public void EspressoTestNotizen() {

        onView(withId(R.id.hinzufuegeButton))
                .perform(click());

        //Erste Notiz
        onView(withText(endsWith("Notiz erstellen")))
                .perform(click());
        onView(withId(R.id.notizenTitel))
                .perform(click())
                .perform(typeText("Notiz 1"))
                .perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.notizTextEingabe))
                .perform(click())
                .perform(typeText(" We rise by lifting others "))
                .perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonNotizen))
                .perform(click());

    }

    // Testet die Erstellung und Ausgaben von einer 2ten Notizen
    @Test
    public void EspressoTestNotiz2() {
        //Zweite Notiz
        onView(withId(R.id.hinzufuegeButton))
                .perform(click());
        onView(withText("Notiz erstellen"))
                .perform(click());
        onView(withId(R.id.notizenTitel))
                .perform(click())
                .perform(typeText("Wichtige Email"))
                .perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.notizTextEingabe))
                .perform(click())
                .perform(typeText("Email-Adresse von Boe: boeboe123@gmail.com "))
                .perform(ViewActions.closeSoftKeyboard());
        onView(withId(R.id.buttonNotizen))
                .perform(click());
        onView(withId(R.id.speicherungListe))
                .perform(click());
    }


    // Testet die Löschung einer Notiz
    @Test
    public void EspressoTestloescheNotizen() {
        // Klicken auf die Liste
        onView(withId(R.id.speicherungListe)).perform(click());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        // Klicken auf die erste Position in der ListView
        onData(anything()).inAdapterView(withId(R.id.notizenListView)).atPosition(0).perform(click());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        // Überprüfen, ob die Ansicht mit der Lösch-Schaltfläche angezeigt wird
        onView(withId(R.id.notizenloescheButton)).check(matches(isDisplayed()));

        // Klicken auf die Lösch-Schaltfläche
        onView(withId(R.id.notizenloescheButton)).perform(click());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        // Überprüfen, ob die Liste nach der Löschung leer ist
        onView(withId(R.id.notizenListView)).check(matches(not(hasDescendant(any(View.class)))));
    }


    // ----------------------------------------------
    // ------            Aufgaben            --------
    // ----------------------------------------------

    // Testet die Erstellun von einer Aufgabe
    @Test
    public void EspressoTestAufgaben() {
        onView(withId(R.id.hinzufuegeButton))
                .perform(click());
        onView(withText("Aufgabe erstellen"))
                .perform(click());
        onView(withId(R.id.aufgabenTitel))
                .perform(click())
                .perform(typeText("Aufgabe 1"));

        //Mit der id
        String expectedHintText = "Schreib etwas ...";
        onView(withId(R.id.TextEingabe))
                .check(matches(withHint(expectedHintText)))
                .perform(typeText("Mobile Betriebssysteme wiederholen"))
                .perform(ViewActions.closeSoftKeyboard());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(withId(R.id.buttonAufgaben))
                .perform(click());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);
    }

    // Testet die Erstellung von einer 2ten Aufgabe
    @Test
    public void EspressoTestAufgaben2() {
        onView(withId(R.id.hinzufuegeButton))
                .perform(click());

        onView(withText("Aufgabe erstellen"))
                .perform(click());
        onView(withId(R.id.aufgabenTitel))
                .perform(click())
                .perform(typeText("Aufgabe 2"))
                .perform(ViewActions.closeSoftKeyboard());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        //Mit der id
        onView(withId(R.id.TextEingabe))
                .perform(typeText("Betriebssysteme wiederholen"))
                .perform(ViewActions.closeSoftKeyboard());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(withId(R.id.buttonAufgaben))
                .perform(click());

    }

    // Testet die Löschung einer Aufgaben am 0ten Index
    @Test
    public void EspressoTestloescheAufgabe() {
        onView(withId(R.id.speicherungListe))
                .perform(click());
        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onData(anything())
                .inAdapterView(withId(R.id.aufgabenListView))
                .atPosition(0)
                .perform(click());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(withId(R.id.loescheAufgabenView))
                .perform(click());
    }


    // ----------------------------------------------
    // ------          Ereignisse            --------
    // ----------------------------------------------

    //Testet die Erstellung in der Liste von Ereignissen

    @Test
    public void EspressoTestEreignisErstellen() {
        onView(withId(R.id.hinzufuegeButton))
                .perform(click());
        onView(withText("Ereignis erstellen"))
                .perform(click());
        onView(withId(R.id.TitelEingabeEreignis))
                .perform(typeText("MBSN Praesentation"));
        onView(withId(R.id.TextEingabeErg))
                .perform(typeText("Vergiss dein Laptop nicht !!! "))
                .perform(ViewActions.closeSoftKeyboard());


        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(withId(R.id.btnPick))
                .perform(click());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(isAssignableFrom(TimePicker.class))
                .perform(PickerActions.setTime(12, 15));

        onView(withText("OK"))
                .perform(click());
        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(withId(R.id.bottonEreigniss))
                .perform(click());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(isAssignableFrom(DatePicker.class))
                .perform(PickerActions.setDate(2023, 07, 10));
        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(withText("OK"))
                .perform(click());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(withText("Ereignis erstellen"))
                .perform(click());
        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(withContentDescription("Zurück zum Home Menue"))
                .perform(click());

    }


    //Testet die Löschung von Ereignissen
    @Test
    public void EspressoTestEreignisLoeschen() {
        onView(withId(R.id.speicherungListe))
                .perform(click());
        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onData(anything())
                .inAdapterView(withId(R.id.ereignisseListView))
                .atPosition(0)
                .perform(click());

        // Eine kurze Verzögerung von 1 Sekunde einfügen
        SystemClock.sleep(1000);

        onView(withId(R.id.ereignisseloescheButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.ereignisseloescheButton))
                .perform(click());

        onView(withId(R.id.ereignisseListView))
                .check(matches(not(hasDescendant(any(View.class)))));

    }
}








