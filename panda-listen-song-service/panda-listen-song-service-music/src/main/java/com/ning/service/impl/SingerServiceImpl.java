package com.ning.service.impl;

import com.ning.ResponseResult;
import com.ning.mapper.SingerMapper;
import com.ning.service.SingerService;
import com.ning.music.entity.Singer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerMapper singerMapper;
    /**
     * 获取歌手列表业务层
     * */
    @Override
    public ResponseResult getSingerList() {
        List<Singer> singerList = singerMapper.getSingerList(null);
        return ResponseResult.success(singerList);
    }
}
