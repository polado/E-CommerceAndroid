package com.example.e_commerceandroid.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceandroid.R;
import com.example.e_commerceandroid.adapters.BrandsAdapter;
import com.example.e_commerceandroid.adapters.CategoriesAdapter;
import com.example.e_commerceandroid.adapters.ProductsAdapter;
import com.example.e_commerceandroid.models.BrandModel;
import com.example.e_commerceandroid.models.CategoryModel;
import com.example.e_commerceandroid.models.ProductModel;
import com.example.e_commerceandroid.view_models.DealsViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DealsFragment extends ParentFragment {

    private DealsViewModel dealsViewModel;

    @BindView(R.id.deals_data_ll)
    LinearLayout llDealsData;

    @BindView(R.id.deals_hot_deals_rv)
    RecyclerView rvHotDealsList;

    @BindView(R.id.deals_top_brands_rv)
    RecyclerView rvTopBrandsList;

    @BindView(R.id.deals_top_categories_rv)
    RecyclerView rvTopCategoriesList;

    @BindView(R.id.deals_pb_loading)
    ProgressBar pbLoading;

    @BindView((R.id.deals_hot_deals_cv))
    CardView cvNoDataHotDeals;

    private ProductsAdapter hotDealsAdapter;
    private BrandsAdapter topBrandsAdapter;
    private CategoriesAdapter topCategoriesAdapter;

    private List<ProductModel> hotDealsList;
        private List<BrandModel> topBrandsList;
    private List<CategoryModel> topCategoriesList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_deals, container, false);
        ButterKnife.bind(this, view);

        dealsViewModel = new DealsViewModel(getContext());

        buildUI(view);
        setObservers();

        return view;
    }

    private void setObservers() {
        dealsViewModel.isLoadingLiveData.observe(this, aBoolean -> {
            pbLoading.setVisibility(aBoolean);
            if (aBoolean == View.INVISIBLE)
                llDealsData.setVisibility(View.VISIBLE);
            else
                llDealsData.setVisibility(View.INVISIBLE);
        });
        dealsViewModel.hotDealsLiveData.observe(this, products -> {
            if (products.size() > 0) {
                hotDealsList.addAll(products);
                hotDealsAdapter.notifyDataSetChanged();
                cvNoDataHotDeals.setVisibility(View.INVISIBLE);
            } else
                cvNoDataHotDeals.setVisibility(View.VISIBLE);
        });
        dealsViewModel.topBrandsLiveData.observe(this, brands -> {
            topBrandsList.addAll(brands);
            topBrandsAdapter.notifyDataSetChanged();
        });
        dealsViewModel.topCategoriesLiveData.observe(this, categories -> {
            topCategoriesList.addAll(categories);
            topCategoriesAdapter.notifyDataSetChanged();
        });
    }

    private void buildUI(View view) {
        hotDealsList = new ArrayList<>();
        hotDealsAdapter = new ProductsAdapter(dealsViewModel, hotDealsList, this);

        topBrandsList = new ArrayList<>();
        topBrandsAdapter = new BrandsAdapter(topBrandsList);

        topCategoriesList = new ArrayList<>();
        topCategoriesAdapter = new CategoriesAdapter(topCategoriesList);

        LinearLayoutManager hotDealsLayoutManager = new LinearLayoutManager(getContext());
        hotDealsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        LinearLayoutManager topBrandsLayoutManager = new LinearLayoutManager(getContext());
        topBrandsLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        LinearLayoutManager topCategoriesLayoutManager = new LinearLayoutManager(getContext());
        topCategoriesLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        rvHotDealsList.setLayoutManager(hotDealsLayoutManager);
        rvHotDealsList.setItemAnimator(new DefaultItemAnimator());
        rvHotDealsList.setAdapter(hotDealsAdapter);

        rvTopBrandsList.setLayoutManager(topBrandsLayoutManager);
        rvTopBrandsList.setItemAnimator(new DefaultItemAnimator());
        rvTopBrandsList.setAdapter(topBrandsAdapter);

        rvTopCategoriesList.setLayoutManager(topCategoriesLayoutManager);
        rvTopCategoriesList.setItemAnimator(new DefaultItemAnimator());
        rvTopCategoriesList.setAdapter(topCategoriesAdapter);
    }
}
