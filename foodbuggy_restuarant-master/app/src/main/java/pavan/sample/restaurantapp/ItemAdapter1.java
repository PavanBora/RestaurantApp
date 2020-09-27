package pavan.sample.restaurantapp;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter1 extends RecyclerView.Adapter<ItemAdapter1.ViewHolder>{

    private List<Items1> items = new ArrayList<>();
    private Context context;
    //    private final onClickListener mListener;
    Bitmap bitmap;
    int lastPosition = -1;
    public ItemAdapter1(Context context, List<Items1> Templist){
        this.context = context;
        this.items=Templist;
    }
    @NonNull
    @Override
    public ItemAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);

        ItemAdapter1.ViewHolder viewHolder = new ItemAdapter1.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemAdapter1.ViewHolder holder, int position) {
        final Items1 item =  items.get(position);

        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice());
        if (item.getStatus().equals("0"))
        {
            holder.aSwitch.setChecked(true);
//            holder.itemPrice.setText("Enabled");
        }
        else
        {
            holder.aSwitch.setChecked(false);
//            holder.itemPrice.setText("Disabled");
        }

        String imageUrl = item.getItemUrl();
        Glide.with(context).load(imageUrl).into(holder.itemImage);
//        if (holder.itemImage != null) {
//            /*-------------fatching image------------*/;
//            new ImageDownloaderTask(holder.itemImage).execute(imageUrl);
//        }
//        holder.itemImage.setImageBitmap(bitmap);
//        if (position>lastPosition)
//        {
//            Animation animation = AnimationUtils.loadAnimation(context,
//                    R.anim.up_from_bottom);
//            holder.itemView.startAnimation(animation);
//            lastPosition = position;
//        }
    }
    @Override
    public int getItemCount() {

        return items.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName, itemPrice,quantity;
        public ImageView itemImage;
        public Switch aSwitch;
        public View mView;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            aSwitch=itemView.findViewById(R.id.switch1);
            itemName = itemView.findViewById(R.id.product_name);
            itemPrice = itemView.findViewById(R.id.product_description);
            itemImage=itemView.findViewById(R.id.imag);
            quantity=itemView.findViewById(R.id.quantity);
        }
    }
}

