package com.example.myapplicationstudyplanner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
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

    private static final String KEY_TITLE = "screen_title";
    private MaterialToolbar toolbar;

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

        // Our title bar. The title is set on the toolbar directly, because the XML
        // already gives it a title and Android then ignores Activity.setTitle().
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setOnItemSelectedListener(item -> {
            showScreen(item.getItemId());
            return true; // highlight the tapped tab
        });

        if (savedInstanceState == null) {
            showScreen(R.id.nav_home); // first launch: open Home
        } else {
            // After rotating, Android restores the fragment; restore the saved title
            toolbar.setTitle(savedInstanceState.getCharSequence(KEY_TITLE, "Today"));
        }
    }

    /** Remember the current title so it survives rotation. */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(KEY_TITLE, toolbar.getTitle());
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

        toolbar.setTitle(titleFor(itemId));
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
