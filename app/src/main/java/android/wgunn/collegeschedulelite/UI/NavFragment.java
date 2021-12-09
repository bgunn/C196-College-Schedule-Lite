package android.wgunn.collegeschedulelite.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.wgunn.collegeschedulelite.R;

public class NavFragment extends Fragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_nav, container, false);
        View termsMenu = v.findViewById(R.id.action_terms);
        termsMenu.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {

        // Implement the other links!!!

        switch (v.getId()) {
            case R.id.action_terms:
                Intent i = new Intent(getActivity().getApplicationContext(), Terms.class);
                startActivity(i);
                break;
        }
    }
}