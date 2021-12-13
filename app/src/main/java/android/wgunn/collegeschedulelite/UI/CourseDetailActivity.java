package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.CourseAssessmentEntity;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.CourseNoteEntity;
import android.wgunn.collegeschedulelite.Entity.CourseWithChildren;
import android.wgunn.collegeschedulelite.Entity.TermEntity;
import android.wgunn.collegeschedulelite.R;
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
    }

    public void onEditCourseButtonClick(View view) {
        Intent intent = new Intent(CourseDetailActivity.this, AddEditCourseActivity.class);
        intent.putExtra("courseId", course.getId().intValue());
        startActivity(intent);
    }

    public void onAddNoteButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddEditCourseNoteActivity.class);
        intent.putExtra("courseId", course.getId().intValue());
        startActivity(intent);
    }

    public void onAddAssessmentButtonClick(View view) {
    }
}