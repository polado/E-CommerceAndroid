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
import com.example.e_commerceandroid.models.CartModel;
import com.example.e_commerceandroid.view_models.ParentViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartsAdapter extends RecyclerView.Adapter<CartsAdapter.ProductsViewHolder> {

    private ParentViewModel viewModel;
    private List<CartModel> cartsList;
    private ParentFragment fragment;

    public CartsAdapter(ParentViewModel viewModel, List<CartModel> cartsList, ParentFragment fragment) {
        this.viewModel = viewModel;
        this.cartsList = cartsList;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_item_widget, parent, false);
        return new ProductsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
        CartModel cartModel = cartsList.get(position);

        holder.setData(cartModel);
    }

    @Override
    public int getItemCount() {
        return cartsList.size();
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

        void setData(CartModel cartModel) {
            tvName.setText(cartModel.getProduct().getNameEn());
            tvPrice.setText(cartModel.getProduct().getPrice() + " EGP");
            Log.d("Adapter", "viewmodel " + viewModel.toString());
            Log.d("Adapter", "fragment " + fragment.toString());


            Picasso.get()
                    .load("https://e-commerce-dev.intcore.net/"
                            + cartModel.getProduct().getDefaultImage())
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.ic_loading)
                    .into(ivImage);
        }
    }
}
