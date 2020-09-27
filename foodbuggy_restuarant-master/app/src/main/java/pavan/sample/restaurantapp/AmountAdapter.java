package pavan.sample.restaurantapp;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class AmountAdapter extends RecyclerView.Adapter<AmountAdapter.ViewHolder>{
    private static final String PREF_NAME = "pre";
    private List<ItemModel> items = new ArrayList<>();
    private Context context;
    //    private final onClickListener mListener;
    Bitmap bitmap;
    int lastPosition = -1;
    public AmountAdapter(Context context, List<ItemModel> Templist){
        this.context = context;
        this.items=Templist;
    }
    @NonNull
    @Override
    public AmountAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);

        AmountAdapter.ViewHolder viewHolder = new AmountAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AmountAdapter.ViewHolder holder, int position) {
        final ItemModel item =  items.get(position);
        int comm=0;
        comm=Integer.parseInt(holder.preferences11.getString("comm",""));
        holder.orderid.setText(item.getItemTime());
        int money=Integer.valueOf(item.getItemSysDia())-Integer.valueOf(item.getItemPulse())-Integer.valueOf(item.getTax());
        money=money-money*comm/100;
        holder.price.setText("₹ "+money);

        if (position>lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.up_from_bottom);
            holder.itemView.startAnimation(animation);
            lastPosition = position;
        }
    }
    @Override
    public int getItemCount() {

        return items.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView orderid,price;
        public View mView;
        SharedPreferences preferences11;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            orderid = itemView.findViewById(R.id.time_time);
            price = itemView.findViewById(R.id.tv_item_sysdia);
             preferences11=context.getSharedPreferences(PREF_NAME,MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences11.edit();
        }
    }
}

