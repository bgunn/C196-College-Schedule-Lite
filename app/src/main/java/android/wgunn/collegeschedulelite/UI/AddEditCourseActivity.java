package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.R;
import android.wgunn.collegeschedulelite.Utilities.DatePickerFragment;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class AddEditCourseActivity extends AppCompatActivity {

    private CourseEntity course;
    private CourseRepository courseRepository;
    private TextView datePicker;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);

        courseRepository = new CourseRepository(getApplication());

        Intent intent = getIntent();
        long courseId = (long) intent.getIntExtra("courseId", 0);
        long termId = (long) intent.getIntExtra("termId", 0);

        // TODO: FINISH THE ADD EDIT COURSE!!!
        
        this.setTitle(courseId == 0 ? "Add Course" : "Edit Course");

        if (courseId != 0) {
            setEditCourseData(courseId, termId);
        }
    }

    public void setEditCourseData(Long courseId, Long termId) {

        course = courseRepository.get(courseId);

        TextView courseTitleData = findViewById(R.id.courseTitleData);
        courseTitleData.setText(course.getTitle());

        TextView courseStartDateData = findViewById(R.id.courseStartDateData);
        courseStartDateData.setText(simpleDateFormat.format(course.getStartDate()));

        TextView courseEndDateData = findViewById(R.id.courseEndDateData);
        courseEndDateData.setText(simpleDateFormat.format(course.getEndDate()));
    }

    public void showStartDatePickerDialog(View v) {
        datePicker = findViewById(R.id.termStartDateData);
        new DatePickerFragment().show(getSupportFragmentManager(), "startDatePicker");
    }

    public void showEndDatePickerDialog(View v) {
        datePicker = findViewById(R.id.termEndDateData);
        new DatePickerFragment().show(getSupportFragmentManager(), "endDatePicker");
    }
}