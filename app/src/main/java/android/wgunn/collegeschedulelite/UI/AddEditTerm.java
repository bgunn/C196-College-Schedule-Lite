package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.Term;
import android.wgunn.collegeschedulelite.R;
import android.wgunn.collegeschedulelite.Utilities.DatePickerFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEditTerm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Term term;
    private TermRepository termRepository;
    private TextView datePicker;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_term);

        Intent intent = getIntent();
        long termId = (long) intent.getIntExtra("termId", 0);

        this.setTitle(termId == 0 ? "Add Term" : "Edit Term");

        if (termId != 0) {
            setEditTermData(termId);
        }
    }

    public void setEditTermData(Long termId) {

        termRepository = new TermRepository(getApplication());

        term = termRepository.get(termId);

        TextView termNameData = findViewById(R.id.termNameData);
        termNameData.setText(term.getName());

        TextView termStartDateData = findViewById(R.id.termStartDateData);
        termStartDateData.setText(simpleDateFormat.format(term.getStartDate()));

        TextView termEndDateData = findViewById(R.id.termEndDateData);
        termEndDateData.setText(simpleDateFormat.format(term.getEndDate()));
    }

    public void showStartDatePickerDialog(View v) {
        datePicker = findViewById(R.id.termStartDateData);
        new DatePickerFragment().show(getSupportFragmentManager(), "startDatePicker");
    }

    public void showEndDatePickerDialog(View v) {
        datePicker = findViewById(R.id.termEndDateData);
        new DatePickerFragment().show(getSupportFragmentManager(), "endDatePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        datePicker.setText(simpleDateFormat.format(calendar.getTime()));
    }

    public void onSaveButtonClick(View view) {

        String msg = null;
        Date startDate = null;
        Date endDate = null;

        EditText termNameData = findViewById(R.id.termNameData);
        TextView termStartDateData = findViewById(R.id.termStartDateData);
        TextView termEndDateData = findViewById(R.id.termEndDateData);

        String name = termNameData.getText().toString();
        String startDateString = termStartDateData.getText().toString();
        String endDateString = termEndDateData.getText().toString();

        Log.d("myDebug", "NAME: " + startDateString);
        Log.d("myDebug", "START: " + startDateString);

        if (name.isEmpty()) {
            Toast.makeText(this, "Term name is required", Toast.LENGTH_LONG).show();
            return;
        }

        if (startDateString.isEmpty()) {
            Toast.makeText(this, "Start date is required", Toast.LENGTH_LONG).show();
            return;
        }

        if (endDateString.isEmpty()) {
            Toast.makeText(this, "End date is required", Toast.LENGTH_LONG).show();
            return;
        }

        // Parse the date strings
        try {
            startDate = simpleDateFormat.parse(startDateString);
            endDate = simpleDateFormat.parse(endDateString);
        } catch (ParseException e) {
            Toast.makeText(this, "Error parsing date!", Toast.LENGTH_LONG).show();
            Log.e("myError", Log.getStackTraceString(e));
            return;
        }

        if (term != null) {
            term.setName(name);
            term.setStartDate(startDate);
            term.setEndDate(endDate);
            msg = "Successfully updated term " + term.getId();
        } else {
            term = new Term(name, startDate, endDate);
            msg = "Successfully added term " + name;
        }

        try {
            termRepository.save(term);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Error saving term to the database!", Toast.LENGTH_LONG).show();
            Log.e("myError", Log.getStackTraceString(e));
            return;
        }

        Intent intent = new Intent(getApplicationContext(), TermDetail.class);
        intent.putExtra("termId", term.getId().intValue());
        startActivity(intent);
    }

    public void onCancelButtonClick(View view) {
        this.finish();
    }
}