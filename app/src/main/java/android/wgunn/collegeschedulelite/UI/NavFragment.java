package android.wgunn.collegeschedulelite.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.wgunn.collegeschedulelite.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;


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

        View homeMenu = v.findViewById(R.id.action_home);
        homeMenu.setOnClickListener(this);

        View coursesMenu = v.findViewById(R.id.action_courses);
        coursesMenu.setOnClickListener(this);

        View assessmentsMenu = v.findViewById(R.id.action_assessments);
        assessmentsMenu.setOnClickListener(this);

        return v;
    }

    /**
     * Set the selected menu item based on the current activity class
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottom_nav);

        String c = getActivity().getClass().getName();

        if (c.contains("MainActivity")) {
            bottomNavigationView.setSelectedItemId(R.id.action_home);
        } else if (c.contains("TermsActivity")) {
            bottomNavigationView.setSelectedItemId(R.id.action_terms);
        } else if (c.contains("CoursesActivity")) {
            bottomNavigationView.setSelectedItemId(R.id.action_courses);
        } else if (c.contains("AssessmentsActivity")) {
            bottomNavigationView.setSelectedItemId(R.id.action_assessments);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.action_home:
                startActivity(new Intent(requireActivity().getApplicationContext(), MainActivity.class));
                break;

            case R.id.action_terms:
                startActivity(new Intent(requireActivity().getApplicationContext(), TermsActivity.class));
                break;

            case R.id.action_courses:
                startActivity(new Intent(requireActivity().getApplicationContext(), TermsActivity.class));
                break;

            case R.id.action_assessments:
                startActivity(new Intent(requireActivity().getApplicationContext(), TermsActivity.class));
                break;
        }
    }
}