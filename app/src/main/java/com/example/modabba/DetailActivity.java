package com.example.modabba;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private AppBarLayout mAppBarLayout;
    private ImageView imageview;
    private TextView title,desc,veg,nonveg;
    private Toolbar mToolbar;
    private RecyclerView recyclerView,recyclerViewPlan;
    private FlexiblePlanAdapter flexiblePlanAdapter;
    private ChoosePlanAdapter choosePlanAdapter;
    private List flexiblePlanList;
    private List choosePlanList;
    private CollapsingToolbarLayout collapsing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageview=findViewById(R.id.imageview);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        veg=findViewById(R.id.veg);
        nonveg=findViewById(R.id.nonveg);
        flexiblePlanList = new ArrayList<>();
        choosePlanList =new ArrayList<>();
        flexiblePlanList.add(new FexiblePlanData(R.drawable.like, "skip meal", "Sudden chanf=ge of schedule?Skip upcoming meal"));
        flexiblePlanList.add(new FexiblePlanData(R.drawable.like, "pause plan", "Going out of town? Pause your plan for those days"));
        flexiblePlanList.add(new FexiblePlanData(R.drawable.like, "Cancel plan", "Never feel bound.Cancel plan anytime if you're unhappy"));



        Bundle extras = getIntent().getExtras();
        int entryId = extras.getInt("id");
        final String tite=extras.getString("title");
        String dec=extras.getString("desc");
        int meal=extras.getInt("meal");
        int combo=extras.getInt("combo");
        imageview.setImageResource(entryId);
        title.setText(tite);
        desc.setText(dec);
        if(meal== 0)
        {
            veg.setVisibility(View.INVISIBLE);
            nonveg.setVisibility(View.VISIBLE);
        }
        choosePlanList.add(new ChoosePlan("07 days","60",meal,combo));
        choosePlanList.add(new ChoosePlan("14 days","75",meal,combo));
        choosePlanList.add(new ChoosePlan("30 days","120",meal,combo));

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mAppBarLayout = findViewById(R.id.appbar);
        collapsing=findViewById(R.id.collapsing);
        collapsing.setTitle(" ");
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsing.setTitle(tite);
                    isShow = true;
                } else if (isShow) {
                    collapsing.setTitle("");
                    isShow = false;
                }
            }
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerViewPlan=findViewById(R.id.recycler_view_plan);
        flexiblePlanAdapter = new FlexiblePlanAdapter(flexiblePlanList);
        choosePlanAdapter=new ChoosePlanAdapter(choosePlanList);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerViewPlan.setLayoutManager(layoutManager);
        recyclerView.setAdapter(flexiblePlanAdapter);
        recyclerViewPlan.setAdapter(choosePlanAdapter);
    }
}
