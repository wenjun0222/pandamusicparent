package com.ning.service.impl;
import com.ning.ResponseResult;
import com.ning.mapper.SingerMapper;
import com.ning.mapper.SongMapper;
import com.ning.music.dto.Music;
import com.ning.music.dto.SingerMusicList;
import com.ning.music.dto.SongTypeMusicList;
import com.ning.music.entity.SongType;
import com.ning.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private SingerMapper singerMapper;
    @Override
    public ResponseResult getSongListBySingerAndUser(Integer userId, Integer singerId) {
        SingerMusicList singerAndSongQuery=singerMapper.getSingerById(singerId);
        List<Music> musicList = songMapper.getSongListBySingerId(singerId,userId);
        singerAndSongQuery.setMusicList(musicList);
        return ResponseResult.success(singerAndSongQuery);
    }
    @Override
    public ResponseResult getSongListBySingerAndType(Integer userId, Integer typeId) {
        SongTypeMusicList songTypeMusicList=songMapper.getSongTypeById(typeId);
        List<Music> musicList = songMapper.getSongListByTypeId(typeId,userId);
        songTypeMusicList.setMusicList(musicList);
        return ResponseResult.success(songTypeMusicList);

    }
    @Override
    public ResponseResult getSongTypeList() {
        List<SongType> songTypeList = songMapper.getSongTypeList(null);
        return ResponseResult.success(songTypeList);
    }
    /**
     * 根据歌手名称或者歌曲名字来获取歌曲列表
     * */
    @Override
    public ResponseResult getMusicListByName(String name,Integer userId) {
        List<Music> musicList=null;
        musicList = songMapper.getMusicListBySongNameAndUserId(name, userId);
        if(musicList.size()>0){
            return ResponseResult.success(musicList);
        }
        musicList=singerMapper.getMusicListBySingerNameAndUserId(name,userId);
        if(musicList.size()>0){
            return ResponseResult.success(musicList);
        }
        musicList= songMapper.getMusicListOnlySix(userId);
        return ResponseResult.error(musicList);
    }

}
