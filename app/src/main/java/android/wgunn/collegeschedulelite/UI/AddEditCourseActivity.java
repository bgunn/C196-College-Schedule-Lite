package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.TermEntity;
import android.wgunn.collegeschedulelite.R;
import android.wgunn.collegeschedulelite.Utilities.DatePickerFragment;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AddEditCourseActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private CourseEntity course;
    private CourseRepository courseRepository;
    private TermRepository termRepository;
    private TextView datePicker;
    private List<String> statusList = new ArrayList(Arrays.asList("in-progress", "completed", "dropped", "planned"));
    private Spinner statusSpinner;
    private Spinner termsSpinner;
    private List<String> termsList = new ArrayList();
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);

        courseRepository = new CourseRepository(getApplication());
        termRepository = new TermRepository(getApplication());

        Intent intent = getIntent();
        long courseId = (long) intent.getIntExtra("courseId", 0);
        long termId = (long) intent.getIntExtra("termId", 0);
        
        this.setTitle(courseId == 0 ? "Add Course" : "Edit Course");

        // Setup the status dropdown selector
        statusSpinner = (Spinner)findViewById(R.id.statusSelector);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(AddEditCourseActivity.this, android.R.layout.simple_spinner_item, statusList);

        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        // Setup the term dropdown selector
        for (TermEntity t : termRepository.getAll()) {
            termsList.add(t.getName());
        }

        termsSpinner = (Spinner)findViewById(R.id.termSelector);
        ArrayAdapter<String> termsAdapter = new ArrayAdapter<String>(AddEditCourseActivity.this, android.R.layout.simple_spinner_item, termsList);

        termsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        termsSpinner.setAdapter(termsAdapter);

        if (courseId != 0) {
            setEditCourseData(courseId);
        }
    }

    public void onSaveButtonClick(View view) {

        Date startDate = null;
        Date endDate = null;
        String msg;

        EditText titleData = findViewById(R.id.titleData);
        String title = titleData.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this, "Course title is required", Toast.LENGTH_LONG).show();
            return;
        }

        TextView startDateData = findViewById(R.id.startDateData);
        String startDateString = startDateData.getText().toString();
        if (startDateString.isEmpty()) {
            Toast.makeText(this, "Start date is required in the form yyyy-mm-dd", Toast.LENGTH_LONG).show();
            return;
        }

        TextView endDateData = findViewById(R.id.endDateData);
        String endDateString = endDateData.getText().toString();
        if (endDateString.isEmpty()) {
            Toast.makeText(this, "End date is required in the form yyyy-mm-dd", Toast.LENGTH_LONG).show();
            return;
        }

        EditText instructorNameData = findViewById(R.id.instructorNameData);
        String instructorName = instructorNameData.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this, "Instructor name is required", Toast.LENGTH_LONG).show();
            return;
        }

        EditText instructorEmailData = findViewById(R.id.instructorEmailData);
        String instructorEmail = instructorEmailData.getText().toString();
        if (instructorEmail.isEmpty()) {
            Toast.makeText(this, "Instructor email is required", Toast.LENGTH_LONG).show();
            return;
        }

        EditText instructorPhoneData = findViewById(R.id.instructorPhoneData);
        String instructorPhone = instructorPhoneData.getText().toString();
        if (instructorPhone.isEmpty()) {
            Toast.makeText(this, "Instructor phone is required", Toast.LENGTH_LONG).show();
            return;
        }

        String status = statusSpinner.getSelectedItem().toString();

        // Get the selected term
        String termName = termsSpinner.getSelectedItem().toString();
        TermEntity selectedTerm = termRepository.getByName(termName);

        // Attempt to parse the date strings
        try {
            startDate = sdf.parse(startDateString);
            endDate = sdf.parse(endDateString);
        } catch (ParseException e) {
            Toast.makeText(this, "ERROR: Date must be in the format yyyy-mm-dd", Toast.LENGTH_LONG).show();
            Log.e("myError", Log.getStackTraceString(e));
            return;
        }

        if (Objects.requireNonNull(endDate).before(startDate)) {
            Toast.makeText(this, "ERROR: End date must come after start date", Toast.LENGTH_LONG).show();
            return;
        }

        // Verify course falls within the specified term
        if (Objects.requireNonNull(startDate).before(selectedTerm.getStartDate()) ||
                Objects.requireNonNull(endDate).after(selectedTerm.getEndDate()))
        {
            Toast.makeText(this,
                    "ERROR: The specified dates fall outside the selected term dates: " +
                            sdf.format(selectedTerm.getStartDate()) + " - " + sdf.format(selectedTerm.getEndDate()),
                    Toast.LENGTH_LONG).show();
            return;
        }


        if (course != null) {
            course.setTitle(title);
            course.setStartDate(startDate);
            course.setEndDate(endDate);
            course.setStatus(status);
            course.setInstructorName(instructorName);
            course.setInstructorEmail(instructorEmail);
            course.setInstructorPhone(instructorPhone);
            msg = "Successfully updated course " + course.getId();
        } else {
            course = new CourseEntity(selectedTerm.getId(), title, startDate, endDate, status, instructorName, instructorPhone, instructorEmail);
            msg = "Successfully added course " + title;
        }

        try {
            courseRepository.save(course);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error saving course!", Toast.LENGTH_LONG).show();
            Log.e("myError", Log.getStackTraceString(e));
            return;
        }

        Intent intent = new Intent(getApplicationContext(), CourseDetailActivity.class);
        intent.putExtra("courseId", course.getId().intValue());
        startActivity(intent);
    }

    public void setEditCourseData(Long courseId) {

        course = courseRepository.get(courseId);

        EditText titleData = findViewById(R.id.titleData);
        titleData.setText(course.getTitle());

        TextView startDateData = findViewById(R.id.startDateData);
        startDateData.setText(sdf.format(course.getStartDate()));

        TextView endDateData = findViewById(R.id.endDateData);
        endDateData.setText(sdf.format(course.getEndDate()));

        EditText instructorNameData = findViewById(R.id.instructorNameData);
        instructorNameData.setText(course.getInstructorName());

        EditText instructorEmailData = findViewById(R.id.instructorEmailData);
        instructorEmailData.setText(course.getInstructorEmail());

        EditText instructorPhoneData = findViewById(R.id.instructorPhoneData);
        instructorPhoneData.setText(course.getInstructorPhone());

        statusSpinner.setSelection(statusList.indexOf(course.getStatus()));
        termsSpinner.setSelection(termsList.indexOf(termRepository.get(course.getTermId()).getName()));
    }

    public void showStartDatePickerDialog(View v) {
        datePicker = findViewById(R.id.startDateData);
        new DatePickerFragment().show(getSupportFragmentManager(), "startDatePicker");
    }

    public void showEndDatePickerDialog(View v) {
        datePicker = findViewById(R.id.endDateData);
        new DatePickerFragment().show(getSupportFragmentManager(), "endDatePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        datePicker.setText(sdf.format(calendar.getTime()));
    }

    public void onCancelButtonClick(View view) {
        this.finish();
    }
}