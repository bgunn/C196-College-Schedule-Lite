package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.CourseNoteEntity;
import android.wgunn.collegeschedulelite.R;
import android.widget.TextView;

public class NoteDetailActivity extends AppCompatActivity {

    private CourseRepository courseRepository;
    private CourseNoteEntity note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long noteId = intent.getIntExtra("noteId", 0);

        courseRepository = new CourseRepository(getApplication());

        note = courseRepository.getNote(noteId);

        TextView noteData = findViewById(R.id.noteData);
        noteData.setText(note.getNote());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void onDeleteNoteButtonClick(View view) {

        Intent intent = new Intent(getApplicationContext(), CourseDetailActivity.class);
        intent.putExtra("courseId", note.getCourseId().intValue());

        courseRepository.deleteNote(note);

        startActivity(intent);
    }

    public void onEditNoteButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddEditNoteActivity.class);
        intent.putExtra("noteId", note.getId().intValue());
        startActivity(intent);
    }

    public void onShareNoteButtonClick(View view) {

        CourseEntity course = courseRepository.get(note.getCourseId());

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT, course.getTitle() + " Note");
        intent.putExtra(Intent.EXTRA_TEXT, note.getNote());
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent, "Share note via"));
    }
}