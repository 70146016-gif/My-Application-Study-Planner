package com.example.myapplicationstudyplanner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * The only Activity. It holds the title bar, the bottom tabs, and swaps
 * the 5 screens (fragments) in and out of fragment_container.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Keep content below the status bar. The bottom bar adds its own bottom space.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(bars.left, bars.top, bars.right, 0);
            return insets;
        });

        // Use our toolbar as the title bar so setTitle(...) shows on it
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            showScreen(item.getItemId());
            return true; // highlight the tapped tab
        });

        if (savedInstanceState == null) {
            showScreen(R.id.nav_home); // first launch: open Home
        } else {
            // After rotating, Android restores the fragment; just restore the title
            setTitle(titleFor(bottomNav.getSelectedItemId()));
        }
    }

    private void showScreen(int itemId) {
        Fragment screen;
        if (itemId == R.id.nav_subjects) {
            screen = new SubjectsFragment();
        } else if (itemId == R.id.nav_tasks) {
            screen = new TasksFragment();
        } else if (itemId == R.id.nav_exams) {
            screen = new ExamsFragment();
        } else if (itemId == R.id.nav_progress) {
            screen = new ProgressFragment();
        } else {
            screen = new HomeFragment();
        }

        setTitle(titleFor(itemId));
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, screen)
                .commit();
    }

    private String titleFor(int itemId) {
        if (itemId == R.id.nav_subjects) return "Subjects";
        if (itemId == R.id.nav_tasks) return "Tasks";
        if (itemId == R.id.nav_exams) return "Exams";
        if (itemId == R.id.nav_progress) return "Progress";
        return "Today";
    }
}
