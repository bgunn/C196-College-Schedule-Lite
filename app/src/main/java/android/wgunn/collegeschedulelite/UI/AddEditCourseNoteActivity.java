package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.CourseNoteEntity;
import android.wgunn.collegeschedulelite.R;
import android.widget.EditText;

public class AddEditCourseNoteActivity extends AppCompatActivity {

    private CourseEntity course;
    private CourseNoteEntity note;
    private CourseRepository courseRepository = new CourseRepository(getApplication());
    private EditText noteData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course_note);

        noteData = findViewById(R.id.noteData);

        Intent intent = getIntent();
        long courseId = intent.getIntExtra("courseId", 0);
        long noteId = intent.getIntExtra("courseId", 0);

        course = courseRepository.get(courseId);

        if (noteId != 0) {
            note = courseRepository.getNote(noteId);
            noteData.setText(note.getNote());
        }
    }

    public void onSaveButtonClick(View view) {

        String noteString = noteData.getText().toString();

        if (note != null) {
            note.setNote(noteString);
            courseRepository.updateNote(note);
        } else {
            courseRepository.addNote(new CourseNoteEntity(noteString, course.getId()));
        }
    }

    public void onCancelButtonClick(View view) {
        this.finish();
    }
}