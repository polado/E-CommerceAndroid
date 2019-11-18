package com.example.e_commerceandroid.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceandroid.R;
import com.example.e_commerceandroid.models.BrandModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BrandsAdapter extends RecyclerView.Adapter<BrandsAdapter.BrandsViewHolder> {

    private List<BrandModel> brandsList;

    public BrandsAdapter(List<BrandModel> brandsList) {
        this.brandsList = brandsList;
    }

    @NonNull
    @Override
    public BrandsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.brand_widget, parent, false);
        return new BrandsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandsViewHolder holder, int position) {
        BrandModel category = brandsList.get(position);

        holder.setData(category);
    }

    @Override
    public int getItemCount() {
        return brandsList.size();
    }

    class BrandsViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;
        TextView tvName;

        BrandsViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.brand_widget_iv);
//            tvName = itemView.findViewById(R.id.brand_widget_tv);
        }

        void setData(BrandModel brand) {
//            tvName.setText(brand.getNameEn());

            Picasso.get()
                    .load("https://e-commerce-dev.intcore.net/"
                            + brand.getImage())
//                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .error(R.drawable.ic_error)
                    .placeholder(R.drawable.ic_loading)
                    .into(ivImage);
        }
    }
}
