package com.ning.controller;
import com.ning.ResponseResult;
import com.ning.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("song")
public class SongController {
    /**
     * 根据用户id以及歌手id获取歌手歌曲列表
     * */
    @Autowired
    private SongService songService;
    @GetMapping("getSongListBySingerAndUser/{userId}/{singerId}")
    public ResponseResult getSongListBySingerAndUser(@PathVariable("userId") Integer userId,
                                                     @PathVariable("singerId") Integer singerId){
        return songService.getSongListBySingerAndUser(userId,singerId);
    }
    /**
     * 根据用户id以歌曲类型id获取歌曲类型的歌曲列表
     * */
    @GetMapping("getSongListBySingerAndType/{userId}/{typeId}")
    public ResponseResult getSongListBySingerAndType(@PathVariable("userId") Integer userId,
                                                     @PathVariable("typeId") Integer typeId){
        return songService.getSongListBySingerAndType(userId,typeId);
    }
    /**
     * 获取歌曲类型列表
     * */
    @GetMapping("getSongTypeList")
    public ResponseResult  getSongTypeList(){
        return songService.getSongTypeList();
    };
    /**
     * 根据歌曲或者歌手名称来获取歌曲列表
     * */
    @GetMapping("getMusicListByName/{name}/{userId}")
    public ResponseResult getMusicListByName(@PathVariable String name,@PathVariable Integer userId){
        return songService.getMusicListByName(name,userId);
    }
}
