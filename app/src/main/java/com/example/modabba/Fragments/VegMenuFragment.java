package com.example.modabba.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.diegodobelo.expandingview.ExpandingItem;
import com.diegodobelo.expandingview.ExpandingList;
import com.example.modabba.Menu;
import com.example.modabba.R;

import java.util.ArrayList;
import java.util.Map;

public class VegMenuFragment extends Fragment {

    private ExpandingList expandingList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_veg_menu, container, false);
        expandingList = view.findViewById(R.id.expanding_list_main_veg);
        createItems();
        return view;
    }
    private void createItems() {
        Menu menu = new Menu();
        int[] color=new int[]{R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary,
                R.color.colorPrimary,
                R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary};
        ArrayList<String> val=new ArrayList<>();
        Map<String, ArrayList<String>> map;
        int c=0;
        map = menu.getMenu_sub_item_veg_lunch();
        for (Map.Entry<String, ArrayList<String>> veg : map.entrySet()) {
            String key = veg.getKey();
            ArrayList<String> values = veg.getValue();
            val.addAll(values);
            addItem(key,val,color[c], R.drawable.arrow_down);
            val.clear();
            c=c+1;
        }

    }
    private void addItem(String title, ArrayList<String> subItems, int colorRes, int iconRes) {
        final ExpandingItem item = expandingList.createNewItem(R.layout.expandable_layout);
        if (item != null) {
            item.setIndicatorColorRes(colorRes);
            item.setIndicatorIconRes(iconRes);
            ((TextView) item.findViewById(R.id.title)).setText(title);
            item.createSubItems(subItems.size());
            for (int i = 0; i < item.getSubItemsCount(); i++) {
                final View view = item.getSubItemView(i);
                configureSubItem(item, view, subItems.get(i));
            }
        }
    }
    private void configureSubItem(final ExpandingItem item, final View view, String subTitle) {
        ((TextView)view.findViewById(R.id.sub_title)).setText(subTitle);
    }
}
