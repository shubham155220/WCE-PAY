package com.abc.wcapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.wcapp.Interface.ItemClickListener;
import com.abc.wcapp.Model.Products;
import com.abc.wcapp.Prevalent.Prevalent;
import com.abc.wcapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.internal.FallbackServiceBroker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Payopt extends AppCompatActivity {

    DatabaseReference ProductsRef;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payopt);
        recyclerView=findViewById(R.id.noticlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
    }

    @Override
    protected void onStart() {
        super.onStart();

      FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(ProductsRef,Products.class).build();

        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {

                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products model) {

                        holder.txtProductName.setText(model.getCategory());
                        holder.txtProductDescription.setText(model.getDescription());
                        holder.txtProductPrice.setText("Price=" +model.getPrice() +"$");
                        Picasso.get().load(model.getImage()).into(holder.imageView);


                    }

                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_items_layout,viewGroup,false);
                        ProductViewHolder holder = new ProductViewHolder(view);
                        return holder;

                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView txtProductName,txtProductDescription,txtProductPrice;
        public ImageView imageView;
        public ItemClickListener listner;

        public ProductViewHolder(View itemView)
        {
            super(itemView);

            imageView =itemView.findViewById(R.id.product_image);
            txtProductName = itemView.findViewById(R.id.product_name);
            txtProductDescription =itemView.findViewById(R.id.product_description);
            txtProductPrice =itemView.findViewById(R.id.product_price);

        }
        public void setItemClickListner(ItemClickListener listner)
        {
            this.listner = listner;
        }



        @Override
        public void onClick(View v) {
            listner.onClick(v,getAdapterPosition(),false);
        }
    }
}
