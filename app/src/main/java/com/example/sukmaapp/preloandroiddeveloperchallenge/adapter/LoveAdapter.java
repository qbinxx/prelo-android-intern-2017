package com.example.sukmaapp.preloandroiddeveloperchallenge.adapter;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sukmaapp.preloandroiddeveloperchallenge.R;
import com.example.sukmaapp.preloandroiddeveloperchallenge.models.Love;

import java.util.List;

/**
 * Created by Sogeking on 11/10/2017.
 */

public class LoveAdapter extends RecyclerView.Adapter<LoveAdapter.ViewHolder> {

    private List<Love> loveList;

    public LoveAdapter(List<Love> loveList) {
        this.loveList = loveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.love_list_row,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Love love = loveList.get(position);

        holder.tvLoveName.setText(love.getName());

        String price = "Rp "+love.getPrice();
        holder.tvLovePrice.setText(price);

        Glide
                .with(holder.itemView.getContext())
                .load(love.getDisplay_picts())
                .centerCrop()
                .into(holder.ivLovePict);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),love.getName(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return loveList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivLovePict;
        public TextView tvLoveName, tvLovePrice;

        public ViewHolder(View itemView) {
            super(itemView);

            ivLovePict = (ImageView) itemView.findViewById(R.id.iv_love_pict);
            tvLoveName = (TextView) itemView.findViewById(R.id.tv_love_name);
            tvLovePrice = (TextView) itemView.findViewById(R.id.tv_love_price);

        }
    }


}
