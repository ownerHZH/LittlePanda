package com.jerry.littlepanda.mytool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.jerry.littlepanda.R;
import com.jerry.littlepanda.Utils.DeviceTool;
import com.jerry.littlepanda.domain.MeEntity;
import com.jerry.littlepanda.readrss.BrowserActivity;
import com.jerry.littlepanda.redenvelope.MainActivity;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by jerry.hu on 18/10/17.
 */

public class MeRecycleViewAdapter extends BaseRecyclerAdapter<MeRecycleViewAdapter.ViewHolder> {

    private final List<MeEntity> mValues;
    protected Context context = null;

    public MeRecycleViewAdapter(List<MeEntity> mValues, Context cotext)
    {
        this.mValues=mValues;
        this.context=cotext;
    }

    @Override
    public ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_me, parent, false);
        return new MeRecycleViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {

        holder.mItem=mValues.get(position);

        holder.me_item_title.setText(holder.mItem.getTitle());
        holder.me_item_image.setImageResource(holder.mItem.getImage());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DeviceTool.isFastClick())
                {
                    if(holder.me_item_title.getText().toString().trim().equals(MyToolFragment.DEVICE_INFO)){
                        //Toast.makeText(context, "ddd", Toast.LENGTH_SHORT).show();
                        DeviceInfoActivity.jumpToDeviceInfoActivity((Activity) context);
                    }else if (holder.me_item_title.getText().toString().trim().equals(MyToolFragment.JSON_INFO)){
                        JsonActivity.jumpToJsonActivity((Activity) context);
                    }else if(holder.me_item_title.getText().toString().trim().equals(MyToolFragment.RED_PACKAGE)){
                        MainActivity.jumpToRebPackageMainActivity((Activity) context);
                    }
                }

            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final static String TAG = "ViewHolder";

        public final TextView me_item_title;
        public final ImageView me_item_image;
        public final CardView cardView;
        public MeEntity mItem;

        public ViewHolder(View view) {
            super(view);
            me_item_title =(TextView)view.findViewById(R.id.me_item_title);
            me_item_image=(ImageView)view.findViewById(R.id.me_item_image);
            cardView=(CardView)view.findViewById(R.id.cardView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" ;
        }
    }
}
