package com.ning.service;

import com.ning.ResponseResult;
import com.ning.user.entity.User;

public interface SongService {
    ResponseResult getSongListBySingerAndUser(Integer userId,Integer singerId);
    ResponseResult getSongListBySingerAndType(Integer userId, Integer typeId);
    ResponseResult getSongTypeList();
    ResponseResult getMusicListByName(String name,Integer userId);
}
