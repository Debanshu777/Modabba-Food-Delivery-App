package com.example.modabba.ActiveSubActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modabba.CheckoutActivity;
import com.example.modabba.R;
import com.example.modabba.Utils.SubscriptionModal;
import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private Context context;
    private List<SubscriptionModal> list;
    private String newDate = null;

    public RecyclerAdapter(Context context, List<SubscriptionModal> list){
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.active_subsc_single_item,null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {

        holder.purchaseDate.setText(list.get(position).getPurchaseDate());
        holder.startDate.setText(list.get(position).getActivationDate());
        holder.planDays.setText(String.valueOf(list.get(position).getPlanDays()) + "Days");
        final long status  = list.get(position).getStatus();
        switch ((int)status){

            case 0: holder.status.setText("Not Started");
            break;
            case 1:holder.status.setText("Active");
            break;
            case 2: holder.status.setText("Expired");
            break;

        }
        long mealCat = list.get(position).getMealCategory();

        switch ((int)mealCat){

            case 0 : holder.planType.setText("Lunch");
            break;
            case 1: holder.planType.setText("Dinner");
            break;
        }
        long foodCat = list.get(position).getFoodCategory();

        switch ((int)mealCat){

            case 0 : holder.foodType.setText("VEG");
                break;
            case 1: holder.foodType.setText("NON-VEG");
                break;
        }
        holder.changeStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(status == 0 ){

                   openDatePicker();

                   if(newDate!=null){

                       //TODO:Update Start Date
                   }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView purchaseDate,startDate,planDays,planType,foodType;
        private TextView status;
        private MaterialButton changeStartDate;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            purchaseDate = itemView.findViewById(R.id.purchaseDate);
            startDate = itemView.findViewById(R.id.startDate);
            changeStartDate = itemView.findViewById(R.id.changeStartDate);
            planDays = itemView.findViewById(R.id.planDays);
            planType  = itemView.findViewById(R.id.planType);
            status = itemView.findViewById(R.id.status);
            foodType = itemView.findViewById(R.id.foodType);
        }
    }

    private void openDatePicker(){


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month  = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date date = new Date(year-1900,month,day);

                        newDate = formateDate(date);

                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private String formateDate(Date date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        return dateFormat.format(date);
    }
}
