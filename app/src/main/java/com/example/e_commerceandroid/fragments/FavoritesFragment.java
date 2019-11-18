package com.example.e_commerceandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceandroid.ProductActivity;
import com.example.e_commerceandroid.R;
import com.example.e_commerceandroid.adapters.ProductsAdapter;
import com.example.e_commerceandroid.models.ProductModel;
import com.example.e_commerceandroid.view_models.FavoritesViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FavoritesFragment extends ParentFragment {
    private FavoritesViewModel favoritesViewModel;

    @BindView(R.id.favorites_rv)
    RecyclerView rvFavoritesList;

    @BindView(R.id.favorites_pb_loading)
    ProgressBar pbLoading;

    private ProductsAdapter favoritesAdapter;

    private List<ProductModel> favoritesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);

        favoritesViewModel = new FavoritesViewModel(getContext());

        buildUI();
        setObservers();

        return view;
    }

    private void setObservers() {
        favoritesViewModel.isLoadingLiveData.observe(this, aBoolean -> pbLoading.setVisibility(aBoolean));
        favoritesViewModel.favoritesLiveData.observe(this, products -> {
            favoritesList.clear();
            favoritesList.addAll(products);
            favoritesAdapter.notifyDataSetChanged();
        });

        favoritesViewModel.productClickedLiveData.observe(this, product -> {
            Intent intent = new Intent(getContext(), ProductActivity.class);
            intent.putExtra("product_id", product.getId());
            startActivity(intent);
        });
    }

    private void buildUI() {
        favoritesList = new ArrayList<>();
        favoritesAdapter = new ProductsAdapter(favoritesViewModel, favoritesList, this);

        GridLayoutManager favoritesLayoutManager = new GridLayoutManager(getContext(), 2);
        favoritesLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvFavoritesList.setLayoutManager(favoritesLayoutManager);
        rvFavoritesList.setItemAnimator(new DefaultItemAnimator());
        rvFavoritesList.setAdapter(favoritesAdapter);
    }
}
