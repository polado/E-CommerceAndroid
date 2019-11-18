package com.example.e_commerceandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceandroid.R;
import com.example.e_commerceandroid.models.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private List<CategoryModel> categoriesList;

    public CategoriesAdapter(List<CategoryModel> categoriesList) {
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_widget, parent, false);
        return new CategoriesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        CategoryModel category = categoriesList.get(position);

        holder.setData(category);
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName;

        CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.category_widget_iv);
            tvName = itemView.findViewById(R.id.category_widget_tv);
        }

        void setData(CategoryModel category) {
            tvName.setText(category.getNameEn());

            Picasso.get()
                    .load("https://e-commerce-dev.intcore.net/"
                            + category.getImage())
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.ic_loading)
                    .into(ivImage);
        }
    }
}
