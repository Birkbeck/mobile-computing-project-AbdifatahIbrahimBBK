package com.example.culinary;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private BottomNavigationView bottomNav;
    private FloatingActionButton fabAddRecipe;
    private RecipePagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        bottomNav = findViewById(R.id.bottomNavigation);
        fabAddRecipe = findViewById(R.id.fabAddRecipe);

        // Initialize ViewPager adapter
        pagerAdapter = new RecipePagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Set up ViewPager with TabLayout
        setupViewPager();
        
        // Set up bottom navigation
        setupBottomNavigation();
        
        // Set up FAB click listener
        setupFab();

        // Set initial selected item
        bottomNav.setSelectedItemId(R.id.navigation_home);
    }

    private void setupViewPager() {
        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager,
            (tab, position) -> {
                switch (position) {
                    case 0:
                        tab.setText("All Recipes");
                        break;
                    case 1:
                        tab.setText("Popular");
                        break;
                    case 2:
                        tab.setText("New");
                        break;
                }
            });
        mediator.attach();
    }

    private void setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                // Already in MainActivity, do nothing
                return true;
            } else if (itemId == R.id.navigation_search) {
                startActivity(new Intent(this, SearchActivity.class));
                finish(); // Close MainActivity
                return true;
            } else if (itemId == R.id.navigation_favorites) {
                startActivity(new Intent(this, FavoritesActivity.class));
                finish(); // Close MainActivity
                return true;
            }
            return false;
        });
    }

    private void setupFab() {
        fabAddRecipe.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
            startActivity(intent);
        });
    }
} 