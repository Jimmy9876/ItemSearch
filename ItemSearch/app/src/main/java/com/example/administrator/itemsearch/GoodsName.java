package com.example.administrator.itemsearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
/**
 * Created by Administrator on 2016/11/12.
 */
public class GoodsName extends Activity{
    private AutoCompleteTextView mGoodsName;
    private Button mFinishButton;

    static final String[] mGoodsNameData=new String[]{
            "zhuozi", "Desk", "Pen",
            "Eraser", "Terrence Malick", "Abbas Kiarostami",
            "Errol Morris", "Hayao Miyzaki", "David Cronenberg",
            "Terence Davies", "Lukas Moodysson", "Lynne Ramsay",
            "Bela Tarr", "Wong Kar-wai", "Pedro Almodovar",
            "Todd Haynes", "Quemtim Tarantino", "Tsai Ming_Liang",
            "Aki Kaurismaki", "Michael WinterBottom", "Paul Thomas Anderson",
            "Michael Haneke", "Walter Salles", "Alexander Payne",
            "Spike Jonze", "Aleksandr Sokurov", "Ang Lee",
            "Michael Moore", "Wes Anderson", "Takeshi Kitano",
            "Richard Linklater", "Gaspar Noe", "Pavel Pawlikwski",
            "David O Ressell", "Larry and Andy Wachowski",
            "Samira makhmalbaf", "Lars von Trier", "Takashi Mike",
            "David Fincher", "Gus Van Sant"
    };
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodsname);

        mGoodsName=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        mFinishButton=(Button)findViewById(R.id.btn8);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this ,R.layout.list_item,mGoodsNameData);

        mGoodsName.setAdapter(adapter);

        mFinishButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent mIntent=new Intent(GoodsName.this,Search.class);
                Bundle mBundle=new Bundle();
                mBundle.putString("key",mGoodsName.getText().toString());
                mIntent.putExtras(mBundle);
                setResult(Activity.RESULT_OK,mIntent);
                finish();
            }
        });
    }

}
