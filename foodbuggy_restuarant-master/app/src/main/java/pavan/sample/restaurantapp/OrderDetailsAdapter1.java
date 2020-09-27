package pavan.sample.restaurantapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsAdapter1 extends RecyclerView.Adapter<OrderDetailsAdapter1.ViewHolder>{

    List<OrderDetails> places;
    private Context context;
    Bitmap bitmap;
    int com;
    //    private final OnPlaceClickListener mListener;

    public OrderDetailsAdapter1(Context context, List<OrderDetails> TempList,String com){
        this.context = context;
        this.places=TempList;
        this.com=Integer.parseInt(com);
    }

    @NonNull
    @Override
    public OrderDetailsAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);

        OrderDetailsAdapter1.ViewHolder viewHolder = new OrderDetailsAdapter1.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderDetailsAdapter1.ViewHolder holder, final int position) {
        final OrderDetails cartItems =  places.get(position);

        holder.mItem = cartItems;
//        SharedPreferences preferences11=getSharedPreferences(PREF_NAME,MODE_PRIVATE);

        holder.restname.setText(cartItems.getRestname());
        holder.orderid.setText(cartItems.getOrderid());
        holder.date.setText(cartItems.getDate());
        int tot=Integer.parseInt(cartItems.getTotalprice());
        int del=Integer.parseInt(cartItems.getDelprice());
        int taxes=Integer.parseInt(cartItems.getTaxes());
        tot=tot-del-taxes;
        holder.totalprice.setText("Total Price: ₹ "+(tot-tot*com/100));
        holder.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((OrderHistory) v.getContext()).onClickCalled1(cartItems.getOrderid());

            }
        });
        if (cartItems.getRestStatus().equals("2"))
        {
            holder.placed.setVisibility(View.VISIBLE);
        }



    }
    @Override
    public int getItemCount() {

        return places.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderid,restname,date,totalprice;
        ImageView info,placed;
        public final View mView;
        public OrderDetails mItem;
        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            restname = itemView.findViewById(R.id.restname);
            orderid = itemView.findViewById(R.id.itemname);
            date = itemView.findViewById(R.id.quantity);
            totalprice = itemView.findViewById(R.id.totalprice);
            info = itemView.findViewById(R.id.edit);
            placed=itemView.findViewById(R.id.edit11);

        }
    }
    public void filterlist(ArrayList<OrderDetails> filteredlist) {
        places = filteredlist;
        notifyDataSetChanged();
    }
}

