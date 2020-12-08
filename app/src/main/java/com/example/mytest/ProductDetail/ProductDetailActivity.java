package com.example.mytest.ProductDetail;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytest.R;
import com.example.mytest.base.BaseActivity;
import com.example.mytest.object.ImageLoaderOptions;
import com.example.mytest.object.PayloadItem;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProductDetailActivity extends BaseActivity {
    public static final String EXTRA_DATA = "EXTRA_DATA";

    public PayloadItem payloadItem = null;

    Button submitButton;
    ImageView productDetailImageView;
    ScrollView mainScrollContainer;
    TextView productDetailProductName,
            productDetailProductDesc,
            productDetailProductStar,
            productDetailProductDistance,
            productDetailProductPromoDesc,
            productDetailProductBranchAround;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        payloadItem = getExtraPayloadItem();

        submitButton = findViewById(R.id.submitButton);
        productDetailImageView = findViewById(R.id.productDetailImageView);
        mainScrollContainer = findViewById(R.id.mainScrollContainer);
        productDetailProductName = findViewById(R.id.productDetailProductName);
        productDetailProductDesc = findViewById(R.id.productDetailProductDesc);
        productDetailProductStar = findViewById(R.id.productDetailProductStar);
        productDetailProductDistance = findViewById(R.id.productDetailProductDistance);
        productDetailProductPromoDesc = findViewById(R.id.productDetailProductPromoDesc);
        productDetailProductBranchAround = findViewById(R.id.productDetailProductBranchAround);
        productDetailProductBranchAround = findViewById(R.id.productDetailProductBranchAround);


        ImageLoader.getInstance().displayImage(payloadItem.getImageUrl(), productDetailImageView, ImageLoaderOptions.forProductImage(getApplicationContext()));

        productDetailProductName.setText(payloadItem.getProductName());
        productDetailProductDesc.setText(payloadItem.getProductDesc());
        productDetailProductStar.setText(String.valueOf(payloadItem.getStar()));
        productDetailProductDistance.setText(payloadItem.getDistance());
        productDetailProductPromoDesc.setText(payloadItem.getPromoDesc());
        productDetailProductPromoDesc.setVisibility(payloadItem.getPromoDesc().equals("-") ? View.GONE : View.VISIBLE);
        productDetailProductBranchAround.setText(getString(R.string.item_detail_branch_around, String.valueOf(payloadItem.getOutletAround())));

        ViewGroup.MarginLayoutParams layout = (ViewGroup.MarginLayoutParams) mainScrollContainer.getLayoutParams();
        if(payloadItem.isClose()){
            submitButton.setVisibility(View.GONE);
            layout.setMargins(0,0,0,0);
        } else {
            submitButton.setVisibility(View.VISIBLE);
            layout.setMargins(0,0,0,dpToPx(40));
        }

        submitButton.setOnClickListener(v -> Toast.makeText(this, getString(R.string.product_detail_move_to_order) , Toast.LENGTH_SHORT).show());
    }

    @Override
    public String getActivityTitle() {
        return getString(R.string.product_detail_title);
    }

    public PayloadItem getExtraPayloadItem(){
        try{
            return new GsonBuilder().serializeSpecialFloatingPointValues().create().fromJson(getIntent().getStringExtra(EXTRA_DATA), PayloadItem.class);
        } catch(Exception e) {
            return null;
        }
    }

    public int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
