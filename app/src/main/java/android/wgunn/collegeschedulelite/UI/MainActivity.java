package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.TermEntity;
import android.wgunn.collegeschedulelite.R;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    /**
     * The term repository
     */
    private TermRepository termRepository;

    /**
     * The course repository
     */
    private CourseRepository courseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        termRepository = new TermRepository(getApplication());
        courseRepository = new CourseRepository(getApplication());

        sampleData();

        setDashboardData();
    }

    public void setDashboardData() {

        // Set the current term
        TextView currentTermData = findViewById(R.id.currentTermData);
        currentTermData.setText(termRepository.getCurrentTerm().getName());

        // Set remaining terms
        TextView remainingTermsData = findViewById(R.id.remainingTermsData);
        remainingTermsData.setText(String.valueOf(termRepository.getRemainingTerms().size()));

        // Set completed terms
        TextView completedTermsData = findViewById(R.id.completedTermsData);
        completedTermsData.setText(String.valueOf(termRepository.getCompletedTerms().size()));

        // Set course counts by type
        TextView inProgressCoursesData = findViewById(R.id.inProgressCoursesData);
        inProgressCoursesData.setText(String.valueOf(courseRepository.getCoursesByStatus("in-progress").size()));

        TextView completedCoursesData = findViewById(R.id.completedCoursesData);
        completedCoursesData.setText(String.valueOf(courseRepository.getCoursesByStatus("completed").size()));

        TextView droppedCoursesData = findViewById(R.id.droppedCoursesData);
        droppedCoursesData.setText(String.valueOf(courseRepository.getCoursesByStatus("dropped").size()));

        TextView plannedCoursesData = findViewById(R.id.plannedCoursesData);
        plannedCoursesData.setText(String.valueOf(courseRepository.getCoursesByStatus("planned").size()));
    }

    public void onTermsButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, TermListActivity.class);
        startActivity(intent);
    }

    private void sampleData() {

        if (termRepository.getAll().size() > 0) {
            return;
        }

        // Add a term
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        start.add(Calendar.MONTH, -2);
        end.add(Calendar.MONTH, 4);

        TermEntity term1 = new TermEntity("Fall 2021", start.getTime(), end.getTime());

        termRepository.save(term1);

        Toast.makeText(this, "TermEntity ID: " + term1.getId(), Toast.LENGTH_SHORT).show();

        // Add a course
        Calendar courseStart = Calendar.getInstance();
        Calendar courseEnd = Calendar.getInstance();

        courseStart.add(Calendar.MONTH, 1);
        courseEnd.add(Calendar.MONTH, 2);

        CourseEntity course1 = new CourseEntity(
            term1.getId(),
            "C195",
            courseStart.getTime(),
            courseEnd.getTime(),
            "plan to take",
            "Instructor",
            "123-456-7890",
            "me@nowhere.com"
        );

        courseRepository.save(course1);
    }
}