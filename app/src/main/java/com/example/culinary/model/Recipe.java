package com.example.culinary.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String ingredients;
    public String instructions;
    public String category;
    public boolean isFavorite;

    public Recipe(String title, String ingredients, String instructions, String category) {
        this.title = title;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.category = category;
        this.isFavorite = false;
    }

    // Getters (and setters if needed)
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getCategory() {
        return category;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
} 