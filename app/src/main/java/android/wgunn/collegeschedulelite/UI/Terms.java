package android.wgunn.collegeschedulelite.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.wgunn.collegeschedulelite.Database.TermRepository;
import android.wgunn.collegeschedulelite.Entity.Term;
import android.wgunn.collegeschedulelite.R;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Objects;

public class Terms extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        ListView termsListView = findViewById(R.id.termsListView);

        TermRepository termRepository = new TermRepository(getApplication());

        List<Term> termsList = termRepository.getAll();

        ArrayAdapter<Term> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, termsList);
        termsListView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        termsListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), TermDetail.class);
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
        // TODO: This will be the add term form
        Intent intent = new Intent(Terms.this, TermDetail.class);
        startActivity(intent);
    }
}