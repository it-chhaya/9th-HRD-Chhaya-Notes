package com.devkh.chhayanotes;

import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.devkh.chhayanotes.data.local.AppDatabase;
import com.devkh.chhayanotes.data.model.local.Note;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private AppDatabase appDatabase;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.devkh.chhayanotes", appContext.getPackageName());
    }

    @Test
    public void insertNote() {

        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appDatabase = AppDatabase.getInstance(appContext);

        Note note = new Note();
        note.setNoteTitle("Shang Chi");
        note.setNoteContent("Super Hero");
        note.setNoteSavedDate(System.currentTimeMillis());

        appDatabase.noteDao().insert(note);

        Log.i("NOTE", "insertNote: " + appDatabase.noteDao().select());

    }

    @Test
    public void select() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appDatabase = AppDatabase.getInstance(appContext);
        Log.i("NOTE", "selected note : " + appDatabase.noteDao().select(3));
    }
}