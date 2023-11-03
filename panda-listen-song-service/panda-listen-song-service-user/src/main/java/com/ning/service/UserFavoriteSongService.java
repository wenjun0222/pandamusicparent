package com.ning.service;

import com.ning.ResponseResult;



public interface UserFavoriteSongService {
    ResponseResult getMusicListByUserId(Integer userId);
    ResponseResult deleteUserLikeSongById(Integer id);
    ResponseResult insertUserLikeSong(Integer userId,Integer song_id);
}
