package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.CourseEntity;
import android.wgunn.collegeschedulelite.Entity.TermEntity;
import android.wgunn.collegeschedulelite.R;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class CourseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);

        TermRepository termRepository = new TermRepository(getApplication());

        List<CourseEntity> courses = new CourseRepository(getApplication()).getAll();

        ListView coursesListView = findViewById(R.id.coursesListView);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.course_list_item, R.id.courseListItemTitle, courses) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                CourseEntity course = courses.get(position);
                TermEntity term = termRepository.get(course.getTermId());

                View view = super.getView(position, convertView, parent);
                TextView title = (TextView) view.findViewById(R.id.courseListItemTitle);
                TextView termName = (TextView) view.findViewById(R.id.courseListItemStatus);
                TextView start = (TextView) view.findViewById(R.id.courseListItemStart);

                title.setText(course.getTitle());
                termName.setText(term.getName());
                start.setText(sdf.format(course.getStartDate()));

                return view;
            }
        };

        coursesListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        // Update the list header to replace status with term name
        TextView h = findViewById(R.id.coursesListStatusHead);
        h.setText(R.string.term_heading);

        coursesListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), CourseDetailActivity.class);
            intent.putExtra("courseId", courses.get(position).getId().intValue());
            startActivity(intent);
        });
    }

    public void onAddCourseFABClick(View view) {
        Intent intent = new Intent(CourseListActivity.this, AddEditCourseActivity.class);
        startActivity(intent);
    }
}