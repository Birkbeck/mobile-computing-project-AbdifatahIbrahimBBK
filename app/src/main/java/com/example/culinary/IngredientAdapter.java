package com.example.culinary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<String> ingredientList;

    public IngredientAdapter(List<String> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        String ingredient = ingredientList.get(position);
        holder.ingredientTextView.setText("• " + ingredient);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredientList = ingredients;
        notifyDataSetChanged();
    }

    static class IngredientViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientTextView;

        IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientTextView = itemView.findViewById(android.R.id.text1);
        }
    }
} 