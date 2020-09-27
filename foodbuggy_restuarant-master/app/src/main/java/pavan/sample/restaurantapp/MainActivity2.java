package pavan.sample.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class MainActivity2 extends AppCompatActivity {
    private static  String HI ="https://foodbuggy.in/FoodApp/delivery/ViewOrders.php" ;
    ArrayList<ItemModel> items= new ArrayList<>();
    ListView list;
    String Restname;
    SpotsDialog progressDialog;
    private static final String PREF_NAME = "pre";
    int comm;
//    String[] bankNames={"Today","Yesterday","One Week","All Time"};
    String[] bankNames={"All Time","One Week","Yesterday","Today"};

    //    ArrayList<ItemModel> items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Review Orders");
//        ArrayList<ItemModel> itemsList = new ArrayList<>();
         list = (ListView) findViewById(R.id.listview);


        Spinner spin = (Spinner) findViewById(R.id.spinner2);
//        spin.setOnItemSelectedListener(this);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
//                Toast.makeText(getApplicationContext(), bankNames[position], Toast.LENGTH_LONG).show();
                items.clear();
                items=sortAndAddSections(items);
                ListAdapter adapter = new ListAdapter(MainActivity2.this, items);
                list.setAdapter((android.widget.ListAdapter) adapter);



                if(position==3){
                    HI ="https://foodbuggy.in/FoodApp/delivery/ViewOrders.php?opt=0" ;
                    getMovieData(HI);
                }
                else if(position==2){
                    HI ="https://foodbuggy.in/FoodApp/delivery/ViewOrders.php?opt=1" ;

                    getMovieData(HI);
                }
                else if(position==1){

                    HI ="https://foodbuggy.in/FoodApp/delivery/ViewOrders.php?opt=2" ;
                    getMovieData(HI);

                }
                else{
                    HI ="https://foodbuggy.in/FoodApp/delivery/ViewOrders.php" ;
                    getMovieData(HI);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,bankNames);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);

//        getMovieData(HI);
        progressDialog = new SpotsDialog(MainActivity2.this, R.style.Custom);
        progressDialog.show();
        SharedPreferences preferences11=getSharedPreferences(PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences11.edit();
        Restname=preferences11.getString("resname","");
        comm=Integer.parseInt(preferences11.getString("comm",""));

        //        itemsList = sortAndAddSections(getMovieData());
//        ListAdapter adapter = new ListAdapter(MainActivity2.this, itemsList);
//        list.setAdapter((android.widget.ListAdapter) adapter);
    }




    private ArrayList sortAndAddSections(ArrayList<ItemModel> itemList)
    {

        ArrayList<ItemModel> tempList = new ArrayList<>();
        //First we sort the array
        Collections.sort(itemList);

        //Loops thorugh the list and add a section before each sectioncell start
        String header = "";
//        for(int i = 0; i < itemList.size(); i++)
        for(int i=itemList.size()-1; i>=0; i-- )
        {
            //If it is the start of a new section we create a new listcell and add it to our array
            if(!(header.equals(itemList.get(i).getDate()))) {
                ItemModel sectionCell = new ItemModel(null, null,null,itemList.get(i).getDate(),null);
                sectionCell.setToSectionHeader();
                tempList.add(sectionCell);
                header = itemList.get(i).getDate();
            }
            tempList.add(itemList.get(i));
        }

        return tempList;
    }


    public class ListAdapter extends ArrayAdapter {

        LayoutInflater inflater;
        public ListAdapter(MainActivity2 context, ArrayList items) {
            super(context, 0, items);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            ItemModel cell = (ItemModel) getItem(position);


            //If the cell is a section header we inflate the header layout
            if(cell.isSectionHeader())
            {
                v = inflater.inflate(R.layout.section_header, null);


                v.setClickable(false);

                TextView header = (TextView) v.findViewById(R.id.section_header);
                TextView header1=v.findViewById(R.id.section_header1);
                header.setText(cell.getDate());
            }
            else
            {
                v = inflater.inflate(R.layout.row_item, null);
                TextView time_time = (TextView) v.findViewById(R.id.time_time);
                TextView tv_item_sysdia = (TextView) v.findViewById(R.id.tv_item_sysdia);

                TextView tv_item_plus = (TextView) v.findViewById(R.id.tv_item_plus);

                int price=Integer.valueOf(cell.getItemSysDia())-Integer.valueOf(cell.getItemPulse())-Integer.parseInt(cell.getTax());
                time_time.setText(cell.getItemTime());
                tv_item_sysdia.setText("₹ "+String.valueOf(price-price*comm/100));
                tv_item_plus.setText(cell.getItemPulse());


            }
            return v;
        }
    }


    private ArrayList<ItemModel> getItems(){

        ArrayList<ItemModel> items = new ArrayList<>();
        items.add(new ItemModel("10.30", "120/10","80","Tue,31 Oct 17",null));
        items.add(new ItemModel("10.30", "142/95","95","Tue,31 Oct 17",null));
        items.add(new ItemModel("15.30", "120/95","200","Tue,31 Oct 17",null));
        items.add(new ItemModel("20.30", "120/10","80","Tue,29 Oct 17",null));
        items.add(new ItemModel("10.30", "120/10","50","Tue,29 Oct 17",null));

        items.add(new ItemModel("10.30", "140/10","80","Tue,28 Oct 17",null));
        items.add(new ItemModel("10.30", "30/75","40","Tue,28 Oct 17",null));
        items.add(new ItemModel("10.30", "150/80","70","Tue,28 Oct 17",null));

        return  items;
    }
    private void getMovieData(String HI) {

        StringRequest stringRequest=new StringRequest(Request.Method.GET, HI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String date22;
                int count=0;
                try {
                    final JSONObject jsonObject=new JSONObject(response);
                    final JSONArray array=jsonObject.getJSONArray("data");
//                    Toast.makeText(getApplicationContext(), array+"", Toast.LENGTH_LONG).show();

                    for (int i=0; i<array.length(); i++ ){
                        JSONObject ob=array.getJSONObject(i);
                        if (ob.getString("restStatus").equals("2")&& ob.getString("restname").equals(Restname)) {
                            ItemModel product = new ItemModel(ob.getString("orderid"), ob.getString("totalprice"),ob.getString("delCharge") , ob.getString("date1"),ob.getString("taxes"));
                            items.add(product);
                            count++;
                        }
                    }
                    items = sortAndAddSections(items);
                    ListAdapter adapter = new ListAdapter(MainActivity2.this, items);
                    list.setAdapter((android.widget.ListAdapter) adapter);
                    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                                Toast.makeText(MainActivity2.this,items.get(position).getDate(), Toast.LENGTH_SHORT).show();

                            Intent i=new Intent(getApplicationContext(),AmountActivity.class);
                            i.putExtra("date",items.get(position).getDate());
                            startActivity(i);
                        }
                    });

                    if(count==0){
                        Toast.makeText(getApplicationContext(), "No orders", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}