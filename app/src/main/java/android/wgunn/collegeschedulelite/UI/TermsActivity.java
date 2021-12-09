package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.TermEntity;
import android.wgunn.collegeschedulelite.R;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Objects;

public class TermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ListView termsListView = findViewById(R.id.termsListView);

        TermRepository termRepository = new TermRepository(getApplication());

        List<TermEntity> termsList = termRepository.getAll();

        ArrayAdapter<TermEntity> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, termsList);
        termsListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        termsListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), TermDetailActivity.class);
            intent.putExtra("termId", termsList.get(position).getId().intValue());
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

    public void onAddTermFABClick(View view) {
        Intent intent = new Intent(TermsActivity.this, AddEditTermActivity.class);
        startActivity(intent);
    }
}