package com.example.e_commerceandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_commerceandroid.models.ProductModel;
import com.example.e_commerceandroid.view_models.ProductViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductActivity extends AppCompatActivity {

    ProductViewModel productViewModel;

    @BindView(R.id.product_data_ll)
    LinearLayout llProductData;

    @BindView(R.id.product_activity_iv)
    ImageView ivProductImage;

    @BindView(R.id.product_activity_name_tv)
    TextView tvProductTitle;

    @BindView(R.id.product_activity_price_tv)
    TextView tvProductPrice;

    @BindView(R.id.product_activity_description_tv)
    TextView tvProductDescription;

    @BindView(R.id.product_activity_btn)
    Button btnAddToCart;

    @BindView(R.id.product_activity_pb_loading)
    ProgressBar pbLoading;

    private ProductModel productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        int productID = intent.getIntExtra("product_id", -1);

        productViewModel = new ProductViewModel(productID);

        setObservers();

    }

    private void setObservers() {
        productViewModel.isLoadingLiveData.observe(this, aBoolean -> {
            pbLoading.setVisibility(aBoolean);
            if (aBoolean == View.INVISIBLE)
                llProductData.setVisibility(View.VISIBLE);
            else
                llProductData.setVisibility(View.INVISIBLE);
        });

        productViewModel.productModelMutableLiveData.observe(this, productModel -> {
            this.productModel = productModel;
            buildUI();
        });
    }

    private void buildUI() {
        Picasso.get()
                .load("https://e-commerce-dev.intcore.net/"
                        + productModel.getDefaultImage())
//                    .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.ic_error)
                .placeholder(R.drawable.ic_loading)
                .into(ivProductImage);

        tvProductTitle.setText(productModel.getNameEn());
        tvProductPrice.setText(productModel.getPrice() + " EGP");
        tvProductDescription.setText(productModel.getDescriptionEn());

        btnAddToCart.setOnClickListener(v -> {
            productViewModel.addToCart(productModel);
        });
    }
}
