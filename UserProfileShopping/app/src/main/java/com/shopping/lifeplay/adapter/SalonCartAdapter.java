package com.shopping.lifeplay.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.model.SalonCart;

import java.util.ArrayList;
import java.util.List;

import static com.shopping.lifeplay.activity.HomeActivity.itemofsaloncart;

/**
 * Created by user on 12/27/2017.
 */

public class SalonCartAdapter extends RecyclerView.Adapter<SalonCartAdapter.MyViewHolder> {

    private Context mContext;
    private List<SalonCart> salonCartList;
    private String select_item;
    public static int salon_item = 1;


    //declare interface
    private SalonCartAdapter.Product_OnItemClicked onClick;

    private SalonCartAdapter.Product_OnClicked onClicknew;

    private SalonCartAdapter.Product_On_spin_Clicked onClick_spin_new;

    public interface Product_On_spin_Clicked {
        void Product_on_spin_Clicked(int position);
    }

    public interface Product_OnClicked {
        void Product_onClick(int position);
    }

    //make interface like this
    public interface Product_OnItemClicked {
        void Product_onItemClick(int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView c_service_name, c_service_price, cart_quantity;
        private ImageView delete;
        private Spinner spinner;


        public MyViewHolder(View view) {
            super(view);
            c_service_name = (TextView) view.findViewById(R.id.c_service_name);
            c_service_price = (TextView) view.findViewById(R.id.c_service_price);
            delete = (ImageView) view.findViewById(R.id.delete);
            spinner = (Spinner) view.findViewById(R.id.salon_spinner);
            cart_quantity = (TextView) view.findViewById(R.id.salon_cart_quantity);


        }
    }


    public SalonCartAdapter(Context mContext, List<SalonCart> salonCartList) {
        this.mContext = mContext;
        this.salonCartList = salonCartList;
    }

    @Override
    public SalonCartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.salon_cart_card_view, parent, false);

        return new SalonCartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SalonCartAdapter.MyViewHolder holder, final int position) {

        final SalonCart salonCart = salonCartList.get(position);
        holder.c_service_name.setText(salonCart.getCart_data_name());
        holder.c_service_price.setText("Rs. " + salonCart.getCart_data_price());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick.Product_onItemClick(position);
                removeItem(position);
                notifyDataSetChanged();
                itemofsaloncart--;
            }
        });
        // Spinner Drop down elements
        List<String> quantity = new ArrayList<String>();
        quantity.add("1");
        quantity.add("2");
        quantity.add("3");
        quantity.add("4");
        quantity.add("5");
        quantity.add("6");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, quantity);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        holder.spinner.setAdapter(dataAdapter);
        holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                select_item = adapterView.getItemAtPosition(i).toString();
                salon_item = Integer.parseInt(select_item);
                holder.c_service_price.setText("Rs. " + salon_item * Integer.parseInt(salonCart.getCart_data_price()) + "");
                onClick_spin_new.Product_on_spin_Clicked(position);
                Toast.makeText(mContext, salon_item + "", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }


    private void removeItem(int position) {
        salonCartList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);

    }

    public void setOn_Spin_Click(SalonCartAdapter.Product_On_spin_Clicked onClick_spin_new) {
        this.onClick_spin_new = onClick_spin_new;
    }

    public void setOnClick(SalonCartAdapter.Product_OnItemClicked onClick) {
        this.onClick = onClick;
    }

    public void setClick(SalonCartAdapter.Product_OnClicked onClicknew) {
        this.onClicknew = onClicknew;
    }

    @Override
    public int getItemCount() {
        return salonCartList.size();
    }

}

