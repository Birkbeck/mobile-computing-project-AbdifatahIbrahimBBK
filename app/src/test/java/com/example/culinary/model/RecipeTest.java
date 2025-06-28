package com.example.culinary.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RecipeTest {

    private Recipe recipe;

    @Before
    public void setUp() {
        recipe = new Recipe("Test Title", "Test Ingredients", "Test Instructions", "Test Category");
    }

    @Test
    public void testRecipeConstructor() {
        assertEquals("Test Title", recipe.getTitle());
        assertEquals("Test Ingredients", recipe.getIngredients());
        assertEquals("Test Instructions", recipe.getInstructions());
        assertEquals("Test Category", recipe.getCategory());
        assertFalse(recipe.isFavorite());
    }

    @Test
    public void testSetFavorite_True() {
        recipe.setFavorite(true);
        assertTrue(recipe.isFavorite());
    }

    @Test
    public void testSetFavorite_False() {
        recipe.setFavorite(true); // first set to true
        assertTrue(recipe.isFavorite());
        recipe.setFavorite(false); // then set to false
        assertFalse(recipe.isFavorite());
    }

    @Test
    public void testGetters() {
        recipe.setId(1);
        assertEquals(1, recipe.getId());

        recipe.setTitle("New Title");
        assertEquals("New Title", recipe.getTitle());

        recipe.setIngredients("New Ingredients");
        assertEquals("New Ingredients", recipe.getIngredients());

        recipe.setInstructions("New Instructions");
        assertEquals("New Instructions", recipe.getInstructions());

        recipe.setCategory("New Category");
        assertEquals("New Category", recipe.getCategory());
    }
} 