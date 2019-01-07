package com.net.yuesejiaoyou.redirect.ResolverB.interface4;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.net.yuesejiaoyou.redirect.ResolverB.getset.Page;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.User_data;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.Videoinfo;
import com.net.yuesejiaoyou.redirect.ResolverB.getset.photo_01162;

import com.net.yuesejiaoyou.classroot.interface4.LogDetect;
/**
 * 
 * json 转换其他对象
 *
 */
public class HelpManager_01158B {
	public static String user_id = null;
    private String citys= "{\"provinces\":[{\"name\":\"北京市\",\"citys\":[\"东城区\",\"西城区\",\"崇文区\",\"宣武区\",\"朝阳区\",\"海淀区\",\"丰台区\",\"石景山区\",\"房山区\",\"通州区\",\"顺义区\",\"昌平区\",\"大兴区\",\"怀柔区\",\"平谷区\",\"门头沟区\",\"密云区\",\"延庆区\",\"其他\"]},{\"name\":\"广东省\",\"citys\":[\"广州\",\"深圳\",\"珠海\",\"汕头\",\"韶关\",\"佛山\",\"江门\",\"湛江\",\"茂名\",\"肇庆\",\"惠州\",\"梅州\",\"汕尾\",\"河源\",\"阳江\",\"清远\",\"东莞\",\"中山\",\"潮州\",\"揭阳\",\"云浮\",\"其他\"]},{\"name\":\"上海市\",\"citys\":[\"黄浦区\",\"卢湾区\",\"徐汇区\",\"长宁区\",\"静安区\",\"普陀区\",\"闸北区\",\"虹口区\",\"杨浦区\",\"宝山区\",\"闵行区\",\"嘉定区\",\"松江区\",\"金山区\",\"青浦区\",\"南汇区\",\"奉贤区\",\"浦东新区\",\"崇明区\",\"其他\"]},{\"name\":\"天津市\",\"citys\":[\"和平区\",\"河东区\",\"河西区\",\"南开区\",\"河北区\",\"红桥区\",\"塘沽区\",\"汉沽区\",\"大港区\",\"东丽区\",\"西青区\",\"北辰区\",\"津南区\",\"武清区\",\"宝坻区\",\"静海县\",\"宁河县\",\"蓟县\",\"其他\"]},{\"name\":\"重庆市\",\"citys\":[\"渝中区\",\"大渡口区\",\"江北区\",\"南岸区\",\"北碚区\",\"渝北区\",\"巴南区\",\"长寿区\",\"双桥区\",\"沙坪坝区\",\"万盛区\",\"万州区\",\"涪陵区\",\"黔江区\",\"永川区\",\"合川区\",\"江津区\",\"九龙坡区\",\"南川区\",\"綦江县\",\"潼南区\",\"荣昌区\",\"璧山区\",\"大足区\",\"铜梁县\",\"梁平县\",\"开县\",\"忠县\",\"城口县\",\"垫江区\",\"武隆县\",\"丰都县\",\"奉节县\",\"云阳县\",\"巫溪县\",\"巫山县\",\"其他\"]},{\"name\":\"辽宁省\",\"citys\":[\"沈阳\",\"大连\",\"鞍山\",\"抚顺\",\"本溪\",\"丹东\",\"锦州\",\"营口\",\"阜新\",\"辽阳\",\"盘锦\",\"铁岭\",\"朝阳\",\"葫芦岛\",\"其他\"]},{\"name\":\"江苏省\",\"citys\":[\"南京\",\"苏州\",\"无锡\",\"常州\",\"镇江\",\"南通\",\"泰州\",\"扬州\",\"盐城\",\"连云港\",\"徐州\",\"淮安\",\"宿州\",\"其他\"]},{\"name\":\"湖北省\",\"citys\":[\"武汉\",\"黄石\",\"十堰\",\"荆州\",\"宜昌\",\"襄樊\",\"鄂州\",\"荆门\",\"孝感\",\"黄冈\",\"咸宁\",\"随州\",\"仙桃\",\"天门\",\"潜江\",\"神农架\",\"其他\"]},{\"name\":\"四川省\",\"citys\":[\"成都\",\"自贡\",\"攀枝花\",\"泸州\",\"德阳\",\"绵阳\",\"广元\",\"遂宁\",\"内江\",\"乐山\",\"南充\",\"眉山\",\"宜宾\",\"广安\",\"达州\",\"雅安\",\"巴中\",\"资阳\",\"其他\"]},{\"name\":\"陕西省\",\"citys\":[\"西安\",\"铜川\",\"宝鸡\",\"咸阳\",\"渭南\",\"延安\",\"汉中\",\"榆林\",\"安康\",\"商洛\",\"其他\"]},{\"name\":\"河北省\",\"citys\":[\"石家庄\",\"唐山\",\"秦皇岛\",\"邯郸\",\"邢台\",\"保定\",\"张家口\",\"承德\",\"沧州\",\"廊坊\",\"衡水\",\"其他\"]},{\"name\":\"山西省\",\"citys\":[\"太原\",\"大同\",\"阳泉\",\"长治\",\"晋城\",\"朔州\",\"晋中\",\"运城\",\"忻州\",\"临汾\",\"吕梁\",\"其他\"]},{\"name\":\"河南省\",\"citys\":[\"郑州\",\"开封\",\"洛阳\",\"平顶山\",\"安阳\",\"鹤壁\",\"新乡\",\"焦作\",\"濮阳\",\"许昌\",\"漯河\",\"三门峡\",\"南阳\",\"商丘\",\"信阳\",\"周口\",\"驻马店\",\"焦作\",\"其他\"]},{\"name\":\"吉林省\",\"citys\":[\"吉林\",\"四平\",\"辽源\",\"通化\",\"白山\",\"松原\",\"白城\",\"延边朝鲜自治区\",\"其他\"]},{\"name\":\"黑龙江\",\"citys\":[\"哈尔滨\",\"齐齐哈尔\",\"鹤岗\",\"双鸭山\",\"鸡西\",\"大庆\",\"伊春\",\"牡丹江\",\"佳木斯\",\"七台河\",\"黑河\",\"绥远\",\"大兴安岭地区\",\"其他\"]},{\"name\":\"内蒙古\",\"citys\":[\"呼和浩特\",\"包头\",\"乌海\",\"赤峰\",\"通辽\",\"鄂尔多斯\",\"呼伦贝尔\",\"巴彦淖尔\",\"乌兰察布\",\"锡林郭勒盟\",\"兴安盟\",\"阿拉善盟\"]},{\"name\":\"山东省\",\"citys\":[\"济南\",\"青岛\",\"淄博\",\"枣庄\",\"东营\",\"烟台\",\"潍坊\",\"济宁\",\"泰安\",\"威海\",\"日照\",\"莱芜\",\"临沂\",\"德州\",\"聊城\",\"滨州\",\"菏泽\",\"其他\"]},{\"name\":\"安徽省\",\"citys\":[\"合肥\",\"芜湖\",\"蚌埠\",\"淮南\",\"马鞍山\",\"淮北\",\"铜陵\",\"安庆\",\"黄山\",\"滁州\",\"阜阳\",\"宿州\",\"巢湖\",\"六安\",\"亳州\",\"池州\",\"宣城\"]},{\"name\":\"浙江省\",\"citys\":[\"杭州\",\"宁波\",\"温州\",\"嘉兴\",\"湖州\",\"绍兴\",\"金华\",\"衢州\",\"舟山\",\"台州\",\"丽水\",\"其他\"]},{\"name\":\"福建省\",\"citys\":[\"福州\",\"厦门\",\"莆田\",\"三明\",\"泉州\",\"漳州\",\"南平\",\"龙岩\",\"宁德\",\"其他\"]},{\"name\":\"湖南省\",\"citys\":[\"长沙\",\"株洲\",\"湘潭\",\"衡阳\",\"邵阳\",\"岳阳\",\"常德\",\"张家界\",\"益阳\",\"滨州\",\"永州\",\"怀化\",\"娄底\",\"其他\"]},{\"name\":\"广西省\",\"citys\":[\"南宁\",\"柳州\",\"桂林\",\"梧州\",\"北海\",\"防城港\",\"钦州\",\"贵港\",\"玉林\",\"百色\",\"贺州\",\"河池\",\"来宾\",\"崇左\",\"其他\"]},{\"name\":\"江西省\",\"citys\":[\"南昌\",\"景德镇\",\"萍乡\",\"九江\",\"新余\",\"鹰潭\",\"赣州\",\"吉安\",\"宜春\",\"抚州\",\"上饶\",\"其他\"]},{\"name\":\"贵州省\",\"citys\":[\"贵阳\",\"六盘水\",\"遵义\",\"安顺\",\"铜仁\",\"毕节\",\"其他\"]},{\"name\":\"云南省\",\"citys\":[\"昆明\",\"曲靖\",\"玉溪\",\"保山\",\"邵通\",\"丽江\",\"普洱\",\"临沧\",\"其他\"]},{\"name\":\"西藏\",\"citys\":[\"拉萨\",\"那曲地区\",\"昌都地区\",\"林芝地区\",\"山南区\",\"阿里区\",\"日喀则\",\"其他\"]},{\"name\":\"海南省\",\"citys\":[\"海口\",\"三亚\",\"五指山\",\"琼海\",\"儋州\",\"文昌\",\"万宁\",\"东方\",\"澄迈县\",\"定安县\",\"屯昌县\",\"临高县\",\"其他\"]},{\"name\":\"甘肃省\",\"citys\":[\"兰州\",\"嘉峪关\",\"金昌\",\"白银\",\"天水\",\"武威\",\"酒泉\",\"张掖\",\"庆阳\",\"平凉\",\"定西\",\"陇南\",\"临夏\",\"甘南\",\"其他\"]},{\"name\":\"宁夏\",\"citys\":[\"银川\",\"石嘴山\",\"吴忠\",\"固原\",\"中卫\",\"其他\"]},{\"name\":\"青海\",\"citys\":[\"西宁\",\"海东地区\",\"海北藏族自治区\",\"海南藏族自治区\",\"黄南藏族自治区\",\"果洛藏族自治区\",\"玉树藏族自治州\",\"还西藏族自治区\",\"其他\"]},{\"name\":\"新疆\",\"citys\":[\"乌鲁木齐\",\"克拉玛依\",\"吐鲁番地区\",\"哈密地区\",\"和田地区\",\"阿克苏地区\",\"喀什地区\",\"克孜勒苏柯尔克孜\",\"巴音郭楞蒙古自治区\",\"昌吉回族自治州\",\"博尔塔拉蒙古自治区\",\"石河子\",\"阿拉尔\",\"图木舒克\",\"五家渠\",\"伊犁哈萨克自治区\",\"其他\"]}]}";

