package com.net.yuesejiaoyou.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bilibili.boxing.Boxing;
import com.bilibili.boxing.model.config.BoxingConfig;
import com.bilibili.boxing.model.entity.BaseMedia;
import com.bilibili.boxing.model.entity.impl.ImageMedia;
import com.bilibili.boxing.utils.BoxingFileHelper;
import com.bilibili.boxing_impl.ui.BoxingActivity;


import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.cache.ImageResizer;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UploadFileTask_v_01150;
import com.net.yuesejiaoyou.redirect.ResolverC.interface3.UsersThread_01150;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.HelpManager_01066;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.MyGridview;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.PickView;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Authen_intro_01150;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Authen_label_01150;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Authen_nicheng_01150;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Authen_phonenum_01150;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Authen_signature_01150;
import com.net.yuesejiaoyou.redirect.ResolverC.uiface.Vliao_job_01168;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.utils.ImageUtils;
import com.net.yuesejiaoyou.utils.LogUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * Created by Administrator on 2018/1/27.
 */

public class EditActivity extends BaseActivity implements OnClickListener {
    private Context mContext;
    private ImageView back;
    private Uri photoUri;
    private static final int REQUEST_CODE = 1024;
    private static final int COMPRESS_REQUEST_CODE = 2048;
    private int tt = 0;
    private static final int TAKE_PICTURE = 0x000001;
    private String share_1 = "女", share_2 = "不限", share_3 = "170cm", share_4 = "双子座", share_5 = "2000元以下", share_6 = "恋爱中", share_7 = "未婚", share_8 = "50 KG", share_9 = "计算机/互联网/通信", share_10 = "汉族", share_11 = "不限", share_12 = "不限", share_13 = "不限", share_14 = "不限", share_15 = "不限";
    private MyGridview noScrollgridview;
    private GridAdapter adapter;
    private ArrayList<String> imglist = new ArrayList<String>();
    private LinearLayout ll_sexname, ll_nickname, ll_phonenum, ll_height, ll_weight, ll_xingzuo, ll_city, ll_intro,
            ll_label, ll_personality, ll_zhiye;
    private TextView nickname, zhiye_text, sexname_text, nickname_text, phonenum, phonenum_text, height, height_text, weight, weight_text, xingzuo, xingzuo_text, city, city_text, intro, intro_text,
            label, label_text, personality, personality_text, submit;
    private PopupWindow popupWindow;
    Intent intent = new Intent();
    String nicheng_1, phonenum_1, intro_1, signature_1, label_1, label_2;
    private String tu;
    private ImageView tu1, l13, tu2, l22, tu3, l32, tu4, l42, tu5, l52, tu6, l62, tu7, l72, tu8, l82;
    private TextView l11, l12, l21, l31, l41, l51, l61, l71, l81;
    private String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private ImageResizer m_imageWork = null;

    private String tupian1 = "", tupian2 = "", tupian3 = "", tupian4 = "", tupian5 = "", tupian6 = "", tupian7 = "", tupian8 = "";
    private int num = 0;
    private int cishu = 0;
    private String image = "";

    //初始化异步加载图片
    private DisplayImageOptions options = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_imageWork = new ImageResizer(this, 200, 200);

