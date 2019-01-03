package com.net.yuesejiaoyou.redirect.ResolverB.interface4.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import net.sf.json.JSONObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import sun.misc.BASE64Encoder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.net.yuesejiaoyou.redirect.ResolverB.uiface.NoSkinActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;
//import com.aliyun.demo.recorder.AliyunVideoRecorder;
//import com.example.tongchengqo.uiface.NoSkinActivity;

/**
 * Created by Administrator on 2017/11/10.
 */

public class DianboUtil {
    //账号AK信息请填写(必选)
    private static String access_key_id = "LTAIMw9n5RwSGy7V";
    //账号AK信息请填写(必选)
    private static String access_key_secret = "ttaQTSsdubE5ydnA0YsjfdQGhDt1vU";
    //STS临时授权方式访问时该参数为必选，使用主账号AK和RAM子账号AK不需要填写
    private static String security_token = "";
    //以下参数不需要修改
    private final static String VOD_DOMAIN = "http://vod.cn-shanghai.aliyuncs.com";
    private final static String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private final static String HTTP_METHOD = "POST";
    private final static String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    private final static String UTF_8 = "utf-8";


    public static void uploadVideo(String title, Callback callback) {
        // 接口私有参数列表, 不同API请替换相应参数
        Map<String, String> privateParams = new HashMap<>();

        // API名称
        privateParams.put("Action", "CreateUploadVideo");
        privateParams.put("Title", "标题0");
        privateParams.put("FileName", "1510130201331.mp4");
        // 接口公共参数
        Map<String, String> publicParams = new HashMap<>();
        publicParams.put("Format", "JSON");
        publicParams.put("Version", "2017-03-21");
        publicParams.put("AccessKeyId", access_key_id);
        publicParams.put("SignatureMethod", "HMAC-SHA1");
        //publicParams.put("Timestamp", generateTimestamp());
        publicParams.put("SignatureVersion", "1.0");
        publicParams.put("SignatureNonce", generateRandom());
        if (security_token != null && security_token.length() > 0) {
            publicParams.put("SecurityToken", security_token);
        }

        // 生成URL
        String URL = generateOpenAPIURL(publicParams, privateParams);

        // 发起网络请求
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(URL).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public static void uploadVideo(DialogCallback callback) {
        // 接口私有参数列表, 不同API请替换相应参数
        Map<String, String> privateParams = new HashMap<>();

        // API名称
        privateParams.put("Action", "CreateUploadVideo");
        privateParams.put("Title", "标题0");
        privateParams.put("FileName", "1510130201331.mp4");
        // 接口公共参数
        Map<String, String> publicParams = new HashMap<>();
        publicParams.put("Format", "JSON");
        publicParams.put("Version", "2017-03-21");
        publicParams.put("AccessKeyId", access_key_id);
        publicParams.put("SignatureMethod", "HMAC-SHA1");
        //publicParams.put("Timestamp", generateTimestamp());
        publicParams.put("SignatureVersion", "1.0");
        publicParams.put("SignatureNonce", generateRandom());
        if (security_token != null && security_token.length() > 0) {
            publicParams.put("SecurityToken", security_token);
        }

        OkHttpUtils
                .post()
                .url(generateOpenAPIURL(publicParams, privateParams))
                .build()
                .execute(callback);
    }

    /**
     * 根据videoid播放视频
     * @param ctxt
     * @param videoid
     */
    public static void playVideo(final Context ctxt, final String videoid) {
        // 生成URL
        Map<String, String> privateParams = generatePrivateParamters(videoid);
        Map<String, String> publicParams = generatePublicParamters();
        String URL = generateOpenAPIURL(publicParams, privateParams);
        Log.v("TCQO","URL:"+URL);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(URL).post(body).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONObject jsonObj = JSONObject.fromObject(json);
                Log.v("TCQO","json:"+json);

                // 跳转页面
                Intent intent = new Intent();
                intent.setClass(ctxt, NoSkinActivity.class);
                intent.putExtra("type", "authInfo");
                intent.putExtra("vid", videoid);
                intent.putExtra("authinfo", jsonObj.getString("PlayAuth"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                ctxt.startActivity(intent);
            }
        });
    }

