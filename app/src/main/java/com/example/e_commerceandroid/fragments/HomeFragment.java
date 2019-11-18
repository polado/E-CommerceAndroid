package com.example.e_commerceandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceandroid.ProductActivity;
import com.example.e_commerceandroid.R;
import com.example.e_commerceandroid.adapters.CategoriesAdapter;
import com.example.e_commerceandroid.adapters.ProductsAdapter;
import com.example.e_commerceandroid.models.CategoryModel;
import com.example.e_commerceandroid.models.ProductModel;
import com.example.e_commerceandroid.view_models.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeFragment extends ParentFragment {

    private HomeViewModel homeViewModel;

    @BindView(R.id.see_more_new_arrival_btn)
    Button btnSeeMore;

    @BindView(R.id.home_data_ll)
    LinearLayout llHomeData;

    @BindView(R.id.home_new_arrival_rv)
    RecyclerView rvNewArrivalList;

    @BindView(R.id.home_best_seller_rv)
    RecyclerView rvBestSellerList;

    @BindView(R.id.home_top_categories_rv)
    RecyclerView rvTopCategoriesList;

    @BindView(R.id.home_pb_loading)
    ProgressBar pbLoading;

    private ProductsAdapter newArrivalAdapter;
    private ProductsAdapter bestSellerAdapter;
    private CategoriesAdapter topCategoriesAdapter;

    private List<ProductModel> newArrivalList;
    private List<ProductModel> bestSellerList;
    private List<CategoryModel> topCategoriesList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        homeViewModel = new HomeViewModel(getContext());
//        homeViewModel.getHome();

        buildUI(view);
        setObservers();

        return view;
    }

    private void setObservers() {
        homeViewModel.isLoadingLiveData.observe(this, aBoolean -> {
            pbLoading.setVisibility(aBoolean);
            if (aBoolean == View.INVISIBLE)
                llHomeData.setVisibility(View.VISIBLE);
            else
                llHomeData.setVisibility(View.INVISIBLE);
        });
        homeViewModel.newArrivalLiveData.observe(this, products -> {
            newArrivalList.addAll(products);
            newArrivalAdapter.notifyDataSetChanged();
        });
        homeViewModel.bestSellerLiveData.observe(this, products -> {
            bestSellerList.addAll(products);
            bestSellerAdapter.notifyDataSetChanged();
        });
        homeViewModel.topCategoriesLiveData.observe(this, categories -> {
            topCategoriesList.addAll(categories);
            topCategoriesAdapter.notifyDataSetChanged();
        });
        homeViewModel.productClickedLiveData.observe(this, product -> {
            Intent intent = new Intent(getContext(), ProductActivity.class);
            intent.putExtra("product_id", product.getId());
            startActivity(intent);
        });
    }

    private void buildUI(View view) {
        btnSeeMore.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.categorizedFragment);
        });

        newArrivalList = new ArrayList<>();
        newArrivalAdapter = new ProductsAdapter(homeViewModel, newArrivalList, this);

        bestSellerList = new ArrayList<>();
        bestSellerAdapter = new ProductsAdapter(homeViewModel, bestSellerList, this);

        topCategoriesList = new ArrayList<>();
        topCategoriesAdapter = new CategoriesAdapter(topCategoriesList);

        LinearLayoutManager newArrivalLayoutManager = new LinearLayoutManager(getContext());
        newArrivalLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        LinearLayoutManager bestSellerLayoutManager = new LinearLayoutManager(getContext());
        bestSellerLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        LinearLayoutManager topCategoriesLayoutManager = new LinearLayoutManager(getContext());
        topCategoriesLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        rvNewArrivalList.setLayoutManager(newArrivalLayoutManager);
        rvNewArrivalList.setItemAnimator(new DefaultItemAnimator());
        rvNewArrivalList.setAdapter(newArrivalAdapter);

        rvBestSellerList.setLayoutManager(bestSellerLayoutManager);
        rvBestSellerList.setItemAnimator(new DefaultItemAnimator());
        rvBestSellerList.setAdapter(bestSellerAdapter);

        rvTopCategoriesList.setLayoutManager(topCategoriesLayoutManager);
        rvTopCategoriesList.setItemAnimator(new DefaultItemAnimator());
        rvTopCategoriesList.setAdapter(topCategoriesAdapter);
    }
}
