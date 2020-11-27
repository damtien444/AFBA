package com.lienhuong.fashionbrandapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.media.Image;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lienhuong.fashionbrandapp.model.Product;
import com.squareup.picasso.Picasso;

public class ProductDetail extends AppCompatActivity {
        TextView product_name,product_price,product_description;
    ImageView product_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;
    String productID="";
    FirebaseDatabase database;
    DatabaseReference products;
    private String productCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

//        //database
        database = FirebaseDatabase.getInstance();
        products = database.getReference("Products");

        //Init View
        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart = (FloatingActionButton) findViewById(R.id.btnCart);

        product_description = (TextView) findViewById(R.id.product_description);
        product_name = (TextView) findViewById(R.id.product_name);
        product_price = (TextView) findViewById(R.id.product_price);
        product_image = (ImageView) findViewById(R.id.img_product);

//        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
//        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
//        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);
        //Get product id from intent    
        if(getIntent() != null){
            productID = getIntent().getStringExtra("ProductId");
            productCategory = getIntent().getStringExtra("ProductCategory");
        }
        if(!productID.isEmpty()){
            getDetailProduct(productID);
        }
    }
    private void getDetailProduct(final String productId){
        products.child("Category/" + productCategory + "/danh_sach/" + productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("TAG","Category/" + productCategory + "/danh_sach/" + productId);
                Log.d("TAG","snapshot:" + snapshot.toString());
                Product product = snapshot.getValue(Product.class);
                //set Image
                Glide.with(getBaseContext()).load(product.getImage())
                        .into(product_image);
                collapsingToolbarLayout.setTitle(product.getName());

                product_price.setText(product.getGia());
                product_name.setText(product.getName());
                product_description.setText(product.getMota());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
