package com.example.e_commerceandroid.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commerceandroid.R;
import com.example.e_commerceandroid.adapters.CartsAdapter;
import com.example.e_commerceandroid.models.CartModel;
import com.example.e_commerceandroid.models.CartResponse;
import com.example.e_commerceandroid.view_models.CartViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CartFragment extends ParentFragment {
    private CartViewModel cartViewModel;

    @BindView(R.id.carts_items_count_tv)
    TextView tvItemsCount;
    @BindView(R.id.carts_items_price_tv)
    TextView tvItemsPrice;
    @BindView(R.id.carts_shipping_price_tv)
    TextView tvShippingPrice;
    @BindView(R.id.carts_total_cost_tv)
    TextView tvTotalCost;

    @BindView(R.id.carts_data_ll)
    LinearLayout llCartsData;

    @BindView(R.id.carts_rv)
    RecyclerView rvCartsList;

    @BindView(R.id.carts_pb_loading)
    ProgressBar pbLoading;

    private CartsAdapter cartsAdapter;

    private CartResponse cartResponse;
    private List<CartModel> cartModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ButterKnife.bind(this, view);

        cartViewModel = new CartViewModel(getContext());

        buildUI();
        setObservers();

        return view;
    }

    private void setObservers() {
        cartViewModel.isLoadingLiveData.observe(this, aBoolean -> {
            pbLoading.setVisibility(aBoolean);
            if (aBoolean == View.INVISIBLE)
                llCartsData.setVisibility(View.VISIBLE);
            else
                llCartsData.setVisibility(View.INVISIBLE);
        });
        cartViewModel.cartItemsLiveData.observe(this, cartResponse -> {
            Log.d("CartFragment", "response " + cartResponse.getCartModels().size());
            this.cartResponse = cartResponse;
            cartModelList.addAll(cartResponse.getCartModels());
            cartsAdapter.notifyDataSetChanged();

            tvItemsCount.setText(cartResponse.getTotalItems().toString());
            tvItemsPrice.setText(cartResponse.getTotalPrice().toString() + " EGP");
            tvShippingPrice.setText(cartResponse.getShipping());
            tvTotalCost.setText((cartResponse.getTotalPrice() + cartResponse.getShipping()) + " EGP");
        });
    }

    private void buildUI() {
        cartResponse = new CartResponse();
        cartResponse.setCartModels(new ArrayList<>());

        cartModelList = new ArrayList<>();

        cartsAdapter = new CartsAdapter(cartViewModel, cartModelList, this);

        LinearLayoutManager cartsLayoutManager = new LinearLayoutManager(getContext());
        cartsLayoutManager.setOrientation(RecyclerView.VERTICAL);

        rvCartsList.setLayoutManager(cartsLayoutManager);
        rvCartsList.setItemAnimator(new DefaultItemAnimator());
        rvCartsList.setAdapter(cartsAdapter);
    }
}
