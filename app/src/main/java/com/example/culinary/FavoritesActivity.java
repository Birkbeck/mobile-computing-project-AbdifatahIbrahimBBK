package com.example.culinary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.culinary.database.AppDatabase;
import com.example.culinary.model.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private RecyclerView favoritesRecyclerView;
    private TextView emptyFavoritesText;
    private RecipeAdapter recipeAdapter;
    private AppDatabase db;
    private List<Recipe> favoriteRecipes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        // Initialize views
        bottomNav = findViewById(R.id.bottomNavigation);
        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        emptyFavoritesText = findViewById(R.id.emptyFavoritesText);
        db = AppDatabase.getDatabase(this);

        setupBottomNavigation();
        setupRecyclerView();
        
        // Set initial selected item
        bottomNav.setSelectedItemId(R.id.navigation_favorites);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFavoriteRecipes();
    }

    private void setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navigation_search) {
                startActivity(new Intent(this, SearchActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navigation_favorites) {
                // Already in FavoritesActivity, do nothing
                return true;
            }
            return false;
        });
    }

    private void setupRecyclerView() {
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this, favoriteRecipes, recipe -> {
            Intent intent = new Intent(FavoritesActivity.this, RecipeDetailActivity.class);
            intent.putExtra("recipe_id", recipe.getId());
            startActivity(intent);
        });
        favoritesRecyclerView.setAdapter(recipeAdapter);
    }

    private void loadFavoriteRecipes() {
        new LoadFavoriteRecipesAsyncTask(db, this).execute();
    }

    private void updateUI(List<Recipe> recipes) {
        favoriteRecipes.clear();
        favoriteRecipes.addAll(recipes);
        recipeAdapter.setRecipes(favoriteRecipes);
        
        if (favoriteRecipes.isEmpty()) {
            emptyFavoritesText.setVisibility(View.VISIBLE);
            favoritesRecyclerView.setVisibility(View.GONE);
        } else {
            emptyFavoritesText.setVisibility(View.GONE);
            favoritesRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private static class LoadFavoriteRecipesAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {
        private AppDatabase db;
        private FavoritesActivity activity;

        LoadFavoriteRecipesAsyncTask(AppDatabase db, FavoritesActivity activity) {
            this.db = db;
            this.activity = activity;
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            return db.recipeDao().getFavoriteRecipes();
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            if (activity != null) {
                activity.updateUI(recipes);
            }
        }
    }
} 