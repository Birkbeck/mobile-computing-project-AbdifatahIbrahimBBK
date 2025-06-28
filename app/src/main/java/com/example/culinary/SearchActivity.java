package com.example.culinary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.example.culinary.database.AppDatabase;
import com.example.culinary.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private BottomNavigationView bottomNav;
    private TextInputEditText searchEditText;
    private RecyclerView searchResultsRecyclerView;
    private TextView noResultsText;
    private RecipeAdapter recipeAdapter;
    private AppDatabase db;
    private List<Recipe> searchResults = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        bottomNav = findViewById(R.id.bottomNavigation);
        searchEditText = findViewById(R.id.searchEditText);
        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView);
        noResultsText = findViewById(R.id.noResultsText);

        db = AppDatabase.getDatabase(getApplicationContext());

        setupBottomNavigation();
        setupSearchResultsRecyclerView();
        setupSearchEditText();

        // Set initial selected item
        bottomNav.setSelectedItemId(R.id.navigation_search);
    }

    private void setupBottomNavigation() {
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return true;
            } else if (itemId == R.id.navigation_search) {
                // Already in SearchActivity, do nothing
                return true;
            } else if (itemId == R.id.navigation_favorites) {
                startActivity(new Intent(this, FavoritesActivity.class));
                finish();
                return true;
            }
            return false;
        });
    }

    private void setupSearchResultsRecyclerView() {
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(this, searchResults, new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(Recipe recipe) {
                // Handle recipe click (e.g., open RecipeDetailActivity)
                Intent intent = new Intent(SearchActivity.this, RecipeDetailActivity.class);
                intent.putExtra("recipe_id", recipe.getId());
                startActivity(intent);
            }
        });
        searchResultsRecyclerView.setAdapter(recipeAdapter);
    }

    private void setupSearchEditText() {
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Trigger search when text changes
                searchRecipes(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }

    private void searchRecipes(String query) {
        new SearchRecipesAsyncTask(db, recipeAdapter, searchResults, noResultsText).execute(query);
    }

    private static class SearchRecipesAsyncTask extends AsyncTask<String, Void, List<Recipe>> {
        private AppDatabase db;
        private RecipeAdapter adapter;
        private List<Recipe> currentList;
        private TextView noResultsTextView;

        SearchRecipesAsyncTask(AppDatabase db, RecipeAdapter adapter, List<Recipe> currentList, TextView noResultsTextView) {
            this.db = db;
            this.adapter = adapter;
            this.currentList = currentList;
            this.noResultsTextView = noResultsTextView;
        }

        @Override
        protected List<Recipe> doInBackground(String... queries) {
            String query = queries[0];
            if (query.isEmpty()) {
                return new ArrayList<>(); // Return empty list if query is empty
            } else {
                // Search by title, ingredients, or instructions
                return db.recipeDao().searchRecipes("%%" + query + "%%");
            }
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            currentList.clear();
            currentList.addAll(recipes);
            adapter.notifyDataSetChanged();

            if (recipes.isEmpty()) {
                noResultsTextView.setVisibility(View.VISIBLE);
            } else {
                noResultsTextView.setVisibility(View.GONE);
            }
        }
    }
} 