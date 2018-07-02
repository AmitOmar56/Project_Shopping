package com.shopping.lifeplay.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.Cart;
import com.shopping.lifeplay.model.SalonCart;
import com.shopping.lifeplay.model.WishList;
import com.shopping.lifeplay.utils.BagdeDrawable;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageView saloon_at_home, online_shooping, offers_deal;
    public static int itemofcart = 0;
    public static int itemofsaloncart = 0;
    public static List<Cart> cartList = new ArrayList<>();
    public static List<WishList> wishListList = new ArrayList<>();
    public static List<SalonCart> salonCartList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getId();
        Glide.with(this).load("http://cdn.playbuzz.com/cdn/ae3e4640-9fd6-4461-8470-6f30d974876e/ffe38d96-13f2-4928-8f71-dd42ebd607ee.jpg").into(saloon_at_home);
        Glide.with(this).load("http://ak6.picdn.net/shutterstock/videos/7157566/thumb/1.jpg").into(online_shooping);
        Glide.with(this).load("https://images.pexels.com/photos/457701/pexels-photo-457701.jpeg?w=940&h=650&auto=compress&cs=tinysrgb").into(offers_deal);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void getId() {
        saloon_at_home = (ImageView) findViewById(R.id.image_salonAthome);
        online_shooping = (ImageView) findViewById(R.id.offer_deal_makeup);
        offers_deal = (ImageView) findViewById(R.id.image_offers_deal);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, icon, itemofcart + "");        // force the ActionBar to relayout its MenuItems.
        // onCreateOptionsMenu(Menu) will be called again.
        invalidateOptionsMenu();
        return super.onPrepareOptionsMenu(menu);
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BagdeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BagdeDrawable) {
            badge = (BagdeDrawable) reuse;
        } else {
            badge = new BagdeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_cart) {

            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_notifications) {
            Toast.makeText(this, cartList.size() + "", Toast.LENGTH_LONG).show();
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_salon) {
            startActivity(new Intent(this, SallonAtHomeActivity.class));

        } else if (id == R.id.nav_shopping) {
            startActivity(new Intent(this, ShoppingActivity.class));

        } else if (id == R.id.nav_offer) {
            startActivity(new Intent(this, OffersDealActivity.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.my_wishlist) {
            Intent intent = new Intent(HomeActivity.this, WishlistActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_cart) {
            Intent intent = new Intent(HomeActivity.this, CartActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
        } else if (id == R.id.nav_refer) {
            startActivity(new Intent(this, ReferAndEarnActivity.class));
        } else if (id == R.id.nav_history) {
            startActivity(new Intent(this, HistoryActivity.class));
        } else if (id == R.id.nav_deals_history) {
            startActivity(new Intent(this, DealsHistoryActivity.class));
        } else if (id == R.id.nav_service_history) {
            startActivity(new Intent(this, ServiceHistoryActivity.class));
        } else if (id == R.id.nav_wallet) {
            startActivity(new Intent(this, WalletActivity.class));
        } else if (id == R.id.nav_logout) {
            insert();
            startActivity(new Intent(this,LoginActivity.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goToshopping(View view) {
        startActivity(new Intent(this, ShoppingActivity.class));
    }

    public void goTosallonatHome(View view) {
        startActivity(new Intent(this, SallonAtHomeActivity.class));
    }

    public void goToOffersdeal(View view) {
        startActivity(new Intent(this, OffersDealActivity.class));
    }

    public void insert() {
        SharedPreferences pref = getSharedPreferences("ActivityPREF", 0);
        SharedPreferences.Editor edt = pref.edit();
        edt.putBoolean("activity_executed", false);
        edt.commit();
    }
}
