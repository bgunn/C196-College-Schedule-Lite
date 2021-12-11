package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.TermEntity;
import android.wgunn.collegeschedulelite.Entity.TermWithCourses;
import android.wgunn.collegeschedulelite.R;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TermDetailActivity extends AppCompatActivity {

    private TermEntity term;
    private List<CourseEntity> courses;

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

        // Set counts for each course status type and populate the list array
        for (CourseEntity course : courses) {

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

        // Show associated courses
        ListView coursesListView = findViewById(R.id.coursesListView);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.course_list_item, R.id.courseListItemTitle, courses) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView title = (TextView) view.findViewById(R.id.courseListItemTitle);
                TextView status = (TextView) view.findViewById(R.id.courseListItemStatus);
                TextView start = (TextView) view.findViewById(R.id.courseListItemStart);

                title.setText(courses.get(position).getTitle());
                status.setText(courses.get(position).getStatus());
                start.setText(simpleDateFormat.format(courses.get(position).getStartDate()));

                return view;
            }
        };

        coursesListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        coursesListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), TermDetailActivity.class);
            intent.putExtra("termId", courses.get(position).getId().intValue());
            startActivity(intent);
        });
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

    public void onAddCourseButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddEditTermActivity.class);
        intent.putExtra("termId", term.getId().intValue());
        startActivity(intent);
    }

    public void onEditTermButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), AddEditTermActivity.class);
        intent.putExtra("termId", term.getId().intValue());
        startActivity(intent);
    }
}