        mContext = EditActivity.this;
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);


        noScrollgridview = (MyGridview) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (imglist.size() > position && imglist.size() != 8) {
                    showPopupspWindow_rp(position, ll_nickname);
                } else {
                    if (imglist.size() == 8) {
                        showPopupspWindow_rp(position, ll_nickname);
                    } else {
                        pickIcon();
                    }

                }

            }
        });

        ll_nickname = (LinearLayout) findViewById(R.id.ll_nickname);//昵称
        ll_nickname.setOnClickListener(this);
        nickname = (TextView) findViewById(R.id.nickname);
        nickname_text = (TextView) findViewById(R.id.nickname_text);

        ll_sexname = (LinearLayout) findViewById(R.id.ll_sexname);//性别
        ll_sexname.setOnClickListener(this);
        sexname_text = (TextView) findViewById(R.id.sexname_text);


        ll_phonenum = (LinearLayout) findViewById(R.id.ll_phonenum);//手机号码
        ll_phonenum.setOnClickListener(this);
        phonenum = (TextView) findViewById(R.id.phonenum);
        phonenum_text = (TextView) findViewById(R.id.phonenum_text);


        ll_height = (LinearLayout) findViewById(R.id.ll_height);//身高
        ll_height.setOnClickListener(this);
        height = (TextView) findViewById(R.id.height);
        height_text = (TextView) findViewById(R.id.height_text);

        ll_weight = (LinearLayout) findViewById(R.id.ll_weight);//体重
        ll_weight.setOnClickListener(this);
        weight = (TextView) findViewById(R.id.weight);
        weight_text = (TextView) findViewById(R.id.weight_text);

        ll_xingzuo = (LinearLayout) findViewById(R.id.ll_xingzuo);//星座
        ll_xingzuo.setOnClickListener(this);

        ll_zhiye = (LinearLayout) findViewById(R.id.ll_zhiye);//星座
        ll_zhiye.setOnClickListener(this);
        ll_zhiye.setVisibility(View.GONE);
        zhiye_text = (TextView) findViewById(R.id.zhiye_text);

        xingzuo = (TextView) findViewById(R.id.xingzuo);
        xingzuo_text = (TextView) findViewById(R.id.xingzuo_text);

        ll_city = (LinearLayout) findViewById(R.id.ll_city);//所在城市
        ll_city.setOnClickListener(this);
        city = (TextView) findViewById(R.id.city);
        city_text = (TextView) findViewById(R.id.city_text);

        ll_intro = (LinearLayout) findViewById(R.id.ll_intro);//个人介绍
        ll_intro.setOnClickListener(this);
        intro = (TextView) findViewById(R.id.intro);
        intro_text = (TextView) findViewById(R.id.intro_text);

        ll_label = (LinearLayout) findViewById(R.id.ll_label);//形象标签
        ll_label.setOnClickListener(this);
        label = (TextView) findViewById(R.id.label);
        label_text = (TextView) findViewById(R.id.label_text);

        ll_personality = (LinearLayout) findViewById(R.id.ll_personality);//个性签名
        ll_personality.setOnClickListener(this);
        personality = (TextView) findViewById(R.id.personality);
        personality_text = (TextView) findViewById(R.id.personality_text);

        submit = (TextView) findViewById(R.id.submit);//提交
        submit.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String permission : permissions) {
                // 检查该权限是否已经获取
                int i = ContextCompat.checkSelfPermission(EditActivity.this, permission);
                // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                if (i != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有授予该权限，就去提示用户请求
                    ActivityCompat.requestPermissions(EditActivity.this, permissions, 321);
                    return;
                }
            }
        }
        String mode1 = "my_info";
        String params1[] = {Util.userid};
        UsersThread_01150 b1 = new UsersThread_01150(mode1, params1, handler);
        Thread thread1 = new Thread(b1.runnable);
        thread1.start();

        //异步加载图片
        options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

    }

    @Override
    protected int getContentView() {
        return R.layout.renzheng_v_01150;
    }

    public void showPopupspWindow_rp(final int pos, View parent) {
        // 加载布局
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View layout = inflater.inflate(R.layout.selectimg_01066, null);
        //取消
        CheckedTextView oneimg, deleteimg;
        oneimg = (CheckedTextView) layout.findViewById(R.id.oneimg);
        deleteimg = (CheckedTextView) layout.findViewById(R.id.deleteimg);

        oneimg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String a = imglist.get(pos);
                imglist.remove(pos);
                imglist.add(0, a);
                adapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

        deleteimg.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                imglist.remove(pos);
                adapter.notifyDataSetChanged();
                popupWindow.dismiss();
            }
        });

        TextView quxiao = (TextView) layout.findViewById(R.id.quxiao);
        quxiao.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                popupWindow.dismiss();
            }
        });


        popupWindow = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popupWindow.setFocusable(true);
        WindowManager.LayoutParams lp = EditActivity.this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        EditActivity.this.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(parent, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = EditActivity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                EditActivity.this.getWindow().setAttributes(lp);
            }
        });
    }


    public void pickIcon() {
        String cachePath = BoxingFileHelper.getCacheDir(this);
        if (TextUtils.isEmpty(cachePath)) {
            Toast.makeText(getApplicationContext(), R.string.boxing_storage_deny, Toast.LENGTH_SHORT).show();
            return;
        }
        Uri destUri = new Uri.Builder()
                .scheme("file")
                .appendPath(cachePath)
                .appendPath(String.format(Locale.US, "%s.png", System.currentTimeMillis()))
                .build();
        BoxingConfig singleCropImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG).needCamera(R.drawable.ic_boxing_camera_white)
                .withMediaPlaceHolderRes(R.drawable.moren);
        Boxing.of(singleCropImgConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_CODE);
    }

    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565).build();

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {
            // loading();
        }

        public int getCount() {
            if (imglist.size() == 8) {
                return 8;
            }
            return (imglist.size() + 1);
        }

        public Object getItem(int arg0) {
            return null;
        }

        public long getItemId(int arg0) {
            return 0;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);

            holder.item_del_image = (TextView) convertView.findViewById(R.id.item_del_image);
            convertView.setTag(holder);


            if (position == imglist.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
                holder.item_del_image.setVisibility(View.GONE);
            } else {
                if (position == 0) {
                    holder.item_del_image.setVisibility(View.VISIBLE);
                } else {
                    holder.item_del_image.setVisibility(View.GONE);
                }
                if (imglist.get(position).contains("http")) {
                    //ImageLoader.getInstance().displayImage(imglist.get(position), holder.image, options);

                    ImageUtils.loadImage(imglist.get(position), holder.image);
                } else {
                    holder.image.setImageBitmap(m_imageWork.processBitmapNet(imglist.get(position), 200, 200));
                }
            }

            return convertView;
        }

        public class ViewHolder {
            public ImageView image;
            public TextView item_del_image;
        }
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        int ab = 0;
        switch (id) {
            case R.id.back:
                finish();
                break;
            case R.id.ll_nickname://昵称
                intent = new Intent();
                intent.setClass(this, Authen_nicheng_01150.class);
                startActivityForResult(intent, 201);
                break;
            case R.id.ll_phonenum://手机号码
                intent = new Intent();
                intent.setClass(this, Authen_phonenum_01150.class);
                startActivityForResult(intent, 202);
                break;
            case R.id.ll_sexname://性别
                tt = 20;
                sharePopupWindow(tt, view);
                break;
            case R.id.ll_zhiye://手机号码
                intent = new Intent();
                LogDetect.send(LogDetect.DataType.specialType, "edit_info_01150页面选中ll_zhiye", "点击职业按钮");
                intent.setClass(this, Vliao_job_01168.class);
                startActivityForResult(intent, 206);
                //startActivity(intent);
                break;

            case R.id.ll_height://身高
                tt = 3;
                sharePopupWindow(tt, view);
                break;
            case R.id.ll_weight://体重
                tt = 8;
                sharePopupWindow(tt, view);
                break;
            case R.id.ll_xingzuo://星座
                tt = 4;
                sharePopupWindow(tt, view);
                break;
            case R.id.ll_city://所在城市
//				ab = 1;
//				showPopaddress(ab, view);
                LogDetect.send(LogDetect.DataType.basicType, "01162-----", "准备弹窗");
                showPopaddress(1, view);
                ll_city.setClickable(false);

                break;
            case R.id.ll_intro://个人介绍
                intent = new Intent();
                intent.setClass(this, Authen_intro_01150.class);
                startActivityForResult(intent, 203);
                break;
            case R.id.ll_label:
                intent = new Intent();
                intent.setClass(this, Authen_label_01150.class);
                startActivityForResult(intent, 204);
                break;
            case R.id.ll_personality:
                intent = new Intent();
                intent.setClass(this, Authen_signature_01150.class);
                startActivityForResult(intent, 205);
                break;

            case R.id.submit:
                for (int i = 0; i < imglist.size(); i++) {
                    if (imglist.get(i).contains("http://")) {
                        if (cishu == 0) {
                            image = imglist.get(i);
                            cishu++;
                        } else {
                            image = image + "," + imglist.get(i);
                        }
                    } else {
                        num = num + 1;
                    }
                }
                cishu = 0;
                if (num == 0) {
                    String mode2 = "mod_info";

                    if (!nickname_text.getText().equals(nickname_old)) {
                        changed = true;
                    }
                    if (changed == false && !phonenum_text.getText().equals(phonenum_old)) {
                        changed = true;
                    }
                    if (changed == false && !height_text.getText().equals(height_old)) {
                        changed = true;
                    }
                    if (changed == false && !weight_text.getText().equals(weight_old)) {
                        changed = true;
                    }
                    if (changed == false && !xingzuo_text.getText().equals(constellation_old)) {
                        changed = true;
                    }
                    if (changed == false && !city_text.getText().equals(city_old)) {
                        changed = true;
                    }
                    if (changed == false && !intro_text.getText().equals(introduce_old)) {
                        changed = true;
                    }
                    if (changed == false && !label_text.getText().equals(image_label_old)) {
                        changed = true;
                    }
                    if (changed == false && !personality_text.getText().equals(signature_old)) {
                        changed = true;
                    }
                    if (changed == false && !sexname_text.getText().equals(gender_old)) {
                        changed = true;
                    }
                    if (changed == false && !ylimgpic.equals(image)) {
                        changed = true;
                    }
                    LogUtil.i("ttt", "---" + image);
                    //if (ylimgpic.equals(image)){
                    if (changed == false) {
                        String[] paramsMap2 = {Util.userid, image, nickname_text.getText().toString(), phonenum_text.getText().toString(), height_text.getText().toString(),
                                weight_text.getText().toString(), xingzuo_text.getText().toString(), city_text.getText().toString(), intro_text.getText().toString(),
                                label_text.getText().toString(), personality_text.getText().toString(), 0 + "", sexname_text.getText().toString()};//city_text.getText().toString()
                        UsersThread_01150 b2 = new UsersThread_01150(mode2, paramsMap2, handler);
                        Thread t2 = new Thread(b2.runnable);
                        t2.start();
                        LogDetect.send(LogDetect.DataType.specialType, "01150 pic=====&&&&&&&======111:", image);
                    } else {
                        String[] paramsMap2 = {Util.userid, image, nickname_text.getText().toString(), phonenum_text.getText().toString(), height_text.getText().toString(),
                                weight_text.getText().toString(), xingzuo_text.getText().toString(), city_text.getText().toString(), intro_text.getText().toString(),
                                label_text.getText().toString(), personality_text.getText().toString(), "1", sexname_text.getText().toString()};//city_text.getText().toString()
                        UsersThread_01150 b2 = new UsersThread_01150(mode2, paramsMap2, handler);
                        Thread t2 = new Thread(b2.runnable);
                        t2.start();
                        LogDetect.send(LogDetect.DataType.specialType, "01150 pic=====&&&&&&&======11111111:", paramsMap2[2]);
                    }


                } else {
                    pdialog = ProgressDialog.show(EditActivity.this, "上传中...", "系统正在处理您的请求！");
                    for (int i = 0; i < imglist.size(); i++) {
                        if (!imglist.get(i).contains("http:")) {
                            imgpos = i;
                            LogDetect.send(LogDetect.DataType.specialType, "01150 pic=====&&&&&&&======:", imglist.get(i));
                            UploadFileTask_v_01150 uploadFileTask = new UploadFileTask_v_01150(EditActivity.this, handler);
                            uploadFileTask.execute(imglist.get(i), Util.userid);
                        }
                    }
                }


                break;

        }
    }

    private ProgressDialog pdialog = null;
    private String ylimgpic = "";
    private boolean changed = false;
    String nickname_old;
    String phonenum_old;
    String height_old;
    String weight_old;
    String constellation_old;
    String city_old;
    String introduce_old;
    String image_label_old;
    String signature_old;
    String pictures_old;
    String gender_old;
    private int imgpos = 0;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 101:
                    String path = (String) msg.obj;
                    LogDetect.send(LogDetect.DataType.specialType, "num：===========================", num);


