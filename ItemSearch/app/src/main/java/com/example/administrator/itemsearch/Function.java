package com.example.administrator.itemsearch;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.os.Bundle;

/**
 * Created by Administrator on 2016/11/11.
 */
public class Function extends Activity{
    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle saveInstance)
    {
        super.onCreate(saveInstance);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.function);

        button1=(Button)findViewById(R.id.btn1);
        button2=(Button)findViewById(R.id.btn2);
        button3=(Button)findViewById(R.id.btn3);

         button1.setOnClickListener(new ClickButton1());
        button2.setOnClickListener(new ClickButton2());
        button3.setOnClickListener(new ClickButton3());
    }
    public class ClickButton1 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent jhintent=new Intent(Function.this,Stock.class);
            startActivity(jhintent);
        }
    }
    public class ClickButton2 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent srhintent=new Intent(Function.this,Search.class);
            startActivity(srhintent);
        }
    }
    public class ClickButton3 implements View.OnClickListener
    {
        @Override
        public void onClick(View arg0)
        {
            Intent takeintent=new Intent(Function.this,Take.class);
            startActivity(takeintent);
        }
    }
}
