package com.shopping.lifeplay.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.user.userprofileshopping.R;
import com.shopping.lifeplay.activity.ProductDetailActivity;
import com.shopping.lifeplay.adapter.ProductAdapter;
import com.shopping.lifeplay.model.Product;
import com.shopping.lifeplay.model.WishList;
import com.shopping.lifeplay.utils.utils;

import java.util.ArrayList;
import java.util.List;

import static com.shopping.lifeplay.utils.utils.dpToPx;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link More.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link More#newInstance} factory method to
 * create an instance of this fragment.
 */
public class More extends Fragment implements ProductAdapter.Product_OnItemClicked ,ProductAdapter.Product_OnClicked{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView;
    private ProductAdapter adapter = null;
    private List<Product> productList;
    private WishList wishList;
    private String detailUrl = "http://littlejoy.co.in/admin_parilok/users/product_det.php";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public More() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment More.
     */
    // TODO: Rename and change types and number of parameters
    public static More newInstance(String param1, String param2) {
        More fragment = new More();
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
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        /*****************(Start) code For Card View*****************/

        recyclerView = (RecyclerView) view.findViewById(R.id.more_recyclerView);
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
        prepareAlbums();

        return view;
    }

    @Override
    public void Product_onItemClick(int position) {
        Product product = productList.get(position);
        Toast.makeText(getContext(), product.getProduct_price()+"", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getContext(), ProductDetailActivity.class);
        intent.putExtra("image", product.getProduct_image());
        intent.putExtra("p_name", product.getProduct_name());
        intent.putExtra("p_price", product.getProduct_price()+"");
        intent.putExtra("p_id", product.getProduct_id() + "");
        intent.putExtra("p_disc", product.getProduct_disc() + "");
        intent.putExtra("p_apply", product.getProduct_how_to_apply() + "");
        intent.putExtra("p_uses", product.getProduct_uses() + "");
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
    /**
     * Adding few albums for testing
     */

    private void prepareAlbums() {
        String[] images = new String[]{
                "https://static.pexels.com/photos/205926/pexels-photo-205926-medium.jpeg",
                "https://static.pexels.com/photos/2396/light-glass-lamp-idea-medium.jpg",
                "https://static.pexels.com/photos/1854/person-woman-hand-relaxing-medium.jpg",
                "https://static.pexels.com/photos/204611/pexels-photo-204611-medium.jpeg",
                "https://static.pexels.com/photos/214487/pexels-photo-214487-medium.jpeg",
                "https://static.pexels.com/photos/168575/pexels-photo-168575-medium.jpeg",
                "https://static.pexels.com/photos/213384/pexels-photo-213384-medium.jpeg",
                "https://static.pexels.com/photos/114907/pexels-photo-114907-medium.jpeg",
                "https://static.pexels.com/photos/169047/pexels-photo-169047-medium.jpeg",
                "https://static.pexels.com/photos/160826/girl-dress-bounce-nature-160826-medium.jpeg",
                "https://static.pexels.com/photos/1702/bow-tie-businessman-fashion-man-medium.jpg",
                "https://static.pexels.com/photos/35188/child-childrens-baby-children-s-medium.jpg",
                "https://static.pexels.com/photos/70845/girl-model-pretty-portrait-70845-medium.jpeg",
                "https://static.pexels.com/photos/26378/pexels-photo-26378-medium.jpg",
                "https://static.pexels.com/photos/193355/pexels-photo-193355-medium.jpeg",
                "https://static.pexels.com/photos/1543/landscape-nature-man-person-medium.jpg"


        };

        Product product = new Product("Face & beuty", "Item", 1, images[0], "1500","1000","","","");
        productList.add(product);


        adapter.notifyDataSetChanged();
    }

    /*******************Code for RecyclerView****************/


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