  /*  public static void playVideo1(final Context ctxt, final String videoid,final String id,final String user_id,final String photo,final String nickname,
                                  final String video_photo,final String is_zan,final String is_guanzhu) {
        // 生成URL
        Map<String, String> privateParams = generatePrivateParamters(videoid);
        Map<String, String> publicParams = generatePublicParamters();
        String URL = generateOpenAPIURL(publicParams, privateParams);
        Log.v("TCQO","URL:"+URL);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        Request request = new Request.Builder().url(URL).post(body).build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                JSONObject jsonObj = JSONObject.fromObject(json);
                Log.v("TCQO","json:"+json);

                // 跳转页面
                Intent intent = new Intent();
                intent.setClass(ctxt, NoSkinActivity_01150.class);
                intent.putExtra("type", "authInfo");
                intent.putExtra("vid", videoid);
                intent.putExtra("id", id);
                intent.putExtra("user_id", user_id);
                intent.putExtra("photo", photo);
                intent.putExtra("nickname", nickname);
                intent.putExtra("video_photo", video_photo);
                intent.putExtra("is_zan", is_zan);
                intent.putExtra("is_guanzhu", is_guanzhu);

                intent.putExtra("authinfo", jsonObj.getString("PlayAuth"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                ctxt.startActivity(intent);
            }
        });
    }*/
    /**
     * 生成视频点播OpenAPI私有参数
     * 不同API需要修改此方法中的参数
     *
     * @return
     */
    private static Map<String, String> generatePrivateParamters(String videoid) {
        // 接口私有参数列表, 不同API请替换相应参数
        Map<String, String> privateParams = new HashMap<>();
        // 视频ID
        //privateParams.put("VideoId", "5aed81b74ba84920be578cdfe004af4b");
        // API名称
//        privateParams.put("Action", "CreateUploadVideo");
//        privateParams.put("Title", "标题0");
//        privateParams.put("FileName", "1510130201331.mp4");

        privateParams.put("Action", "GetVideoPlayAuth");
        privateParams.put("VideoId", videoid);

        //privateParams.put("Action", "GetVideoList");

        return privateParams;
    }

    /**
     * 生成视频点播OpenAPI公共参数
     * 不需要修改
     *
     * @return
     */
    private static Map<String, String> generatePublicParamters() {
        Map<String, String> publicParams = new HashMap<>();
        publicParams.put("Format", "JSON");
        publicParams.put("Version", "2017-03-21");
        publicParams.put("AccessKeyId", access_key_id);
        publicParams.put("SignatureMethod", "HMAC-SHA1");
        //publicParams.put("Timestamp", generateTimestamp());
        publicParams.put("SignatureVersion", "1.0");
        publicParams.put("SignatureNonce", generateRandom());
        if (security_token != null && security_token.length() > 0) {
            publicParams.put("SecurityToken", security_token);
        }
        return publicParams;
    }

    /**
     * 生成OpenAPI地址
     * @param privateParams
     * @return
     * @throws Exception
     */
    private static String generateOpenAPIURL(Map<String, String> publicParams, Map<String, String> privateParams) {
        return generateURL(VOD_DOMAIN, HTTP_METHOD, publicParams, privateParams);
    }

