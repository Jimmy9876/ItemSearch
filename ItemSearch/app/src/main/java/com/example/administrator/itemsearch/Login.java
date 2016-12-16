package com.example.administrator.itemsearch;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

public class Login extends Activity {
    private EditText Account1;
    private EditText Password1;
    private Button Button1;
    private ProgressBar progressBar;
    private String rs;

    //定义获取注册的命名空间,作为常量
    //private static final String namespace = "http://ws.ws_cxf_spring.njupt.com/";
    private static final String namespace = "http://nupt/";
    //查询相关参数
    //private static final String  serviceUrl = "http://202.119.234.4/IoTPlatform/platformws?wsdl";
    private static final String  serviceUrl = "http://192.168.1.167:8080/LOGIN?wsdl";
    //定义一个Handler用来更新页面：
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001:
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "验证通过！", Toast.LENGTH_SHORT).show();
                    Intent dlintent=new Intent(Login.this,Function.class);
                    startActivity(dlintent);
                    break;
                case 0x002:
                    Toast.makeText(Login.this, "验证失败请重新输入", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);


        Account1=(EditText)findViewById(R.id.account);
        Password1=(EditText)findViewById(R.id.password);
        Button1=(Button)findViewById(R.id.button1);
        progressBar=(ProgressBar)findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.GONE);

        Button1.setOnClickListener(new OnButtonClick());

    }
    public class OnButtonClick implements OnClickListener{
        @Override
        public void onClick(View arg0)
        {
            String str1=Account1.getText().toString();
            String str2=Password1.getText().toString();


            if(str1.equalsIgnoreCase(""))
            {
                Toast.makeText(Login.this,"请输入账号",Toast.LENGTH_SHORT).show();
            }

            if(str2.equalsIgnoreCase(""))
            {
                Toast.makeText(Login.this,"请输入密码",Toast.LENGTH_SHORT).show();
            }

            if(progressBar.getVisibility()==View.GONE)
            {
                progressBar.setVisibility(View.VISIBLE);
            }


            new Thread()
            {
                @Override
                public void run() {
                    try {
                        getPermission("name","pswd");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }


    }
    public   void getPermission(String arg0,String arg1) throws Exception {

        String methodName="validate";
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("arg0", Account1.getText().toString());
        request.addProperty("arg1", Password1.getText().toString());
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

        if (rs.equals("true"))
        {
            handler.sendEmptyMessage(0x001);

        }
        else
        {
            handler.sendEmptyMessage(0x002);
        }

    }

}
