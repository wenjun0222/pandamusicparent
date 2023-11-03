package com.ning.controller;

import com.ning.ResponseResult;
import com.ning.service.SingerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("singer")
public class SingerController {
    @Autowired
    private SingerService singerService;
    /**
     * 获取歌手列表
     * */
    @GetMapping("getSingerList")
    public ResponseResult getSingerList(){
        return singerService.getSingerList();
    }
}
