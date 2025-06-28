package com.example.culinary.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.culinary.R;
import com.example.culinary.RecipeAdapter;
import com.example.culinary.RecipeDetailActivity;
import com.example.culinary.database.AppDatabase;
import com.example.culinary.model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class AllRecipesFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private AppDatabase db;
    private List<Recipe> recipeList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_recipes, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewAllRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        db = AppDatabase.getDatabase(getContext());

        recipeAdapter = new RecipeAdapter(getContext(), recipeList, new RecipeAdapter.OnRecipeClickListener() {
            @Override
            public void onRecipeClick(Recipe recipe) {
                // Handle recipe click (e.g., open RecipeDetailActivity for editing/viewing)
                Intent intent = new Intent(getContext(), RecipeDetailActivity.class);
                intent.putExtra("recipe_id", recipe.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(recipeAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRecipes();
    }

    private void loadRecipes() {
        new LoadRecipesAsyncTask(db, recipeAdapter, recipeList).execute();
    }

    private static class LoadRecipesAsyncTask extends AsyncTask<Void, Void, List<Recipe>> {
        private AppDatabase db;
        private RecipeAdapter adapter;
        private List<Recipe> currentList;

        LoadRecipesAsyncTask(AppDatabase db, RecipeAdapter adapter, List<Recipe> currentList) {
            this.db = db;
            this.adapter = adapter;
            this.currentList = currentList;
        }

        @Override
        protected List<Recipe> doInBackground(Void... voids) {
            return db.recipeDao().getAllRecipes();
        }

        @Override
        protected void onPostExecute(List<Recipe> recipes) {
            super.onPostExecute(recipes);
            currentList.clear();
            currentList.addAll(recipes);
            adapter.notifyDataSetChanged();
        }
    }
} 