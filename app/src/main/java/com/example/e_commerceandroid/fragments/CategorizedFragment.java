package com.example.e_commerceandroid.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceandroid.ProductActivity;
import com.example.e_commerceandroid.R;
import com.example.e_commerceandroid.adapters.ProductsAdapter;
import com.example.e_commerceandroid.models.PageModel;
import com.example.e_commerceandroid.models.ProductModel;
import com.example.e_commerceandroid.view_models.CategorizedViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategorizedFragment extends ParentFragment {
    private CategorizedViewModel categorizedViewModel;

    @BindView(R.id.categorized_rv)
    RecyclerView rvCategorizedList;

    @BindView(R.id.categorized_pb_loading)
    ProgressBar pbLoading;

    private ProductsAdapter categorizedAdapter;

    private List<ProductModel> categorizedList;
    private PageModel pageModel;

    private int page =1;
    private int firstVisibleItem, visibleItemCount, totalItemCount, previousTotal = 0;
    private int visibleThreshold = 6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorized, container, false);
        ButterKnife.bind(this, view);

        categorizedViewModel = new CategorizedViewModel(getContext(), "new-arrival");

        buildUI();
        setObservers();

        return view;
    }

    private void setObservers() {
        categorizedViewModel.isLoadingLiveData.observe(this, aBoolean -> pbLoading.setVisibility(aBoolean));
        categorizedViewModel.categorizedLiveData.observe(this, page -> {
            pageModel = page;
            if (page.getCurrentPage() == 1) {
                categorizedList.clear();
            }
            categorizedList.addAll(page.getData());
            categorizedAdapter.notifyDataSetChanged();
        });

        categorizedViewModel.productClickedLiveData.observe(this, product -> {
            Intent intent = new Intent(getContext(), ProductActivity.class);
            intent.putExtra("product_id", product.getId());
            startActivity(intent);
        });
    }

    private void buildUI() {
        categorizedList = new ArrayList<>();
        categorizedAdapter = new ProductsAdapter(categorizedViewModel, categorizedList, this);

        GridLayoutManager categorizedLayoutManager = new GridLayoutManager(getContext(), 2);
        categorizedLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvCategorizedList.setLayoutManager(categorizedLayoutManager);
        rvCategorizedList.setItemAnimator(new DefaultItemAnimator());
        rvCategorizedList.setAdapter(categorizedAdapter);


        rvCategorizedList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (!recyclerView.canScrollVertically(1)) {
                    page++;
                    categorizedViewModel.getCategorized(page);
                }

//                visibleItemCount = rvCategorizedList.getChildCount();
//                totalItemCount = categorizedLayoutManager.getItemCount();
//                firstVisibleItem = categorizedLayoutManager.findFirstVisibleItemPosition();
//
//                if (totalItemCount > previousTotal)
//                    previousTotal = totalItemCount;
//
//                if ((totalItemCount - visibleItemCount)
//                        <= (firstVisibleItem + visibleThreshold)) {
//                    page++;
//                    categorizedViewModel.getCategorized(page);
//                }
            }
        });
    }
}
