package com.ning.mapper;

import com.ning.music.dto.Music;
import com.ning.music.dto.SingerMusicList;
import com.ning.music.entity.Singer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SingerMapper {
    List<Singer> getSingerList(String singerName);
    SingerMusicList getSingerById(Integer id);
    List<Music> getMusicListBySingerNameAndUserId(String singerName,Integer userId);
    int addSinger(Singer singer);
    Singer getSingerBySingerName(String singerName);
    int updateSinger(Singer singer);
    List<Singer> getSingerLikeSingerName(String singerName);
}
