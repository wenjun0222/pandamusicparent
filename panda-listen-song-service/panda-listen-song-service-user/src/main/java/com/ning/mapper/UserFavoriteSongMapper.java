package com.ning.mapper;

import com.ning.music.dto.Music;
import com.ning.music.entity.Song;
import com.ning.user.entity.UserFavoriteSong;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFavoriteSongMapper {
    List<Music> getMusicListByUserId(Integer userId);
    int deleteUserLikeSongById(Integer id);
    int insertUserLikeSong(UserFavoriteSong userFavoriteSong);
    Song getIdsBySongId(Integer song_id);
    int getByUserIdAndSongId(Integer userId,Integer songId);
}
