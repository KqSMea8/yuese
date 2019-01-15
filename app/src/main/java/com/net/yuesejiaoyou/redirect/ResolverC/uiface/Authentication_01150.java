package com.net.yuesejiaoyou.redirect.ResolverC.uiface;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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
import com.bilibili.boxing.model.config.BoxingCropOption;
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
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

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

public class Authentication_01150 extends BaseActivity implements OnClickListener {

    private Context mContext;
    private static final int REQUEST_CODE = 1024;
    private static final int COMPRESS_REQUEST_CODE = 2048;
    private ImageView back,xczhaipianup;


    private int tt = 0;
    private static final int TAKE_PICTURE = 0x000001;
   private String share_1 = "女", share_2 = "不限", share_3 = "170cm", share_4 = "双子座", share_5 = "2000元以下", share_6 = "恋爱中", share_7 = "未婚", share_8 = "50 KG", share_9 = "计算机/互联网/通信", share_10 = "汉族", share_11 = "不限", share_12 = "不限", share_13 = "不限", share_14 = "不限", share_15 = "不限";


    private int c;


    private LinearLayout  ll_sexname,ll_nickname, ll_phonenum,ll_wxnum, ll_height, ll_weight, ll_zhiye, ll_xingzuo, ll_city, ll_intro,
            ll_label, ll_personality;
    private TextView nickname, sexname_text,nickname_text, phonenum, phonenum_text,wxnum_text, height, height_text, weight, weight_text, xingzuo, xingzuo_text, zhiye_text, city, city_text, intro, intro_text,
            label, label_text, personality, personality_text, submit;
    private PopupWindow popupWindow;
    Intent intent = new Intent();
    String nicheng_1, phonenum_1, intro_1, signature_1, label_1, label_2;


    private ImageResizer m_imageWork = null;

    private int num = 0;
    private int cishu = 0;
    private String image = "";
    private String xcimage = "";
    private MyGridview noScrollgridview;
    private GridAdapter adapter;
    private ArrayList<String> imglist = new ArrayList<String>();
    private String jobp = "";
    private boolean isxiancheng=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_imageWork = new ImageResizer(this, 200, 200);

