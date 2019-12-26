package com.example.modabba;

import android.os.Bundle;
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
    private LinearLayout mTitleContainer;

    private TextView mTitle;

    private AppBarLayout mAppBarLayout;

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
        flexiblePlanList = new ArrayList<>();
        choosePlanList =new ArrayList<>();
        //flexiblePlanList.add(new FexiblePlanData(R.drawable.like,"swap meal","Craving a change?Swap upcoming mealwith any other meal"));
        flexiblePlanList.add(new FexiblePlanData(R.drawable.like, "skip meal", "Sudden chanf=ge of schedule?Skip upcoming meal"));
        flexiblePlanList.add(new FexiblePlanData(R.drawable.like, "pause plan", "Going out of town? Pause your plan for those days"));
        flexiblePlanList.add(new FexiblePlanData(R.drawable.like, "Cancel plan", "Never feel bound.Cancel plan anytime if you're unhappy"));

        choosePlanList.add(new ChoosePlan("3 day","60"));
        choosePlanList.add(new ChoosePlan("14 day","75"));
        choosePlanList.add(new ChoosePlan("30 day","120"));

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        //  mTitle          = (TextView) findViewById(R.id.main_textview_title);
        //mTitleContainer = (LinearLayout) findViewById(R.id.linearlayouttitle);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar);
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
                    collapsing.setTitle("Delicious Lunch Plan");
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
