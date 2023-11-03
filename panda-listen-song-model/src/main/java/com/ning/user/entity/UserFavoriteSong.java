package com.ning.user.entity;

import java.io.Serializable;

/**
 * 用户喜欢的歌曲表(UserFavoriteSong)实体类
 */
public class UserFavoriteSong implements Serializable {
    private static final long serialVersionUID = -20305659403639631L;
    /**
     * 用户喜欢歌曲id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 歌曲id
     */
    private Integer songId;
    /**
     * 歌手id
     */
    private Integer singerId;
    /**
     * 歌曲类型id
     * */
    private Integer typeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public Integer getSingerId() {
        return singerId;
    }

    public void setSingerId(Integer singerId) {
        this.singerId = singerId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @Override
    public String toString() {
        return "UserFavoriteSong{" +
                "id=" + id +
                ", userId=" + userId +
                ", songId=" + songId +
                ", singerId=" + singerId +
                ", typeId=" + typeId  +
                '}';
    }





}

