package com.example.culinary;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.culinary.database.AppDatabase;
import com.example.culinary.model.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipeList;
    private OnRecipeClickListener listener;
    private AppDatabase db;

    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    public RecipeAdapter(Context context, List<Recipe> recipeList, OnRecipeClickListener listener) {
        this.context = context;
        this.recipeList = recipeList;
        this.listener = listener;
        this.db = AppDatabase.getDatabase(context);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getTitle());
        holder.recipeCategory.setText(recipe.getCategory());
        holder.cookingTime.setText("30 mins"); // This should come from the recipe model
        holder.difficulty.setText("Medium"); // This should come from the recipe model
        holder.recipeRating.setRating(4.0f); // This should come from the recipe model

        // Set favorite button state
        holder.favoriteButton.setImageResource(
            recipe.isFavorite() ? R.drawable.ic_favorite : R.drawable.ic_favorite_border
        );

        // Set click listeners
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRecipeClick(recipe);
            }
        });

        holder.favoriteButton.setOnClickListener(v -> {
            boolean newFavoriteState = !recipe.isFavorite();
            recipe.setFavorite(newFavoriteState);
            holder.favoriteButton.setImageResource(
                newFavoriteState ? R.drawable.ic_favorite : R.drawable.ic_favorite_border
            );
            new UpdateFavoriteAsyncTask(db).execute(recipe.getId(), newFavoriteState);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        ImageView recipeImage;
        TextView recipeName;
        TextView recipeCategory;
        TextView cookingTime;
        TextView difficulty;
        RatingBar recipeRating;
        ImageButton favoriteButton;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeCategory = itemView.findViewById(R.id.recipeCategory);
            cookingTime = itemView.findViewById(R.id.cookingTime);
            difficulty = itemView.findViewById(R.id.difficulty);
            recipeRating = itemView.findViewById(R.id.recipeRating);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }

    // Method to update the list of recipes in the adapter
    public void setRecipes(List<Recipe> newRecipes) {
        this.recipeList = newRecipes;
        notifyDataSetChanged();
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
} 