    /**
     * @param domain        请求地址
     * @param httpMethod    HTTP请求方式GET，POST等
     * @param publicParams  公共参数
     * @param privateParams 接口的私有参数
     * @return 最后的url
     */
    private static String generateURL(String domain, String httpMethod, Map<String, String> publicParams, Map<String, String> privateParams) {
        List<String> allEncodeParams = getAllParams(publicParams, privateParams);
        String cqsString = getCQS(allEncodeParams);
        out("CanonicalizedQueryString = " + cqsString);
        String stringToSign = httpMethod + "&" + percentEncode("/") + "&" + percentEncode(cqsString);
        out("StringtoSign = " + stringToSign);
        String signature = hmacSHA1Signature(access_key_secret, stringToSign);
        out("Signature = " + signature);
        return domain + "?" + cqsString + "&" + percentEncode("Signature") + "=" + percentEncode(signature);
    }

    private static List<String> getAllParams(Map<String, String> publicParams, Map<String, String> privateParams) {
        List<String> encodeParams = new ArrayList<String>();
        if (publicParams != null) {
            for (String key : publicParams.keySet()) {
                String value = publicParams.get(key);
                //将参数和值都urlEncode一下。
                String encodeKey = percentEncode(key);
                String encodeVal = percentEncode(value);
                encodeParams.add(encodeKey + "=" + encodeVal);
            }
        }
        if (privateParams != null) {
            for (String key : privateParams.keySet()) {
                String value = privateParams.get(key);
                //将参数和值都urlEncode一下。
                String encodeKey = percentEncode(key);
                String encodeVal = percentEncode(value);
                encodeParams.add(encodeKey + "=" + encodeVal);
            }
        }
        return encodeParams;
    }

    /**
     * 参数urlEncode
     *
     * @param value
     * @return
     */
    private static String percentEncode(String value) {
        try {
            String urlEncodeOrignStr = URLEncoder.encode(value, "UTF-8");
            String plusReplaced = urlEncodeOrignStr.replace("+", "%20");
            String starReplaced = plusReplaced.replace("*", "%2A");
            String waveReplaced = starReplaced.replace("%7E", "~");
            return waveReplaced;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取CQS 的字符串
     *
     * @param allParams
     * @return
     */
    private static String getCQS(List<String> allParams) {
        ParamsComparator paramsComparator = new ParamsComparator();
        Collections.sort(allParams, paramsComparator);
        String cqString = "";
        for (int i = 0; i < allParams.size(); i++) {
            cqString += allParams.get(i);
            if (i != allParams.size() - 1) {
                cqString += "&";
            }
        }

        return cqString;
    }

    private static class ParamsComparator implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            return lhs.compareTo(rhs);
        }
    }

    private static String hmacSHA1Signature(String accessKeySecret, String stringtoSign) {
        try {
            String key = accessKeySecret + "&";
            try {
                SecretKeySpec signKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
                Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
                mac.init(signKey);
                byte[] rawHmac = mac.doFinal(stringtoSign.getBytes());
                //按照Base64 编码规则把上面的 HMAC 值编码成字符串，即得到签名值（Signature）
                return new String(new BASE64Encoder().encode(rawHmac));
            } catch (Exception e) {
                throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
            }
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 生成随机数
     *
     * @return
     */
    private static String generateRandom() {
        String signatureNonce = UUID.randomUUID().toString();
        return signatureNonce;
    }

    /**
     * 生成当前UTC时间戳
     *
     * @return
     */
    public static String generateTimestamp() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat(ISO8601_DATE_FORMAT);
        df.setTimeZone(new SimpleTimeZone(0, "GMT"));
        return df.format(date);
    }

    private static String httpGet(String url) throws IOException {
//    	/*
//         * Read and covert a inputStream to a String.
//         * Referred this:
//         * http://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
//         */
//        out("URL = " +  url);
//        @SuppressWarnings("resource")
//        Scanner s = new Scanner(new URL(url).openStream(), UTF_8).useDelimiter("\\A");
//        try {
//            String resposne = s.hasNext() ? s.next() : "true";
//            out("Response = " + resposne);
//            return resposne;
//        } finally {
//            s.close();
//        }
        return "";
    }

    private static void out(String newLine) {
        //LOG.log(Level.INFO, newLine);
    }
}
