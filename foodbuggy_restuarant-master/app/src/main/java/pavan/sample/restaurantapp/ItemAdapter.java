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

import android.content.SharedPreferences;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    private List<Items> items = new ArrayList<>();
    private Context context;
    //    private final onClickListener mListener;
    private static final String PREF_NAME = "pre";

    Bitmap bitmap;
    int lastPosition = -1;
    public ItemAdapter(Context context, List<Items> Templist){
        this.context = context;
        this.items=Templist;
    }
    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pp, parent, false);

        ItemAdapter.ViewHolder viewHolder = new ItemAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemAdapter.ViewHolder holder, int position) {
        final Items item =  items.get(position);


        holder.itemName.setText(item.getItemName());
        int price= Integer.parseInt(item.getItemPrice().trim());
        price=price-price*item.getComm()/100;
        holder.itemPrice.setText("₹ "+price);
        holder.quantity.setText("Quantity: "+item.getQuantity());
        String imageUrl = item.getItemUrl();
        Glide.with(context).load(imageUrl).into(holder.itemImage);
//        if (holder.itemImage != null) {
//            /*-------------fatching image------------*/;
//            new ImageDownloaderTask(holder.itemImage).execute(imageUrl);
//        }
//        holder.itemImage.setImageBitmap(bitmap);
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
        public TextView itemName, itemPrice,quantity;
        public ImageView itemImage;
        public View mView;


        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            itemName = itemView.findViewById(R.id.product_name);
            itemPrice = itemView.findViewById(R.id.product_description);
            itemImage=itemView.findViewById(R.id.imag);
            quantity=itemView.findViewById(R.id.quantity);
        }
    }
}

