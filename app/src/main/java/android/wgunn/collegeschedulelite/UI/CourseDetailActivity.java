package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.CourseAssessmentEntity;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.CourseNoteEntity;
import android.wgunn.collegeschedulelite.Entity.CourseWithChildren;
import android.wgunn.collegeschedulelite.Entity.TermEntity;
import android.wgunn.collegeschedulelite.R;
import android.wgunn.collegeschedulelite.Utilities.Alerts;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {

    private CourseEntity course;
    private List<CourseNoteEntity> notes;
    private List<CourseAssessmentEntity> assessments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long courseId = intent.getIntExtra("courseId", 0);

        CourseRepository courseRepository = new CourseRepository(getApplication());
        CourseWithChildren courseWithChildren = courseRepository.getWithChildren(courseId);

        course = courseWithChildren.course;
        notes = courseWithChildren.notes;
        assessments = courseWithChildren.assessments;

        this.setTitle(course.getTitle());

        setCourseData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private void setCourseData() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        TermEntity term = new TermRepository(getApplication()).get(course.getTermId());

        TextView termData = findViewById(R.id.termData);
        termData.setText(term.getName());

        TextView startDateData = findViewById(R.id.startDateData);
        startDateData.setText(simpleDateFormat.format(course.getStartDate()));

        TextView endDateData = findViewById(R.id.endDateData);
        endDateData.setText(simpleDateFormat.format(course.getEndDate()));

        TextView statusData = findViewById(R.id.statusData);
        statusData.setText(course.getStatus());

        TextView instructorNameData = findViewById(R.id.instructorNameData);
        instructorNameData.setText(course.getInstructorName());

        TextView instructorEmailData = findViewById(R.id.instructorEmailData);
        instructorEmailData.setText(course.getInstructorEmail());

        TextView instructorPhoneData = findViewById(R.id.instructorPhoneData);
        instructorPhoneData.setText(course.getInstructorPhone());

        // Setup the notes list
        ListView notesListView = findViewById(R.id.notesListView);
        ArrayAdapter<CourseNoteEntity> noteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        notesListView.setAdapter(noteAdapter);

        noteAdapter.notifyDataSetChanged();

        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), NoteDetailActivity.class);
            intent.putExtra("noteId", notes.get(position).getId().intValue());
            startActivity(intent);
        });

        // Setup the assessments list
        ListView assessmentsListView = findViewById(R.id.assessmentsListView);
        ArrayAdapter<CourseAssessmentEntity> assessmentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, assessments);
        assessmentsListView.setAdapter(assessmentAdapter);

        assessmentAdapter.notifyDataSetChanged();

        // Finish the detail view!!!
        assessmentsListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), AssessmentDetailActivity.class);
            intent.putExtra("assessmentId", assessments.get(position).getId().intValue());
            startActivity(intent);
        });
    }

    public void onEditCourseButtonClick(View view) {
        Intent intent = new Intent(CourseDetailActivity.this, AddEditCourseActivity.class);
        intent.putExtra("courseId", course.getId().intValue());
        startActivity(intent);
    }

    public void onAddNoteButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddEditNoteActivity.class);
        intent.putExtra("courseId", course.getId().intValue());
        startActivity(intent);
    }

    public void onAddAssessmentButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddEditAssessmentActivity.class);
        intent.putExtra("courseId", course.getId().intValue());
        startActivity(intent);
    }

    public void onDeleteCourseButtonClick(View view) {

        int termId = Math.toIntExact(course.getTermId());

        new AlertDialog.Builder(this)
            .setTitle("Confirm Delete")
            .setMessage("Delete course " + course.getTitle() + "?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {

                int startRC = Integer.parseInt(course.getId() + "" + 1);
                int endRC = Integer.parseInt(course.getId() + "" + 2);

                Alerts alerts = new Alerts();
                alerts.cancelAlert(getApplicationContext(), startRC);
                alerts.cancelAlert(getApplicationContext(), endRC);

                new CourseRepository(getApplication()).delete(course);

                Intent intent = new Intent(getApplicationContext(), TermDetailActivity.class);
                intent.putExtra("termId", termId);
                startActivity(intent);
            })
            .setNegativeButton(android.R.string.no, null).show();
    }
}