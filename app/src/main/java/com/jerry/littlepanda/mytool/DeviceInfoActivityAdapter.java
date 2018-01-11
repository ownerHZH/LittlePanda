package com.jerry.littlepanda.mytool;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.jerry.littlepanda.R;
import com.jerry.littlepanda.domain.DeviceInfoEntity;
import com.jerry.littlepanda.domain.MeEntity;

import java.util.List;

/**
 * Created by jerry.hu on 18/10/17.
 */

public class DeviceInfoActivityAdapter extends BaseRecyclerAdapter<DeviceInfoActivityAdapter.ViewHolder> {

    private final List<DeviceInfoEntity> mValues;
    protected Context context = null;

    public DeviceInfoActivityAdapter(List<DeviceInfoEntity> mValues, Context cotext)
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
                .inflate(R.layout.activity_device_info_item, parent, false);
        return new DeviceInfoActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position, boolean isItem) {

        holder.mItem=mValues.get(position);

        holder.title.setText(holder.mItem.getTitle());
        holder.detail.setText(holder.mItem.getDetail());

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Log.e("longpress","longpress");
                ClipboardManager cm =(ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Desc", holder.mItem.getDetail());
                cm.setPrimaryClip(mClipData);
                //cm.setText(orderDetailsTvOrderNumber.getText().toString());
                Toast.makeText((Activity)context,"已复制到剪切板，快去粘贴吧~",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

    @Override
    public int getAdapterItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final static String TAG = "ViewHolder";

        public final TextView title;
        public final TextView detail;
        public final CardView cardView;

        public DeviceInfoEntity mItem;

        public ViewHolder(View view) {
            super(view);
            title =(TextView)view.findViewById(R.id.title);
            detail=(TextView)view.findViewById(R.id.detail);
            cardView=(CardView)view.findViewById(R.id.cardView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" ;
        }
    }
}
