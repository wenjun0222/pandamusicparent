package com.ning.controller;


import com.ning.ResponseResult;
import com.ning.service.UserFavoriteSongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class UserFavoriteSongController {
    @Autowired
    private UserFavoriteSongService userFavoriteSongService;
    /**
     * 根据用户id获取用户喜欢的音乐列表
     * */
    @GetMapping("getUserFavoriteSongById/{userId}")
    public ResponseResult getUserFavoriteSongById(@PathVariable("userId") Integer userId){
        return userFavoriteSongService.getMusicListByUserId(userId);
    }
    @GetMapping("deleteUserLikeSongById/{id}/{userId}")
    public ResponseResult deleteUserLikeSongById(@PathVariable("id") Integer id,
                                                 @PathVariable("userId") Integer userId){
        return userFavoriteSongService.deleteUserLikeSongById(id);
    }
    @GetMapping("addUserLikeSong/{songId}/{userId}")
    public ResponseResult addUserLikeSong(@PathVariable("songId") Integer songId,
                                          @PathVariable("userId") Integer userId){
        System.out.println(songId+"..."+userId);
        return userFavoriteSongService.insertUserLikeSong(userId,songId);
    }
    @GetMapping("deleteUserLikeSong/{userLikeSongId}/{userId}")
    public ResponseResult deleteUserLikeSong(@PathVariable("userLikeSongId") Integer userLikeSongId,
                                             @PathVariable("userId") Integer userId){
        return userFavoriteSongService.deleteUserLikeSongById(userLikeSongId);
    }
}
