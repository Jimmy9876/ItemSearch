package com.example.administrator.itemsearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Administrator on 2016/11/11.
 */
public class Stock extends Activity {
    private EditText editText11;
    private EditText editText21;
    private Button button4;
    private Button button5;
    private ProgressBar progressBar2;
    private String rs;
    private static final String namespace = "http://nupt/";
    //查询相关参数
    //private static final String  serviceUrl = "http://202.119.234.4/IoTPlatform/platformws?wsdl";
    private static final String serviceUrl = "http://192.168.1.167:8080/LOGIN?wsdl";
    //定义一个Handler用来更新页面：
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001:
                    Toast.makeText(Stock.this, "增加成功！", Toast.LENGTH_SHORT).show();
                    break;
                case 0x002:
                    Toast.makeText(Stock.this, "增加失败", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.stock);

        editText11=(EditText)findViewById(R.id.edt1);
        editText21=(EditText)findViewById(R.id.edt2);
        button4=(Button)findViewById(R.id.btn4);
        button5=(Button)findViewById(R.id.btn5);
        progressBar2=(ProgressBar)findViewById(R.id.progress_bar2);

        progressBar2.setVisibility(View.GONE);

        button4.setOnClickListener(new OnButtonClick4());
        button5.setOnClickListener(new OnButtonClick5());
    }
    public class OnButtonClick4 implements View.OnClickListener {
        @Override
        public void onClick(View arg0)
        {
            String str1=editText11.getText().toString();
            String str2=editText21.getText().toString();

            if(str1.equalsIgnoreCase(""))
            {
                Toast.makeText(Stock.this,"请输入商品名",Toast.LENGTH_SHORT).show();
            }

            if(str2.equalsIgnoreCase(""))
            {
                Toast.makeText(Stock.this,"请输入增加数量",Toast.LENGTH_SHORT).show();
            }
            if (progressBar2.getVisibility()==View.GONE)
            {
                progressBar2.setVisibility(View.VISIBLE);
            }
            new Thread()
            {
                @Override
                public void run() {
                    try {
                        getPermission("name","psword");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }


    }
    public   void getPermission(String arg0,String arg1) throws Exception {

        String methodName = "increasegoods";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("arg0", editText11.getText().toString());
        request.addProperty("arg1",editText21.getText().toString());
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
        if (rs.equals("update ok"))
        {
            handler.sendEmptyMessage(0x001);
            progressBar2.setVisibility(View.GONE);
        }
        else
        {
            handler.sendEmptyMessage(0x002);
        }

    }
    public class OnButtonClick5 implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            Intent bkintent=new Intent(Stock.this,Function.class);
            startActivity(bkintent);
        }
    }

}

