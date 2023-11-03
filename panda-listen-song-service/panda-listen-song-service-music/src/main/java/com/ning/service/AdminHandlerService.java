package com.ning.service;

import com.ning.ResponseResult;
import com.ning.SearchCondition;
import com.ning.music.entity.Singer;
import com.ning.music.entity.Song;
import com.ning.music.entity.SongType;
import org.springframework.web.multipart.MultipartFile;
public interface AdminHandlerService {
    ResponseResult uploadMusicFile(MultipartFile file);
    ResponseResult getSingerList(SearchCondition searchCondition);
    ResponseResult getSongTypeList(SearchCondition searchCondition);
    ResponseResult uploadSingerAvatar(MultipartFile file);
    ResponseResult addSinger(Singer singer);
    ResponseResult getSingerById(Integer singerId);
    ResponseResult updateSinger(Singer singer);
    ResponseResult getSongList(SearchCondition searchCondition);
    ResponseResult uploadLrcFile(MultipartFile file, String songUTCDuration, Integer songPlayLength);
    ResponseResult addSong(Song song);
    ResponseResult getSongById(Integer songId);
    ResponseResult editSong(Song song);
    ResponseResult getSongTypeById(Integer typeId);
    ResponseResult uploadTypePhoto(MultipartFile file);
    ResponseResult addSongType(SongType songType);
    ResponseResult editSongType(SongType songType);
}
