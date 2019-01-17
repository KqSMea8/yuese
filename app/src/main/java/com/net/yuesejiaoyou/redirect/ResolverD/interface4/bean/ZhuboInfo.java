package com.net.yuesejiaoyou.redirect.ResolverD.interface4.bean;

import android.os.Parcel;
import android.os.Parcelable;



public class ZhuboInfo implements Parcelable {

    // 对于direct的枚举量
    public static final int GUKE_CALL_ZHUBO = 1;
    public static final int ZHUBO_CALL_GUKE = 2;

    private String zhuboId;
    private String zhuboName;
    private String zhuboHeadpic;
    private String roomid;
    private int direct; // 1:顾客主动打给主播    2:主播打给顾客
    private String yuyue;    // 是否已经预约: 0:没有预约  1:已经预约

    public ZhuboInfo(String userid, String username, String headpic, String roomid, int direct, String yuyue) {
        this.zhuboId = userid;
        this.zhuboName = username;
        this.zhuboHeadpic = headpic;
        this.roomid = roomid;
        this.direct = direct;
        this.yuyue = yuyue;
    }

    public String getZhuboId() {
        return zhuboId;
    }

    public void setZhuboId(String zhuboId) {
        this.zhuboId = zhuboId;
    }

    public String getZhuboName() {
        return zhuboName;
    }

    public void setZhuboName(String zhuboName) {
        this.zhuboName = zhuboName;
    }

    public String getZhuboHeadpic() {
        return zhuboHeadpic;
    }

    public void setZhuboHeadpic(String zhuboHeadpic) {
        this.zhuboHeadpic = zhuboHeadpic;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public String getYuyue() {
        return yuyue;
    }

    public void setYuyue(String yuyue) {
        this.yuyue = yuyue;
    }

    public ZhuboInfo(Parcel in) {
        zhuboId = in.readString();
        zhuboName = in.readString();
        zhuboHeadpic = in.readString();
        roomid = in.readString();
        direct = in.readInt();
        yuyue = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(zhuboId);
        parcel.writeString(zhuboName);
        parcel.writeString(zhuboHeadpic);
        parcel.writeString(roomid);
        parcel.writeInt(direct);
        parcel.writeString(yuyue);
    }

    public static final Parcelable.Creator<ZhuboInfo> CREATOR = new Parcelable.Creator<ZhuboInfo>() {
        @Override
        public ZhuboInfo createFromParcel(Parcel in) {
            return new ZhuboInfo(in);
        }

        @Override
        public ZhuboInfo[] newArray(int size) {
            return new ZhuboInfo[size];
        }
    };

    @Override
    public String toString() {
        return "ZhuboInfo{" +
                "zhuboId='" + zhuboId + '\'' +
                ", zhuboName='" + zhuboName + '\'' +
                ", zhuboHeadpic='" + zhuboHeadpic + '\'' +
                ", roomid='" + roomid + '\'' +
                ", direct=" + direct +
                ", yuyue='" + yuyue + '\'' +
                '}';
    }
}
