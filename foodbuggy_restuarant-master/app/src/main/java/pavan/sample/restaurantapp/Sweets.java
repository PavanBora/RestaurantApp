package pavan.sample.restaurantapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.facebook.shimmer.ShimmerFrameLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import static android.content.Context.MODE_PRIVATE;

public class Sweets extends Fragment {
    public RecyclerView recyclerView;
    public Context context=getContext();
    ArrayList<Items2> items= new ArrayList<>();
    ListView list1;
    RequestQueue requestQueue,queue;
    String resid,status,rid;
    private static final String PREF_NAME = "pre";
    private static final String TAG = PackageManager.class.getName();
    private ShimmerFrameLayout mShimmerViewContainer;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment89, container, false);
        list1 = rootView.findViewById(R.id.listview);
        mShimmerViewContainer = rootView.findViewById(R.id.shimmer_view_container);
        getMovieData();
        SharedPreferences preferences11=getActivity().getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences11.edit();
        rid=preferences11.getString("resid","");
//        Toast.makeText(getContext(), rid, Toast.LENGTH_SHORT).show();
        return rootView;
    }

    private void getMovieData() {
        items.clear();
        final String HI ="https://foodbuggy.in/FoodApp/ViewSweets.php";
        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONArray array=jsonObject.getJSONArray("data");
                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        if (ob.getString("resid").equals(rid)) {
                            Items2 category = new Items2(ob.getString("itemname"), ob.getString("itemprice"),ob.getString("status"), ob.getString("id"), ob.getString("url"),ob.getString("offer") ,ob.getString("best"),ob.getString("itemDescription").toUpperCase(),ob.getString("vegStatus") );
                            items.add(category);
                        }

                    }
                    items = sortAndAddSections(items);
                    ListAdapter adapter = new ListAdapter(getContext(), items);
                    list1.setAdapter((android.widget.ListAdapter) adapter);
                    list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            Intent i=new Intent(getApplicationContext(),AmountActivity.class);
//                            i.putExtra("date",items.get(position).getDate());
//                            startActivity(i);
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                pd.hide();
                mShimmerViewContainer.stopShimmerAnimation();
                mShimmerViewContainer.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private ArrayList sortAndAddSections(ArrayList<Items2> itemList)
    {

        ArrayList<Items2> tempList = new ArrayList<>();
        //First we sort the array
        Collections.sort(itemList);

        //Loops thorugh the list and add a section before each sectioncell start
        String header = "";
//        for(int i = 0; i < itemList.size(); i++)
        for(int i=itemList.size()-1; i>=0; i-- )
        {
            //If it is the start of a new section we create a new listcell and add it to our array
            if(!(header.equals(itemList.get(i).getDes()))) {
                Items2 sectionCell = new Items2(null, null,null,null,null,null,null,itemList.get(i).getDes(),null);
                sectionCell.setToSectionHeader();
                tempList.add(sectionCell);
                header = itemList.get(i).getDes();
            }
            tempList.add(itemList.get(i));
        }

        return tempList;
    }

    public class ListAdapter extends ArrayAdapter {

        LayoutInflater inflater;
        public ListAdapter(Context context, ArrayList items) {
            super(context, 0, items);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            Items2 cell = (Items2) getItem(position);
            //If the cell is a section header we inflate the header layout
            if(cell.isSectionHeader())
            {
                v = inflater.inflate(R.layout.header, null);


                v.setClickable(false);

                TextView header = (TextView) v.findViewById(R.id.section_header);
                TextView header1=v.findViewById(R.id.section_header1);
                header.setText(cell.getDes().toUpperCase());
            }
            else
            {
                v = inflater.inflate(R.layout.row_item1, null);
                TextView iname= v.findViewById(R.id.itemname);
                TextView iprice= v.findViewById(R.id.itemprice);
                ImageView vegicon=v.findViewById(R.id.vegicon);
                final Switch aSwitch= v.findViewById(R.id.switch1);

                iname.setText(cell.getItemName());
                if (cell.getVegStatus().equals("0"))
                {
                    vegicon.setImageResource(R.drawable.veg99);
                }
                else
                {
                    vegicon.setImageResource(R.drawable.nonveg99);
                }
                iprice.setText("₹ "+cell.getItemPrice().replaceAll("[^0-9]", ""));
                if (cell.getStatus().equals("1"))
                {
                    aSwitch.setChecked(false);
                }
                else
                {
                    aSwitch.setChecked(true);
                }
                aSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new AlertDialog.Builder(getContext())
                                .setTitle("Item Availability")
                                .setCancelable(false)
                                .setMessage("Are you sure you want to change the item Status?")
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0, int arg1) {
                                        if (aSwitch.isChecked())
                                        {
                                            aSwitch.setChecked(false);
                                        }
                                        else if (!aSwitch.isChecked())
                                        {
                                            aSwitch.setChecked(true);
                                        }

                                    }
                                })
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jsonResponse = new JSONObject(response);
                                                    boolean success = jsonResponse.getBoolean("success");
                                                    if (success) {
                                                        Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();
                                                        if (aSwitch.isChecked())
                                                        {
                                                            aSwitch.setChecked(false);
                                                        }
                                                        else if (!aSwitch.isChecked())
                                                        {
                                                            aSwitch.setChecked(true);
                                                        }
                                                    } else {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                                        builder.setMessage("Item not disabled/enabled")
                                                                .setNegativeButton("Retry", null)
                                                                .create()
                                                                .show();
                                                    }
                                                }
                                                catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        };

                                        ChangeStatus registerRequest = new ChangeStatus(resid,status,"Bevarages", responseListener);
                                        queue = Volley.newRequestQueue(getContext());
                                        queue.add(registerRequest);
                                    }
                                }).create().show();
                    }
                });

            }
            return v;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmerAnimation();
        super.onPause();
    }
}