        mContext = Authentication_01150.this;

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);


        noScrollgridview = (MyGridview) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        // adapter.update();
        noScrollgridview.setAdapter(adapter);
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (imglist.size() > position && imglist.size() != 8) {
                    showPopupspWindow_rp(position, ll_nickname);
                } else {
                    if (imglist.size() == 8) {
                        showPopupspWindow_rp(position, ll_nickname);
                    } else {
                        isxiancheng=false;
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

        xczhaipianup= (ImageView) findViewById(R.id.xczhaipianup);
        xczhaipianup.setOnClickListener(this);

        ll_phonenum = (LinearLayout) findViewById(R.id.ll_phonenum);//手机号码
        ll_phonenum.setOnClickListener(this);
        phonenum = (TextView) findViewById(R.id.phonenum);
        phonenum_text = (TextView) findViewById(R.id.phonenum_text);

        ll_wxnum = (LinearLayout) findViewById(R.id.ll_wxnum);//微信号
        ll_wxnum.setOnClickListener(this);
        wxnum_text = (TextView) findViewById(R.id.wxnum_text);



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
        xingzuo = (TextView) findViewById(R.id.xingzuo);
        xingzuo_text = (TextView) findViewById(R.id.xingzuo_text);


        ll_zhiye = (LinearLayout) findViewById(R.id.ll_zhiye);//星座
        ll_zhiye.setOnClickListener(this);
        zhiye_text = (TextView) findViewById(R.id.zhiye_text);


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


    }

    @Override
    protected int getContentView() {
        return R.layout.renzheng_v1_01150;
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
        WindowManager.LayoutParams lp = Authentication_01150.this.getWindow().getAttributes();
        lp.alpha = 0.5f;
        Authentication_01150.this.getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //popupWindow.showAsDropDown(parent, 0, 0,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
        popupWindow.showAtLocation(parent, Gravity.CENTER | Gravity.BOTTOM, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = Authentication_01150.this.getWindow().getAttributes();
                lp.alpha = 1f;
                Authentication_01150.this.getWindow().setAttributes(lp);
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
        BoxingConfig singleCropImgConfig = new BoxingConfig(BoxingConfig.Mode.SINGLE_IMG).needCamera(R.drawable.ic_boxing_camera_white).withCropOption(new BoxingCropOption(destUri))
                .withMediaPlaceHolderRes(R.drawable.moren);
        Boxing.of(singleCropImgConfig).withIntent(this, BoxingActivity.class).start(this, REQUEST_CODE);
    }


    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private int selectedPosition = -1;
        private boolean shape;

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

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            //if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);

                holder.item_del_image = (TextView) convertView
                        .findViewById(R.id.item_del_image);
                convertView.setTag(holder);
            //} else {
            //    holder = (ViewHolder) convertView.getTag();
            //}
//			if(((MyGridview) parent).isOnMeasure){
//	            //如果是onMeasure调用的就立即返回
//	            return convertView;
//	        }

            if (position == imglist.size()) {
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                holder.item_del_image.setVisibility(View.GONE);
//				if (position == 8) {
//					holder.image.setVisibility(View.GONE);
//				}
            } else {
                if (position == 0) {
                    holder.item_del_image.setVisibility(View.VISIBLE);
                } else {
                    holder.item_del_image.setVisibility(View.GONE);
                }

//				holder.image.setImageBitmap(Bimp.tempSelectBitmap1.get(position)
//						.getBitmap());
                //m_imageWork.loadImage(Bimp.tempSelectBitmap1.get(position).getImagePath(), holder.image, 200, 200);
                //holder.image.setImageURI(Bimp.tempSelectBitmap1.get(position).getImagePath());
                //holder.image.setImageBitmap(BitmapFactory.decodeFile(Bimp.tempSelectBitmap1.get(position).getImagePath()));
//本地文件
                //File file = new File(imglist.get(position));
                //加载图片
                //Glide.with(Authentication_01150.this).load(file).into(holder.image);

                holder.image.setImageBitmap(m_imageWork.processBitmapNet(imglist.get(position), 200, 200));

            }
//			holder.itemdel.setOnClickListener(new OnClickListener() {
//				public void onClick(View v) {
//					Bimp.tempSelectBitmap1.remove(position);
//
//					adapter.notifyDataSetChanged();
//				}
//			});
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
            case R.id.ll_wxnum://微信号
                intent = new Intent();
                intent.setClass(this, Authen_wx_01150.class);
                startActivityForResult(intent, 251);
                break;



            case R.id.ll_phonenum://手机号码
                intent = new Intent();
                intent.setClass(this, Authen_phonenum_01150.class);
                startActivityForResult(intent, 202);
                break;
            case R.id.xczhaipianup:
                //isxiancheng=true;
                takePhoto();
                //pickIcon();
                break;



            case R.id.ll_sexname://性别
                tt = 20;
                sharePopupWindow(tt, view);
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
            case R.id.ll_zhiye://职业
                //tt = 4;
                //sharePopupWindow(tt, view);
                intent = new Intent();
                intent.setClass(this, Vliao_job_01168.class);
                startActivityForResult(intent, 206);
                break;


            case R.id.ll_city://所在城市
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

                if (imglist.size() == 0) {//必传图片
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                } else if (nickname_text.getText().toString().equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                } else if (height_text.getText().toString().equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                } else if (weight_text.getText().toString().equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                } else if (xingzuo_text.getText().toString().equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                }else if (sexname_text.getText().toString().equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                }else if (phonenum_text.getText().toString().equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                }else if (wxnum_text.getText().toString().equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                }else if (xcimage.equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                }else if (intro_text.getText().toString().equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                } else if (label_text.getText().toString().equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                } else if (personality_text.getText().toString().equals("")) {
                    Toast.makeText(Authentication_01150.this, "不完整的认证信息", Toast.LENGTH_SHORT).show();
                }  else {
                    pdialog = ProgressDialog.show(Authentication_01150.this, "上传中...", "系统正在处理您的请求！");

                    for (int i = 0; i < imglist.size(); i++) {
                        if (!imglist.get(i).equals("")) {
                            LogDetect.send(LogDetect.DataType.specialType, "01150 pic=====&&&&&&&======:", imglist.get(i));
                            UploadFileTask_v_01150 uploadFileTask = new UploadFileTask_v_01150(Authentication_01150.this, handler);
                            uploadFileTask.execute(imglist.get(i), Util.userid);
                        }
                    }
                }
                break;

        }
    }
    private ProgressDialog pdialog;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 101:
                    String path = (String) msg.obj;
                    if(isxiancheng){
                        xcimage=Util.url+"/img/imgheadpic/" + path;
                        isxiancheng=false;
                        if(pdialog!=null){
                            pdialog.dismiss();
                        }
                        return;
                    }


                    LogDetect.send(LogDetect.DataType.specialType, "num：===========================", num);
                        if (cishu == 0) {
                            image = Util.url+"/img/imgheadpic/" + path;
                        } else {
                            image = image + ","+Util.url+"/img/imgheadpic/" + path;
                        }
                        cishu++;