    public static String getRandom() {
		String string = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";   
		StringBuffer sb = new StringBuffer();
	    int len = string.length();
	    for (int i = 0; i < 8; i++) {
	        sb.append(string.charAt(getRandom(len-1)));
	    }
	    return sb.toString();
	}

    public static List<String> getsheng() {
        String citys= "[{\"name\":\"北京市\",\"citys\":[\"东城区\",\"西城区\",\"崇文区\",\"宣武区\",\"朝阳区\",\"海淀区\",\"丰台区\",\"石景山区\",\"房山区\",\"通州区\",\"顺义区\",\"昌平区\",\"大兴区\",\"怀柔区\",\"平谷区\",\"门头沟区\",\"密云区\",\"延庆区\",\"其他\"]},{\"name\":\"广东省\",\"citys\":[\"广州\",\"深圳\",\"珠海\",\"汕头\",\"韶关\",\"佛山\",\"江门\",\"湛江\",\"茂名\",\"肇庆\",\"惠州\",\"梅州\",\"汕尾\",\"河源\",\"阳江\",\"清远\",\"东莞\",\"中山\",\"潮州\",\"揭阳\",\"云浮\",\"其他\"]},{\"name\":\"上海市\",\"citys\":[\"黄浦区\",\"卢湾区\",\"徐汇区\",\"长宁区\",\"静安区\",\"普陀区\",\"闸北区\",\"虹口区\",\"杨浦区\",\"宝山区\",\"闵行区\",\"嘉定区\",\"松江区\",\"金山区\",\"青浦区\",\"南汇区\",\"奉贤区\",\"浦东新区\",\"崇明区\",\"其他\"]},{\"name\":\"天津市\",\"citys\":[\"和平区\",\"河东区\",\"河西区\",\"南开区\",\"河北区\",\"红桥区\",\"塘沽区\",\"汉沽区\",\"大港区\",\"东丽区\",\"西青区\",\"北辰区\",\"津南区\",\"武清区\",\"宝坻区\",\"静海县\",\"宁河县\",\"蓟县\",\"其他\"]},{\"name\":\"重庆市\",\"citys\":[\"渝中区\",\"大渡口区\",\"江北区\",\"南岸区\",\"北碚区\",\"渝北区\",\"巴南区\",\"长寿区\",\"双桥区\",\"沙坪坝区\",\"万盛区\",\"万州区\",\"涪陵区\",\"黔江区\",\"永川区\",\"合川区\",\"江津区\",\"九龙坡区\",\"南川区\",\"綦江县\",\"潼南区\",\"荣昌区\",\"璧山区\",\"大足区\",\"铜梁县\",\"梁平县\",\"开县\",\"忠县\",\"城口县\",\"垫江区\",\"武隆县\",\"丰都县\",\"奉节县\",\"云阳县\",\"巫溪县\",\"巫山县\",\"其他\"]},{\"name\":\"辽宁省\",\"citys\":[\"沈阳\",\"大连\",\"鞍山\",\"抚顺\",\"本溪\",\"丹东\",\"锦州\",\"营口\",\"阜新\",\"辽阳\",\"盘锦\",\"铁岭\",\"朝阳\",\"葫芦岛\",\"其他\"]},{\"name\":\"江苏省\",\"citys\":[\"南京\",\"苏州\",\"无锡\",\"常州\",\"镇江\",\"南通\",\"泰州\",\"扬州\",\"盐城\",\"连云港\",\"徐州\",\"淮安\",\"宿州\",\"其他\"]},{\"name\":\"湖北省\",\"citys\":[\"武汉\",\"黄石\",\"十堰\",\"荆州\",\"宜昌\",\"襄樊\",\"鄂州\",\"荆门\",\"孝感\",\"黄冈\",\"咸宁\",\"随州\",\"仙桃\",\"天门\",\"潜江\",\"神农架\",\"其他\"]},{\"name\":\"四川省\",\"citys\":[\"成都\",\"自贡\",\"攀枝花\",\"泸州\",\"德阳\",\"绵阳\",\"广元\",\"遂宁\",\"内江\",\"乐山\",\"南充\",\"眉山\",\"宜宾\",\"广安\",\"达州\",\"雅安\",\"巴中\",\"资阳\",\"其他\"]},{\"name\":\"陕西省\",\"citys\":[\"西安\",\"铜川\",\"宝鸡\",\"咸阳\",\"渭南\",\"延安\",\"汉中\",\"榆林\",\"安康\",\"商洛\",\"其他\"]},{\"name\":\"河北省\",\"citys\":[\"石家庄\",\"唐山\",\"秦皇岛\",\"邯郸\",\"邢台\",\"保定\",\"张家口\",\"承德\",\"沧州\",\"廊坊\",\"衡水\",\"其他\"]},{\"name\":\"山西省\",\"citys\":[\"太原\",\"大同\",\"阳泉\",\"长治\",\"晋城\",\"朔州\",\"晋中\",\"运城\",\"忻州\",\"临汾\",\"吕梁\",\"其他\"]},{\"name\":\"河南省\",\"citys\":[\"郑州\",\"开封\",\"洛阳\",\"平顶山\",\"安阳\",\"鹤壁\",\"新乡\",\"焦作\",\"濮阳\",\"许昌\",\"漯河\",\"三门峡\",\"南阳\",\"商丘\",\"信阳\",\"周口\",\"驻马店\",\"焦作\",\"其他\"]},{\"name\":\"吉林省\",\"citys\":[\"吉林\",\"四平\",\"辽源\",\"通化\",\"白山\",\"松原\",\"白城\",\"延边朝鲜自治区\",\"其他\"]},{\"name\":\"黑龙江\",\"citys\":[\"哈尔滨\",\"齐齐哈尔\",\"鹤岗\",\"双鸭山\",\"鸡西\",\"大庆\",\"伊春\",\"牡丹江\",\"佳木斯\",\"七台河\",\"黑河\",\"绥远\",\"大兴安岭地区\",\"其他\"]},{\"name\":\"内蒙古\",\"citys\":[\"呼和浩特\",\"包头\",\"乌海\",\"赤峰\",\"通辽\",\"鄂尔多斯\",\"呼伦贝尔\",\"巴彦淖尔\",\"乌兰察布\",\"锡林郭勒盟\",\"兴安盟\",\"阿拉善盟\"]},{\"name\":\"山东省\",\"citys\":[\"济南\",\"青岛\",\"淄博\",\"枣庄\",\"东营\",\"烟台\",\"潍坊\",\"济宁\",\"泰安\",\"威海\",\"日照\",\"莱芜\",\"临沂\",\"德州\",\"聊城\",\"滨州\",\"菏泽\",\"其他\"]},{\"name\":\"安徽省\",\"citys\":[\"合肥\",\"芜湖\",\"蚌埠\",\"淮南\",\"马鞍山\",\"淮北\",\"铜陵\",\"安庆\",\"黄山\",\"滁州\",\"阜阳\",\"宿州\",\"巢湖\",\"六安\",\"亳州\",\"池州\",\"宣城\"]},{\"name\":\"浙江省\",\"citys\":[\"杭州\",\"宁波\",\"温州\",\"嘉兴\",\"湖州\",\"绍兴\",\"金华\",\"衢州\",\"舟山\",\"台州\",\"丽水\",\"其他\"]},{\"name\":\"福建省\",\"citys\":[\"福州\",\"厦门\",\"莆田\",\"三明\",\"泉州\",\"漳州\",\"南平\",\"龙岩\",\"宁德\",\"其他\"]},{\"name\":\"湖南省\",\"citys\":[\"长沙\",\"株洲\",\"湘潭\",\"衡阳\",\"邵阳\",\"岳阳\",\"常德\",\"张家界\",\"益阳\",\"滨州\",\"永州\",\"怀化\",\"娄底\",\"其他\"]},{\"name\":\"广西省\",\"citys\":[\"南宁\",\"柳州\",\"桂林\",\"梧州\",\"北海\",\"防城港\",\"钦州\",\"贵港\",\"玉林\",\"百色\",\"贺州\",\"河池\",\"来宾\",\"崇左\",\"其他\"]},{\"name\":\"江西省\",\"citys\":[\"南昌\",\"景德镇\",\"萍乡\",\"九江\",\"新余\",\"鹰潭\",\"赣州\",\"吉安\",\"宜春\",\"抚州\",\"上饶\",\"其他\"]},{\"name\":\"贵州省\",\"citys\":[\"贵阳\",\"六盘水\",\"遵义\",\"安顺\",\"铜仁\",\"毕节\",\"其他\"]},{\"name\":\"云南省\",\"citys\":[\"昆明\",\"曲靖\",\"玉溪\",\"保山\",\"邵通\",\"丽江\",\"普洱\",\"临沧\",\"其他\"]},{\"name\":\"西藏\",\"citys\":[\"拉萨\",\"那曲地区\",\"昌都地区\",\"林芝地区\",\"山南区\",\"阿里区\",\"日喀则\",\"其他\"]},{\"name\":\"海南省\",\"citys\":[\"海口\",\"三亚\",\"五指山\",\"琼海\",\"儋州\",\"文昌\",\"万宁\",\"东方\",\"澄迈县\",\"定安县\",\"屯昌县\",\"临高县\",\"其他\"]},{\"name\":\"甘肃省\",\"citys\":[\"兰州\",\"嘉峪关\",\"金昌\",\"白银\",\"天水\",\"武威\",\"酒泉\",\"张掖\",\"庆阳\",\"平凉\",\"定西\",\"陇南\",\"临夏\",\"甘南\",\"其他\"]},{\"name\":\"宁夏\",\"citys\":[\"银川\",\"石嘴山\",\"吴忠\",\"固原\",\"中卫\",\"其他\"]},{\"name\":\"青海\",\"citys\":[\"西宁\",\"海东地区\",\"海北藏族自治区\",\"海南藏族自治区\",\"黄南藏族自治区\",\"果洛藏族自治区\",\"玉树藏族自治州\",\"还西藏族自治区\",\"其他\"]},{\"name\":\"新疆\",\"citys\":[\"乌鲁木齐\",\"克拉玛依\",\"吐鲁番地区\",\"哈密地区\",\"和田地区\",\"阿克苏地区\",\"喀什地区\",\"克孜勒苏柯尔克孜\",\"巴音郭楞蒙古自治区\",\"昌吉回族自治州\",\"博尔塔拉蒙古自治区\",\"石河子\",\"阿拉尔\",\"图木舒克\",\"五家渠\",\"伊犁哈萨克自治区\",\"其他\"]}]";
        List<String> data = new ArrayList<String>();
        try {
            JSONArray jsonObj=new JSONArray(citys);

            String[] a1=new String[jsonObj.length()];
            for (int i = 0; i < jsonObj.length(); i++) {
                data.add(jsonObj.getJSONObject(i).get("name").toString());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogDetect.send(LogDetect.DataType.specialType, "this0",e);
        }

        return data;
    }

    public ArrayList<photo_01162> activity_search(String json) {
        ArrayList<photo_01162> list = new ArrayList<photo_01162>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                photo_01162 bean=new photo_01162();
                bean.setPhoto(item.getString("photo"));
                bean.setPhoto_item(item.getString("photo_item"));
                list.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public static List<String> getshi(String sheng) {
        String citys= "[{\"name\":\"北京市\",\"citys\":[\"东城区\",\"西城区\",\"崇文区\",\"宣武区\",\"朝阳区\",\"海淀区\",\"丰台区\",\"石景山区\",\"房山区\",\"通州区\",\"顺义区\",\"昌平区\",\"大兴区\",\"怀柔区\",\"平谷区\",\"门头沟区\",\"密云区\",\"延庆区\",\"其他\"]},{\"name\":\"广东省\",\"citys\":[\"广州\",\"深圳\",\"珠海\",\"汕头\",\"韶关\",\"佛山\",\"江门\",\"湛江\",\"茂名\",\"肇庆\",\"惠州\",\"梅州\",\"汕尾\",\"河源\",\"阳江\",\"清远\",\"东莞\",\"中山\",\"潮州\",\"揭阳\",\"云浮\",\"其他\"]},{\"name\":\"上海市\",\"citys\":[\"黄浦区\",\"卢湾区\",\"徐汇区\",\"长宁区\",\"静安区\",\"普陀区\",\"闸北区\",\"虹口区\",\"杨浦区\",\"宝山区\",\"闵行区\",\"嘉定区\",\"松江区\",\"金山区\",\"青浦区\",\"南汇区\",\"奉贤区\",\"浦东新区\",\"崇明区\",\"其他\"]},{\"name\":\"天津市\",\"citys\":[\"和平区\",\"河东区\",\"河西区\",\"南开区\",\"河北区\",\"红桥区\",\"塘沽区\",\"汉沽区\",\"大港区\",\"东丽区\",\"西青区\",\"北辰区\",\"津南区\",\"武清区\",\"宝坻区\",\"静海县\",\"宁河县\",\"蓟县\",\"其他\"]},{\"name\":\"重庆市\",\"citys\":[\"渝中区\",\"大渡口区\",\"江北区\",\"南岸区\",\"北碚区\",\"渝北区\",\"巴南区\",\"长寿区\",\"双桥区\",\"沙坪坝区\",\"万盛区\",\"万州区\",\"涪陵区\",\"黔江区\",\"永川区\",\"合川区\",\"江津区\",\"九龙坡区\",\"南川区\",\"綦江县\",\"潼南区\",\"荣昌区\",\"璧山区\",\"大足区\",\"铜梁县\",\"梁平县\",\"开县\",\"忠县\",\"城口县\",\"垫江区\",\"武隆县\",\"丰都县\",\"奉节县\",\"云阳县\",\"巫溪县\",\"巫山县\",\"其他\"]},{\"name\":\"辽宁省\",\"citys\":[\"沈阳\",\"大连\",\"鞍山\",\"抚顺\",\"本溪\",\"丹东\",\"锦州\",\"营口\",\"阜新\",\"辽阳\",\"盘锦\",\"铁岭\",\"朝阳\",\"葫芦岛\",\"其他\"]},{\"name\":\"江苏省\",\"citys\":[\"南京\",\"苏州\",\"无锡\",\"常州\",\"镇江\",\"南通\",\"泰州\",\"扬州\",\"盐城\",\"连云港\",\"徐州\",\"淮安\",\"宿州\",\"其他\"]},{\"name\":\"湖北省\",\"citys\":[\"武汉\",\"黄石\",\"十堰\",\"荆州\",\"宜昌\",\"襄樊\",\"鄂州\",\"荆门\",\"孝感\",\"黄冈\",\"咸宁\",\"随州\",\"仙桃\",\"天门\",\"潜江\",\"神农架\",\"其他\"]},{\"name\":\"四川省\",\"citys\":[\"成都\",\"自贡\",\"攀枝花\",\"泸州\",\"德阳\",\"绵阳\",\"广元\",\"遂宁\",\"内江\",\"乐山\",\"南充\",\"眉山\",\"宜宾\",\"广安\",\"达州\",\"雅安\",\"巴中\",\"资阳\",\"其他\"]},{\"name\":\"陕西省\",\"citys\":[\"西安\",\"铜川\",\"宝鸡\",\"咸阳\",\"渭南\",\"延安\",\"汉中\",\"榆林\",\"安康\",\"商洛\",\"其他\"]},{\"name\":\"河北省\",\"citys\":[\"石家庄\",\"唐山\",\"秦皇岛\",\"邯郸\",\"邢台\",\"保定\",\"张家口\",\"承德\",\"沧州\",\"廊坊\",\"衡水\",\"其他\"]},{\"name\":\"山西省\",\"citys\":[\"太原\",\"大同\",\"阳泉\",\"长治\",\"晋城\",\"朔州\",\"晋中\",\"运城\",\"忻州\",\"临汾\",\"吕梁\",\"其他\"]},{\"name\":\"河南省\",\"citys\":[\"郑州\",\"开封\",\"洛阳\",\"平顶山\",\"安阳\",\"鹤壁\",\"新乡\",\"焦作\",\"濮阳\",\"许昌\",\"漯河\",\"三门峡\",\"南阳\",\"商丘\",\"信阳\",\"周口\",\"驻马店\",\"焦作\",\"其他\"]},{\"name\":\"吉林省\",\"citys\":[\"吉林\",\"四平\",\"辽源\",\"通化\",\"白山\",\"松原\",\"白城\",\"延边朝鲜自治区\",\"其他\"]},{\"name\":\"黑龙江\",\"citys\":[\"哈尔滨\",\"齐齐哈尔\",\"鹤岗\",\"双鸭山\",\"鸡西\",\"大庆\",\"伊春\",\"牡丹江\",\"佳木斯\",\"七台河\",\"黑河\",\"绥远\",\"大兴安岭地区\",\"其他\"]},{\"name\":\"内蒙古\",\"citys\":[\"呼和浩特\",\"包头\",\"乌海\",\"赤峰\",\"通辽\",\"鄂尔多斯\",\"呼伦贝尔\",\"巴彦淖尔\",\"乌兰察布\",\"锡林郭勒盟\",\"兴安盟\",\"阿拉善盟\"]},{\"name\":\"山东省\",\"citys\":[\"济南\",\"青岛\",\"淄博\",\"枣庄\",\"东营\",\"烟台\",\"潍坊\",\"济宁\",\"泰安\",\"威海\",\"日照\",\"莱芜\",\"临沂\",\"德州\",\"聊城\",\"滨州\",\"菏泽\",\"其他\"]},{\"name\":\"安徽省\",\"citys\":[\"合肥\",\"芜湖\",\"蚌埠\",\"淮南\",\"马鞍山\",\"淮北\",\"铜陵\",\"安庆\",\"黄山\",\"滁州\",\"阜阳\",\"宿州\",\"巢湖\",\"六安\",\"亳州\",\"池州\",\"宣城\"]},{\"name\":\"浙江省\",\"citys\":[\"杭州\",\"宁波\",\"温州\",\"嘉兴\",\"湖州\",\"绍兴\",\"金华\",\"衢州\",\"舟山\",\"台州\",\"丽水\",\"其他\"]},{\"name\":\"福建省\",\"citys\":[\"福州\",\"厦门\",\"莆田\",\"三明\",\"泉州\",\"漳州\",\"南平\",\"龙岩\",\"宁德\",\"其他\"]},{\"name\":\"湖南省\",\"citys\":[\"长沙\",\"株洲\",\"湘潭\",\"衡阳\",\"邵阳\",\"岳阳\",\"常德\",\"张家界\",\"益阳\",\"滨州\",\"永州\",\"怀化\",\"娄底\",\"其他\"]},{\"name\":\"广西省\",\"citys\":[\"南宁\",\"柳州\",\"桂林\",\"梧州\",\"北海\",\"防城港\",\"钦州\",\"贵港\",\"玉林\",\"百色\",\"贺州\",\"河池\",\"来宾\",\"崇左\",\"其他\"]},{\"name\":\"江西省\",\"citys\":[\"南昌\",\"景德镇\",\"萍乡\",\"九江\",\"新余\",\"鹰潭\",\"赣州\",\"吉安\",\"宜春\",\"抚州\",\"上饶\",\"其他\"]},{\"name\":\"贵州省\",\"citys\":[\"贵阳\",\"六盘水\",\"遵义\",\"安顺\",\"铜仁\",\"毕节\",\"其他\"]},{\"name\":\"云南省\",\"citys\":[\"昆明\",\"曲靖\",\"玉溪\",\"保山\",\"邵通\",\"丽江\",\"普洱\",\"临沧\",\"其他\"]},{\"name\":\"西藏\",\"citys\":[\"拉萨\",\"那曲地区\",\"昌都地区\",\"林芝地区\",\"山南区\",\"阿里区\",\"日喀则\",\"其他\"]},{\"name\":\"海南省\",\"citys\":[\"海口\",\"三亚\",\"五指山\",\"琼海\",\"儋州\",\"文昌\",\"万宁\",\"东方\",\"澄迈县\",\"定安县\",\"屯昌县\",\"临高县\",\"其他\"]},{\"name\":\"甘肃省\",\"citys\":[\"兰州\",\"嘉峪关\",\"金昌\",\"白银\",\"天水\",\"武威\",\"酒泉\",\"张掖\",\"庆阳\",\"平凉\",\"定西\",\"陇南\",\"临夏\",\"甘南\",\"其他\"]},{\"name\":\"宁夏\",\"citys\":[\"银川\",\"石嘴山\",\"吴忠\",\"固原\",\"中卫\",\"其他\"]},{\"name\":\"青海\",\"citys\":[\"西宁\",\"海东地区\",\"海北藏族自治区\",\"海南藏族自治区\",\"黄南藏族自治区\",\"果洛藏族自治区\",\"玉树藏族自治州\",\"还西藏族自治区\",\"其他\"]},{\"name\":\"新疆\",\"citys\":[\"乌鲁木齐\",\"克拉玛依\",\"吐鲁番地区\",\"哈密地区\",\"和田地区\",\"阿克苏地区\",\"喀什地区\",\"克孜勒苏柯尔克孜\",\"巴音郭楞蒙古自治区\",\"昌吉回族自治州\",\"博尔塔拉蒙古自治区\",\"石河子\",\"阿拉尔\",\"图木舒克\",\"五家渠\",\"伊犁哈萨克自治区\",\"其他\"]}]";
        List<String> data = new ArrayList<String>();
        try {
            JSONArray jsonObj=new JSONArray(citys);

            String[] a1=new String[jsonObj.length()];
            for (int i = 0; i < jsonObj.length(); i++) {
                if (jsonObj.getJSONObject(i).get("name").toString().equals(sheng)){

                    String a=jsonObj.getJSONObject(i).get("citys").toString();
                    JSONArray b=new JSONArray(a);
                    for (int m = 0; m < b.length(); m++){
                        data.add(b.getString(m));
                    }
                    break;
                }

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogDetect.send(LogDetect.DataType.specialType, "this0",e);
        }

        return data;
    }




	private static int getRandom(int count) {
	    return (int) Math.round(Math.random() * (count));
	}


	public Page xunyuan(String json) {

            ArrayList<User_data> list = new ArrayList<User_data>();
            Page page = new Page();
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    if(i==jsonArray.length()-1){
                        page.setTotlePage(item.getInt("totlePage"));
                        page.setCurrent(item.getInt("pagenum"));
                    }else{
                        User_data bean=new User_data();
                        bean.setNickname(item.getString("nickname"));
                        bean.setId(item.getInt("id"));
                        //bean.setWxnumber(item.getString("wxnumber"));
                        //bean.setIsvip(item.getInt("isvip"));
                        bean.setIdentify_check(item.getInt("is_anchor"));
                        bean.setOnline(item.getInt("online"));
                        bean.setSignature(item.getString("signature"));
                        bean.setPhoto(item.getString("photo"));
                        bean.setPrice(item.getString("price"));
                        bean.setStar(item.getInt("star"));
                        //bean.setGender(item.getString("gender"));
                        //bean.setHeight(item.getString("height"));
                        //bean.setIslike(item.getInt("islike"));
                        //bean.setIsmatchmaker(item.getInt("ismatchmaker"));
                        //bean.setIslike(0);
                        list.add(bean);
                    }
                }
                page.setList(list);
                // session.page=page;
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                LogDetect.send(LogDetect.DataType.specialType, "this0",e);
            }

		return page;
	}






    public Page videolist(String json) {

        ArrayList<Videoinfo> list = new ArrayList<Videoinfo>();
        Page page = new Page();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                if(i==jsonArray.length()-1){
                    page.setTotlePage(item.getInt("totlePage"));
                    page.setCurrent(item.getInt("pagenum"));
                }else{
                    Videoinfo bean=new Videoinfo();
                    bean.setNickname(item.getString("nickname"));
                    bean.setId(item.getInt("id"));
                    bean.setIspay(item.getInt("ispay"));
                    //bean.setIszan(item.getInt("iszan"));
                    bean.setStatus(item.getInt("status"));
                    bean.setLike_num(item.getString("like_num"));
                    bean.setPrice(item.getString("price"));
                    bean.setVideo_id(item.getString("video_id"));
                    bean.setVideo_photo(item.getString("video_photo"));
                    bean.setExplain(item.getString("explain"));
                    bean.setPhoto(item.getString("photo"));
                    bean.setUser_id(item.getString("user_id"));
                    list.add(bean);
                }
            }
            page.setList(list);
            // session.page=page;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogDetect.send(LogDetect.DataType.specialType, "this0",e);
        }

