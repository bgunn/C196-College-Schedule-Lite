package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Entity.CourseNoteEntity;
import android.wgunn.collegeschedulelite.R;
import android.widget.Button;
import android.widget.EditText;

public class AddEditNoteActivity extends AppCompatActivity {

    private long courseId;
    private CourseNoteEntity note;
    private CourseRepository courseRepository;
    private EditText noteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        courseRepository = new CourseRepository(getApplication());

        noteData = findViewById(R.id.noteData);

        Intent intent = getIntent();
        long noteId = intent.getIntExtra("noteId", 0);

        if (noteId != 0) {
            note = courseRepository.getNote(noteId);
            noteData.setText(note.getNote());
            this.setTitle("Edit Note");
        } else {
            courseId = intent.getIntExtra("courseId", 0);
            this.setTitle("Add Note");
        }
    }

    public void onSaveButtonClick(View view) {

        String noteString = noteData.getText().toString();

        if (note != null) {
            note.setNote(noteString);
            courseRepository.updateNote(note);
        } else {
            note = new CourseNoteEntity(noteString, courseId);
            courseRepository.addNote(note);
        }

        Intent intent = new Intent(getApplicationContext(), CourseDetailActivity.class);
        intent.putExtra("courseId", note.getCourseId().intValue());
        startActivity(intent);
    }

    public void onCancelButtonClick(View view) {
        this.finish();
    }
}