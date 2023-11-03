package com.ning.service.impl;

import com.alibaba.nacos.api.naming.pojo.healthcheck.impl.Http;
import com.ning.HttpStatusCode;
import com.ning.ResponseResult;
import com.ning.mapper.UserFavoriteSongMapper;
import com.ning.music.dto.Music;
import com.ning.music.entity.Song;
import com.ning.service.UserFavoriteSongService;
import com.ning.user.entity.UserFavoriteSong;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.awt.image.RescaleOp;
import java.util.List;

@Service
public class UserFavoriteSongServiceImpl implements UserFavoriteSongService {
    @Autowired
    private UserFavoriteSongMapper userFavoriteSongMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    /**
     * 获取用户喜欢的音乐列表。
     * */
    @Override
    public ResponseResult getMusicListByUserId(Integer userId) {

        List<Music> musicList = userFavoriteSongMapper.
                getMusicListByUserId(userId);
        return ResponseResult.success(musicList);
    }
    /**
     * 删除用户喜欢的歌曲
     * */
    @Override
    public ResponseResult deleteUserLikeSongById(Integer id) {
        int row = userFavoriteSongMapper.deleteUserLikeSongById(id);
        if(row>0){
            return ResponseResult.success(HttpStatusCode.DELETE_SUCCESS);
        }
        return ResponseResult.fail(HttpStatusCode.DELETE_ERROR);
    }
    /**
     * 用户添加喜欢的歌曲
     * */
    @Override
    public ResponseResult insertUserLikeSong(Integer userId, Integer song_id) {
        int userLikeSongId = getUserLikeSongId();
        Song song = userFavoriteSongMapper.getIdsBySongId(song_id);
        UserFavoriteSong userFavoriteSong=new UserFavoriteSong();
        userFavoriteSong.setId(userLikeSongId);
        BeanUtils.copyProperties(song,userFavoriteSong);
        userFavoriteSong.setId(userLikeSongId);
        userFavoriteSong.setSongId(song_id);
        userFavoriteSong.setUserId(userId);
        int row = userFavoriteSongMapper.insertUserLikeSong(userFavoriteSong);
        if(row>0){
            return ResponseResult.success(userLikeSongId);
        }
        return ResponseResult.fail(HttpStatusCode.ERROR);
    }


    /**
     * 从redis中获取用户喜欢的歌曲id
     * */
    private final static Object lock=new Object();
    private Integer getUserLikeSongId(){
        Integer userLikeSongId = Integer.valueOf(redisTemplate.opsForValue().get("userLikeSongId"));
        synchronized (lock){
            userLikeSongId++;
            redisTemplate.opsForValue().set("userLikeSongId",userLikeSongId+"");
        }
        return userLikeSongId;
    }
}
