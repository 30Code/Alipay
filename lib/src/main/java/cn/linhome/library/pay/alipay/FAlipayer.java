package cn.linhome.library.pay.alipay;

import android.app.Activity;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;

import java.util.Map;

/**
 * des:
 * Created by 30Code
 * on 2019/2/20
 */
public class FAlipayer
{
    private Activity mActivity;
    private FAlipayerListener mListener;

    public FAlipayer(Activity activity)
    {
        this.mActivity = activity;
    }

    public FAlipayerListener getListener()
    {
        return this.mListener;
    }

    public void setListener(FAlipayerListener listener)
    {
        this.mListener = listener;
    }

    public void pay(String orderSpec, String sign)
    {
        this.pay(orderSpec, sign, "RSA2");
    }

    public void pay(String orderSpec, String sign, String signType)
    {
        if (TextUtils.isEmpty(orderSpec))
        {
            this.notifyFailure((Exception) null, "order_spec为空");
        } else if (TextUtils.isEmpty(sign))
        {
            this.notifyFailure((Exception) null, "sign为空");
        } else if (TextUtils.isEmpty(signType))
        {
            this.notifyFailure((Exception) null, "signType为空");
        } else
        {
            String info = orderSpec + "&sign=" + "\"" + sign + "\"" + "&" + "sign_type=" + "\"" + signType + "\"";
            pay(info);
        }
    }

    public void pay(final String payInfo)
    {
        (new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    PayTask alipay = new PayTask(FAlipayer.this.mActivity);
                    Map<String, String> result = alipay.payV2(payInfo, true);
                    notifyResult(new PayResult(result));
                } catch (Exception var3)
                {
                    FAlipayer.this.notifyFailure(var3, (String) null);
                }

            }
        })).start();
    }

    private void notifyFailure(Exception e, String msg)
    {
        if (this.mListener != null)
        {
            this.mListener.onFailure(e, msg);
        }

    }

    private void notifyResult(PayResult result)
    {
        if (this.mListener != null)
        {
            this.mListener.onResult(result);
        }

    }

    public interface FAlipayerListener
    {
        void onFailure(Exception var1, String var2);

        void onResult(PayResult var1);
    }
}
