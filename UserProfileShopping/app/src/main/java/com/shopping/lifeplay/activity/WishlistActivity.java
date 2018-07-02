package com.shopping.lifeplay.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.Toast;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.adapter.WishListAdapter;
import com.shopping.lifeplay.model.WishList;
import com.shopping.lifeplay.utils.utils;

import static com.shopping.lifeplay.activity.HomeActivity.wishListList;
import static com.shopping.lifeplay.utils.utils.dpToPx;

public class WishlistActivity extends AppCompatActivity implements WishListAdapter.Product_OnClicked, WishListAdapter.Product_OnItemClicked {

    private RecyclerView recyclerView;
    private WishListAdapter adapter = null;
//    private List<WishList> wishListList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*****************(Start) code For Card View*****************/

        recyclerView = (RecyclerView) findViewById(R.id.wishlistRecyclerView);
        adapter = new WishListAdapter(this, wishListList);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new utils.GridSpacingItemDecoration(2, dpToPx(this, 10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
        adapter.setClick(this);
//        prepareAlbums();
        /*****************(End) code For Card View Vertical*****************/
    }

    /*******************Code for RecyclerView****************/
//    /**
//     * Adding few albums for testing
//     */
//
//    private void prepareAlbums() {
//        String[] images = new String[]{
//                "https://static.pexels.com/photos/205926/pexels-photo-205926-medium.jpeg",
//                "https://static.pexels.com/photos/1854/person-woman-hand-relaxing-medium.jpg",
//                "https://static.pexels.com/photos/204611/pexels-photo-204611-medium.jpeg",
//                "https://static.pexels.com/photos/214487/pexels-photo-214487-medium.jpeg",
//                "https://static.pexels.com/photos/168575/pexels-photo-168575-medium.jpeg",
//                "https://static.pexels.com/photos/213384/pexels-photo-213384-medium.jpeg",
//                "https://static.pexels.com/photos/114907/pexels-photo-114907-medium.jpeg",
//                "https://static.pexels.com/photos/169047/pexels-photo-169047-medium.jpeg",
//                "https://static.pexels.com/photos/160826/girl-dress-bounce-nature-160826-medium.jpeg",
//                "https://static.pexels.com/photos/1702/bow-tie-businessman-fashion-man-medium.jpg",
//                "https://static.pexels.com/photos/35188/child-childrens-baby-children-s-medium.jpg",
//                "https://static.pexels.com/photos/70845/girl-model-pretty-portrait-70845-medium.jpeg",
//                "https://static.pexels.com/photos/26378/pexels-photo-26378-medium.jpg",
//                "https://static.pexels.com/photos/193355/pexels-photo-193355-medium.jpeg",
//                "https://static.pexels.com/photos/1543/landscape-nature-man-person-medium.jpg"
//
//
//        };
//
//        WishList wishList = new WishList("Face & beuty", "Item", 1, images[0], 1500);
//        wishListList.add(wishList);
//
//        wishList = new WishList(" beuty & Face", "Item", 1, images[1], 1000);
//        wishListList.add(wishList);
//
//        wishList = new WishList(" beuty & Face", "Item", 1, images[1], 1000);
//        wishListList.add(wishList);
//
//        wishList = new WishList(" beuty & Face", "Item", 1, images[1], 1000);
//        wishListList.add(wishList);
//
//        wishList = new WishList(" beuty & Face", "Item", 1, images[1], 1000);
//        wishListList.add(wishList);
//
//        wishList = new WishList(" beuty & Face", "Item", 1, images[1], 1000);
//        wishListList.add(wishList);
//
//
//        adapter.notifyDataSetChanged();
//    }
    @Override
    public void Product_onClick(int position) {

    }

    @Override
    public void Product_onItemClick(int position) {
        WishList wishList = wishListList.get(position);
        Toast.makeText(this, wishList.getProduct_price() + "", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("image", wishList.getProduct_image());
        intent.putExtra("p_name", wishList.getProduct_name());
        intent.putExtra("p_price", wishList.getProduct_price() + "");
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}