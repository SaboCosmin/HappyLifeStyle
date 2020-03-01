package com.hls.happylifestyle.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hls.happylifestyle.Classes.Food;
import com.hls.happylifestyle.R;

import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Food> {

    private Context mContext;
    private int mResurce;

    public FoodListAdapter(Context context, int resource, List<Food> foods) {
        super(context, resource, foods);
        mContext = context;
        mResurce = resource;
    }

    static class ViewHolder{
        TextView name;
        TextView calories;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final ViewHolder holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResurce, parent, false);

            holder.name = convertView.findViewById(R.id.nameTextViewElement);
            holder.calories = convertView.findViewById(R.id.caloriesTextViewElement);
            convertView.setTag(holder);
        }

        final ViewHolder holder = (ViewHolder) convertView.getTag();

        holder.name.setText(getItem(position).getName());
        holder.calories.setText(String.valueOf(getItem(position).getCalories()));

        return convertView;
    }
}
