package com.example.modabba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private int mCount;

    public SliderAdapterExample(Context context) {
        this.context = context;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });


        switch (position) {
            case 0:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.login_image)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 1:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.photo1)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 2:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.photo2)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 3:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.photo3)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 4:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.photo4)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 5:
                viewHolder.imageGifContainer.setVisibility(View.GONE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.poster)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            default:
                viewHolder.imageGifContainer.setVisibility(View.VISIBLE);
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.login_image)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                Glide.with(viewHolder.itemView)
                        .asGif()
                        .load(R.drawable.login_image)
                        .into(viewHolder.imageGifContainer);
                break;

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            this.itemView = itemView;
        }
    }


}
