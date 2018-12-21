package com.net.yuesejiaoyou.redirect.ResolverB.interface4.translate;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/3/26.
 */

public class Translate {

    public static String translate(boolean bCn, String txt) throws Exception {
        Log.v("TT","txt: "+txt);
        String appKey = "6482188e61370af4";	//"您的appKey";
        String query = txt;
        String salt = String.valueOf(System.currentTimeMillis());

        String from = bCn?"zh-CHS":"EN";
        String to = bCn?"EN":"zh-CHS";
        String sign = md5(appKey + query + salt+ "j8Nlx7PJjQXakfUfBhptzGbdUNQ60U35");
        Map<String,String> params = new HashMap<String,String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);
        params.put("sign", sign);
        params.put("salt", salt);
        params.put("appKey", appKey);

        return requestForHttp("http://openapi.youdao.com/api", params);
    }


    public static String requestForHttp(String url,Map<String,String> requestParams) throws Exception{
//        String result = null;
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        /**HttpPost*/
//        HttpPost httpPost = new HttpPost(url);
//        System.out.println(new JSONObject(requestParams).toString());
//        List params = new ArrayList();
//        Iterator it = requestParams.entrySet().iterator();
//        while (it.hasNext()) {
//            Entry en = (Entry) it.next();
//            String key = (String) en.getKey();
//            String value = (String) en.getValue();
//            if (value != null) {
//                params.add(new BasicNameValuePair(key, value));
//            }
//        }
//        httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
//        /**HttpResponse*/
//        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
//        try{
//            HttpEntity httpEntity = httpResponse.getEntity();
//            result = EntityUtils.toString(httpEntity, "utf-8");
//            //EntityUtils.consume(httpEntity);
//            httpEntity.consumeContent();
//        }finally{
//            try{
//                if(httpResponse!=null){
//                    httpResponse.close();
//                }
//            }catch(IOException e){
//                e.printStackTrace();
//            }
//        }
//        return result;
        //--------------------------------
        Log.v("TT","++translate");
        OkHttpClient client = new OkHttpClient();


        FormBody.Builder builder = new FormBody.Builder();

        for(String key : requestParams.keySet()) {
            builder.add(key,requestParams.get(key));
        }
        FormBody body = builder.build();
        Request attentionRequest = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call attentionCall = client.newCall(attentionRequest);

        Response attentionResponse; attentionResponse = attentionCall.execute();
        ResponseBody respBody = attentionResponse.body();

        String en = respBody.string();
        Log.v("TT","en: "+en);
        return en;
    }

    /**
     * 生成32位MD5摘要
     * @param string
     * @return
     */
    public static String md5(String string) {
        if(string == null){
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};

        try{
            byte[] btInput = string.getBytes("utf-8");
            /** 获得MD5摘要算法的 MessageDigest 对象 */
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            /** 使用指定的字节更新摘要 */
            mdInst.update(btInput);
            /** 获得密文 */
            byte[] md = mdInst.digest();
            /** 把密文转换成十六进制的字符串形式 */
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        }catch(NoSuchAlgorithmException | UnsupportedEncodingException e){
            return null;
        }
    }

    /**
     * 根据api地址和参数生成请求URL
     * @param url
     * @param params
     * @return
     */
    public static String getUrlWithQueryString(String url, Map params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
//        if (url.contains("?")) {
//            builder.append("&");
//        } else {
//            builder.append("?");
//        }
//
//        int i = 0;
//        for (String key : params.keySet()) {
//            String value = (String) params.get(key);
//            if (value == null) { // 过滤空的key
//                continue;
//            }
//
//            if (i != 0) {
//                builder.append('&');
//            }
//
//            builder.append(key);
//            builder.append('=');
//            builder.append(encode(value));
//
//            i++;
//        }

        return builder.toString();
    }
    /**
     * 进行URL编码
     * @param input
     * @return
     */
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }
}