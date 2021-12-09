package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.Course;
import android.wgunn.collegeschedulelite.Entity.Term;
import android.wgunn.collegeschedulelite.Entity.TermWithCourses;
import android.wgunn.collegeschedulelite.R;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

public class TermDetail extends AppCompatActivity {

    private Term term;
    private List<Course> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long termId = intent.getIntExtra("termId", 0);

        TermRepository termRepository = new TermRepository(getApplication());
        TermWithCourses termWithCourses = termRepository.getWithCourses(termId);

        term = termWithCourses.term;
        courses = termWithCourses.courses;

        this.setTitle(term.getName());

        setTermData();
    }

    private void setTermData() {

        int inprogress = 0;
        int completed = 0;
        int dropped = 0;
        int planned = 0;

        // Format the dates
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        TextView startDateData = findViewById(R.id.startDateData);
        startDateData.setText(simpleDateFormat.format(term.getStartDate()));

        TextView endDateData = findViewById(R.id.endDateData);
        endDateData.setText(simpleDateFormat.format(term.getEndDate()));

        // Set the card title label
        TextView termCoursesLabel = findViewById(R.id.termCoursesLabel);
        termCoursesLabel.setText(getString(R.string.term_courses_label, term.getName()));

        // Set counts for each course status type
        for (Course course : courses) {
            switch (course.getStatus()) {
                case "in-progress": inprogress++; break;
                case "completed": completed++; break;
                case "dropped": dropped++; break;
                case "planned": planned++; break;
            }
        }

        TextView inProgressCoursesData = findViewById(R.id.inProgressCoursesData);
        inProgressCoursesData.setText(String.valueOf(inprogress));

        TextView completedCoursesData = findViewById(R.id.completedCoursesData);
        completedCoursesData.setText(String.valueOf(completed));

        TextView droppedCoursesData = findViewById(R.id.droppedCoursesData);
        droppedCoursesData.setText(String.valueOf(dropped));

        TextView plannedCoursesData = findViewById(R.id.plannedCoursesData);
        plannedCoursesData.setText(String.valueOf(planned));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCoursesButtonClick(View view) {

    }

    public void onEditTermButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddEditTerm.class);
        intent.putExtra("termId", term.getId().intValue());
        startActivity(intent);
    }
}