package com.ning.controller;

import com.ning.ResponseResult;
import com.ning.SearchCondition;
import com.ning.music.entity.Singer;
import com.ning.music.entity.Song;
import com.ning.music.entity.SongType;
import com.ning.service.AdminHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class AdminHandlerController {
    @Autowired
    private AdminHandlerService adminHandlerService;
    //上传歌曲文件
    @PostMapping("uploadMusicFile")
    public ResponseResult uploadMusicFile(MultipartFile file){
        return adminHandlerService.uploadMusicFile(file);
    }
    //上传歌曲文件
    @PostMapping("uploadLrcFile/{songUTCDuration}/{songPlayLength}")
    public ResponseResult uploadLrcFile(MultipartFile file,
                                        @PathVariable String songUTCDuration,
                                        @PathVariable Integer songPlayLength){
        return adminHandlerService.uploadLrcFile(file,songUTCDuration,songPlayLength);
    }
    //获取当前页面的歌曲列表数据
    @PostMapping("getSongList")
    public ResponseResult getSongList(@RequestBody SearchCondition searchCondition){
        return adminHandlerService.getSongList(searchCondition);
    }
    //获取当前页面的歌手列表数据
    @PostMapping("getSingerList")
    public ResponseResult getSingerList(@RequestBody SearchCondition searchCondition){
        System.out.println(searchCondition);
        return adminHandlerService.getSingerList(searchCondition);
    }
    //获取当前页面歌曲类型列表数据
    @PostMapping("getSongTypeList")
    public ResponseResult getSongTypeList(@RequestBody SearchCondition searchCondition){
        return adminHandlerService.getSongTypeList(searchCondition);
    }
    //上传歌手头像文件
    @PostMapping("uploadSingerAvatar")
    public ResponseResult uploadSingerAvatar(MultipartFile file){
        return adminHandlerService.uploadSingerAvatar(file);
    }
    //添加歌手
    @PostMapping("addSinger")
    public ResponseResult addSinger(@RequestBody Singer singer){
        return adminHandlerService.addSinger(singer);
    }
    //根据歌手id获取歌手信息
    @GetMapping("getSingerById/{singerId}")
    public ResponseResult getSingerById(@PathVariable Integer singerId){
        return adminHandlerService.getSingerById(singerId);
    }
    //修改歌手信息
    @PostMapping("editSinger")
    public ResponseResult editSinger(@RequestBody Singer singer){
        return adminHandlerService.updateSinger(singer);
    }
    //添加歌曲
    @PostMapping("addSong")
    public ResponseResult addSong(@RequestBody Song song){
        return adminHandlerService.addSong(song);
    }
    //根据歌曲id获取歌曲
    @GetMapping("getSongById/{songId}")
    public ResponseResult addSong(@PathVariable Integer songId){
        return adminHandlerService.getSongById(songId);
    }
    //修改歌曲信息
    @PostMapping("editSong")
    public ResponseResult editSong(@RequestBody Song song){
        return adminHandlerService.editSong(song);
    }
    //根据歌曲类型id来获取歌曲类型信息
    @GetMapping("getSongTypeById/{typeId}")
    public ResponseResult getSongTypeById(@PathVariable Integer typeId){

        return adminHandlerService.getSongTypeById(typeId);
    }
    //上传歌曲类型图片文件
    @PostMapping("uploadTypePhoto")
    public ResponseResult uploadTypePhoto(MultipartFile file){
        return adminHandlerService.uploadTypePhoto(file);
    }
    //添加歌曲类型
    @PostMapping("addSongType")
    public ResponseResult addSongType(@RequestBody SongType songType){
        return adminHandlerService.addSongType(songType);
    }
    //修改歌曲类型信息
    @PostMapping("editSongType")
    public ResponseResult editSongType(@RequestBody SongType songType){
        return adminHandlerService.editSongType(songType);
    }
}
