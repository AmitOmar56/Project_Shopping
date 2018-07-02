package com.shopping.lifeplay.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.user.userprofileshopping.R;
import com.shopping.lifeplay.activity.ProductDetailActivity;
import com.shopping.lifeplay.adapter.ProductAdapter;
import com.shopping.lifeplay.model.Product;
import com.shopping.lifeplay.model.WishList;
import com.shopping.lifeplay.utils.MyProgressDialog;
import com.shopping.lifeplay.utils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import static com.shopping.lifeplay.utils.utils.dpToPx;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SexualWellness.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SexualWellness#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SexualWellness extends Fragment implements ProductAdapter.Product_OnItemClicked, ProductAdapter.Product_OnClicked {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private RecyclerView recyclerView;
    private ProductAdapter adapter = null;
    private List<Product> productList;
    private WishList wishList;
    private int sexual_wellness_id = 6;

    private String product_name;
    private String product_disc;
    private String product_price;
    private String product_cut_price;
    private String product_image;
    private int product_id;
    private int key_id;
    private String product_uses;
    private String product_how_to_apply;
    private String product_cat_name;
    private Product product;
    private String detailUrl = "http://littlejoy.co.in/admin_parilok/users/product_det.php";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SexualWellness() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SexualWellness.
     */
    // TODO: Rename and change types and number of parameters
    public static SexualWellness newInstance(String param1, String param2) {
        SexualWellness fragment = new SexualWellness();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sexual_wellness, container, false);

        /*****************(Start) code For Card View*****************/

        recyclerView = (RecyclerView) view.findViewById(R.id.sexual_recyclerView);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(getContext(), productList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new utils.GridSpacingItemDecoration(2, dpToPx(getActivity(), 10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.setOnClick(this);
        adapter.setClick(this);
        /*****************(End) code For Card View Vertical*****************/
        signupRequest();
        return view;
    }

    @Override
    public void Product_onItemClick(int position) {
        Product product = productList.get(position);
        Toast.makeText(getContext(), product.getProduct_price() + "", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
//        intent.putExtra("image", product.getProduct_image());
//        intent.putExtra("p_name", product.getProduct_name());
//        intent.putExtra("p_price", product.getProduct_price() + "");
        intent.putExtra("p_id", product.getProduct_id() + "");
//        intent.putExtra("p_disc", product.getProduct_disc() + "");
//        intent.putExtra("p_apply", product.getProduct_how_to_apply() + "");
//        intent.putExtra("p_uses", product.getProduct_uses() + "");
        startActivity(intent);
    }

    @Override
    public void Product_onClick(int position) {
        Product product = productList.get(position);
//        wishList = new WishList(product.getProduct_discription(), product.getProduct_name(), product.getProduct_id(), product.getProduct_image(), product.getProduct_price());
//        wishListList.add(wishList);
//        Toast.makeText(getContext(), "Save to Wish list", Toast.LENGTH_LONG).show();

    }
    /*******************Code for RecyclerView****************/

    private void signupRequest() {
        MyProgressDialog.showPDialog(getContext());

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String response = null;
        final String finalResponse = response;

        StringRequest postRequest = new StringRequest(Request.Method.GET, detailUrl,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("amit", response);
                        try {
                            Object json = new JSONTokener(response).nextValue();
                            JSONObject jsonObject = (JSONObject) json;
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            Log.d("jsonObject", jsonObject + "");
                            Log.d("jsonArray", jsonArray + "");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject profile = jsonArray.getJSONObject(i);
                                key_id = profile.getInt("cat_id");
                                product_name = profile.getString("pr_name");
                                product_cat_name = profile.getString("cat_name");
                                product_price = profile.getString("pr_price");
                                product_cut_price = profile.getString("pr_sp");
                                product_disc = profile.getString("pr_desc");
                                product_uses = profile.getString("uses");
                                product_how_to_apply = profile.getString("how_to_apply");
                                product_image = profile.getString("image");
                                product_image = "http://littlejoy.co.in/admin_parilok/api/" + product_image;
                                product_id = Integer.parseInt(profile.getString("product_id"));

                                if (sexual_wellness_id == key_id) {
                                    product = new Product(product_cat_name, product_name, product_id, product_image, product_price, product_cut_price, product_disc, product_how_to_apply, product_uses);
                                    productList.add(product);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            MyProgressDialog.hidePDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Network Error", Toast.LENGTH_LONG).show();

                        MyProgressDialog.hidePDialog();
                    }
                }
        );
        postRequest.setRetryPolicy(new DefaultRetryPolicy(0, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
