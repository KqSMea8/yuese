package com.net.yuesejiaoyou.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.net.yuesejiaoyou.R;
import com.net.yuesejiaoyou.redirect.ResolverD.interface4.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class XieyiActivity extends BaseActivity  {

    @BindView(R.id.xieyi)
    TextView xieyi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        xieyi.setText("  \n悦色交友平台是重庆市承轴网络科技有限公司打造的全国最大的真人互动一对一视频聊天社区，为艺人（或称“主播”）提供娱乐演艺、一对一视频聊天交友之平台。\n\n" +
                "本人具有演艺特长，致力于悦色交友平台上长期发展，以逐步提升演艺水平和知名度。\n\n" +
                "本人仔细阅读并理解本承诺书之内容，同意作出以下承诺：\n" +
                "1、本人同意并保证悦色交友平台官方审核期间，提交真实和完整的个人资料，并如实反映个人的演艺特长，如存在任何虚假、欺诈，悦色交友平台有权随时取消主播资格。\n" +
                "2、本人同意并保证在悦色交友平台直播期间，遵纪守法、绿色直播、不涉黄、不擦边，并严格遵守悦色交友平台的各项管理条例（包括但不限于《悦色交友用户信息审核规范》、《悦色交友用户视频聊天行为管理规范》等）。\n" +
                "3、本人同意并保证全力维护悦色交友品牌形象，无论在任何时间、地点均不从事任何损坏悦色交友品牌之行为。\n" +
                "4、本人同意并保证在悦色交友平台直播期间，决不进行其他平台的广告宣传，决不引导用户去其他竞品平台。\n" +
                "5、本人同意若有违反本承诺书项下的任何保证，愿意承担因此产生的所有责任，包括但不限于在悦色交友平台上被限制资源、封禁账号、违规处罚、罚扣分成、赔偿悦色交友平台或其他第三方损失以及其他相应的法律责任。\n" +
                "6、本人保证在签署本承诺书时已详细阅读并理解了本承诺书的全部内容，同意无条件遵守此承诺书，且在完全自愿情况下签订本承诺书。\n" +
                "7、入驻悦色APP的主播表示认可重庆子燃网络科技有限公司对其进行的推广行为（包括但是不限于真实姓名、网名、昵称、播出姓名、昵称、平台帐号名称等及肖像包括真实肖像及卡通形象、虚拟形象等；）\n" +
                "\n");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_xieyi;
    }

    @OnClick(R.id.back)
    public void backClick(){
        finish();
    }


}
