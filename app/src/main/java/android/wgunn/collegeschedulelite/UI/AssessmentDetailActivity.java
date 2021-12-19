package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Entity.CourseAssessmentEntity;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.R;
import android.wgunn.collegeschedulelite.Utilities.Alerts;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class AssessmentDetailActivity extends AppCompatActivity {

    private CourseRepository courseRepository;
    private CourseEntity course;
    private CourseAssessmentEntity assessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        long assessmentId = intent.getIntExtra("assessmentId", 0);

        courseRepository = new CourseRepository(getApplication());
        assessment = courseRepository.getAssessment(assessmentId);
        course = courseRepository.get(assessment.getCourseId());

        TextView titleData = findViewById(R.id.titleData);
        titleData.setText(assessment.getTitle());

        TextView courseData = findViewById(R.id.courseData);
        courseData.setText(course.getTitle());

        TextView typeData = findViewById(R.id.typeData);
        typeData.setText(assessment.getType());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        TextView startDateData = findViewById(R.id.startDateData);
        startDateData.setText(formatter.format(assessment.getStartDate()));

        TextView endDateData = findViewById(R.id.endDateData);
        endDateData.setText(formatter.format(assessment.getEndDate()));

        this.setTitle("Assessment Detail");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    public void onEditCourseButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddEditAssessmentActivity.class);
        intent.putExtra("assessmentId", assessment.getId().intValue());
        startActivity(intent);
    }

    public void onDeleteCourseButtonClick(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Delete assessment?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {

                    int startRC = Integer.parseInt(assessment.getId() + "" + 1);
                    int endRC = Integer.parseInt(assessment.getId() + "" + 2);

                    Alerts alerts = new Alerts();
                    alerts.cancelAlert(getApplicationContext(), startRC);
                    alerts.cancelAlert(getApplicationContext(), endRC);

                    Intent intent = new Intent(getApplicationContext(), CourseDetailActivity.class);
                    intent.putExtra("courseId", assessment.getCourseId().intValue());

                    courseRepository.deleteAssessment(assessment);

                    startActivity(intent);
                })
                .setNegativeButton(android.R.string.no, null).show();
    }
}