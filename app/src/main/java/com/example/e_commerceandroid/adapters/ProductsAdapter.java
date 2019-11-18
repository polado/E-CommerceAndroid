package com.example.e_commerceandroid.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceandroid.R;
import com.example.e_commerceandroid.fragments.ParentFragment;
import com.example.e_commerceandroid.models.ProductModel;
import com.example.e_commerceandroid.view_models.IsFavorite;
import com.example.e_commerceandroid.view_models.ParentViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    private ParentViewModel viewModel;
    private List<ProductModel> productsList;
    private ParentFragment fragment;

    public ProductsAdapter(ParentViewModel viewModel, List<ProductModel> productsList, ParentFragment fragment) {
        this.viewModel = viewModel;
        this.productsList = productsList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_widget, parent, false);
        return new ProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        ProductModel product = productsList.get(position);

        holder.setData(product);
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    class ProductsViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName, tvPrice;
        ImageButton ibIsFavorite;

        ProductsViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.cart_widget_iv);
            tvName = itemView.findViewById(R.id.cart_widget_name_tv);
            tvPrice = itemView.findViewById(R.id.cart_widget_price_tv);
            ibIsFavorite = itemView.findViewById(R.id.cart_widget_fb);
        }

        void setData(ProductModel product) {
            tvName.setText(product.getNameEn());
            tvPrice.setText(product.getPrice() + " EGP");

            checkIsFavorite(product);

            this.itemView.setOnClickListener(v -> viewModel.onProductClick(product));

            Picasso.get()
                    .load("https://e-commerce-dev.intcore.net/"
                            + product.getDefaultImage())
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.ic_loading)
                    .into(ivImage);
        }

        void checkIsFavorite(ProductModel productModel) {
            Log.d("Adapter", " checkIsFavorite " + productModel.getId());

            for (ProductModel product : IsFavorite.favoriteList) {
                Log.d("Adapter", product.getId() + " checkIsFavorite " + productModel.getId());
                if (product.getId().equals(productModel.getId())) {
                    Log.d("Adapter", "checkIsFavorite " + product.getNameEn());
                    Log.d("Adapter", "checkIsFavorite " + product.getId());
                    productModel.setIsFav(true);
                    ibIsFavorite.setImageResource(R.drawable.ic_favorite_color);
                }
            }
            Log.d("Adapter", " checkIsFavorite DONE " + productModel.getIsFav());

            IsFavorite.favoritesLiveData.observe(fragment, product -> {
                if (productModel.getId().equals(product.getId())) {
                    Log.d("Adapter", product.getId() + " fragment " + productModel.getId());
                    productModel.setIsFav(product.getIsFav());
                }
                productModel.setIsFav(false);
                ibIsFavorite.setImageResource(R.drawable.ic_favorite_border);

                for (ProductModel p : IsFavorite.favoriteList) {
                    Log.d("Adapter", p.getId() + " checkIsFavorite " + productModel.getId());
                    if (p.getId().equals(productModel.getId())) {
                        Log.d("Adapter", "checkIsFavorite " + p.getNameEn());
                        Log.d("Adapter", "checkIsFavorite " + p.getId());
                        productModel.setIsFav(true);
                        ibIsFavorite.setImageResource(R.drawable.ic_favorite_color);
                    }
                }
            });
            ibIsFavorite.setOnClickListener(v -> IsFavorite.onToggleFavoriteClick(productModel));
        }
    }
}
