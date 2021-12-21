package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Entity.CourseAssessmentEntity;
import android.wgunn.collegeschedulelite.R;
import android.wgunn.collegeschedulelite.Utilities.Alerts;
import android.wgunn.collegeschedulelite.Utilities.DatePickerFragment;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddEditAssessmentActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private long courseId;
    private TextView datePicker;
    private TimePicker timePicker;
    private Spinner typeSelector;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private List<String> assessmentType = new ArrayList(Arrays.asList("performance", "objective"));
    private CourseAssessmentEntity assessment;
    private CourseRepository courseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessment);

        // Setup the type dropdown selector
        typeSelector = findViewById(R.id.typeSelector);
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(AddEditAssessmentActivity.this, android.R.layout.simple_spinner_item, assessmentType);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSelector.setAdapter(typeAdapter);

        courseRepository = new CourseRepository(getApplication());

        Intent intent = getIntent();
        long assessmentId = intent.getIntExtra("assessmentId", 0);

        if (assessmentId != 0) {
            setEditAssessmentData(assessmentId);
        } else {
            courseId = intent.getIntExtra("courseId", 0);
        }
    }

    private void setEditAssessmentData(Long assessmentId) {
        assessment = courseRepository.getAssessment(assessmentId);

        EditText titleData = findViewById(R.id.titleData);
        titleData.setText(assessment.getTitle());

        typeSelector.setSelection(assessmentType.indexOf(assessment.getType()));

        TextView startDateData = findViewById(R.id.startDateData);
        startDateData.setText(sdf.format(assessment.getStartDate()));

        TextView endDateData = findViewById(R.id.endDateData);
        endDateData.setText(sdf.format(assessment.getEndDate()));
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

    public void onSaveButtonClick(View view) {

        Date startDate = null;
        Date endDate = null;
        String msg;

        EditText titleData = findViewById(R.id.titleData);
        String title = titleData.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this, "Assessment title is required", Toast.LENGTH_LONG).show();
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

        // Attempt to parse the date strings
        try {
            startDate = sdf.parse(startDateString);
            endDate = sdf.parse(endDateString);
        } catch (ParseException e) {
            Toast.makeText(this, "ERROR: Date must be in the format yyyy-mm-dd", Toast.LENGTH_LONG).show();
            Log.e("myError", Log.getStackTraceString(e));
            return;
        }

        String type = typeSelector.getSelectedItem().toString();

        if (assessment != null) {
            assessment.setTitle(title);
            assessment.setType(type);
            assessment.setStartDate(startDate);
            assessment.setEndDate(endDate);
            courseRepository.updateAssessment(assessment);
        } else {
            assessment = new CourseAssessmentEntity(courseId, title, type, startDate, endDate);
            courseRepository.addAssessment(assessment);
        }


        /**
         *         // Add or remove alerts
         *         CheckBox enableAlerts = findViewById(R.id.enableAlerts);
         *         Alerts alerts = new Alerts();
         *
         *         int startRC = Integer.parseInt(course.getId() + "" + 1);
         *         int endRC = Integer.parseInt(course.getId() + "" + 2);
         *
         *         Alerts alerts = new Alerts();
         *
         *         if (enableAlerts.isChecked()) {
         *             String AlertTitle = "Course Notification";
         *             String startAlertMsg = "Course " + course.getTitle() + " starts on " + sdf.format(course.getStartDate());
         *             String endAlertMsg = "Course " + course.getTitle() + " ends on " + sdf.format(course.getEndDate());
         *
         *             // Set the start and end alert times to 1 week before the course start/end date
         *             long startAlertTime = course.getStartDate().getTime() - (86400000 * 7);
         *             long endAlertTime = course.getEndDate().getTime() - (86400000 * 7);
         *
         *             alerts.createAlert(getApplicationContext(), startRC, startAlertTime, "course", course.getId().intValue(), AlertTitle, startAlertMsg);
         *             alerts.createAlert(getApplicationContext(), endRC, endAlertTime, "course", course.getId().intValue(), AlertTitle, endAlertMsg);
         *         } else {
         *             alerts.cancelAlert(getApplicationContext(), startRC);
         *             alerts.cancelAlert(getApplicationContext(), endRC);
         *         }
         */

        CheckBox enableAlerts = findViewById(R.id.enableAlerts);
        Alerts alerts = new Alerts();

        int startRC = Integer.parseInt(assessment.getId() + "" + 1);
        int endRC = Integer.parseInt(assessment.getId() + "" + 2);

        if (enableAlerts.isChecked()) {
            String AlertTitle = "Assessment Notification";
            String startAlertMsg = "Assessment " + assessment.getTitle() + " starts on " + sdf.format(assessment.getStartDate());
            String endAlertMsg = "Assessment " + assessment.getTitle() + " ends on " + sdf.format(assessment.getEndDate());

            // Set the start and end alert times to 1 week before the course start/end date
            long startAlertTime = assessment.getStartDate().getTime() - (86400000 * 7);
            long endAlertTime = assessment.getEndDate().getTime() - (86400000 * 7);

            alerts.createAlert(getApplicationContext(), startRC, startAlertTime, "assessment", assessment.getId().intValue(), AlertTitle, startAlertMsg);
            alerts.createAlert(getApplicationContext(), endRC, endAlertTime, "assessment", assessment.getId().intValue(), AlertTitle, endAlertMsg);
        } else {
            alerts.cancelAlert(getApplicationContext(), startRC);
            alerts.cancelAlert(getApplicationContext(), endRC);
        }

        Intent intent = new Intent(getApplicationContext(), CourseDetailActivity.class);
        intent.putExtra("courseId", assessment.getCourseId().intValue());
        startActivity(intent);
    }

    public void onCancelButtonClick(View view) {
        this.finish();
    }
}