package com.net.yuesejiaoyou.redirect.ResolverB.getset;

/**
 * Created by Administrator on 2017/11/14.
 */

public class Video_01107B {
    private String videoId;
    private String shoturl;

    public Video_01107B(String videoid, String shoturl) {
        this.videoId = videoid;
        this.shoturl = shoturl;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getShoturl() {
        return shoturl;
    }

    public void setShoturl(String shoturl) {
        this.shoturl = shoturl;
    }
}
