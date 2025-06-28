package com.example.culinary;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.culinary.fragments.AllRecipesFragment;

public class RecipePagerAdapter extends FragmentStateAdapter {
    private static final int NUM_PAGES = 3;

    public RecipePagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new AllRecipesFragment();
            case 1:
                // TODO: Return Popular Recipes Fragment
                return new Fragment(); // Placeholder for Popular
            case 2:
                // TODO: Return New Recipes Fragment
                return new Fragment(); // Placeholder for New
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
} 