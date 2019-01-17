package com.net.yuesejiaoyou.redirect.ResolverD.interface4.fragment;

import java.util.ArrayList;
import java.util.List;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
import com.net.yuesejiaoyou.classroot.interface4.util.Util;
import com.net.yuesejiaoyou.classroot.interface4.openfire.uiface.CoustomerActivity;
import com.net.yuesejiaoyou.redirect.ResolverA.getset.Member_01152;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.CleanCacheManager;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.HelpActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.MeiyanActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.BlackNameActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.IntimateActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.ModifyAvaterActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.ShareActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.PriceActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.FansActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.ShareHelp;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.URL;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.LoginActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.adapter.MineAdapter;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.bean.MineBean;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.ImageUtils;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.utils.YDDialog;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.widget.GridItemDecoration;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.RechargeActivity;

///////////////////////A区调用C区的相关文件类引入
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.WalletActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.EditActivity;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.DailiActivity;
///////////////////////A区调用B区的相关文件类引入
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.activity.MyVideoActivity;
import com.net.yuesejiaoyou.redirect.ResolverB.uiface.GroupMessageActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.DialogCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;


public class ZhuboFragment extends Fragment implements BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.xingji)
    ImageView xingji;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.user_name)
    TextView tvName;
    @BindView(R.id.dailishang)
    TextView tvDaili;
    @BindView(R.id.my_fans)
    TextView tvFans;
    @BindView(R.id.price)
    TextView tvPrice;
    @BindView(R.id.v_currency)
    TextView tvCoin;

    private View mBaseView;

    private MineAdapter adapter;
    private MineBean wuraoBean;

    private String ybprice;
    private String reward_percent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.fragment_zhubo, null);
        ButterKnife.bind(this, mBaseView);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        adapter = new MineAdapter(getItems());
        recyclerView.setAdapter(adapter);

        tvDaili.setVisibility(View.GONE);

        recyclerView.addItemDecoration(new GridItemDecoration(getContext()));
        adapter.setOnItemClickListener(this);

        wdarao();
        return mBaseView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    private List<MineBean> getItems() {
        List<MineBean> list = new ArrayList<>();
        list.add(new MineBean(R.drawable.shipin, "小视频"));
        list.add(new MineBean(R.drawable.qinmi, "与我亲密的"));
        list.add(new MineBean(R.drawable.qianbao, "我的钱包"));
        list.add(new MineBean(R.drawable.fencheng, "分享赚钱"));
        list.add(new MineBean(R.drawable.qunfa, "群发私信"));
        list.add(new MineBean(R.drawable.meiyan, "美颜设置"));
        wuraoBean = new MineBean(R.drawable.wuraonoo, "在线");
        list.add(wuraoBean);
        list.add(new MineBean(R.drawable.heimingdan, "黑名单"));
        list.add(new MineBean(R.drawable.kefu, "在线客服"));
        list.add(new MineBean(R.drawable.help, "帮助中心"));
        list.add(new MineBean(R.drawable.huancun, "清除缓存"));
        list.add(new MineBean(R.drawable.tuichu, "安全退出"));
        return list;
    }

    public void getData() {
        OkHttpUtils.post(this)
                .url(URL.URL_ZHUBOZHONGXING)
                .addParams("param1", Util.userid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        List<Member_01152> list = JSON.parseArray(response, Member_01152.class);
                        if (list == null || list.size() == 0) {
                            Toast.makeText(getContext(), "网络出错", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (list.get(0).getPhoto().contains("http")) {
                            ImageUtils.loadImage(list.get(0).getPhoto(), touxiang);
                        }
                        ImageUtils.loadImage("http://116.62.220.67:8090/img/star" + list.get(0).getStar_ranking() + ".png", xingji);
                        if (list.get(0).getdailishang() != 0) {
                            tvDaili.setVisibility(View.VISIBLE);
                            tvDaili.setText("DL." + list.get(0).getdailishang());
                        }
                        tvName.setText(list.get(0).getNickname() + "(ID:" + list.get(0).getId() + ")");
                        tvFans.setText(list.get(0).getFans_num() + "");
                        tvPrice.setText(list.get(0).getPrice() + "悦币/分钟");
                        tvCoin.setText(list.get(0).getMoney() + "");

                        ybprice = list.get(0).getPrice();
                        reward_percent = list.get(0).getReward_percent();
                    }
                });
    }

    @OnClick(R.id.bianji1)
    public void editClick() {
        startActivity(EditActivity.class);
    }

    @OnClick(R.id.touxiang)
    public void avaterClick() {
        startActivity(ModifyAvaterActivity.class);
    }

    @OnClick(R.id.user_my_like_layout)
    public void fansClick() {
        startActivity(FansActivity.class);
    }

    @OnClick(R.id.vmin)
    public void priceClick() {
        Intent intent = new Intent(getContext(), PriceActivity.class);
        intent.putExtra("ybprice", ybprice);
        intent.putExtra("reward_percent", reward_percent);
        startActivityForResult(intent, 111);
    }

    private void wdarao() {
        OkHttpUtils.post(this)
                .url(URL.URL_GETWURAO)
                .addParams("param1", Util.userid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.equals("勿扰")) {//勿打扰
                            wuraoBean.setImage(R.drawable.wuraoyes);
                            wuraoBean.setName(response);
                        } else if (response.equals("在线")) {//在线
                            wuraoBean.setImage(R.drawable.wuraonoo);
                            wuraoBean.setName(response);
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }


    private void xiugai() {
        OkHttpUtils.post(this)
                .url(URL.URL_XIUGAI)
                .addParams("param1", Util.userid)
                .build()
                .execute(new DialogCallback(getContext()) {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (response.equals("勿扰")) {//勿打扰
                            wuraoBean.setImage(R.drawable.wuraoyes);
                            wuraoBean.setName(response);

                            Toast.makeText(getActivity(), "已修改为勿扰", Toast.LENGTH_SHORT).show();
                        } else if (response.equals("在线")) {//在线
                            wuraoBean.setImage(R.drawable.wuraonoo);
                            wuraoBean.setName(response);
                            Toast.makeText(getActivity(), "已修改为在线", Toast.LENGTH_SHORT).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }


    @OnClick(R.id.dailishang)
    public void dailiClick() {
        startActivity(DailiActivity.class);
    }

    @OnClick(R.id.chongzhi)
    public void chongzhiClick() {
        if (Util.iszhubo.equals("0")) {
            startActivity(RechargeActivity.class);
        } else {
            Toast.makeText(getActivity(), "主播暂无此功能", Toast.LENGTH_SHORT).show();
        }
    }

    private void startActivity(Class<?> cls) {
        Intent intent = new Intent(getContext(), cls);
        startActivity(intent);
    }


    private void clearCache() {
        try {
            CleanCacheManager.getTotalCacheSize(getActivity());
            CleanCacheManager.clearAllCache(getActivity());
            CleanCacheManager.getTotalCacheSize(getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter1, View view, int position) {
        MineBean mineBean = adapter.getData().get(position);
        Intent intent;
        switch (mineBean.getName()) {
            case "小视频":
                startActivity(MyVideoActivity.class);
                break;
            case "与我亲密的":
                //startActivity(Vliao_wodeqinmibang_01178.class);
                intent = new Intent();
                intent.setClass(getContext(), IntimateActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", Util.userid);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case "我的钱包":
                startActivity(WalletActivity.class);
                break;
            case "分享赚钱":
                startActivity(ShareActivity.class);
                break;
            case "群发私信":
                startActivity(GroupMessageActivity.class);
                break;
            case "美颜设置":
                startActivity(MeiyanActivity.class);
                break;
            case "在线":
            case "勿扰":
                xiugai();
                break;
            case "黑名单":
                startActivity(BlackNameActivity.class);
                break;
            case "在线客服":
                intent = new Intent();
                intent.setClass(getContext(), CoustomerActivity.class);//客服
                intent.putExtra("id", "40");
                intent.putExtra("name", "小客服");
                intent.putExtra("headpic", "http://116.62.220.67:8090/img/imgheadpic/launch_photo.png");
                startActivity(intent);
                break;
            case "帮助中心":
                startActivity(HelpActivity.class);
                break;
            case "清除缓存":
                clearCache();
                Toast.makeText(getActivity(), "清除成功", Toast.LENGTH_SHORT).show();
                break;
            case "安全退出":
                new YDDialog.Builder(getContext())
                        .setMessage("是否确定退出？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                OkHttpUtils.post(this)
                                        .url(URL.URL_CHANGESTATUS)
                                        .addParams("param1", Util.userid)
                                        .addParams("param2", 0)
                                        .build()
                                        .execute(new DialogCallback(getContext()) {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                loginout();
                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                loginout();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
        }
    }

    private void loginout() {
        SharedPreferences share = getActivity().getSharedPreferences("Acitivity", Activity.MODE_PRIVATE);
        share.edit().putString("logintype", "").apply();
        share.edit().putString("openid", "").apply();
        share.edit().putString("username", "").apply();
        share.edit().putString("password", "").apply();
        JPushInterface.setAlias(getActivity().getApplicationContext(), 1, "0");    // 退出登录后，撤销极光别名，就收不到一对一视频推送了
        ShareHelp share1 = new ShareHelp();
        share1.wx_delete();
        LogDetect.send(LogDetect.DataType.specialType, "share: ", share.getString("userid", ""));
        Util.userid = "0";
        Util.imManager.xmppDisconnect();

        Toast.makeText(getActivity(), "退出成功", Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent();
        intent1.setClass(getActivity(), LoginActivity.class);
        startActivity(intent1);
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            getData();
        }
    }
}
