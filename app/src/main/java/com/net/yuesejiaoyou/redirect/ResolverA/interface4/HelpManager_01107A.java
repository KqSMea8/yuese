package com.net.yuesejiaoyou.redirect.ResolverA.interface4;



import com.net.yuesejiaoyou.redirect.ResolverA.getset.Update;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/14.
 */

public class HelpManager_01107A {

    public String getResult(String json) {
        JSONObject jsonObj = JSONObject.fromObject(json);
        return jsonObj.getString("result");
    }


    public ArrayList<Update> check_update(String json) {
        ArrayList<Update> list = new ArrayList<Update>();
        try {
            JSONArray jsonArray = JSONArray.fromObject(json);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                Update bean = new Update();
                bean.setUpdateWhat(item.getString("update_what"));
                bean.setIsUpdate(item.getString("isUpdate"));
                bean.setDownurl(item.getString("downurl"));
                bean.setVsion(item.getString("vsion"));
                bean.setYuming(item.getString("yuming"));
                list.add(bean);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
