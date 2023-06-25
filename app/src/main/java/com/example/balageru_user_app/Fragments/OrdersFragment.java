package com.example.balageru_user_app.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.balageru_user_app.Adapters.BannerAdapter;
import com.example.balageru_user_app.Adapters.CatAdapter;
import com.example.balageru_user_app.Adapters.GreatOffersAdapter;
import com.example.balageru_user_app.Adapters.ProductAdapter;
import com.example.balageru_user_app.Adapters.SimpleVerticalAdapter;
import com.example.balageru_user_app.Adapters.UserAdapter;
import com.example.balageru_user_app.HelpAndSupport;
import com.example.balageru_user_app.MainActivity;
import com.example.balageru_user_app.Models.BannerModel;
import com.example.balageru_user_app.Models.CategoryModel;
import com.example.balageru_user_app.Models.GreatOffersModel;
import com.example.balageru_user_app.Models.SimpleVerticalModel;
import com.example.balageru_user_app.Models.UserModel;
import com.example.balageru_user_app.OperationRetrofitApi.ApiClient;
import com.example.balageru_user_app.OperationRetrofitApi.ApiInterface;
import com.example.balageru_user_app.OperationRetrofitApi.Users;
import com.example.balageru_user_app.Order;
import com.example.balageru_user_app.Product.Product;
import com.example.balageru_user_app.Product.ProductDatabaseActivity;
import com.example.balageru_user_app.Product.ProductDescription;
import com.example.balageru_user_app.Product.RecyclerTouchListener;
import com.example.balageru_user_app.R;
import com.example.balageru_user_app.Sessions.SessionManager;
import com.example.balageru_user_app.TermsAndConditions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrdersFragment extends Fragment implements  View.OnClickListener, ProductDatabaseActivity.UploadCallback {



    public OrdersFragment() {
        // Required empty public constructor
    }

    DrawerLayout drawerLayout;
    ImageView navigationBar, strip_banner_image;
    NavigationView navigationView;
    private View view;
    private TextView your_orders, helpAndSupport, termsAndConditions, callCenter;
    SessionManager sessionManager;
    private TextView login, logout, location;
    private FloatingActionButton btn_post;
    private EditText productName,productDesc, productPrice, productQty, productCat, productImg, searchInput;
    private Button cancel, btn_img;
    private Uri selectedImageUri;
    private static final int PICK_IMAGE_REQUEST=1;
    private static final int PERMISSION_REQUEST_CODE=2;
    private ImageView imageView;
    Product product, singleProduct;
    private ImageButton btnSearch;


    private String phoneNumber = "0923938609";

    ArrayList<Product> dataList;




    RecyclerView recyclerView, myProductRecyclerView;
    ProductAdapter adapter;

    ///////////////USER start/////////////////////
    RecyclerView recyclerViewUser;
    private UserAdapter userAdapter;
    private List<UserModel> userModelList;
    ///////////////USER end/////////////////////

    ///////////////category slider start/////////////////////
    RecyclerView recyclerViewCategory;
    private CatAdapter catAdapter;
    private List<CategoryModel> categoryModelList;
    ///////////////category slider end/////////////////////

    ///////////////Banner slider start/////////////////////
    private RecyclerView recyclerViewBanner;
    private BannerAdapter bannerAdapter;
    private List<BannerModel> bannerModelList;
    ///////////////Banner slider end/////////////////////

    ///////////////Simple Vertical slider start/////////////////////
    private RecyclerView recyclerViewSimple;
    private SimpleVerticalAdapter simpleVerticalAdapter;
    private List<SimpleVerticalModel> simpleVerticalModelList;
    ///////////////Simple Vertical  slider end/////////////////////

    ////////////////////////////////Great offer horizontal start//////////////////////////////
    private RecyclerView greatGreatOffersHorizontal;
    private List<GreatOffersModel> greatOffersModel;
    private GreatOffersAdapter greatOffersAdapter;
    ////////////////////////////////Great offer horizontal end//////////////////////////////

    ////////////////////////////////Great Offers vertical slider start//////////////////////////////
    private RecyclerView greatOffersRecyclerViewVertical;
    ////////////////////////////////Great Offers vertical slider end//////////////////////////////

    ////////////////////////////////New Arrival Horizontal slider start//////////////////////////////
    private RecyclerView newArrivalHorizontalRecyclerview;
    ////////////////////////////////New Arrival Horizontal slider end//////////////////////////////

    ////////////////////////////////New Arrival vertical slider start//////////////////////////////
    private RecyclerView newArrivalVerticalRecyclerview;
    ////////////////////////////////New Arrival Vertical slider end//////////////////////////////

    ////////////////////////////////balageru exclusive vertical slider start//////////////////////////////
    private RecyclerView exclusiveVerticalRecyclerview;
    ////////////////////////////////balageru exclusive Vertical slider end//////////////////////////////

    ////////////////////////////////balageru exclusive horizontal slider start//////////////////////////////
    private RecyclerView exclusiveHorizontalRecyclerview;
    ////////////////////////////////balageru exclusive horizontal slider end//////////////////////////////

    ///////////////api's calling////////////////
    public static ApiInterface apiInterface;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);
        sessionManager = new SessionManager(getContext());
        Dialog dialog= new Dialog(getActivity());
        dialog.setContentView(R.layout.post_product);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        onSetNavigationDrawerEvents();
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        SharedPreferences user_id_stored=getActivity().getSharedPreferences("USER_ID",MODE_PRIVATE);
        String user_id = user_id_stored.getString("userIdStored", null);
        String sellerName = user_id_stored.getString("userNameStored", null);
        String city = user_id_stored.getString("userCity", null);
        String subCity = user_id_stored.getString("userSubcity", null);
        location = view.findViewById(R.id.location);
        location.setText( subCity + ", "+ city);
        String productId= String.valueOf(UUID.randomUUID());
        btn_post= view.findViewById(R.id.btn_post);
        productName = dialog.findViewById(R.id.etProductName);
        productDesc = dialog.findViewById(R.id.etProductDesc);
        productPrice = dialog.findViewById(R.id.etProductPrice);
        productQty = dialog.findViewById(R.id.etProductQty);
        productCat = dialog.findViewById(R.id.etProductCat);
        cancel = dialog.findViewById(R.id.btn_cancel);
        Button upload = dialog.findViewById(R.id.upload);
        btn_img= dialog.findViewById(R.id.btn_img);
        imageView= dialog.findViewById(R.id.product_img);

        searchInput = view.findViewById(R.id.searchProduct);
        btnSearch = view.findViewById(R.id.btnSearch);



        dataList= new ArrayList<>();

        recyclerViewCategory = (RecyclerView) view.findViewById(R.id.recyclerViewCategory);


        btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product= new Product(productName.getText().toString(),productDesc.getText().toString(),productPrice.getText().toString(),productQty.getText().toString(),productCat.getText().toString(),productId, user_id, selectedImageUri, sellerName);


                ProductDatabaseActivity.uploadImage(selectedImageUri,OrdersFragment.this);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermissionsAndOpenGallery();
            }
        });



        recyclerView = view.findViewById(R.id.productList);

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("Product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){

                    GridLayoutManager layoutManagerProduct = new GridLayoutManager(getActivity(), 2);


                    if (getActivity() != null) {
                        Context context = getActivity().getApplicationContext();
                        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.divider);
                        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL | DividerItemDecoration.VERTICAL);
                        dividerItemDecoration.setDrawable(drawable);
                        recyclerView.addItemDecoration(dividerItemDecoration);
                        recyclerView.setLayoutManager(layoutManagerProduct);
                    }

                    for(DocumentSnapshot snapshot:task.getResult()){
                        singleProduct = new Product(snapshot.get("product_name").toString(), snapshot.get("product_description").toString(), snapshot.get("product_price").toString(), snapshot.get("product_quantity").toString(), snapshot.get("product_category").toString(), snapshot.get("product_id").toString(), snapshot.get("user_id").toString(), Uri.parse(snapshot.get("product_img_url").toString()),sellerName);
                        dataList.add(singleProduct);
                    }


                    adapter = new ProductAdapter(dataList);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.d("TAG", "onCreateView: " + singleProduct.getProductId());
                }
                else{

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        recyclerViewCategory.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerViewCategory, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                adapter=new ProductAdapter(searchProduct(catAdapter.getData().get(position).getCat_title()));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent= new Intent(getActivity(), ProductDescription.class);
                intent.putExtra("productName",dataList.get(position).getProductName());
                intent.putExtra("sellerName",dataList.get(position).getSellerName());
                intent.putExtra("sellerId",dataList.get(position).getUserId());
                intent.putExtra("productPrice",dataList.get(position).getProductPrice());
                intent.putExtra("productDescription",dataList.get(position).getProductDesc());
                intent.putExtra("productImageUrl",dataList.get(position).getProductImg().toString());
//                Toast.makeText(getActivity(), ""+ dataList.get(position).getSellerName(), Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adapter = new ProductAdapter(searchProduct(searchInput.getText().toString()));
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });

        init();
        return view;
    }

    private void checkPermissionsAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        } else {
            openGallery();
        }
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            selectedImageUri=imageUri;
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageURI(imageUri);
        }
    }

    private void init() {

        ////////////////////////////User start///////////////////////
//        recyclerViewUser = (RecyclerView) view.findViewById(R.id.recyclerViewUser);
//        LinearLayoutManager layoutManagerUser = new LinearLayoutManager(getContext());
//        layoutManagerUser.setOrientation(RecyclerView.HORIZONTAL);
//        recyclerViewUser.setLayoutManager(layoutManagerUser);
//
//        userModelList = new ArrayList<>();
//        Call<Users> userCall = apiInterface.getUsers();
//        userCall.enqueue(new Callback<Users>() {
//            @Override
//            public void onResponse(Call<Users> userCall, Response<Users> response) {
//
//                userModelList = response.body().getUsers();
//
//                userAdapter = new UserAdapter(userModelList,getContext());
//                recyclerViewUser.setAdapter(userAdapter);
//                userAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<Users> categoryCall, Throwable t) {
//
//            }
//        });

        ////////////////////////////User end///////////////////////

        //////////////////strip banner image start///////////////////////
        strip_banner_image= (ImageView) view.findViewById(R.id.strip_banner_image);
        Call<Users> stripBannerCall = apiInterface.getStripBanners();
        stripBannerCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> stripBannerCall, Response<Users> response) {

                Glide.with(getContext()).load(response.body().getStrip_banner_image()).placeholder(R.drawable.small_placeholder).into(strip_banner_image);
            }

            @Override
            public void onFailure(Call<Users> stripBannerCall, Throwable t) {

            }
        });

        //////////////////strip banner image end///////////////////////


        ////////////////////////////Category model start///////////////////////

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewCategory.setLayoutManager(layoutManager);

        categoryModelList = new ArrayList<>();
        Call<Users> categoryCall = apiInterface.getCategories();
        categoryCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> categoryCall, Response<Users> response) {

                categoryModelList = response.body().getCategory();

                catAdapter = new CatAdapter(categoryModelList,getContext());
                recyclerViewCategory.setAdapter(catAdapter);
                catAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> categoryCall, Throwable t) {

            }
        });

        ////////////////////////////Category model end///////////////////////

        ////////////////////////////////////Banner model list start////////////////////////////
        recyclerViewBanner = (RecyclerView) view.findViewById(R.id.recyclerViewBanner);
        LinearLayoutManager layoutManagerBanner = new LinearLayoutManager(getContext());
        layoutManagerBanner.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewBanner.setLayoutManager(layoutManagerBanner);

        bannerModelList = new ArrayList<>();
        Call<Users> bannerCall = apiInterface.getBanners();
        bannerCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> bannerCall, Response<Users> response) {
                bannerModelList = response.body().getBanners();

                bannerAdapter = new BannerAdapter(bannerModelList,getContext());
                recyclerViewBanner.setAdapter(bannerAdapter);
                bannerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> bannerCall, Throwable t) {

            }
        });


        ////////////////////////////////Banner model list end//////////////////////////////

        ////////////////////////////////////Simple Vertical list start////////////////////////////
