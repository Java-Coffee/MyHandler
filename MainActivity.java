package com.example.dhw.myhandler;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.show.api.ShowApiRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Serializable{
    protected Handler mHandler;
    private List<ItemBean> itemBeanList = new ArrayList<>();
    private MyNewAdapter adapter;
private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x123){
                    ItemBean itemBean = (ItemBean) msg.getData().getSerializable("Item");
                    Log.i("item",itemBean.ItemContent);
                    Log.i("item",itemBean.ItemTitle);
                    itemBeanList.add(itemBean);
                    Log.i("Bean",itemBeanList.get(0).ItemTitle);
                    adapter.notifyDataSetChanged();
                }
                super.handleMessage(msg);
            }
        };
      //  listView = (ListView) findViewById(R.id.listview);
        //ArrayAdapter adapter = new ArrayAdapter<List<ItemBean>>(this,itemBeanList);
       // MyAdapter adapter = new MyAdapter(this,itemBeanList);
      //  listView.setAdapter(adapter);
        final TextView txt = (TextView) this.findViewById(R.id.textView1);
        Button myBtn = (Button) this.findViewById(R.id.button1);
        myBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new Thread(){
                    //在新线程中发送网络请求
                    public void run() {
                        JSONObject jsonObject;
                        JSONObject singleJsonObject;
                        ItemBean itemBean = null;
                        String appid="23548";//要替换成自己的
                        String secret="949bb47505ac4b0ca362989f2fdaba7b";//要替换成自己的
                        final String res=new ShowApiRequest( "http://route.showapi.com/213-4", appid, secret)
                                .addTextPara("topid", " ")
                                .post();

                        System.out.println(res);
                        try {
                            jsonObject = new JSONObject(res);
                            JSONObject AllJsonData = jsonObject.getJSONObject("showapi_res_body").getJSONObject("pagebean");
                            Log.i("myapp", String.valueOf(AllJsonData));
                            JSONArray jsonArray = AllJsonData.getJSONArray("songlist");
                            //Log.i("myapp1", String.valueOf(jsonArray));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                singleJsonObject = jsonArray.getJSONObject(i);
                                Log.i("myapp1", String.valueOf(singleJsonObject));
                                itemBean = new ItemBean();
                                itemBean.ItemImageID = singleJsonObject.getString("albumpic_small");
                                itemBean.ItemTitle = singleJsonObject.getString("songname");
                                itemBean.ItemContent = singleJsonObject.getString("singername");
                                itemBean.MusicUrl = singleJsonObject.getString("url");
                                Message message = new Message();
                                message.what = 0x123;
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Item", (Serializable) itemBean);
                                message.setData(bundle);
                                mHandler.sendMessage(message);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();


            }
        });
        listView = (ListView) findViewById(R.id.listview1);

        adapter = new MyNewAdapter(this,itemBeanList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String musicUrl = itemBeanList.get(i).MusicUrl;
            }
        });
    }
    }
