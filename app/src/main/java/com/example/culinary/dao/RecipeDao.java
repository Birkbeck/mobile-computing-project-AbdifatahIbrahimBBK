package com.example.culinary.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.culinary.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Insert
    void insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE id = :id")
    Recipe getRecipeById(int id);

    @Query("SELECT * FROM recipes WHERE title LIKE :query OR ingredients LIKE :query OR instructions LIKE :query")
    List<Recipe> searchRecipes(String query);

    @Query("SELECT * FROM recipes WHERE isFavorite = 1")
    List<Recipe> getFavoriteRecipes();

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE id = :recipeId")
    void updateFavoriteStatus(int recipeId, boolean isFavorite);
} 