//        recyclerViewSimple = (RecyclerView) view.findViewById(R.id.recyclerViewSimple);
//        LinearLayoutManager layoutManagerSimpleVerticalSlider = new LinearLayoutManager(getContext());
//        layoutManagerSimpleVerticalSlider.setOrientation(RecyclerView.VERTICAL);
//        recyclerViewSimple.setLayoutManager(layoutManagerSimpleVerticalSlider);
//
//        simpleVerticalModelList = new ArrayList<>();
//        Call<Users> random_shops = apiInterface.getRandomShops();
//        random_shops.enqueue(new Callback<Users>() {
//            @Override
//            public void onResponse(Call<Users> call, Response<Users> response) {
//                simpleVerticalModelList = response.body().getRandom_shops();
//
//                simpleVerticalAdapter = new SimpleVerticalAdapter(simpleVerticalModelList,getContext());
//                recyclerViewSimple.setAdapter(simpleVerticalAdapter);
//                simpleVerticalAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<Users> call, Throwable t) {
//
//            }
//        });




        ////////////////////////////////Simple Vertical list end//////////////////////////////

        ////////////////////////////////////Great offers model list start////////////////////////////
        greatGreatOffersHorizontal = (RecyclerView) view.findViewById(R.id.recyclerViewgreatOffersHorizontal);
        LinearLayoutManager layoutManagerGreatOffers = new LinearLayoutManager(getContext());
        layoutManagerGreatOffers.setOrientation(RecyclerView.HORIZONTAL);
        greatGreatOffersHorizontal.setLayoutManager(layoutManagerGreatOffers);

        greatOffersModel = new ArrayList<>();
        Call<Users> greatOffersCall = apiInterface.greatOffersShop();
        greatOffersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                greatOffersModel = response.body().getGreat_offers_shops();

                greatOffersAdapter = new GreatOffersAdapter(greatOffersModel,getContext());
                greatGreatOffersHorizontal.setAdapter(greatOffersAdapter);
                greatOffersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

        ////////////////////////////////Great offers model list end//////////////////////////////


        ////////////////////////////////////New Great Offers vertical slider start////////////////////////////
        greatOffersRecyclerViewVertical = (RecyclerView) view.findViewById(R.id.greatOffersRecyclerViewVertical);
        LinearLayoutManager layoutManagerVerticalGreatOffers = new LinearLayoutManager(getContext());
        layoutManagerVerticalGreatOffers.setOrientation(RecyclerView.VERTICAL);
        greatOffersRecyclerViewVertical.setLayoutManager(layoutManagerVerticalGreatOffers);

        simpleVerticalModelList = new ArrayList<>();
        Call<Users> greatOffersVerticalCall = apiInterface.greatOffersVerticalShop();
        greatOffersVerticalCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                simpleVerticalModelList = response.body().getGreat_offers_shops_vertical();

                simpleVerticalAdapter = new SimpleVerticalAdapter(simpleVerticalModelList,getContext());
                greatOffersRecyclerViewVertical.setAdapter(simpleVerticalAdapter);
                simpleVerticalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
        ////////////////////////////////New Great Offers vertical slider end//////////////////////////////


        ////////////////////////////////////New arrival horizontal model list start////////////////////////////
        newArrivalHorizontalRecyclerview = (RecyclerView) view.findViewById(R.id.newArrivalHorizontalRecyclerview);
        LinearLayoutManager layoutManagerHorizontalNewArrival = new LinearLayoutManager(getContext());
        layoutManagerHorizontalNewArrival.setOrientation(RecyclerView.HORIZONTAL);
        newArrivalHorizontalRecyclerview.setLayoutManager(layoutManagerHorizontalNewArrival);

        greatOffersModel = new ArrayList<>();
        Call<Users> arrivalCall = apiInterface.newArrivalsShops();
        arrivalCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                greatOffersModel = response.body().getNew_arrivals_shops();

                greatOffersAdapter = new GreatOffersAdapter(greatOffersModel,getContext());
                newArrivalHorizontalRecyclerview.setAdapter(greatOffersAdapter);
                greatOffersAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

        ////////////////////////////////New arrival horizontal model list end//////////////////////////////

        ////////////////////////////////////New arrival vertical slider start////////////////////////////
        newArrivalVerticalRecyclerview = (RecyclerView) view.findViewById(R.id.newArrivalVerticalRecyclerview);
        LinearLayoutManager layoutManagerVerticalNewArrival = new LinearLayoutManager(getContext());
        layoutManagerVerticalNewArrival.setOrientation(RecyclerView.VERTICAL);
        newArrivalVerticalRecyclerview.setLayoutManager(layoutManagerVerticalNewArrival);

        simpleVerticalModelList = new ArrayList<>();
        Call<Users> arrivalVerticalCall = apiInterface.newArrivalsVerticalShops();
        arrivalVerticalCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                simpleVerticalModelList = response.body().getNew_arrivals_shops_vertical();

                simpleVerticalAdapter = new SimpleVerticalAdapter(simpleVerticalModelList,getContext());
                newArrivalVerticalRecyclerview.setAdapter(simpleVerticalAdapter);
                simpleVerticalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });


        ////////////////////////////////New arrival vertical slider end//////////////////////////////


        ////////////////////////////////////Exclusive horizontal model list start////////////////////////////
        exclusiveHorizontalRecyclerview = (RecyclerView) view.findViewById(R.id.exclusiveHorizontalRecyclerview);
        LinearLayoutManager layoutManagerExclusiveHorizontal = new LinearLayoutManager(getContext());
        layoutManagerExclusiveHorizontal.setOrientation(RecyclerView.HORIZONTAL);
        exclusiveHorizontalRecyclerview.setLayoutManager(layoutManagerExclusiveHorizontal);

        greatOffersModel = new ArrayList<>();
        Call<Users> exclusiveCall = apiInterface.balageruExclusive();
        exclusiveCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                greatOffersModel = response.body().getBalageru_exclusive();

                greatOffersAdapter = new GreatOffersAdapter(greatOffersModel,getContext());
                exclusiveHorizontalRecyclerview.setAdapter(greatOffersAdapter);
                greatOffersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
        ////////////////////////////////Exclusive horizontal model list end//////////////////////////////


        ////////////////////////////////////Exclusive vertical slider start////////////////////////////
        exclusiveVerticalRecyclerview = (RecyclerView) view.findViewById(R.id.exclusiveVerticalRecyclerview);
        LinearLayoutManager layoutManagerExclusiveVertical = new LinearLayoutManager(getContext());
        layoutManagerExclusiveVertical.setOrientation(RecyclerView.VERTICAL);
        exclusiveVerticalRecyclerview.setLayoutManager(layoutManagerExclusiveVertical);

        simpleVerticalModelList = new ArrayList<>();
        Call<Users> exclusiveVerticalCall = apiInterface.balageruExclusiveVertical();
        exclusiveVerticalCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                simpleVerticalModelList = response.body().getBalageru_exclusive_vertical();

                simpleVerticalAdapter = new SimpleVerticalAdapter(simpleVerticalModelList,getContext());
                exclusiveVerticalRecyclerview.setAdapter(simpleVerticalAdapter);
                simpleVerticalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
        ////////////////////////////////Exclusive vertical slider end//////////////////////////////


    }

    private void onSetNavigationDrawerEvents() {

        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) view.findViewById(R.id.navigationView);


        navigationBar = (ImageView) view.findViewById(R.id.navigationBar);

        login = (TextView) view.findViewById(R.id.login);
        logout = (TextView) view.findViewById(R.id.logout);


        your_orders = (TextView) view.findViewById(R.id.your_orders);
        helpAndSupport = (TextView) view.findViewById(R.id.helpAndSupport);
        termsAndConditions = (TextView) view.findViewById(R.id.termsAndConditions);
        callCenter = (TextView) view.findViewById(R.id.callCenter);



        navigationBar.setOnClickListener(this);
        login.setOnClickListener(this);
        logout.setOnClickListener(this);


        your_orders.setOnClickListener(this);
        helpAndSupport.setOnClickListener(this);
        termsAndConditions.setOnClickListener(this);
        callCenter.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.navigationBar:
                drawerLayout.openDrawer(navigationView, true);
                break;
            case R.id.login:
                Login();
                break;
            case R.id.logout:
                Logout();
                break;
            case R.id.your_orders:
                Intent intent1 = new Intent(getActivity(), Order.class);
                startActivity(intent1);
                break;
            case R.id.helpAndSupport:
                Intent intent2 = new Intent(getActivity(), HelpAndSupport.class);
                startActivity(intent2);
                break;
            case R.id.termsAndConditions:
                Intent intent3 = new Intent(getActivity(), TermsAndConditions.class);
                startActivity(intent3);
                break;
            case R.id.callCenter:
                dialPhoneNumber(getContext());
                break;
        }
    }

    private void Logout() {

        sessionManager.editor.clear();
        sessionManager.editor.commit();

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

//        Animatoo.animateSwipeRight(getContext());
    }
    private void Login() {

        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);

