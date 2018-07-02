package com.shopping.lifeplay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.utils.BagdeDrawable;
import com.shopping.lifeplay.viewPager.Viewpager_Adapter;
import com.shopping.lifeplay.viewPager.ZoomOutPageTransformer;

import static com.shopping.lifeplay.activity.HomeActivity.itemofsaloncart;

public class OffersDealActivity extends AppCompatActivity {

    /********************code for view pager************************/
    private Viewpager_Adapter viewpager_adapter;
    private ViewPager viewPager;
    private Handler handler;
    private int delay = 3000; //milliseconds
    private int page = 0;
    Runnable runnable;


    /********************code for view pager************************/

    private ImageView offer_deal_health, offer_deal_makeup, offer_deal_spa, offer_deal_beauty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_deal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getId();
        viewPagerCode();
        Glide.with(this).load("http://littlejoy.co.in/admin_parilok/api/merchant_cat//5a5ef2eeb8610.jpeg").into(offer_deal_health);
        Glide.with(this).load("http://littlejoy.co.in/admin_parilok/api/merchant_cat//5a5de427e47a8.jpg").into(offer_deal_makeup);
        Glide.with(this).load("http://littlejoy.co.in/admin_parilok/api/merchant_cat//5a5ef30f2eb8a.jpeg").into(offer_deal_spa);
        Glide.with(this).load("http://littlejoy.co.in/admin_parilok/api/merchant_cat//5a5ef2ccbea72.jpg").into(offer_deal_beauty);

    }

    private void getId() {
        offer_deal_health = (ImageView) findViewById(R.id.offer_deal_health);
        offer_deal_makeup = (ImageView) findViewById(R.id.offer_deal_makeup);
        offer_deal_spa = (ImageView) findViewById(R.id.offer_deal_spa);
        offer_deal_beauty = (ImageView) findViewById(R.id.offer_deal_beauty);
    }

    private void viewPagerCode() {

        /***************(start) code for viewPager*************************/
        String images[] = {
                "http://opinionest.weebly.com/uploads/5/9/3/5/59356443/professional-makeup-artist_orig.jpg",
                "http://cdn2.stylecraze.com/wp-content/uploads/2013/02/3.-Arabic-Eye-Makeup.jpg",
                "http://www.theagelessbeautyreport.com/wp-content/uploads/2013/09/makeup.jpg",
                "https://3260m61dbtt52csl993zzx61-wpengine.netdna-ssl.com/wp-content/uploads/2010/11/Maquillaje.jpg",
                "https://www.elegancebeautygroup.co.uk/uploads/images/products/large/elegancebeautygroup_elegance_makeuptrainingcourse_1478711640Makeup.jpg",
                "https://www.miladies.net/wp-content/uploads/2017/02/makeup.jpg",
                "https://static.pexels.com/photos/2713/wall-home-deer-medium.jpg"
        };
        runnable = new Runnable() {
            public void run() {
                if (viewpager_adapter.getCount() == page) {
                    page = 0;
                } else {
                    page++;
                }
                viewPager.setCurrentItem(page, true);
                handler.postDelayed(this, delay);
            }
        };

        handler = new Handler();
        viewPager = (ViewPager) findViewById(R.id.teacherViewpager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        viewpager_adapter = new Viewpager_Adapter(this, images);

        viewPager.setAdapter(viewpager_adapter);
        tabLayout.setupWithViewPager(viewPager, true);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        /***************(End) code for viewPager*************************/
    }

    /********************code for view pager************************/

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    /********************code for view pager************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // Get the notifications MenuItem and
        // its LayerDrawable (layer-list)
        MenuItem itemCart = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();
        setBadgeCount(this, icon, itemofsaloncart + "");        // force the ActionBar to relayout its MenuItems.
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

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {

            Intent intent = new Intent(OffersDealActivity.this, SalonCartActivity.class);
            startActivity(intent);

        } else {
            finish();
        }
//        finish();
        return super.onOptionsItemSelected(item);
    }

    public void goToMakeUpStudio(View view) {
        startActivity(new Intent(this, OffersDealDataActivity.class));
    }

}
