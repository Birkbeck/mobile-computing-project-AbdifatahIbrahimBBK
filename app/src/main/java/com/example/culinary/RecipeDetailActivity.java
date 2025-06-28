package com.example.culinary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.example.culinary.database.AppDatabase;
import com.example.culinary.model.Recipe;

import java.util.Arrays;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {

    private int recipeId;
    private AppDatabase db;
    private Recipe currentRecipe;

    private MaterialToolbar toolbar;
    private ImageView recipeImage;
    private TextView recipeNameTextView;
    private TextView cookingTimeTextView;
    private TextView difficultyLevelTextView;
    private RecyclerView ingredientsRecyclerView;
    private TextView instructionsTextView;
    private ImageView favoriteFab;

    private IngredientAdapter ingredientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Initialize views
        toolbar = findViewById(R.id.toolbar);
        recipeImage = findViewById(R.id.recipeImage);
        recipeNameTextView = findViewById(R.id.recipeName);
        cookingTimeTextView = findViewById(R.id.cookingTime);
        difficultyLevelTextView = findViewById(R.id.difficultyLevel);
        ingredientsRecyclerView = findViewById(R.id.ingredientsRecyclerView);
        instructionsTextView = findViewById(R.id.instructions);
        favoriteFab = findViewById(R.id.favoriteFab);

        // Set up toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(""); // Set title programmatically later
        }

        // Initialize database
        db = AppDatabase.getDatabase(getApplicationContext());

        // Get recipe ID from intent
        recipeId = getIntent().getIntExtra("recipe_id", -1);

        if (recipeId != -1) {
            loadRecipeDetails(recipeId);
        } else {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Setup ingredients RecyclerView
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingredientAdapter = new IngredientAdapter(Arrays.asList()); // Empty list initially
        ingredientsRecyclerView.setAdapter(ingredientAdapter);
    }

    private void loadRecipeDetails(int id) {
        new GetRecipeAsyncTask(db, this).execute(id);
    }

    private void displayRecipe(Recipe recipe) {
        if (recipe != null) {
            currentRecipe = recipe;
            getSupportActionBar().setTitle(recipe.getTitle());
            
            // Update favorite FAB state
            favoriteFab.setImageResource(
                recipe.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border
            );
            
            // Set up favorite FAB click listener
            favoriteFab.setOnClickListener(v -> {
                boolean newFavoriteState = !recipe.isFavorite();
                recipe.setFavorite(newFavoriteState);
                favoriteFab.setImageResource(
                    newFavoriteState ? R.drawable.ic_favorite : R.drawable.ic_favorite_border
                );
                new UpdateFavoriteAsyncTask(db).execute(recipe.getId(), newFavoriteState);
            });

            recipeNameTextView.setText(recipe.getTitle());
            // You might want to load a dynamic image here
            // recipeImage.setImageResource(R.drawable.your_recipe_image);
            // For now, setting a placeholder or leaving as is
            recipeImage.setImageResource(R.drawable.placeholder_recipe); // Assuming you have this drawable

            // Split ingredients string into a list
            List<String> ingredientsList = Arrays.asList(recipe.getIngredients().split(","));
            ingredientAdapter.setIngredients(ingredientsList);

            instructionsTextView.setText(recipe.getInstructions());

            // Update cooking time and difficulty level if you add them to Recipe entity
            // For now, using placeholders as they are not in Recipe entity yet
            cookingTimeTextView.setText("N/A");
            difficultyLevelTextView.setText(recipe.getCategory()); // Display category here for now

        } else {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish(); // Go back to previous activity
            return true;
        } else if (itemId == R.id.action_edit_recipe) {
            editRecipe();
            return true;
        } else if (itemId == R.id.action_delete_recipe) {
            confirmDeleteRecipe();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editRecipe() {
        if (currentRecipe != null) {
            Intent intent = new Intent(this, AddRecipeActivity.class);
            intent.putExtra("recipe_id", currentRecipe.getId());
            // Pass existing recipe data to AddRecipeActivity for pre-filling
            intent.putExtra("recipe_title", currentRecipe.getTitle());
            intent.putExtra("recipe_ingredients", currentRecipe.getIngredients());
            intent.putExtra("recipe_instructions", currentRecipe.getInstructions());
            intent.putExtra("recipe_category", currentRecipe.getCategory());
            startActivity(intent);
        }
    }

    private void confirmDeleteRecipe() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Recipe")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    new DeleteRecipeAsyncTask(db, RecipeDetailActivity.this).execute(currentRecipe);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // AsyncTask to get a single recipe
    private static class GetRecipeAsyncTask extends AsyncTask<Integer, Void, Recipe> {
        private AppDatabase db;
        private RecipeDetailActivity activity;

        GetRecipeAsyncTask(AppDatabase db, RecipeDetailActivity activity) {
            this.db = db;
            this.activity = activity;
        }

        @Override
        protected Recipe doInBackground(Integer... ids) {
            return db.recipeDao().getRecipeById(ids[0]);
        }

        @Override
        protected void onPostExecute(Recipe recipe) {
            super.onPostExecute(recipe);
            if (activity != null) {
                activity.displayRecipe(recipe);
            }
        }
    }

    // AsyncTask to delete a recipe
    private static class DeleteRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private AppDatabase db;
        private RecipeDetailActivity activity;

        DeleteRecipeAsyncTask(AppDatabase db, RecipeDetailActivity activity) {
            this.db = db;
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            db.recipeDao().delete(recipes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (activity != null) {
                Toast.makeText(activity, "Recipe deleted!", Toast.LENGTH_SHORT).show();
                activity.finish(); // Go back after deletion
            }
        }
    }

    // AsyncTask to update favorite status
    private static class UpdateFavoriteAsyncTask extends AsyncTask<Object, Void, Void> {
        private AppDatabase db;

        UpdateFavoriteAsyncTask(AppDatabase db) {
            this.db = db;
        }

        @Override
        protected Void doInBackground(Object... params) {
            int recipeId = (int) params[0];
            boolean isFavorite = (boolean) params[1];
            db.recipeDao().updateFavoriteStatus(recipeId, isFavorite);
            return null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload recipe details in case it was edited in AddRecipeActivity
        if (recipeId != -1) {
            loadRecipeDetails(recipeId);
        }
    }
} 