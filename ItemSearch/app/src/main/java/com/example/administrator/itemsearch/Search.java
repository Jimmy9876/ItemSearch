package com.example.administrator.itemsearch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
/**
 * Created by Administrator on 2016/10/26.
 */
public class Search  extends Activity{
    private EditText eEditText1;
    private Button Button2;
    private ProgressBar progressBar1;
    private TextView textView1;
    private Spinner mGenres;
    private ArrayAdapter<String> mAdapter;
    private String[] mGenresData=
            {
                    "-Select Genre-",
                    "学习器材","家具","衣服","零食",
                    "文具","电脑","水果","照明",
                    "五金","手机","书籍"
            };
    private String rs;
    //定义获取注册的命名空间,作为常量
    //private static final String namespace = "http://ws.ws_cxf_spring.njupt.com/";
    private static final String namespace = "http://nupt/";
    //查询相关参数
    //private static final String  serviceUrl = "http://202.119.234.4/IoTPlatform/platformws?wsdl";
    private static final String  serviceUrl = "http://172.20.10.2:8080/LOGIN?wsdl";

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001:
                    textView1.setText("结果显示：\n" + rs);
                    Toast.makeText(Search.this, "查询成功", Toast.LENGTH_SHORT).show();
                    break;
                case 0x002:
                    Toast.makeText(Search.this, "查询失败请重新输入", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        eEditText1=(EditText)findViewById(R.id.editText1);
        mGenres=(Spinner)findViewById(R.id.spinner1);
        mAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mGenresData);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGenres.setAdapter(mAdapter);
        textView1=(TextView)findViewById(R.id.textview2);
        Button2=(Button)findViewById(R.id.button2);
        progressBar1=(ProgressBar)findViewById(R.id.progress_bar1);

        eEditText1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent mAutoCompleteIntent=new Intent(Search.this,GoodsName.class);
                startActivityForResult(mAutoCompleteIntent, 0123456);
            }
        });

        progressBar1.setVisibility(View.GONE);
        Button2.setOnClickListener(new onButtonClick2());
    }
    public class onButtonClick2 implements OnClickListener{
        @Override
        public void onClick(View arg0)
        {
            if(mGenres.getSelectedItem().toString().equalsIgnoreCase("-Select Genre-"))
            {
                Toast.makeText(Search.this,"Please Select vaild Genre",Toast.LENGTH_SHORT).show();
                return;
            }
            if (progressBar1.getVisibility()==View.GONE)
            {
                progressBar1.setVisibility(View.VISIBLE);
            }
            new Thread()
            {
                @Override
                public void run() {
                    try {
                        getPermission("name");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }
    public   void getPermission(String arg0) throws Exception {

        String methodName = "getinfo";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("arg0", eEditText1.getText().toString());
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.bodyOut = request;
        //envelope.dotNet=true;
        (new MarshalBase64()).register(envelope);
        envelope.encodingStyle = "UTF-8";
        HttpTransportSE ht = new HttpTransportSE(serviceUrl);
        ht.debug = true;
        try {
            ht.call(null, envelope);
        } catch (Exception e) {
            e.printStackTrace();

        }
        SoapObject object = (SoapObject) envelope.bodyIn;
        if (envelope.bodyIn != null) {
            rs = object.getProperty(0).toString();
        }
        if (rs.toString()!=null)
        {
            handler.sendEmptyMessage(0x001);
            progressBar1.setVisibility(View.GONE);
        }
        else
        {
            handler.sendEmptyMessage(0x002);
        }

    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            if((requestCode==0123456)&&(resultCode== Activity.RESULT_OK))
            {
                Bundle myResults=data.getExtras();
                String vresult=myResults.getString("key");
                eEditText1.setText(vresult);
            }
        }
        catch(Exception e) {
            eEditText1.setText("Oops!-" + requestCode + " " + resultCode);
        }

    }
}
