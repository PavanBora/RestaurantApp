package pavan.sample.restaurantapp;

import android.content.Context;
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

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.ViewHolder>{

    List<OrderDetails> places;
    private Context context;
    Bitmap bitmap;
    int comm=0;
    //    private final OnPlaceClickListener mListener;
    public OrderDetailsAdapter(Context context, List<OrderDetails> TempList){
        this.context = context;
        this.places=TempList;
    }

    public OrderDetailsAdapter(Context context, List<OrderDetails> TempList,String comm){
        this.context = context;
        this.places=TempList;
        this.comm=Integer.parseInt(comm);
    }

    @NonNull
    @Override
    public OrderDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);

        OrderDetailsAdapter.ViewHolder viewHolder = new OrderDetailsAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final OrderDetailsAdapter.ViewHolder holder, final int position) {
        final OrderDetails cartItems =  places.get(position);

        holder.mItem = cartItems;


        holder.restname.setText(cartItems.getRestname());
        holder.orderid.setText(cartItems.getOrderid());
        holder.date.setText(cartItems.getDate());
        int tot=Integer.parseInt(cartItems.getTotalprice());
        int del=Integer.parseInt(cartItems.getDelprice());
        int taxes=Integer.parseInt(cartItems.getTaxes());
        tot=tot-del-taxes;
        holder.totalprice.setText("Total Price: ₹ "+(tot-tot*comm/100));
//        holder.totalprice.setText("Total Price: "+cartItems.getTotalprice());
        holder.info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) v.getContext()).onClickCalled1(cartItems.getOrderid());

                }
            });
//        if (cartItems.getPaystatus().equals("1"))
//        {
//            holder.paymode.setImageResource(R.drawable.pmoney);
//        }
//        else
//        {
//            holder.paymode.setImageResource(R.drawable.pphone);
//        }
//        if (cartItems.getDeliveryStatus().equals("1")) {
//            holder.delmode.setImageResource(R.drawable.pdelivered);
//            holder.delmode.setEnabled(false);
//        }
//        else {
//            holder.delmode.setImageResource(R.drawable.ptrack);
//            holder.delmode.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    ((ProfileActivity) v.getContext()).onClickCalled1(cartItems.getOrderid());
//
//                }
//            });
//        }

    }
    @Override
    public int getItemCount() {

        return places.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView orderid,restname,date,totalprice;
        ImageView info;
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

        }
    }
    public void filterlist(ArrayList<OrderDetails> filteredlist) {
        places = filteredlist;
        notifyDataSetChanged();
    }
}

