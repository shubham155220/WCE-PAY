package com.abc.wcapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.abc.wcapp.Model.notic;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Notice extends AppCompatActivity {
    RecyclerView noticlist;
    DatabaseReference aRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        noticlist = findViewById(R.id.noticlist);
        noticlist.setLayoutManager(new LinearLayoutManager(this));

        aRef = FirebaseDatabase.getInstance().getReference().child("notic");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<notic> options = new FirebaseRecyclerOptions.Builder<notic>().setQuery(aRef, notic.class).build();

        FirebaseRecyclerAdapter<notic,UserViewHolder> adapter = new FirebaseRecyclerAdapter<notic,UserViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserViewHolder holder, int position, @NonNull notic model) {
                //holder.noticid.setText(model.getNoticid());
                holder.noticm.setText(model.getNoticM());
                holder.noticg.setText(model.getNoticg());

            }


            @NonNull
            @Override
            public UserViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_layout_item, viewGroup, false);
                UserViewHolder holder = new UserViewHolder(view);
                return holder;
            }
        };

        noticlist.setAdapter(adapter);
        adapter.startListening();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView noticid, noticm, noticg;


        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            noticg = itemView.findViewById(R.id.noticg);
            noticm = itemView.findViewById(R.id.noticM);

        }
    }
}