//                        UploadFileTask_v_01150 uploadFileTask = new UploadFileTask_v_01150(Authentication_01150.this, handler);
//                        uploadFileTask.execute(imglist.get(cishu), Util.userid);

					if (cishu == imglist.size()) {
						String[] paramsMap2 = {Util.userid, image, nickname_text.getText().toString(), phonenum_text.getText().toString(), height_text.getText().toString(),
								weight_text.getText().toString(), xingzuo_text.getText().toString(), city_text.getText().toString(), intro_text.getText().toString(),
								label_text.getText().toString(), personality_text.getText().toString(), sexname_text.getText().toString(),wxnum_text.getText().toString(),xcimage};//city_text.getText().toString()
						String mode2 = "renzheng";
						LogDetect.send(LogDetect.DataType.specialType, "cishu：===========================", paramsMap2);
						UsersThread_01150 b2 = new UsersThread_01150(mode2, paramsMap2, handler);
						Thread t2 = new Thread(b2.runnable);
						t2.start();
						if(pdialog!=null){
							pdialog.dismiss();
						}
					}

                    break;
                case 202:
                    String json = (String) msg.obj;
                    if (json.contains("success")) {


                        showPopupspWindow(back);

                        //Toast.makeText(Authentication_01150.this, "提交成功，请等待审核！", Toast.LENGTH_SHORT).show();
                        //finish();
                    } else {
                        //Toast.makeText(Authentication_01150.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                    break;


            }
        }
    };


    PopupWindow mPopWindow;
    public void showPopupspWindow(View parent) {
        // 加载布局
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.zp_renzheng, null);



        //点击确定兑换
        TextView queding = (TextView)layout.findViewById(R.id.updateon);
        queding.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mPopWindow = new PopupWindow(layout, ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        mPopWindow.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable(null, ""));
        //WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //@SuppressWarnings("deprecation")
        // 获取xoff
        // int xpos = manager.getDefaultDisplay().getWidth() / 2
        //		- mPopWindow.getWidth() / 2;
        // xoff,yoff基于anchor的左下角进行偏移。
        // popupWindow.showAsDropDown(parent, 0, 0);
        mPopWindow.showAtLocation(parent, Gravity.CENTER | Gravity.CENTER,252, 0);
        // 监听

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                finish();
            }
        });

    }



    String sh1 = "Default", sh2 = "Default", sh3 = "Default";
    PopupWindow popup;

    public void showPopaddress(final int ab, View parent) {
        sh1 = "默认";
        sh2 = "默认";
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.doupopview_01066, null);
        TextView stitle = (TextView) layout.findViewById(R.id.stitle);
        stitle.setText("请选择地址");
        PickView minute_pv = (PickView) layout.findViewById(R.id.minute_pv);
        final PickView minute_pv1 = (PickView) layout.findViewById(R.id.minute_pv1);
        final PickView minute_pv2 = (PickView) layout.findViewById(R.id.minute_pv2);



        Log.v("TT", "---- 省 ----");
        List<String> data1 = HelpManager_01066.getsheng(Authentication_01150.this, "中国");   //new ArrayList<String>();
        //data1.add("默认");
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
                    data2 = HelpManager_01066.getCity(Authentication_01150.this, text);
                    Log.v("TT", "city: (" + data2.size() + ")");
                    for (String item : data2) {
                        Log.v("TT", item);
                    }
                    //data2.add("默认");
                    minute_pv2.setData(data2);
                    minute_pv2.setSelected(3);
                    sh3 = data2.get(3);
                } else {
                    sh2 = "默认";
                }
            }
        });

        Log.v("TT", "---- 城市 ----");
        List<String> data2 = HelpManager_01066.getCity(Authentication_01150.this, "山西");
        //data2.add("默认");
        minute_pv2.setData(data2);
        minute_pv2.setSelected(3);
        minute_pv2.setOnSelectListener(new PickView.onSelectListener() {

            @Override
            public void onSelect(String text) {

                sh3 = text;
                //sh2=text;
                //----------------------

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
                if (!sh2.equals("默认")) {

                    if (ab == 1) {
                        city_text.setText(sh2 + " " + sh3);
                    } else if (ab == 2) {
                        city_text.setText(sh2 + " " + sh3);
                    } else if (ab == 3) {
                        city_text.setText(sh2 + " " + sh3);
                    } else if (ab == 4) {
                        city_text.setText(sh2 + " " + sh3);
                    }
                } else {

                    if (ab == 1) {
                        city_text.setText(sh1);
                    } else if (ab == 2) {
                        city_text.setText(sh1);
                    } else if (ab == 3) {
                        city_text.setText(sh1);
                    } else if (ab == 4) {
                        city_text.setText(sh1);
                    }

                }
                popup.dismiss();
            }
        });

        popup = new PopupWindow(layout, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        // 控制键盘是否可以获得焦点
        popup.setFocusable(true);
        // 设置popupWindow弹出窗体的背景
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popup.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // xoff,yoff基于anchor的左下角进行偏移。
        // popupWindow.showAsDropDown(parent, 0, 0);
        popup.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        // 监听
        //popupWindow.showAsDropDown(parent, 0, 0);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                ll_city.setClickable(true);
                getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getWindow().setAttributes(lp);
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
        }else if (num == 20) {
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
                }else if (num == 20) {
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
        WindowManager.LayoutParams lp = Authentication_01150.this.getWindow().getAttributes();
        lp.alpha = 0.4f;
        Authentication_01150.this.getWindow().setAttributes(lp);
        popup.setBackgroundDrawable(new BitmapDrawable(null, ""));
        WindowManager manager = (WindowManager) Authentication_01150.this.getSystemService(Context.WINDOW_SERVICE);
        // xoff,yoff基于anchor的左下角进行偏移。
        // popupWindow.showAsDropDown(parent, 0, 0);
        popup.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER, 0, 0);
        // 监听
        //popupWindow.showAsDropDown(parent, 0, 0);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = Authentication_01150.this.getWindow().getAttributes();
                lp.alpha = 1f;
                Authentication_01150.this.getWindow()
                        .addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                Authentication_01150.this.getWindow().setAttributes(lp);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==TAKE_PICTURE && resultCode == RESULT_OK)
        {
            String imagePath="";
            Uri uri = null;
            if (data != null && data.getData() != null) {// 有数据返回直接使用返回的图片地址
                /*uri = data.getData();
                Cursor cursor = ((FragmentActivity) mContext).getContentResolver().query(uri, proj, null,
                        null, null);
                if (cursor == null) {//出现如小米等手机返回的绝对路径错误时,自己拼出路径
                    uri = getUri(mContext, data);
                }*/
                LogDetect.send(LogDetect.DataType.specialType, "mImagePath: ", mImagePath);
                imagePath = data.getData().getEncodedPath();

            } else {// 无数据使用指定的图片路径
                imagePath = mImagePath;
                LogDetect.send(LogDetect.DataType.specialType, "mImagePath: ", mImagePath);
            }
            isxiancheng=true;
            xczhaipianup.setImageBitmap(m_imageWork.processBitmapNet(imagePath, 200, 200));
            pdialog = ProgressDialog.show(Authentication_01150.this, "上传中...", "系统正在处理您的请求！");
            UploadFileTask_v_01150 uploadFileTask = new UploadFileTask_v_01150(Authentication_01150.this, handler);
            uploadFileTask.execute(imagePath, Util.userid);
            return;
        }


        if (resultCode == RESULT_OK) {
            //if (mRecyclerView == null || mAdapter == null) {
            //	return;
            //}
            //mRecyclerView.setVisibility(View.VISIBLE);
            final ArrayList<BaseMedia> medias = Boxing.getResult(data);
            BaseMedia baseMedia = medias.get(0);
            if (requestCode == REQUEST_CODE) {
                //mAdapter.setList(medias);
                String path;
                if (baseMedia instanceof ImageMedia) {
                    path = ((ImageMedia) baseMedia).getThumbnailPath();
                } else {
                    path = baseMedia.getPath();
                }
                Log.e("ggggg", path);
                LogDetect.send(LogDetect.DataType.specialType, "--path--a:--", path);
                //Toast.makeText(Authentication_01150.this,path,Toast.LENGTH_SHORT).show();
                if(isxiancheng){
                    xczhaipianup.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));
                    pdialog = ProgressDialog.show(Authentication_01150.this, "上传中...", "系统正在处理您的请求！");
                    UploadFileTask_v_01150 uploadFileTask = new UploadFileTask_v_01150(Authentication_01150.this, handler);
                    uploadFileTask.execute(path, Util.userid);
                }else{
                    imglist.add(path);
                    adapter.notifyDataSetChanged();
                }

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
                if(isxiancheng){
                    xczhaipianup.setImageBitmap(m_imageWork.processBitmapNet(path, 200, 200));
                }else{
                    imglist.add(path);
                    adapter.notifyDataSetChanged();
                }


            }
        }

        if (resultCode == 201 || resultCode == 202 || resultCode == 203 || resultCode == 204 || resultCode == 205 || resultCode == 206 || resultCode == 251) {
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

                case 251:
                    LogDetect.send(LogDetect.DataType.specialType, "昵称部分===01150====", "回到编辑资料页面201");
                    Bundle bundle10 = data.getExtras();
                    String wxnum = bundle10.getString("wxnum");
                    LogDetect.send(LogDetect.DataType.specialType, "昵称：", nicheng_1);
                    if (!(wxnum.equals(""))) {
                        wxnum_text.setText(wxnum);
                    }
                    break;


                case 206:
                    LogDetect.send(LogDetect.DataType.specialType, "昵称部分===01150====", "回到编辑资料页面201");
                    Bundle bundle1 = data.getExtras();
                    String job1 = bundle1.getString("job");
                    LogDetect.send(LogDetect.DataType.specialType, "昵称：", nicheng_1);
                    if (!(job1.equals(""))) {
                        zhiye_text.setText(job1);
                        jobp = bundle1.getString("photo_job");
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
				/*	label_2 = data.getStringExtra("biaoqian2");
					LogDetect.send(LogDetect.DataType.specialType, "形象标签部分——01150()", intro_1);
					if (!label_1.equals("") && !label_2.equals("")) {
						label_text.setText(label_1 + "," + label_2);
					} else if (!label_1.equals("") && label_2.equals("")) {
						label_text.setText(label_1);
					} else if (label_1.equals("") && !label_2.equals("")) {
						label_text.setText(label_2);
					}*/
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
//			}

//		}
            }
        }
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