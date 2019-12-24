package com.example.modabba.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.modabba.Adapter;
import com.example.modabba.Model;
import com.example.modabba.R;
import com.example.modabba.SessionManagement.SessionManagement;
import com.example.modabba.SlidePagerAdapter;
import com.example.modabba.SliderAdapterExample;
import com.example.modabba.ViewPagerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kofigyan.stateprogressbar.StateProgressBar;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private Context context;
    private FirebaseFirestore db;
    private SliderView sliderView;
    private SessionManagement sessionManagement;
    private TextView dashBoardCredit;
    private String[] descriptionData = {"Preparing", "On Way", "Delivered"};
    private StateProgressBar stateProgressBar;
    private TextView dashboardUsername,dashboardLunch,dashBoardDinner;
    private ViewPager viewPager,pager,horiscroll;
    private PagerAdapter pageadapter;
    private TabLayout tabLayout;
    Adapter adapter;
    List<Model> models;


    public DashboardFragment(){}
    public DashboardFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            init();

            View view = inflater.inflate(R.layout.fragment_dashboard,container,false);
            //scrollable card view
        models = new ArrayList<>();
        models.add(new Model(R.drawable.login_image, "FOOD1", "Brochure is an informative paper document (often also used for advertising) that can be folded into a template"));
        models.add(new Model(R.drawable.login_image, "FOOD2", "Sticker is a type of label: a piece of printed paper, plastic, vinyl, or other material with pressure sensitive adhesive on one side"));
        models.add(new Model(R.drawable.login_image, "FOOD3", "Poster is any piece of printed paper designed to be attached to a wall or vertical surface."));
        models.add(new Model(R.drawable.login_image, "FOOD4", "Business cards are cards bearing business information about a company or individual."));
        adapter = new Adapter(models,getContext());
        horiscroll = view.findViewById(R.id.horiscroll);
        horiscroll.setAdapter(adapter);
        horiscroll.setPadding(130, 0, 130, 0);




        //top flipper
        sliderView = view.findViewById(R.id.imageSlider);
        final SliderAdapterExample adapter = new SliderAdapterExample(getContext());
        adapter.setCount(5);
        sliderView.setSliderAdapter(adapter);
        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.FADETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.startAutoCycle();
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });
         return view;
    }

    private void init() {
        db = FirebaseFirestore.getInstance();
        sessionManagement = new SessionManagement(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            initView(view);

            //setCredits();
        //menu
        //setTodaysMenu();
        tabLayout.addTab(tabLayout.newTab().setText("Veg"));
        tabLayout.addTab(tabLayout.newTab().setText("Non Veg"));
        tabLayout.setupWithViewPager(viewPager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(),2);
        viewPager.setAdapter(viewPagerAdapter);

        //today's menu
        List<Fragment>list=new ArrayList<>();
        list.add(new LunchDashboard());
        list.add(new DinnerDashboard());
        pageadapter=new SlidePagerAdapter(getChildFragmentManager(),list);
        pager.setAdapter(pageadapter);



    }

    private void setCredits() {
        db.collection("user").document(sessionManagement.getUserDocumentId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        long credit  = (long)documentSnapshot.get("wallet");

                        dashBoardCredit.setText(String.valueOf(credit));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        pager=view.findViewById(R.id.pager);
        tabLayout = view.findViewById(R.id.tab_layout);

        dashBoardCredit = view.findViewById(R.id.dashboard_credits);
        stateProgressBar  = view.findViewById(R.id.progress_bar);
        stateProgressBar.setStateDescriptionData(descriptionData);
        //dashboardUsername = view.findViewById(R.id.dashboard_username);

       // dashboardUsername.setText(sessionManagement.getUserName());

        dashboardLunch =view.findViewById(R.id.dashboard_lunch);
        dashBoardDinner = view.findViewById(R.id.dashboard_dinner);

    }

    private void setTodaysMenu(){

        DocumentReference lunchRef = db.collection("menu").document("lunch");
        DocumentReference dinnerRef = db.collection("menu").document("dinner");

        lunchRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        StringBuilder builder = new StringBuilder();

                        Map<String,String> data = (Map<String, String>) documentSnapshot.get("lunch");

                        Iterator<String> itr  = data.keySet().iterator();

                        while (itr.hasNext()){

                            String key = itr.next();
                            String value = data.get(key);

                            String cap  = key.substring(0, 1).toUpperCase() + key.substring(1);

                            builder.append(" "+value);
                            builder.append(cap+" ");

                            if((itr.hasNext()))
                                builder.append("+");

                        }
                        dashboardLunch.setText(builder);
                        System.out.println(builder);
                    }
                });

        dinnerRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        StringBuilder builder = new StringBuilder();

                        Map<String,String> data = (Map<String, String>) documentSnapshot.get("dinner");

                        Iterator<String> itr  = data.keySet().iterator();

                        while (itr.hasNext()){

                            String key = itr.next();
                            String value = data.get(key);

                            String cap  = key.substring(0, 1).toUpperCase() + key.substring(1);

                            builder.append(" "+value);
                            builder.append(cap+" ");

                            if((itr.hasNext()))
                                builder.append("+");

                        }
                        dashBoardDinner.setText(builder);
                        System.out.println(builder);
                    }
                });
    }
}