//					if (cishu == 0) {
//						image = "http://120.27.98.128:9112/img/imgheadpic/" + path;
//					} else {
//						image = image + ",http://120.27.98.128:9112/img/imgheadpic/" + path;
//					}
//					cishu++;

                    int m = 0;
                    for (int i = 0; i < imglist.size(); i++) {
                        if (!imglist.get(i).contains("http:") && m == 0) {
                            imglist.set(i, Util.url + "/img/imgheadpic/" + path);
                            m++;
                        }
                    }
                    cishu++;

                    if (cishu == num) {
                        LogDetect.send(LogDetect.DataType.specialType, "cishu：===========================", cishu);
                        for (int i = 0; i < imglist.size(); i++) {
                            if (i == 0) {
                                image = imglist.get(i);
                            } else {
                                image = image + "," + imglist.get(i);
                            }
                        }

                        String mode2 = "mod_info";
                        String[] paramsMap2 = {Util.userid, image, nickname_text.getText().toString(), phonenum_text.getText().toString(), height_text.getText().toString(),
                                weight_text.getText().toString(), xingzuo_text.getText().toString(), city_text.getText().toString(), intro_text.getText().toString(),
                                label_text.getText().toString(), personality_text.getText().toString(), "1", sexname_text.getText().toString()};//city_text.getText().toString()
                        UsersThread_01150 b2 = new UsersThread_01150(mode2, paramsMap2, handler);
                        Thread t2 = new Thread(b2.runnable);
                        t2.start();
                        if (pdialog != null) {
                            pdialog.dismiss();
                        }
                    }
                    break;
                case 202:
                    String json = (String) msg.obj;
                    if (json.contains("success")) {
                        Toast.makeText(EditActivity.this, "提交成功，请等待审核！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        //Toast.makeText(Authentication_01150.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 203:
                    String json1 = (String) msg.obj;
                    JSONArray jsonArray = null;
                    if (!json1.equals("")) {
                        try {
                            jsonArray = new JSONArray(json1);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);
                            nickname_old = jsonObject.getString("nickname");
                            phonenum_old = jsonObject.getString("phonenum");
                            height_old = jsonObject.getString("height");
                            weight_old = jsonObject.getString("weight");
                            constellation_old = jsonObject.getString("constellation");
                            city_old = jsonObject.getString("city");
                            introduce_old = jsonObject.getString("introduce");
                            image_label_old = jsonObject.getString("image_label");
                            signature_old = jsonObject.getString("signature");
                            String photo = jsonObject.getString("photo");
                            pictures_old = jsonObject.getString("pictures");
                            gender_old = jsonObject.getString("gender");
                            sexname_text.setText(jsonObject.getString("gender"));


                            if (!photo.equals("")) {
                                ImageLoader.getInstance().displayImage(
                                        photo, tu1, options);
                            }
                            ylimgpic = pictures_old;

                            if (!pictures_old.equals("")) {
                                String[] pics = pictures_old.split(",");
                                for (int i = 0; i < pics.length; i++) {
                                    imglist.add(pics[i]);
                                }
                                adapter = new GridAdapter(EditActivity.this);
                                // adapter.update();
                                noScrollgridview.setAdapter(adapter);
                            }
//							if (!pictures.equals("")) {
//								String[] pics = pictures.split(",");
//								if (pics.length == 1) {
//									ImageLoader.getInstance().displayImage(
//											pics[0], tu2, options);
//									M2 = pics[0];
//
//								} else if (pics.length == 2) {
//									ImageLoader.getInstance().displayImage(
//											pics[0], tu2, options);
//									ImageLoader.getInstance().displayImage(
//											pics[1], tu3, options);
//									M2 = pics[0];
//									M3 = pics[1];
//
//								} else if (pics.length == 3) {
//									ImageLoader.getInstance().displayImage(
//											pics[0], tu2, options);
//									ImageLoader.getInstance().displayImage(
//											pics[1], tu3, options);
//									ImageLoader.getInstance().displayImage(
//											pics[2], tu4, options);
//									M2 = pics[0];
//									M3 = pics[1];
//									M4 = pics[2];
//								} else if (pics.length == 4) {
//									ImageLoader.getInstance().displayImage(
//											pics[0], tu2, options);
//									ImageLoader.getInstance().displayImage(
//											pics[1], tu3, options);
//									ImageLoader.getInstance().displayImage(
//											pics[2], tu4, options);
//									ImageLoader.getInstance().displayImage(
//											pics[3], tu5, options);
//									M2 = pics[0];
//
//									M3 = pics[1];
//
//									M4 = pics[2];
//
//									M5 = pics[3];
//
//								} else if (pics.length == 5) {
//									ImageLoader.getInstance().displayImage(
//											pics[0], tu2, options);
//									ImageLoader.getInstance().displayImage(
//											pics[1], tu3, options);
//									ImageLoader.getInstance().displayImage(
//											pics[2], tu4, options);
//									ImageLoader.getInstance().displayImage(
//											pics[3], tu5, options);
//									ImageLoader.getInstance().displayImage(
//											pics[4], tu6, options);
//									M2 = pics[0];
//
//									M3 = pics[1];
//
//									M4 = pics[2];
//
//									M5 = pics[3];
//
//									M6 = pics[4];
//
//								} else if (pics.length == 6) {
//									ImageLoader.getInstance().displayImage(
//											pics[0], tu2, options);
//									ImageLoader.getInstance().displayImage(
//											pics[1], tu3, options);
//									ImageLoader.getInstance().displayImage(
//											pics[2], tu4, options);
//									ImageLoader.getInstance().displayImage(
//											pics[3], tu5, options);
//									ImageLoader.getInstance().displayImage(
//											pics[4], tu6, options);
//									ImageLoader.getInstance().displayImage(
//											pics[5], tu7, options);
//									M2 = pics[0];
//
//									M3 = pics[1];
//
//									M4 = pics[2];
//
//									M5 = pics[3];
//
//									M6 = pics[4];
//
//									M7 = pics[5];
//
//								} else {
//									ImageLoader.getInstance().displayImage(
//											pics[0], tu2, options);
//									ImageLoader.getInstance().displayImage(
//											pics[1], tu3, options);
//									ImageLoader.getInstance().displayImage(
//											pics[2], tu4, options);
//									ImageLoader.getInstance().displayImage(
//											pics[3], tu5, options);
//									ImageLoader.getInstance().displayImage(
//											pics[4], tu6, options);
//									ImageLoader.getInstance().displayImage(
//											pics[5], tu7, options);
//									ImageLoader.getInstance().displayImage(
//											pics[6], tu8, options);
//									M2 = pics[0];
//									M3 = pics[1];
//									M4 = pics[2];
//									M5 = pics[3];
//									M6 = pics[4];
//									M7 = pics[5];
//									M8 = pics[6];
//
//								}
                            nickname_text.setText(nickname_old);
                            phonenum_text.setText(phonenum_old);
                            height_text.setText(height_old);
                            weight_text.setText(weight_old);
                            xingzuo_text.setText(constellation_old);
                            city_text.setText(city_old);
                            intro_text.setText(introduce_old);
                            label_text.setText(image_label_old);
                            personality_text.setText(signature_old);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 204:
                    String json2 = (String) msg.obj;
                    if (json2.contains("success")) {
                        Toast.makeText(EditActivity.this, "提交成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                    }
                    break;
            }
        }
    };

    private String citys = "{\"provinces\":[{\"name\":\"北京市\",\"citys\":[\"东城区\",\"西城区\",\"崇文区\",\"宣武区\",\"朝阳区\",\"海淀区\",\"丰台区\",\"石景山区\",\"房山区\",\"通州区\",\"顺义区\",\"昌平区\",\"大兴区\",\"怀柔区\",\"平谷区\",\"门头沟区\",\"密云区\",\"延庆区\",\"其他\"]},{\"name\":\"广东省\",\"citys\":[\"广州\",\"深圳\",\"珠海\",\"汕头\",\"韶关\",\"佛山\",\"江门\",\"湛江\",\"茂名\",\"肇庆\",\"惠州\",\"梅州\",\"汕尾\",\"河源\",\"阳江\",\"清远\",\"东莞\",\"中山\",\"潮州\",\"揭阳\",\"云浮\",\"其他\"]},{\"name\":\"上海市\",\"citys\":[\"黄浦区\",\"卢湾区\",\"徐汇区\",\"长宁区\",\"静安区\",\"普陀区\",\"闸北区\",\"虹口区\",\"杨浦区\",\"宝山区\",\"闵行区\",\"嘉定区\",\"松江区\",\"金山区\",\"青浦区\",\"南汇区\",\"奉贤区\",\"浦东新区\",\"崇明区\",\"其他\"]},{\"name\":\"天津市\",\"citys\":[\"和平区\",\"河东区\",\"河西区\",\"南开区\",\"河北区\",\"红桥区\",\"塘沽区\",\"汉沽区\",\"大港区\",\"东丽区\",\"西青区\",\"北辰区\",\"津南区\",\"武清区\",\"宝坻区\",\"静海县\",\"宁河县\",\"蓟县\",\"其他\"]},{\"name\":\"重庆市\",\"citys\":[\"渝中区\",\"大渡口区\",\"江北区\",\"南岸区\",\"北碚区\",\"渝北区\",\"巴南区\",\"长寿区\",\"双桥区\",\"沙坪坝区\",\"万盛区\",\"万州区\",\"涪陵区\",\"黔江区\",\"永川区\",\"合川区\",\"江津区\",\"九龙坡区\",\"南川区\",\"綦江县\",\"潼南区\",\"荣昌区\",\"璧山区\",\"大足区\",\"铜梁县\",\"梁平县\",\"开县\",\"忠县\",\"城口县\",\"垫江区\",\"武隆县\",\"丰都县\",\"奉节县\",\"云阳县\",\"巫溪县\",\"巫山县\",\"其他\"]},{\"name\":\"辽宁省\",\"citys\":[\"沈阳\",\"大连\",\"鞍山\",\"抚顺\",\"本溪\",\"丹东\",\"锦州\",\"营口\",\"阜新\",\"辽阳\",\"盘锦\",\"铁岭\",\"朝阳\",\"葫芦岛\",\"其他\"]},{\"name\":\"江苏省\",\"citys\":[\"南京\",\"苏州\",\"无锡\",\"常州\",\"镇江\",\"南通\",\"泰州\",\"扬州\",\"盐城\",\"连云港\",\"徐州\",\"淮安\",\"宿州\",\"其他\"]},{\"name\":\"湖北省\",\"citys\":[\"武汉\",\"黄石\",\"十堰\",\"荆州\",\"宜昌\",\"襄樊\",\"鄂州\",\"荆门\",\"孝感\",\"黄冈\",\"咸宁\",\"随州\",\"仙桃\",\"天门\",\"潜江\",\"神农架\",\"其他\"]},{\"name\":\"四川省\",\"citys\":[\"成都\",\"自贡\",\"攀枝花\",\"泸州\",\"德阳\",\"绵阳\",\"广元\",\"遂宁\",\"内江\",\"乐山\",\"南充\",\"眉山\",\"宜宾\",\"广安\",\"达州\",\"雅安\",\"巴中\",\"资阳\",\"其他\"]},{\"name\":\"陕西省\",\"citys\":[\"西安\",\"铜川\",\"宝鸡\",\"咸阳\",\"渭南\",\"延安\",\"汉中\",\"榆林\",\"安康\",\"商洛\",\"其他\"]},{\"name\":\"河北省\",\"citys\":[\"石家庄\",\"唐山\",\"秦皇岛\",\"邯郸\",\"邢台\",\"保定\",\"张家口\",\"承德\",\"沧州\",\"廊坊\",\"衡水\",\"其他\"]},{\"name\":\"山西省\",\"citys\":[\"太原\",\"大同\",\"阳泉\",\"长治\",\"晋城\",\"朔州\",\"晋中\",\"运城\",\"忻州\",\"临汾\",\"吕梁\",\"其他\"]},{\"name\":\"河南省\",\"citys\":[\"郑州\",\"开封\",\"洛阳\",\"平顶山\",\"安阳\",\"鹤壁\",\"新乡\",\"焦作\",\"濮阳\",\"许昌\",\"漯河\",\"三门峡\",\"南阳\",\"商丘\",\"信阳\",\"周口\",\"驻马店\",\"焦作\",\"其他\"]},{\"name\":\"吉林省\",\"citys\":[\"吉林\",\"四平\",\"辽源\",\"通化\",\"白山\",\"松原\",\"白城\",\"延边朝鲜自治区\",\"其他\"]},{\"name\":\"黑龙江\",\"citys\":[\"哈尔滨\",\"齐齐哈尔\",\"鹤岗\",\"双鸭山\",\"鸡西\",\"大庆\",\"伊春\",\"牡丹江\",\"佳木斯\",\"七台河\",\"黑河\",\"绥远\",\"大兴安岭地区\",\"其他\"]},{\"name\":\"内蒙古\",\"citys\":[\"呼和浩特\",\"包头\",\"乌海\",\"赤峰\",\"通辽\",\"鄂尔多斯\",\"呼伦贝尔\",\"巴彦淖尔\",\"乌兰察布\",\"锡林郭勒盟\",\"兴安盟\",\"阿拉善盟\"]},{\"name\":\"山东省\",\"citys\":[\"济南\",\"青岛\",\"淄博\",\"枣庄\",\"东营\",\"烟台\",\"潍坊\",\"济宁\",\"泰安\",\"威海\",\"日照\",\"莱芜\",\"临沂\",\"德州\",\"聊城\",\"滨州\",\"菏泽\",\"其他\"]},{\"name\":\"安徽省\",\"citys\":[\"合肥\",\"芜湖\",\"蚌埠\",\"淮南\",\"马鞍山\",\"淮北\",\"铜陵\",\"安庆\",\"黄山\",\"滁州\",\"阜阳\",\"宿州\",\"巢湖\",\"六安\",\"亳州\",\"池州\",\"宣城\"]},{\"name\":\"浙江省\",\"citys\":[\"杭州\",\"宁波\",\"温州\",\"嘉兴\",\"湖州\",\"绍兴\",\"金华\",\"衢州\",\"舟山\",\"台州\",\"丽水\",\"其他\"]},{\"name\":\"福建省\",\"citys\":[\"福州\",\"厦门\",\"莆田\",\"三明\",\"泉州\",\"漳州\",\"南平\",\"龙岩\",\"宁德\",\"其他\"]},{\"name\":\"湖南省\",\"citys\":[\"长沙\",\"株洲\",\"湘潭\",\"衡阳\",\"邵阳\",\"岳阳\",\"常德\",\"张家界\",\"益阳\",\"滨州\",\"永州\",\"怀化\",\"娄底\",\"其他\"]},{\"name\":\"广西省\",\"citys\":[\"南宁\",\"柳州\",\"桂林\",\"梧州\",\"北海\",\"防城港\",\"钦州\",\"贵港\",\"玉林\",\"百色\",\"贺州\",\"河池\",\"来宾\",\"崇左\",\"其他\"]},{\"name\":\"江西省\",\"citys\":[\"南昌\",\"景德镇\",\"萍乡\",\"九江\",\"新余\",\"鹰潭\",\"赣州\",\"吉安\",\"宜春\",\"抚州\",\"上饶\",\"其他\"]},{\"name\":\"贵州省\",\"citys\":[\"贵阳\",\"六盘水\",\"遵义\",\"安顺\",\"铜仁\",\"毕节\",\"其他\"]},{\"name\":\"云南省\",\"citys\":[\"昆明\",\"曲靖\",\"玉溪\",\"保山\",\"邵通\",\"丽江\",\"普洱\",\"临沧\",\"其他\"]},{\"name\":\"西藏\",\"citys\":[\"拉萨\",\"那曲地区\",\"昌都地区\",\"林芝地区\",\"山南区\",\"阿里区\",\"日喀则\",\"其他\"]},{\"name\":\"海南省\",\"citys\":[\"海口\",\"三亚\",\"五指山\",\"琼海\",\"儋州\",\"文昌\",\"万宁\",\"东方\",\"澄迈县\",\"定安县\",\"屯昌县\",\"临高县\",\"其他\"]},{\"name\":\"甘肃省\",\"citys\":[\"兰州\",\"嘉峪关\",\"金昌\",\"白银\",\"天水\",\"武威\",\"酒泉\",\"张掖\",\"庆阳\",\"平凉\",\"定西\",\"陇南\",\"临夏\",\"甘南\",\"其他\"]},{\"name\":\"宁夏\",\"citys\":[\"银川\",\"石嘴山\",\"吴忠\",\"固原\",\"中卫\",\"其他\"]},{\"name\":\"青海\",\"citys\":[\"西宁\",\"海东地区\",\"海北藏族自治区\",\"海南藏族自治区\",\"黄南藏族自治区\",\"果洛藏族自治区\",\"玉树藏族自治州\",\"还西藏族自治区\",\"其他\"]},{\"name\":\"新疆\",\"citys\":[\"乌鲁木齐\",\"克拉玛依\",\"吐鲁番地区\",\"哈密地区\",\"和田地区\",\"阿克苏地区\",\"喀什地区\",\"克孜勒苏柯尔克孜\",\"巴音郭楞蒙古自治区\",\"昌吉回族自治州\",\"博尔塔拉蒙古自治区\",\"石河子\",\"阿拉尔\",\"图木舒克\",\"五家渠\",\"伊犁哈萨克自治区\",\"其他\"]}]}";


    String sh1 = "Default", sh2 = "Default", sh3 = "Default";

    PopupWindow popup;

    public void showPopaddress(final int ab, View parent) {

        sh1 = "默认";
        sh2 = "默认";
        sh3 = "默认";

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.doupopview_01066, null);
        TextView stitle = (TextView) layout.findViewById(R.id.stitle);
        stitle.setText("请选择地区");
        PickView minute_pv = (PickView) layout.findViewById(R.id.minute_pv);
        final PickView minute_pv1 = (PickView) layout.findViewById(R.id.minute_pv1);
        final PickView minute_pv2 = (PickView) layout.findViewById(R.id.minute_pv2);

//		Log.v("TT", "---- 国家 ----");
//		List<String> data = new ArrayList<String>();
//		data = HelpManager_01066.getNation(this);    //HelpManager_01066.getsheng();
//		data.add(0, "默认");
//		minute_pv.setData(data);
//		minute_pv.setSelected(0);
//		minute_pv.setOnSelectListener(new PickView.onSelectListener() {
//
//			@Override
//			public void onSelect(String text) {
//
//				//	Log.v("TT","nation: "+text);
//				if (!text.toString().equals("默认")) {
//					sh1 = text.toString();
//					sh2 = "默认";
//					List<String> data1 = new ArrayList<String>();
//					data1 = HelpManager_01066.getsheng(EditActivity.this, text);
//					//	Log.v("TT","province("+text+"): ("+data1.size()+")");
//					for (String item : data1) {
//						Log.v("TT", item);
//					}
//					data1.add(0, "默认");
//					minute_pv1.setData(data1);
//					minute_pv1.setSelected(0);
//
//					// 切换国家城市修改为 不限
//					//	Log.v("TT","data1.size(): "+data1.size());
//					if (data1.size() <= 1) {
//						List<String> data2 = HelpManager_01066.getCity(EditActivity.this, text);
//						data2.add(0, "默认");
//						minute_pv2.setData(data2);
//						minute_pv2.setSelected(0);
//					} else {
//						List<String> data2 = new ArrayList<String>();
//						data2.add(0, "默认");
//						minute_pv2.setData(data2);
//						minute_pv2.setSelected(0);
//					}
//					sh1 = text;
//				} else {
//					sh1 = "默认";
//				}
//
////				Toast.makeText(mContext, text,
////						Toast.LENGTH_SHORT).show();
//			}
//		});

        Log.v("TT", "---- 省 ----");
        List<String> data1 = HelpManager_01066.getsheng(EditActivity.this, "中国");
        minute_pv1.setData(data1);
        minute_pv1.setSelected(3);
        minute_pv1.setOnSelectListener(new PickView.onSelectListener() {

            @Override
            public void onSelect(String text) {

                //sh2=text;
                //----------------------
                Log.v("TT", "province: " + text);
                if (!text.toString().equals("默认")) {
                    sh2 = text.toString();
                    sh3 = "默认";
                    List<String> data2 = new ArrayList<String>();
                    data2 = HelpManager_01066.getCity(EditActivity.this, text);
                    Log.v("TT", "city: (" + data2.size() + ")");
                    for (String item : data2) {
                        Log.v("TT", item);
                    }
                    data2.add("默认");
                    minute_pv2.setData(data2);
                    minute_pv2.setSelected(3);
                    sh3 = data2.get(3);
                } else {
                    sh2 = "默认";
                }
            }
        });

        Log.v("TT", "---- 城市 ----");
        List<String> data2 = HelpManager_01066.getCity(EditActivity.this, "山西");
        //data2.add("默认");
        minute_pv2.setData(data2);
        minute_pv2.setSelected(3);
        minute_pv2.setOnSelectListener(new PickView.onSelectListener() {

            @Override
            public void onSelect(String text) {

                sh3 = text;

            }
        });


        TextView tv_cancel = (TextView) layout.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        TextView tv_sure = (TextView) layout.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                city_text.setText((sh2 + " " + sh3).replace("Default", ""));
                //	city_text.setText("China Beijing Xicheng");

//				if (!sh2.equals("Default")){
//					address=sh1+sh3;
//					if(ab ==1) {
//						city_text.setText(sh1 + " " + sh2);
//					}else if(ab ==2){
//						city_text.setText(sh1 + " " + sh2);
//					}else if(ab ==3){
//						city_text.setText(sh1 +" "+ sh2);
//					}else if(ab ==4){
//						city_text.setText(sh1 + " " + sh2);
//					}
//				}else{
//					if (!sh1.equals("Default")){
//						address=sh1;
//					}else{
//						address="";
//					}
//					if(ab ==1){
//						city_text.setText(sh1);
//					}else if(ab == 2){
//						city_text.setText(sh1);
//					}else if(ab == 3){
//						city_text.setText(sh1);
//					}else if(ab == 4){
//						city_text.setText(sh1);
//					}
//
//				}
                popup.dismiss();
            }
        });

        popup = new PopupWindow(layout, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popup.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        WindowManager.LayoutParams lp = EditActivity.this.getWindow().getAttributes();
        lp.alpha = 0.4f;
        EditActivity.this.getWindow().setAttributes(lp);
        popup.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) EditActivity.this.getSystemService(Context.WINDOW_SERVICE);
        // xoff,yoff基于anchor的左下角进行偏移。
        // popupWindow.showAsDropDown(parent, 0, 0);
        popup.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        // 监听
        //popupWindow.showAsDropDown(parent, 0, 0);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = EditActivity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                EditActivity.this.getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                EditActivity.this.getWindow().setAttributes(lp);
                ll_city.setClickable(true);
            }
        });
    }

    public void sharePopupWindow(final int num, View parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popview_mod_info_01150, null);

        PickView minute_pv = (PickView) layout.findViewById(R.id.minute_pv);
        List<String> data = new ArrayList<String>();
        switch (num) {
            case 3:
                for (int i = 120; i < 220; i++) {
                    data.add(i + "cm");
                }
                break;
            case 20:
                data.add("女");
                data.add("男");
                break;

            case 8:
                for (int i = 40; i < 200; i++) {
                    data.add(i + "kg");
                }
                break;
            case 4:
                data.add("摩羯座");//摩羯座
                data.add("水瓶座");//水瓶座
                data.add("双鱼座");//双鱼座
                data.add("白羊座");//白羊座
                data.add("金牛座");//金牛座
                data.add("双子座");//双子座
                data.add("巨蟹座");//巨蟹座
                data.add("狮子座");//狮子座
                data.add("处女座");//处女座
                data.add("天秤座");//天秤座
                data.add("天蝎座");//天蝎座
                data.add("射手座");//射手座
                break;

        }

        minute_pv.setData(data);
        if (num == 3) {
            minute_pv.setSelected(50);
        } else if (num == 4) {
            minute_pv.setSelected(5);
        } else if (num == 8) {
            minute_pv.setSelected(10);
        } else if (num == 20) {
            minute_pv.setSelected(0);
        }


        minute_pv.setOnSelectListener(new PickView.onSelectListener() {

            @Override
            public void onSelect(String text) {
                if (num == 3) {
                    share_3 = text;
                } else if (num == 4) {
                    share_4 = text;
                } else if (num == 8) {
                    share_8 = text;
                } else if (num == 20) {
                    share_1 = text;
                }
            }
        });

        TextView tv_sure = (TextView) layout.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (num) {
                    case 3:
                        height_text.setText(share_3);
                        break;
                    case 8:
                        weight_text.setText(share_8);
                        break;
                    case 4:
                        xingzuo_text.setText(share_4);
                        break;
                    case 20:
                        sexname_text.setText(share_1);
                        break;
                }
                popup.dismiss();
            }
        });
        popup = new PopupWindow(layout, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popup.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        WindowManager.LayoutParams lp = EditActivity.this.getWindow().getAttributes();
        lp.alpha = 0.4f;
        EditActivity.this.getWindow().setAttributes(lp);
        popup.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) EditActivity.this.getSystemService(Context.WINDOW_SERVICE);
        // xoff,yoff基于anchor的左下角进行偏移。
        // popupWindow.showAsDropDown(parent, 0, 0);
        popup.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        // 监听
        //popupWindow.showAsDropDown(parent, 0, 0);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = EditActivity.this.getWindow().getAttributes();
                lp.alpha = 1f;
                EditActivity.this.getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                EditActivity.this.getWindow().setAttributes(lp);
            }
        });
    }


    private void
    xiangce() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//		if (requestCode == 3) {
