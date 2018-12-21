package com.net.yuesejiaoyou.redirect.ResolverB.interface4;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/14.
 */

public class HelpManager_01107B {

    public String getResult(String json) {
        JSONObject jsonObj = JSONObject.fromObject(json);
        return jsonObj.getString("result");
    }

    public ArrayList<com.net.yuesejiaoyou.redirect.ResolverB.getset.Video_01107B> getvideolist(String json) {
        JSONArray array = JSONArray.fromObject(json);

        ArrayList<com.net.yuesejiaoyou.redirect.ResolverB.getset.Video_01107B> list = new ArrayList<com.net.yuesejiaoyou.redirect.ResolverB.getset.Video_01107B>();

        for(int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            com.net.yuesejiaoyou.redirect.ResolverB.getset.Video_01107B video = new com.net.yuesejiaoyou.redirect.ResolverB.getset.Video_01107B(obj.getString("videoid"),obj.getString("shoturl"));
            list.add(video);
        }

        return list;
    }

    public ArrayList<com.net.yuesejiaoyou.redirect.ResolverB.getset.Focus_01107B> getfocusdetail(String json) {
        JSONArray array = JSONArray.fromObject(json);

        ArrayList<com.net.yuesejiaoyou.redirect.ResolverB.getset.Focus_01107B> list = new ArrayList<com.net.yuesejiaoyou.redirect.ResolverB.getset.Focus_01107B>();

        for(int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            com.net.yuesejiaoyou.redirect.ResolverB.getset.Focus_01107B video = new com.net.yuesejiaoyou.redirect.ResolverB.getset.Focus_01107B(obj.getString("photo"),obj.getString("nickname"));
            list.add(video);
        }

        return list;
    }
}
