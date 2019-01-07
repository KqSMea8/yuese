package com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity;


import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverC.interface4.RoundImageView;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.ImageUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;

import java.util.List;

import butterknife.OnClick;
import okhttp3.Call;


public class ShareActivity extends BaseActivity {

    private TextView zhanghu, xingming, shouji, num, money, daili;
    private RoundImageView touxiang;
    private TextView biaoti;

    private List<Member_01152> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        touxiang = (RoundImageView) findViewById(R.id.touxiang);//头像
        zhanghu = (TextView) findViewById(R.id.zhanghu);//提现账户
        xingming = (TextView) findViewById(R.id.xingming);//提现姓名
        shouji = (TextView) findViewById(R.id.shouji);//手机号
        num = (TextView) findViewById(R.id.num);//累计邀请
        money = (TextView) findViewById(R.id.money);//累计奖励
        biaoti = (TextView) findViewById(R.id.biaoti);//累计奖励
        daili = (TextView) findViewById(R.id.daili);//累计奖励

        getDatas();

        // 判断是否是代理
        if ("1".equals(Util.is_agent)) {
            daili.setVisibility(View.GONE);
            biaoti.setText("分成计划(代理)");
        }
    }

    public void getDatas() {
        OkHttpUtils.post(this)
                .url(URL.URL_PROMOTE)
                .addParams("param1", Util.userid)
                .build()
                .execute(new DialogCallback(this) {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String resultBean, int id) {
                        list = JSON.parseArray(resultBean, Member_01152.class);

                        if (list == null || list.size() == 0) {
                            Toast.makeText(ShareActivity.this, "集合为空", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (list.get(0).getPhoto().contains("http")) {
                            ImageUtils.loadImage(list.get(0).getPhoto(), touxiang);
                        } else {
                            ImageUtils.loadImage(Util.url + "/img/imgheadpic/" + list.get(0).getPhoto(), touxiang);
                        }

                        zhanghu.setText(list.get(0).getTixian_account());
                        xingming.setText(list.get(0).getAccount_name());
                        shouji.setText(list.get(0).getPhonenum());
                        num.setText(list.get(0).getXiaji_num() + "人");
                        LogDetect.send(LogDetect.DataType.specialType, "ShareActivity:", list.get(0).getXiaji_num());
                        money.setText(list.get(0).getAbleinvite_price() + "元");

                        TextView gongluetext = (TextView) findViewById(R.id.gongluetext);
                        gongluetext.setText(Html.fromHtml("1.所有用户都可以参与分成计划；<br><br>2.通过自己分享的专属推广链接下载注册的用户，才算“我”推荐的用户；<br><br>3.推荐的用户充值即可获得充值金额" + list.get(0).getCash_onefee() + "%的分成奖励；<br><br>4.推荐的用户提现可以获得提现金额" + list.get(0).getDvcash_onefee() + "%的分成奖励；<br><br>5.活动的最终解释权归平台所有。"));
                    }
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_tuiguang_01152;
    }

    @Override
    public int statusBarColor() {
        return R.color.vhongse;
    }

    @OnClick(R.id.back)
    public void backClick(){
        finish();
    }

    @OnClick(R.id.daili)
    public void dailiClick(){
        startActivity(DailiActivity.class);
    }

    @OnClick(R.id.tuiguang)
    public void tuiguangClick(){
        ShareHelp shareHelp1 = new ShareHelp();
        shareHelp1.showShare(ShareActivity.this, Util.invite_num);
    }

    @OnClick(R.id.gonglue)
    public void gonglueClick(){
        Intent intent1 = new Intent(ShareActivity.this, AgreeActivity.class);
        intent1.putExtra("cash_onefee", list.get(0).getCash_onefee());
        intent1.putExtra("cash_twofee", list.get(0).getCash_twofee());
        intent1.putExtra("dvcash_onefee", list.get(0).getDvcash_onefee());
        intent1.putExtra("dvcash_twofee", list.get(0).getDvcash_twofee());
        startActivity(intent1);
    }

    @OnClick(R.id.quyu1)
    public void quyuClick(){
        startActivity(TuiguangActivity.class);
    }

    @OnClick(R.id.quyu2)
    public void jiangliClick(){
        startActivity( TuijianActivity.class);
    }
}