//			upimg(data);
//		}
//		switch (requestCode) {
//
//			case TAKE_PICTURE:
//				String imagePath = "";
//				if (data != null && data.getData() != null) {// 有数据返回直接使用返回的图片地址
//			/*uri = data.getData();
//            Cursor cursor = ((FragmentActivity) mContext).getContentResolver().query(uri, proj, null,
//                    null, null);
//            if (cursor == null) {//出现如小米等手机返回的绝对路径错误时,自己拼出路径
//                uri = getUri(mContext, data);
//            }*/
//					LogDetect.send(LogDetect.DataType.specialType, "mImagePath: ", mImagePath);
//					imagePath = data.getData().getEncodedPath();
//
//				} else {// 无数据使用指定的图片路径
//					imagePath = mImagePath;
//					LogDetect.send(LogDetect.DataType.specialType, "mImagePath: ", mImagePath);
//				}
//				String a = compressPic(imagePath);
//
////				UploadFileTask_v_01150 uploadFileTask2 = new UploadFileTask_v_01150(Authentication_01150.this, handler);
////				uploadFileTask2.execute(a, Util.userid);
//
//				break;
//
//		}

        if (resultCode == RESULT_OK) {
            //if (mRecyclerView == null || mAdapter == null) {
            //	return;
            //}
            //mRecyclerView.setVisibility(View.VISIBLE);
            final ArrayList<BaseMedia> medias = Boxing.getResult(data);
            BaseMedia baseMedia = medias.get(0);
            if (requestCode == REQUEST_CODE) {
                String path;
                if (baseMedia instanceof ImageMedia) {
                    path = ((ImageMedia) baseMedia).getThumbnailPath();
                } else {
                    path = baseMedia.getPath();
                }
                imglist.add(path);
                adapter.notifyDataSetChanged();
            } else if (requestCode == COMPRESS_REQUEST_CODE) {
                String path;
                if (baseMedia instanceof ImageMedia) {
                    path = ((ImageMedia) baseMedia).getThumbnailPath();
                } else {
                    path = baseMedia.getPath();
                }
                Log.e("ggggg", path);
                //Toast.makeText(Authentication_01150.this,path,Toast.LENGTH_SHORT).show();
                LogDetect.send(LogDetect.DataType.specialType, "--path--a:--", path);
                imglist.add(path);
                adapter.notifyDataSetChanged();
                final List<BaseMedia> imageMedias = new ArrayList<>(1);
                //BaseMedia baseMedia = medias.get(0);
                if (!(baseMedia instanceof ImageMedia)) {
                    return;
                }

//				final ImageMedia imageMedia = (ImageMedia) baseMedia;
//				// the compress task may need time
//				if (imageMedia.compress(new ImageCompressor(this))) {
//					imageMedia.removeExif();
//					imageMedias.add(imageMedia);
//					mAdapter.setList(imageMedias);
//				}

            }
        }


        if (resultCode == 201 || resultCode == 202 || resultCode == 203 || resultCode == 204 || resultCode == 205 || resultCode == 206) {
            switch (resultCode) {

                case 201:
                    LogDetect.send(LogDetect.DataType.specialType, "昵称部分===01150====", "回到编辑资料页面201");
                    Bundle bundle = data.getExtras();
                    nicheng_1 = bundle.getString("nicheng");
                    LogDetect.send(LogDetect.DataType.specialType, "昵称：", nicheng_1);
                    if (!(nicheng_1.equals(""))) {
                        nickname_text.setText(nicheng_1);
                    }
                    break;
                case 206:
                    LogDetect.send(LogDetect.DataType.specialType, "昵称部分===01150====", "回到编辑资料页面201");
                    Bundle bundle1 = data.getExtras();
                    String job1 = bundle1.getString("job");
                    LogDetect.send(LogDetect.DataType.specialType, "昵称：", nicheng_1);
                    if (!(job1.equals(""))) {
                        zhiye_text.setText(job1);
                    }
                    break;
                case 202:
                    LogDetect.send(LogDetect.DataType.specialType, "绑定手机号部分===01150====", "回到编辑资料页面202");
                    phonenum_1 = data.getStringExtra("phonenum");
                    LogDetect.send(LogDetect.DataType.specialType, "绑定手机号——01150()", phonenum_1);
                    if (!(phonenum_1.equals(""))) {
                        phonenum_text.setText(phonenum_1);
                    }
                    break;
                case 203:
                    LogDetect.send(LogDetect.DataType.specialType, "个人介绍部分===01150====", "回到编辑资料页面203");
                    intro_1 = data.getStringExtra("introduction");
                    LogDetect.send(LogDetect.DataType.specialType, "个人介绍部分——01150()", intro_1);
                    if (!(intro_1.equals(""))) {
                        intro_text.setText(intro_1);
                    }
                    break;
                case 204:
                    LogDetect.send(LogDetect.DataType.specialType, "形象标签部分===01150====", "回到编辑资料页面203");
                    label_1 = data.getStringExtra("biaoqian");
                    //	label_2 = data.getStringExtra("biaoqian2");
                    LogDetect.send(LogDetect.DataType.specialType, "形象标签部分——01150()", intro_1);
                    if (!label_1.equals("")) {
                        label_text.setText(label_1);
                    }
//					if(!(label_2.equals(""))){
//						label_text.setText(label_2);
//					}
                    break;
                case 205:
                    LogDetect.send(LogDetect.DataType.specialType, "个性签名部分===01150====", "回到编辑资料页面203");
                    signature_1 = data.getStringExtra("signature");
                    LogDetect.send(LogDetect.DataType.specialType, "个性签名部分——01150()", signature_1);
                    if (!(signature_1.equals(""))) {
                        personality_text.setText(signature_1);
                    }
                    break;
            }

        }

    }

    protected void upimg(Intent data) {
        int colunm_index = 0;
        String path;
        LogDetect.send(LogDetect.DataType.basicType, "01162", "进入相册上传图片方法");
        if (data == null) {
            Toast.makeText(EditActivity.this, "尚未选择图片", Toast.LENGTH_LONG).show();
            return;
        }

        photoUri = data.getData();
        if (photoUri == null) {
            Toast.makeText(EditActivity.this, "选择图片出现错误", Toast.LENGTH_LONG).show();
            return;
        }
        String[] pojo = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
        if (cursor != null) {
            colunm_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            path = cursor.getString(colunm_index);

            // 华为手机方案
            if (path == null) {
                final String docId = DocumentsContract.getDocumentId(photoUri);
                final String[] split = docId.split(":");
                final String[] selectionArgs = new String[]{split[1]};
                cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, pojo, "_id=?", selectionArgs, null);
                cursor.moveToFirst();
                final int index = cursor.getColumnIndexOrThrow("_data");
                path = cursor.getString(index);
            }


            path = compressPic(path);    //压缩图片并保存，返回小图片path


            //ContentResolver cr = getContentResolver();
        } else {
            // 小米手机方案
            path = data.getData().getEncodedPath();
            path = compressPic(path);
        }

        if (tu.equals("1")) {
            tupian1 = path;
            tu1.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));
        } else if (tu.equals("2")) {
            tupian2 = path;
            tu2.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));

        } else if (tu.equals("3")) {
            tupian3 = path;
            tu3.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));

        } else if (tu.equals("4")) {
            tupian4 = path;
            tu4.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));

        } else if (tu.equals("5")) {
            tupian5 = path;
            tu5.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));

        } else if (tu.equals("6")) {
            tupian6 = path;
            tu6.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));

        } else if (tu.equals("7")) {
            tupian7 = path;
            tu7.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));

        } else if (tu.equals("8")) {
            tupian8 = path;
            tu8.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));

        }

		/*try {
            bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(path)));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
//		LogDetect.send(LogDetect.DataType.basicType, "01150", "相册选择准备上传");
//		UploadFileTask_v_01150 uploadFileTask2 = new UploadFileTask_v_01150(Authentication_01150.this, handler);
//		uploadFileTask2.execute(path, Util.userid);
    }

    //压缩图片的方法
    private String compressPic(String path) {
        //01107m add start
        File fileTemp = new File(path);
        long length = fileTemp.length();
        if (length > 1024 * 200) {    //文件大于1MB就把图片长宽各压缩1/4，图片质量降低为原来的80%
            FileOutputStream fos;
            BitmapFactory.Options newOpts = new BitmapFactory.Options();
            newOpts.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(path, newOpts);
            newOpts.inJustDecodeBounds = false;
            newOpts.inSampleSize = 4;
            bitmap = BitmapFactory.decodeFile(path, newOpts);

            try {
                File newFile = new File(Environment.getExternalStorageDirectory() + "/" + fileTemp.getName());
                if (newFile.exists()) {
                    fos = new FileOutputStream(fileTemp);
                } else {    //如果是从相册发送的图片且体积过大，压缩后存放入SD根目录
                    path = Environment.getExternalStorageDirectory() + "/" + fileTemp.getName();
                    fos = new FileOutputStream(new File(path));
                }

                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                // 释放局部变量bitmap资源,防止OOM异常
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                    System.gc();
                }
            }
        }
        return path;
    }

    String mImagePath = "";

    private void takePhoto() {
        // 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            /**
             * 通过指定图片存储路径，解决部分机型onActivityResult回调 data返回为null的情况
             */
            //获取与应用相关联的路径
            String imageFilePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
            //String imageFilePath =getSDPath();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            //根据当前时间生成图片的名称
            String timestamp = "/" + formatter.format(new Date()) + ".jpg";
            File imageFile = new File(imageFilePath, timestamp);// 通过路径创建保存文件

            Uri imageFileUri;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果是7.0android系统
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, imageFile.getAbsolutePath());
                imageFileUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else {
                imageFileUri = Uri.fromFile(imageFile);
            }

            mImagePath = imageFile.getAbsolutePath();
            LogDetect.send(LogDetect.DataType.specialType, "mImagePath: ", mImagePath);
            //Uri imageFileUri = Uri.fromFile(imageFile);// 获取文件的Uri
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageFileUri);// 告诉相机拍摄完毕输出图片到指定的Uri
            startActivityForResult(intent, TAKE_PICTURE);
        } else {
            //Toast.makeText(this, "内存卡不存在！", Toast.LENGTH_LONG).show();
        }
    }


}