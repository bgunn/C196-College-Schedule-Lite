package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.Course;
import android.wgunn.collegeschedulelite.Entity.Term;
import android.wgunn.collegeschedulelite.R;
import android.widget.Toast;

import java.util.Calendar;;

public class MainActivity extends AppCompatActivity {

    private TermRepository termRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        termRepository = new TermRepository(getApplication());

        sampleData();

        this.setTitle("Dashboard");
    }

    public void onTermsButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, Terms.class);
        startActivity(intent);
    }

    private void sampleData() {

        // Add a term
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        start.add(Calendar.MONTH, -2);
        end.add(Calendar.MONTH, 4);

        Term term1 = new Term("Fall 2021", start.getTime(), end.getTime());

        termRepository.save(term1);

        Toast.makeText(this, "Term ID: " + term1.getId(), Toast.LENGTH_SHORT).show();

        // Add and attach a course
        //Course course1 = new Course(term1.getId());
    }
}