package com.example.modabba;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.modabba.Fragments.DashboardFragment;

import java.util.List;

public class Adapter extends PagerAdapter {

    private List<Model> models;
    private LayoutInflater layoutInflater;
    private DashboardFragment context;
    private Context con;

    public Adapter(List<Model> models, DashboardFragment context,Context con) {
        this.models = models;
        this.context = context;
        this.con= con;
    }

    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = LayoutInflater.from(con);
        final View view = layoutInflater.inflate(R.layout.item, container, false);

        ImageView imageView;
        TextView title, desc,veg,nonveg;


        imageView = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        desc = view.findViewById(R.id.desc);
        veg=view.findViewById(R.id.veg);
        nonveg=view.findViewById(R.id.nonveg);

        imageView.setImageResource(models.get(position).getImage());
        title.setText(models.get(position).getTitle());
        desc.setText(models.get(position).getDesc());
        if(models.get(position).getMeal()== 0)
        {
            veg.setVisibility(View.INVISIBLE);
            nonveg.setVisibility(View.VISIBLE);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),DetailActivity.class).putExtra("id",models.get(position).getImage());
                intent.putExtra("title",models.get(position).getTitle());
                intent.putExtra("desc",models.get(position).getDesc());
                intent.putExtra("meal",models.get(position).getMeal());
                view.getContext().startActivity(intent);
            }
        });

        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
