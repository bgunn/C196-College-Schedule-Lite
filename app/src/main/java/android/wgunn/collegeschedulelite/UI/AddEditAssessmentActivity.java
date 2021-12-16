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

        timePicker = findViewById(R.id.timeData);
        timePicker.setIs24HourView(true);

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

        TextView dateData = findViewById(R.id.dateData);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        dateData.setText(dtf.format(assessment.getDateTime()));

        timePicker.setHour(assessment.getDateTime().getHour());
        timePicker.setMinute(assessment.getDateTime().getMinute());
    }

    public void showDatePickerDialog(View view) {
        datePicker = findViewById(R.id.dateData);
        new DatePickerFragment().show(getSupportFragmentManager(), "datePicker");
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

        Date date = null;

        EditText titleData = findViewById(R.id.titleData);
        String title = titleData.getText().toString();
        if (title.isEmpty()) {
            Toast.makeText(this, "Assessment title is required", Toast.LENGTH_LONG).show();
            return;
        }

        TextView dateData = findViewById(R.id.dateData);
        String dateString = dateData.getText().toString();
        if (dateString.isEmpty()) {
            Toast.makeText(this, "Date is required in the form yyyy-mm-dd", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            Toast.makeText(this, "ERROR: Date must be in the format yyyy-mm-dd", Toast.LENGTH_LONG).show();
            Log.e("myError", Log.getStackTraceString(e));
            return;
        }

        // Create a LocalDateTime object from the date and time values
        int hour = timePicker.getHour();
        int min = timePicker.getMinute();

        String dateTimeString = dateString + " " + String.format("%02d:%02d", hour, min);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Caused by: java.time.format.DateTimeParseException: Text '2021-12-31 8:30' could not be parsed at index 11
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeString, dtf);

        String type = typeSelector.getSelectedItem().toString();

        if (assessment != null) {
            assessment.setTitle(title);
            assessment.setType(type);
            assessment.setDateTime(dateTime);
            courseRepository.updateAssessment(assessment);
        } else {
            assessment = new CourseAssessmentEntity(courseId, title, type, dateTime);
            courseRepository.addAssessment(assessment);
        }

        String AlertTitle = "Assessment Notification";
        String startAlertMsg = "Assessment " + assessment.getTitle() + " starts on " + dtf.format(assessment.getDateTime());

        // Set the start and end alert times to 1 week before the course start/end date
        long startAlertTime = assessment.getDateTime().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - (86400000 * 7);

        Alerts alerts = new Alerts();
        alerts.createAlert(getApplicationContext(), assessment.getId().intValue(), startAlertTime, "assessment", assessment.getId().intValue(), AlertTitle, startAlertMsg);

        Intent intent = new Intent(getApplicationContext(), CourseDetailActivity.class);
        intent.putExtra("courseId", assessment.getCourseId().intValue());
        startActivity(intent);
    }

    public void onCancelButtonClick(View view) {
        this.finish();
    }
}