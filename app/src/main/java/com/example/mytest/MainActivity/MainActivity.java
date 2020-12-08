package com.example.mytest.MainActivity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mytest.ProductDetail.ProductDetailActivity;
import com.example.mytest.R;
import com.example.mytest.base.BaseActivity;
import com.example.mytest.object.ImageLoaderOptions;
import com.example.mytest.object.PayloadItem;
import com.google.gson.GsonBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class MainActivity extends BaseActivity<MainActivityPresenter> implements MainActivityContract.View {

    private RecyclerView ListingRv;
    private ItemAdapter adapter;
    private long backPressTimer = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListingRv = findViewById(R.id.ListingRv);
        adapter = new ItemAdapter();
        ListingRv.setAdapter(adapter);
        ListingRv.setLayoutManager(new LinearLayoutManager(this));
        setLoadingView(findViewById(R.id.progressBar));

        // call api get info
        requestGetPayloadListRx();
    }

    @Override
    public String getActivityTitle() {
        return getString(R.string.app_name);
    }

    @Override
    public boolean showBackButton() {
        return false;
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        createOptionsMenu(menu);
        return super.onCreateOptionsMenu(menu);
    }

    protected void createOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.refreshMenu:
                requestGetPayloadListRx();
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - backPressTimer < 3 * 1000) {
            finish();
        } else {
            backPressTimer = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.util_press_again_to_exit), Toast.LENGTH_SHORT).show();
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter {

        ArrayList<PayloadItem> list = new ArrayList<>();

        public ItemAdapter() {
        }

        public void setList(ArrayList<PayloadItem> list) {
            this.list = list;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payload_item_view, parent, false);
            return new PayloadItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            PayloadItemViewHolder viewholder = (PayloadItemViewHolder) holder;

            ImageLoader.getInstance().displayImage(list.get(position).getImageUrl(), viewholder.mainImageView, ImageLoaderOptions.forProductImage(getApplicationContext()));

            viewholder.overlayContainer.setVisibility(list.get(position).isClose() ? View.VISIBLE : View.GONE);
            viewholder.overlayText.setText(list.get(position).getCloseLabel());

            viewholder.itemDetailProductName.setText(list.get(position).getProductName());
            viewholder.itemDetailProductDesc.setText(list.get(position).getProductDesc());
            viewholder.itemDetailProductStar.setText(String.valueOf(list.get(position).getStar()));
            viewholder.itemDetailProductDistance.setText(list.get(position).getDistance());
            viewholder.itemDetailProductPromoDesc.setText(list.get(position).getPromoDesc());
            viewholder.itemDetailProductPromoDesc.setVisibility(list.get(position).getPromoDesc().equals("-") ? View.INVISIBLE : View.VISIBLE);
            viewholder.itemDetailProductBranchAround.setText(getString(R.string.item_detail_branch_around, String.valueOf(list.get(position).getOutletAround())));

            viewholder.mainContainer.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putString(ProductDetailActivity.EXTRA_DATA, new GsonBuilder().serializeSpecialFloatingPointValues().create().toJson(list.get(position)));
                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        private class PayloadItemViewHolder extends RecyclerView.ViewHolder {
            LinearLayout mainContainer;
            RelativeLayout overlayContainer;
            ImageView mainImageView;
            TextView overlayText,
                    itemDetailProductName,
                    itemDetailProductDesc,
                    itemDetailProductStar,
                    itemDetailProductDistance,
                    itemDetailProductPromoDesc,
                    itemDetailProductBranchAround;

            public PayloadItemViewHolder(@NonNull View itemView) {
                super(itemView);
                mainContainer = itemView.findViewById(R.id.mainContainer);
                overlayContainer = itemView.findViewById(R.id.overlayContainer);
                mainImageView = itemView.findViewById(R.id.mainImageView);
                overlayText = itemView.findViewById(R.id.overlayText);
                itemDetailProductName = itemView.findViewById(R.id.itemDetailProductName);
                itemDetailProductDesc = itemView.findViewById(R.id.itemDetailProductDesc);
                itemDetailProductStar = itemView.findViewById(R.id.itemDetailProductStar);
                itemDetailProductDistance = itemView.findViewById(R.id.itemDetailProductDistance);
                itemDetailProductPromoDesc = itemView.findViewById(R.id.itemDetailProductPromoDesc);
                itemDetailProductBranchAround = itemView.findViewById(R.id.itemDetailProductBranchAround);
            }
        }
    }

    @Override
    public MainActivityPresenter createPresenter() {
        return new MainActivityPresenter();
    }

    private void requestGetPayloadListRx(){
        if(!isLoading()){
            setLoading(true, true);
        }
        getmPresenter().getItemListApi(this);
    }

    @Override
    public void getItemListApiResponse(ArrayList<PayloadItem> listRecords) {
        setLoading(false, false);
        adapter.setList(listRecords);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getItemListApiThrowable(Throwable throwable) {
        setLoading(false, false);
        Log.d("Error API:::", ":::"+throwable.getMessage());
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }
}