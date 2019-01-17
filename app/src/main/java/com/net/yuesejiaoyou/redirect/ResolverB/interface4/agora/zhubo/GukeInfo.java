package com.net.yuesejiaoyou.redirect.ResolverB.interface4.agora.zhubo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018\8\17 0017.
 */

public class GukeInfo implements Parcelable {

    private String gukeId;
    private String gukeName;
    private String gukeHeadpic;
    private String roomid;
    private int direct; // 1:顾客主动打给主播    2:主播打给顾客
    private String yuyue;    // 是否已经预约: 0:没有预约  1:已经预约



    public GukeInfo(String userid, String username, String headpic, String roomid, int direct, String yuyue) {
//        this(userid, username, headpic,roomid,direct);
        this.gukeId = userid;
        this.gukeName = username;
        this.gukeHeadpic = headpic;
        this.roomid = roomid;
        this.direct = direct;
        this.yuyue = yuyue;
    }

    public String getGukeId() {
        return gukeId;
    }

    public void setGukeId(String gukeId) {
        this.gukeId = gukeId;
    }

    public String getGukeName() {
        return gukeName;
    }

    public void setGukeName(String gukeName) {
        this.gukeName = gukeName;
    }

    public String getGukeHeadpic() {
        return gukeHeadpic;
    }

    public void setGukeHeadpic(String gukeHeadpic) {
        this.gukeHeadpic = gukeHeadpic;
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

    public GukeInfo(Parcel in) {
        gukeId = in.readString();
        gukeName = in.readString();
        gukeHeadpic = in.readString();
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
        parcel.writeString(gukeId);
        parcel.writeString(gukeName);
        parcel.writeString(gukeHeadpic);
        parcel.writeString(roomid);
        parcel.writeInt(direct);
        parcel.writeString(yuyue);
    }

    public static final Parcelable.Creator<GukeInfo> CREATOR = new Parcelable.Creator<GukeInfo>() {
        @Override
        public GukeInfo createFromParcel(Parcel in) {
            return new GukeInfo(in);
        }

        @Override
        public GukeInfo[] newArray(int size) {
            return new GukeInfo[size];
        }
    };

    @Override
    public String toString() {
        return "GukeInfo{" +
                "gukeId='" + gukeId + '\'' +
                ", gukeName='" + gukeName + '\'' +
                ", gukeHeadpic='" + gukeHeadpic + '\'' +
                ", roomid='" + roomid + '\'' +
                ", direct=" + direct +
                ", yuyue='" + yuyue + '\'' +
                '}';
    }
}