//        Animatoo.animateSwipeRight(getContext());
    }

    @Override
    public void onStart() {
        super.onStart();

        if(sessionManager.isLogin())
        {
            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onProgress(int progress) {

    }

    @Override
    public void onSuccess(String downloadUrl) {
        HashMap<String, Object> productToBePosted= new HashMap<>();
        productToBePosted.put("product_name",product.getProductName());
        productToBePosted.put("product_description",product.getProductDesc());
        productToBePosted.put("product_price",product.getProductPrice());
        productToBePosted.put("product_quantity",product.getProductQty());
        productToBePosted.put("product_category",product.getProductCat());
        productToBePosted.put("product_id",product.getProductId());
        productToBePosted.put("user_id",product.getUserId());
        productToBePosted.put("product_img_url",downloadUrl);

        ProductDatabaseActivity.postProduct(productToBePosted, getActivity());
        Toast.makeText(getActivity(), "Product posted Successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(String message) {

    }
    public ArrayList<Product> searchProduct(String searchString)  {
        ArrayList<Product>searchResult=new ArrayList<>();
        Pattern pattern= Pattern.compile(searchString,Pattern.CASE_INSENSITIVE);
        for (int i=0; i<dataList.size(); i++){
            Matcher nameMatcher=pattern.matcher(dataList.get(i).getProductName());
            boolean nameMatchFound=nameMatcher.find();
            Matcher priceMatcher=pattern.matcher(dataList.get(i).getProductPrice());
            boolean priceMatcherFound=priceMatcher.find();
            Matcher categoryMatcher=pattern.matcher(dataList.get(i).getProductCat());
            boolean categoryMatcherFound=categoryMatcher.find();
            if(nameMatchFound || priceMatcherFound || categoryMatcherFound){
                searchResult.add(dataList.get(i));
            }

        }
        return searchResult;
    }

    private void dialPhoneNumber(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}