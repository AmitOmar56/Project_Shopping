package com.shopping.lifeplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.WishList;

import java.util.List;


/**
 * Created by user on 12/23/2017.
 */

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.MyViewHolder> {

    private Context mContext;
    private List<WishList> wishListList;

    //declare interface
    private WishListAdapter.Product_OnItemClicked onClick;

    private WishListAdapter.Product_OnClicked onClicknew;

    public interface Product_OnClicked {
        void Product_onClick(int position);
    }

    //make interface like this
    public interface Product_OnItemClicked {
        void Product_onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView wishlistName, wishlistDisc, wishlistPrice;
        private ImageView image_wishlist,delete_wishlist;
        private LinearLayout wishlistLayout;

        public MyViewHolder(View view) {
            super(view);
            wishlistDisc = (TextView) view.findViewById(R.id.wishlistDisc);
            wishlistPrice = (TextView) view.findViewById(R.id.wishlistPrice);
            wishlistName = (TextView) view.findViewById(R.id.wishlistName);
            image_wishlist = (ImageView) view.findViewById(R.id.image_wishlist);
            wishlistLayout = (LinearLayout) view.findViewById(R.id.wishlistLayout);
            delete_wishlist = (ImageView) view.findViewById(R.id.delete_wishlist);
        }
    }


    public WishListAdapter(Context mContext, List<WishList> wishListList) {
        this.mContext = mContext;
        this.wishListList = wishListList;
    }

    @Override
    public WishListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wishlist_item_cardview, parent, false);

        return new WishListAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final WishListAdapter.MyViewHolder holder, final int position) {

        final WishList wishList = wishListList.get(position);
        holder.wishlistDisc.setText(wishList.getProduct_discription());
        holder.wishlistName.setText(wishList.getProduct_name());
        holder.wishlistPrice.setText("Rs. " + wishList.getProduct_price() + "");
        Glide.with(mContext).load(wishList.getProduct_image()).into(holder.image_wishlist);
        Log.d("Image", wishList.getProduct_image());

        holder.wishlistLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.Product_onItemClick(position);
            }
        });

        holder.delete_wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClicknew.Product_onClick(position);

                removeItem(position);
                notifyDataSetChanged();
//                itemofcart--;
            }
        });
    }

    //
    public void removeItem(int position) {
        wishListList.remove(position);
//        // notify the item removed by position
//         to perform recycler view delete animations
//         NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
//
    }

    public void setOnClick(WishListAdapter.Product_OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public void setClick(WishListAdapter.Product_OnClicked onClicknew) {
        this.onClicknew = onClicknew;
    }

    @Override
    public int getItemCount() {
        return wishListList.size();
    }

}


