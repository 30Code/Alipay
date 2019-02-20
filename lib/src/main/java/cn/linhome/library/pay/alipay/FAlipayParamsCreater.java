package cn.linhome.library.pay.alipay;

import android.text.TextUtils;

import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * des:
 * Created by 30Code
 * on 2019/2/20
 */
public class FAlipayParamsCreater
{
    /**
     * app_id=2015052600090779
     * &biz_content=%7B%22
     * timeout_express%22%3A%2230m%22%2C%22
     * seller_id%22%3A%22%22%2C%22
     * product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22
     * total_amount%22%3A%220.02%22%2C%22
     * subject%22%3A%221%22%2C%22
     * body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22
     * out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D
     * &charset=utf-8
     * &method=alipay.trade.app.pay
     * &sign_type=RSA2
     * &timestamp=2016-08-15%2012%3A12%3A15
     * &version=1.0
     * &sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D
     */

    /**
     * partner="2088101568358171"
     * &seller_id="xxx@alipay.com"
     * &out_trade_no="0819145412-6177"
     * &subject="测试"
     * &body="测试测试"
     * &total_fee="0.01"
     * &notify_url="http://notify.msp.hk/notify.htm"
     * &service="mobile.securitypay.pay"
     * &payment_type="1"
     * &_input_charset="utf-8"
     * &it_b_pay="30m"
     * &sign="lBBK%2F0w5LOajrMrji7DUgEqNjIhQbidR13GovA5r3TgIbNqv231yC1NksLdw%2Ba3JnfHXoXuet6XNNHtn7VE%2BeCoRO1O%2BR1KugLrQEZMtG5jmJIe2pbjm%2F3kb%2FuGkpG%2BwYQYI51%2BhA3YBbvZHVQBYveBqK%2Bh8mUyb7GM1HxWs9k4%3D"
     * &sign_type="RSA"
     */
    public String app_id;
    public String biz_content;
    public String timeout_express;
    public String product_code;
    public String total_amount;
    private String charset = "utf-8";
    private String method = "alipay.trade.app.pay";
    public String timestamp;
    public String version;

    public String partner;
    public String seller_private_pkcs8;
    public String total_fee;
    public String notify_url;
    private String service = "mobile.securitypay.pay";
    private String payment_type = "1";
    private String _input_charset = "utf-8";
    public String it_b_pay = "30m";
    public String return_url;

    public String subject;
    public String body;
    public String out_trade_no;
    public String seller_id;
    public String sign_type = "RSA2";

    public FAlipayParamsCreater()
    {
    }

    private String wrapperQuotes(String content)
    {
        if (!TextUtils.isEmpty(content))
        {
            content = "\"" + content + "\"";
        }

        return content;
    }

    private String creatOrderInfo()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("partner=").append(this.wrapperQuotes(this.partner));
        sb.append("&seller_id=").append(this.wrapperQuotes(this.seller_id));
        sb.append("&out_trade_no=").append(this.wrapperQuotes(this.out_trade_no));
        sb.append("&subject=").append(this.wrapperQuotes(this.subject));
        sb.append("&body=").append(this.wrapperQuotes(this.body));
        sb.append("&total_fee=").append(this.wrapperQuotes(this.total_fee));
        sb.append("&notify_url=").append(this.wrapperQuotes(this.notify_url));
        sb.append("&service=").append(this.wrapperQuotes(this.service));
        sb.append("&payment_type=").append(this.wrapperQuotes(this.payment_type));
        sb.append("&_input_charset=").append(this.wrapperQuotes(this._input_charset));
        sb.append("&it_b_pay=").append(this.wrapperQuotes(this.it_b_pay));
        if (!TextUtils.isEmpty(this.return_url))
        {
            sb.append("&return_url=").append(this.wrapperQuotes(this.return_url));
        }

        return sb.toString();
    }

    public String createPayInfo() throws Exception
    {
        String payInfo = null;
        String orderInfo = creatOrderInfo();
        if (!TextUtils.isEmpty(orderInfo))
        {
            String sign = sign(orderInfo, seller_private_pkcs8);
            sign = URLEncoder.encode(sign, "UTF-8");
            StringBuilder sb = new StringBuilder();
            sb.append(orderInfo);
            sb.append("&sign=").append(wrapperQuotes(sign));
            sb.append("&sign_type=").append(wrapperQuotes(sign_type));
            payInfo = sb.toString();
        }

        return payInfo;
    }

    public static String sign(String content, String sellerPrivatePkcs8)
    {
        try
        {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(sellerPrivatePkcs8));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            signature.update(content.getBytes("UTF-8"));
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        } catch (Exception var7)
        {
            var7.printStackTrace();
            return null;
        }
    }
}