        return page;
    }

    public Videoinfo video_info(String json) {
        Videoinfo bean=new Videoinfo();
        try {
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                    bean.setNickname(item.getString("nickname"));
                    bean.setId(item.getInt("id"));
                    bean.setUser_id(item.getString("user_id"));
                    bean.setIspay(item.getInt("ispay"));
                    bean.setIszan(item.getInt("iszan"));
                    bean.setStatus(item.getInt("status"));
                    bean.setLike_num(item.getString("like_num"));
                    bean.setPrice(item.getString("price"));
                    bean.setVideo_id(item.getString("video_id"));
                    bean.setVideo_photo(item.getString("video_photo"));
                    bean.setExplain(item.getString("explain"));
                    bean.setPhoto(item.getString("photo"));
                    bean.setLiulan_num(item.getInt("liulan_num"));
                    bean.setShare_num(item.getInt("share_num"));
                    bean.setIsguanzhu(0);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogDetect.send(LogDetect.DataType.specialType, "this0",e);
        }

        return bean;
    }

    public ArrayList<User_data> evaluate_list(String json) {
        ArrayList<User_data> list = new ArrayList<User_data>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                User_data bean=new User_data();
                //User_data bean=new User_data();
                bean.setNickname(item.getString("nickname"));
                //bean.setId(item.getInt("id"));
                bean.setPhoto(item.getString("photo"));
                bean.setYhping(item.getString("pl_value"));
                list.add(bean);
            }
            // session.page=page;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogDetect.send(LogDetect.DataType.specialType, "this0",e);
        }

        return list;
    }



    public User_data userinfo(String json) {

        User_data bean=new User_data();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);

                    //User_data bean=new User_data();
                    bean.setNickname(item.getString("nickname"));
                    bean.setId(item.getInt("id"));
                    //bean.setWxnumber(item.getString("wxnumber"));
                    //bean.setIsvip(item.getInt("isvip"));
                    bean.setIdentify_check(item.getInt("is_anchor"));
                    bean.setOnline(item.getInt("online"));
                    bean.setSignature(item.getString("signature"));
                    bean.setPhoto(item.getString("photo"));
                    bean.setPrice(item.getString("price"));
                    bean.setStar(item.getInt("star"));
                    bean.setLab_tab(item.getString("lab_tab"));
                    bean.setPicture(item.getString("pictures"));
                    bean.setIslike(item.getInt("isguanzhu"));
                    bean.setLikep(item.getString("likep"));
                    bean.setFans_num(item.getString("fans_num"));
                bean.setImage_label(item.getString("image_label"));
                bean.setYhping(item.getString("yhping"));
                bean.setAddress(item.getString("city"));
                bean.setHeight(item.getString("height"));
                bean.setWeight(item.getString("weight"));
                bean.setConstellation(item.getString("constellation"));
                bean.setGood_num(item.getString("good_num"));
                bean.setBad_num(item.getString("bad_num"));
                    //bean.setGender(item.getString("gender"));
                    //bean.setHeight(item.getString("height"));
                    //bean.setIslike(item.getInt("islike"));
                    //bean.setIsmatchmaker(item.getInt("ismatchmaker"));
                    //bean.setIslike(0);

            }
            // session.page=page;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            LogDetect.send(LogDetect.DataType.specialType, "this0",e);
        }

        return bean;
    }





	
	
	/**
     * 获取外网的IP(要访问Url，要放到后台线程里处理)
     *
     * @param @return
     * @return String
     * @throws
     * @Title: GetNetIp
     * @Description:
     */
    public static String getNetIp() {
        URL infoUrl = null;
        InputStream inStream = null;
        String ipLine = "";
        HttpURLConnection httpConnection = null;
        try {
//          infoUrl = new URL("http://ip168.com/");
            infoUrl = new URL("http://pv.sohu.com/cityjson?ie=utf-8");
            URLConnection connection = infoUrl.openConnection();
            httpConnection = (HttpURLConnection) connection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                inStream = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inStream, "utf-8"));
                StringBuilder strber = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null){
                    strber.append(line + "\n");
                }
                Pattern pattern = Pattern
                        .compile("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))");
                Matcher matcher = pattern.matcher(strber.toString());
                if (matcher.find()) {
                    ipLine = matcher.group();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inStream.close();
                httpConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        Log.e("getNetIp", ipLine);
        return ipLine;
    }
}
