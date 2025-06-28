package com.example.culinary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.example.culinary.database.AppDatabase;
import com.example.culinary.model.Recipe;

public class AddRecipeActivity extends AppCompatActivity {

    private TextInputEditText titleEditText, ingredientsEditText, instructionsEditText;
    private Spinner categorySpinner;
    private Button saveRecipeButton;
    private AppDatabase db;
    private int recipeId = -1; // -1 indicates a new recipe, otherwise it's an existing one
    private Recipe existingRecipe; // To hold the recipe if we are editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        // Initialize views
        titleEditText = findViewById(R.id.titleEditText);
        ingredientsEditText = findViewById(R.id.ingredientsEditText);
        instructionsEditText = findViewById(R.id.instructionsEditText);
        categorySpinner = findViewById(R.id.categorySpinner);
        saveRecipeButton = findViewById(R.id.saveRecipeButton);

        // Set up category spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.recipe_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Initialize database
        db = AppDatabase.getDatabase(getApplicationContext());

        // Check if we are editing an existing recipe
        recipeId = getIntent().getIntExtra("recipe_id", -1);
        if (recipeId != -1) {
            // Load existing recipe details
            new GetRecipeAsyncTask(db, this).execute(recipeId);
            saveRecipeButton.setText("Update Recipe");
        } else {
            saveRecipeButton.setText("Save Recipe");
        }

        // Set up save button click listener
        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrUpdateRecipe();
            }
        });
    }

    private void displayRecipe(Recipe recipe) {
        if (recipe != null) {
            existingRecipe = recipe;
            titleEditText.setText(recipe.getTitle());
            ingredientsEditText.setText(recipe.getIngredients());
            instructionsEditText.setText(recipe.getInstructions());

            // Set spinner selection
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) categorySpinner.getAdapter();
            if (adapter != null) {
                int spinnerPosition = adapter.getPosition(recipe.getCategory());
                categorySpinner.setSelection(spinnerPosition);
            }
        }
    }

    private void saveOrUpdateRecipe() {
        String title = titleEditText.getText().toString().trim();
        String ingredients = ingredientsEditText.getText().toString().trim();
        String instructions = instructionsEditText.getText().toString().trim();
        String category = categorySpinner.getSelectedItem().toString();

        if (title.isEmpty() || ingredients.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (existingRecipe != null) {
            // Update existing recipe
            existingRecipe.setTitle(title);
            existingRecipe.setIngredients(ingredients);
            existingRecipe.setInstructions(instructions);
            existingRecipe.setCategory(category);
            new UpdateRecipeAsyncTask(db, this).execute(existingRecipe);
        } else {
            // Save new recipe
            final Recipe newRecipe = new Recipe(title, ingredients, instructions, category);
            new InsertRecipeAsyncTask(db, this).execute(newRecipe);
        }
    }

    // AsyncTask to get a single recipe for editing
    private static class GetRecipeAsyncTask extends AsyncTask<Integer, Void, Recipe> {
        private AppDatabase db;
        private AddRecipeActivity activity;

        GetRecipeAsyncTask(AppDatabase db, AddRecipeActivity activity) {
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

    // AsyncTask to insert a new recipe
    private static class InsertRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private AppDatabase db;
        private AddRecipeActivity activity;

        InsertRecipeAsyncTask(AppDatabase db, AddRecipeActivity activity) {
            this.db = db;
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            db.recipeDao().insert(recipes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (activity != null) {
                Toast.makeText(activity, "Recipe saved successfully!", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        }
    }

    // AsyncTask to update an existing recipe
    private static class UpdateRecipeAsyncTask extends AsyncTask<Recipe, Void, Void> {
        private AppDatabase db;
        private AddRecipeActivity activity;

        UpdateRecipeAsyncTask(AppDatabase db, AddRecipeActivity activity) {
            this.db = db;
            this.activity = activity;
        }

        @Override
        protected Void doInBackground(Recipe... recipes) {
            db.recipeDao().update(recipes[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (activity != null) {
                Toast.makeText(activity, "Recipe updated successfully!", Toast.LENGTH_SHORT).show();
                activity.finish();
            }
        }
    }
} 