package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.wgunn.collegeschedulelite.Database.CourseRepository;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.CourseAssessmentEntity;
import android.wgunn.collegeschedulelite.Entity.TermEntity;
import android.wgunn.collegeschedulelite.R;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class AssessmentListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_list);

        ListView aListView = findViewById(R.id.assessmentsListView);

        CourseRepository courseRepository = new CourseRepository(getApplication());

        List<CourseAssessmentEntity> aList = courseRepository.getAllAssessments();

        ArrayAdapter<CourseAssessmentEntity> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aList);
        aListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        aListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), AssessmentDetailActivity.class);
            intent.putExtra("assessmentId", aList.get(position).getId().intValue());
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
}