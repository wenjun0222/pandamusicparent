package com.ning.mapper;


import com.ning.SearchCondition;
import com.ning.music.dto.Music;
import com.ning.music.dto.SongTypeMusicList;
import com.ning.music.entity.Song;
import com.ning.music.entity.SongType;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface SongMapper {
    int insertSong(Song song);
    List<Music> getSongListBySingerId(Integer singerId,Integer userId);
    List<Music> getSongListByTypeId(Integer typeId,Integer userId);
    SongTypeMusicList getSongTypeById(Integer id);
    List<SongType> getSongTypeList(String typeName);
    List<Music> getMusicListBySongNameAndUserId(String songName,Integer userId);
    List<Music> getMusicListOnlySix(Integer userId);
    List<Music> getSongList(SearchCondition searchCondition);
    int updateSong(Song song);
    Song getSongById(Integer songId);
    int updateSongType(SongType songType);

    int addSongType(SongType songType);
}
