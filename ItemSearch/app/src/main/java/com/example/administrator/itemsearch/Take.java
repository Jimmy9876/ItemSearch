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
public class Take extends Activity {
    private EditText editText12;
    private EditText editText22;
    private Button button6;
    private Button button7;
    private ProgressBar progressBar3;
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
                    Toast.makeText(Take.this, "减少成功！", Toast.LENGTH_SHORT).show();
                    break;
                case 0x002:
                    Toast.makeText(Take.this, "减少失败", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.take);

        editText12=(EditText)findViewById(R.id.edt3);
        editText22=(EditText)findViewById(R.id.edt4);
        button6=(Button)findViewById(R.id.btn6);
        button7=(Button)findViewById(R.id.btn7);
        progressBar3=(ProgressBar)findViewById(R.id.progress_bar3);

        progressBar3.setVisibility(View.GONE);

        button6.setOnClickListener(new OnButtonClick6());
        button7.setOnClickListener(new OnButtonClick7());
    }
    public class OnButtonClick6 implements View.OnClickListener {
        @Override
        public void onClick(View arg0)
        {
            String str1=editText12.getText().toString();
            String str2=editText22.getText().toString();

            if(str1.equalsIgnoreCase(""))
            {
                Toast.makeText(Take.this,"请输入商品名",Toast.LENGTH_SHORT).show();
            }

            if(str2.equalsIgnoreCase(""))
            {
                Toast.makeText(Take.this,"请输入减少数量",Toast.LENGTH_SHORT).show();
            }
            if (progressBar3.getVisibility()==View.GONE)
            {
                progressBar3.setVisibility(View.VISIBLE);
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

        String methodName = "decreasegoods";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("arg0", editText12.getText().toString());
        request.addProperty("arg1",editText22.getText().toString());
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
            progressBar3.setVisibility(View.GONE);
        }
        else
        {
            handler.sendEmptyMessage(0x002);
        }

    }
    public class OnButtonClick7 implements View.OnClickListener {
        @Override
        public void onClick(View arg0) {
            Intent bkintent=new Intent(Take.this,Function.class);
            startActivity(bkintent);
        }
    }
}
