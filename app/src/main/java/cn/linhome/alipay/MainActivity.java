package cn.linhome.alipay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.linhome.library.pay.alipay.FAlipayer;
import cn.linhome.library.pay.alipay.PayResult;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FAlipayer alipayer = new FAlipayer(this);
        alipayer.setListener(new FAlipayer.FAlipayerListener()
        {
            @Override
            public void onFailure(Exception e, String msg)
            {

            }

            @Override
            public void onResult(PayResult var1)
            {

            }
        });
        alipayer.pay("");
    }
}
