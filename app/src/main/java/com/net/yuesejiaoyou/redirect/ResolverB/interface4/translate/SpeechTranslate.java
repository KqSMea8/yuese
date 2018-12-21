package com.net.yuesejiaoyou.redirect.ResolverB.interface4.translate;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.sunflower.FlowerCollector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2018/3/27.
 */

public class SpeechTranslate {

    public static final int INPUT_LANGUAGE_CHINESE = 1;
    public static final int INPUT_LANGUAGE_ENGLISH = 2;
    public static final int OUTPUT_LANGUAGE_CHINESE = 3;
    public static final int OUTPUT_LANGUAGE_ENGLISH = 4;

    private static SpeechTranslate instance;
    private static Context applicationContext;




    //-------------------------------------------------------------------
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    private boolean mTranslateEnable = false;

    private TranslateListener transListener;

    private int curInputLanguage;

    private String curResultString;
    private String curTransResultString;

    private SpeechTranslate() {
        mIat = SpeechRecognizer.createRecognizer(applicationContext, mInitListener);
    }

    public static SpeechTranslate getInstance() {
        if(instance == null) {
            instance = new SpeechTranslate();
        }

        return instance;
    }

    public static void init(Context mCtxt) {
        applicationContext = mCtxt;
        SpeechUtility.createUtility(mCtxt, "appid=5ab753b1");
    }

    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                //showTip("初始化失败，错误码：" + code);
            }
        }
    };

    public void startListening(int languageType, TranslateListener listener) {

        // 移动数据分析，收集开始听写事件
        FlowerCollector.onEvent(applicationContext, "iat_recognize");
        mIatResults.clear();

        curInputLanguage = languageType;

        switch(languageType) {
            case INPUT_LANGUAGE_CHINESE:
                // 设置参数
                setParam("mandarin");
                break;
            case INPUT_LANGUAGE_ENGLISH:
                // 设置参数
                setParam("en_us");
                break;
        }

        int ret = mIat.startListening(mRecognizerListener);

        if(listener != null) {
            if(ret != ErrorCode.SUCCESS) {
                listener.onError(ret,"听写失败");
            }
        }

        transListener = listener;
    }

    /**
     * 参数设置
     *
     * @return
     */
    public void setParam(String lag) {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

//        this.mTranslateEnable = mSharedPreferences.getBoolean( this.getString(R.string.pref_key_translate), false );
        if( mTranslateEnable ){
            //Log.i( TAG, "translate enable" );
            mIat.setParameter( SpeechConstant.ASR_SCH, "1" );
            mIat.setParameter( SpeechConstant.ADD_CAP, "translate" );
            mIat.setParameter( SpeechConstant.TRS_SRC, "its" );
        }

//        String lag = mSharedPreferences.getString("iat_language_preference","mandarin");
        //String lag = "mandarin";
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);

            if( mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "en" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "cn" );
            }
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);

            if( mTranslateEnable ){
                mIat.setParameter( SpeechConstant.ORI_LANG, "cn" );
                mIat.setParameter( SpeechConstant.TRANS_LANG, "en" );
            }
        }
        //此处用于设置dialog中不显示错误码信息
        //mIat.setParameter("view_tips_plain","false");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, "3600000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS,  "3600000");

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, "1");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }

    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
//            showTip("开始说话");
//            LogDetect.send(LogDetect.DataType.noType, "RecognizerListener.onBeginOfSpeech(): ","开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            if(mTranslateEnable && error.getErrorCode() == 14002) {
//                showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
            } else {
//                showTip(error.getPlainDescription(true));
            }
//            LogDetect.send(LogDetect.DataType.noType, "RecognizerListener.onError(): ",error.getErrorDescription()+"("+error.getErrorCode()+")");

            if(transListener != null) {
                transListener.onError(error.getErrorCode(),error.getErrorDescription());
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
//            showTip("结束说话");


        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
//            Log.d(TAG, results.getResultString());
            if( mTranslateEnable ){
                //printTransResult( results );
            }else{
                printResult(results);
            }

            if (isLast) {
                // TODO 最后的结果

                if(transListener != null) {
                    if(curInputLanguage == INPUT_LANGUAGE_CHINESE) {
                        transListener.onResult(OUTPUT_LANGUAGE_CHINESE, curResultString);
                    } else if(curInputLanguage == INPUT_LANGUAGE_ENGLISH) {
                        transListener.onResult(OUTPUT_LANGUAGE_ENGLISH, curResultString);
                    }
                }

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            String transJson = Translate.translate(curInputLanguage==INPUT_LANGUAGE_CHINESE,curResultString);
                            JSONObject jsonObj = new JSONObject(transJson);
                            String str = jsonObj.get("translation").toString();
                            String transStr = (str != null && str.length() > 4)?str.substring(2,str.length()-2):str;
                            if(transListener != null) {
                                if(curInputLanguage == INPUT_LANGUAGE_CHINESE) {
                                    transListener.onTransResult(OUTPUT_LANGUAGE_ENGLISH, transStr);
                                } else if(curInputLanguage == INPUT_LANGUAGE_ENGLISH) {
                                    transListener.onTransResult(OUTPUT_LANGUAGE_CHINESE, transStr);
                                }
                            }

                            curTransResultString = transStr;
                        } catch (Exception e) {
                            e.printStackTrace();
                            if(transListener != null) {
                                transListener.onError(-1,e.toString());
                            }
                        }
                    }
                }).start();
            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
//            showTip("当前正在说话，音量大小：" + volume);
//            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        curResultString = resultBuffer.toString();

//        if(transListener != null) {
//            if(curInputLanguage == INPUT_LANGUAGE_CHINESE) {
//                transListener.onResult(OUTPUT_LANGUAGE_CHINESE, hanStr);
//            } else if(curInputLanguage == INPUT_LANGUAGE_ENGLISH) {
//                transListener.onResult(OUTPUT_LANGUAGE_ENGLISH, hanStr);
//            }
//
//        }




    }

    public void stopListening() {
        if(mIat != null) {
            mIat.stopListening();
        }
    }
}
