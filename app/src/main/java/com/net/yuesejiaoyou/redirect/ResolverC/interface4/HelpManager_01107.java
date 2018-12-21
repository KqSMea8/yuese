package com.net.yuesejiaoyou.redirect.ResolverC.interface4;

/*import com.example.vliao.getset.Focus_01107;
import com.example.vliao.getset.Video_01107;*/

import com.net.yuesejiaoyou.redirect.ResolverC.getset.Focus_01107;
import com.net.yuesejiaoyou.redirect.ResolverC.getset.Video_01107;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/14.
 */

public class HelpManager_01107 {

    public String getResult(String json) {
        JSONObject jsonObj = JSONObject.fromObject(json);
        return jsonObj.getString("result");
    }

    public ArrayList<Video_01107> getvideolist(String json) {
        JSONArray array = JSONArray.fromObject(json);

        ArrayList<Video_01107> list = new ArrayList<Video_01107>();

        for(int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Video_01107 video = new Video_01107(obj.getString("videoid"),obj.getString("shoturl"));
            list.add(video);
        }

        return list;
    }

    public ArrayList<Focus_01107> getfocusdetail(String json) {
        JSONArray array = JSONArray.fromObject(json);

        ArrayList<Focus_01107> list = new ArrayList<Focus_01107>();

        for(int i = 0; i < array.size(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Focus_01107 video = new Focus_01107(obj.getString("attention_id"), obj.getString("photo"),obj.getString("nickname"));
            list.add(video);
        }

        return list;
    }
}
