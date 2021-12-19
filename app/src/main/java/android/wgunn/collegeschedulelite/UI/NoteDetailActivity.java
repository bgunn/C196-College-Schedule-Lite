package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.CourseNoteEntity;
import android.wgunn.collegeschedulelite.R;
import android.widget.EditText;
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

        EditText phoneNumber = new EditText(this);
        phoneNumber.setHint("123-456-7890");

        new AlertDialog.Builder(this)
            .setTitle("Share Note")
            .setMessage("Enter recipients phone number")
            .setView(phoneNumber)
            .setPositiveButton("Go", (dialog, whichButton) -> {
                Uri uri = Uri.parse("smsto:" + phoneNumber.getText().toString());
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", note.getNote());
                startActivity(intent);
            })
            .setNegativeButton("Cancel", (dialog, whichButton) -> {
                dialog.cancel();
            })
            .show();
    }
}