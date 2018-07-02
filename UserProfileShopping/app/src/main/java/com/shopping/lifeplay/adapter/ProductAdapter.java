package com.shopping.lifeplay.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.Product;

import java.util.List;

/**
 * Created by user on 12/20/2017.
 */

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> productList;

    //declare interface
    private ProductAdapter.Product_OnItemClicked onClick;

    private ProductAdapter.Product_OnClicked onClicknew;

    public interface Product_OnClicked {
        void Product_onClick(int position);
    }

    //make interface like this
    public interface Product_OnItemClicked {
        void Product_onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView product_name, product_discription, product_price;
        private ImageView product_image, ic_wishlist;
        private CardView cardview;

        public MyViewHolder(View view) {
            super(view);
            product_discription = (TextView) view.findViewById(R.id.product_discription);
            product_price = (TextView) view.findViewById(R.id.product_price);
            product_name = (TextView) view.findViewById(R.id.product_name);
            product_image = (ImageView) view.findViewById(R.id.product_image);
            ic_wishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
            cardview = (CardView) view.findViewById(R.id.cardview);
        }
    }


    public ProductAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public ProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_cardview, parent, false);

        return new ProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductAdapter.MyViewHolder holder, final int position) {

        final Product product = productList.get(position);
        holder.product_discription.setText(product.getProduct_discription());
        holder.product_name.setText(product.getProduct_name());
        holder.product_price.setText("Rs. " + product.getProduct_price() + "");
        Glide.with(mContext).load(product.getProduct_image()).into(holder.product_image);
        Log.d("Image", product.getProduct_image());

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.Product_onItemClick(position);
            }
        });

        holder.ic_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicknew.Product_onClick(position);

//                Toast.makeText(mContext, "clicked", Toast.LENGTH_LONG).show();
                Glide.with(mContext).load(R.drawable.ic_favorite_black_18dp).into(holder.ic_wishlist);
            }
        });
    }

//    private void removeItem(int position) {
//        productList.remove(position);
//        // notify the item removed by position
//        // to perform recycler view delete animations
//        // NOTE: don't call notifyDataSetChanged()
//        notifyItemRemoved(position);
//        removeItem(position);
//        notifyDataSetChanged();
//    }

    public void setOnClick(ProductAdapter.Product_OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public void setClick(ProductAdapter.Product_OnClicked onClicknew) {
        this.onClicknew = onClicknew;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}

