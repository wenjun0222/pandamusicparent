package com.ning.service.impl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ning.HttpStatusCode;
import com.ning.MusicFileUtil;
import com.ning.ResponseResult;
import com.ning.SearchCondition;
import com.ning.config.MinioUtil;
import com.ning.mapper.SingerMapper;
import com.ning.mapper.SongMapper;
import com.ning.music.dto.Music;
import com.ning.music.dto.SingerMusicList;
import com.ning.music.dto.SongTypeMusicList;
import com.ning.music.entity.Singer;
import com.ning.music.entity.Song;
import com.ning.music.entity.SongType;
import com.ning.service.AdminHandlerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.List;
@Service
@Transactional
public class AdminHandlerServiceImpl implements AdminHandlerService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private MinioUtil minioUtil;
    @Autowired
    private SongMapper songMapper;
    @Autowired
    private SingerMapper singerMapper;
    /**
     * 将歌曲文件添加到minion文件系统中
     * */
    @Override
    public ResponseResult uploadMusicFile(MultipartFile sourceFile) {
        //判断文件类型是否为mp3格式
        String contentType = sourceFile.getContentType();
        if(!contentType.equals("audio/mpeg")){
            return ResponseResult.fail(HttpStatusCode.MUSIC_FILE_NOT_MP3);
        }
        File file=MusicFileUtil.multiPartFileToFile(sourceFile);
        Song song=new Song();
        int musicPlayLength = MusicFileUtil.getMusicPlayLength(file);
        String songUTCDuration = MusicFileUtil.getSongUTCDuration(file);
        String originalFilename = sourceFile.getOriginalFilename();
        String songName = MusicFileUtil.getSongName(originalFilename);
        song.setSongDuration(songUTCDuration);
        song.setSongPlayLength(musicPlayLength);
        song.setSongName(songName);
        String songUrl = minioUtil.uploadFileTest(sourceFile);
        song.setSongUrl(songUrl);
        return ResponseResult.success(song);
    }
    /**
     * 获取某一页的歌曲列表数据，每页6条数据
     * */
    @Override
    public ResponseResult getSongList(SearchCondition searchCondition) {
        PageHelper.startPage(searchCondition.getCurrentPage(),6);
        List<Music> songList = songMapper.getSongList(searchCondition);
        PageInfo<Music> musicPageInfo=new PageInfo<>(songList);
        return ResponseResult.success(musicPageInfo);
    }
    /**
     * 上传歌词文件
     * */
    @Override
    public ResponseResult uploadLrcFile(MultipartFile sourceFile, String songUTCDuration, Integer songPlayLength) {
        File file = MusicFileUtil.multiPartFileToFile(sourceFile);
        File file1 = MusicFileUtil.getLrcFile(file, songUTCDuration, songPlayLength);
        FileInputStream fileInputStream = null;
        MultipartFile multipartFile=null;
        String lrcUrl="";
        try {
            fileInputStream = new FileInputStream(file1);
            multipartFile= new MockMultipartFile("file",file1.getName(), MediaType.TEXT_PLAIN_VALUE,  fileInputStream);
            lrcUrl=minioUtil.uploadFileTest(multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.success(lrcUrl);
    }
    /**
     * 添加歌曲
     * */
    @Override
    public ResponseResult addSong(Song song) {
        Integer songId = getSongId();
        song.setId(songId);
        song.setStatus(0);
        song.setIsDeleted(0);
        song.setCreateTime(LocalDateTime.now());
        song.setUpdateTime(LocalDateTime.now());
        songMapper.insertSong(song);
        return ResponseResult.success();
    }
    /**
     * 根据歌曲id查询歌曲
     * */
    @Override
    public ResponseResult getSongById(Integer songId) {
        Song song = songMapper.getSongById(songId);
        if(song!=null){
            return ResponseResult.success(song);
        }
        return ResponseResult.fail(HttpStatusCode.SONG_NOT_EXIST);
    }
    /**
     * 修改歌曲信息
     * */
    @Override
    public ResponseResult editSong(Song song) {
        //判断该歌曲类型信息是否存在
        Song songById = songMapper.getSongById(song.getId());
        if(songById==null){
            return ResponseResult.fail(HttpStatusCode.SONG_NOT_EXIST);
        }
        //修改数据库中歌曲类型信息
        song.setUpdateTime(LocalDateTime.now());
        songMapper.updateSong(song);
        return ResponseResult.success();
    }
    /**
     * 根据歌曲类型id获取歌曲类型信息
     * */
    @Override
    public ResponseResult getSongTypeById(Integer typeId) {
        SongTypeMusicList songTypeById = songMapper.getSongTypeById(typeId);
        if(songTypeById!=null){
            return ResponseResult.success(songTypeById);
        }
        return ResponseResult.fail(HttpStatusCode.SONGTYPE_NOT_EXIST);
    }
    /**
     * 上传歌曲类型图片文件
     * */
    @Override
    public ResponseResult uploadTypePhoto(MultipartFile file) {
        String path = minioUtil.uploadFileTest(file);
        return ResponseResult.success(path);
    }
    /**
     * 添加歌曲类型
     * */
    @Override
    public ResponseResult addSongType(SongType songType) {
        Integer songTypeId = getSongTypeId();
        songType.setId(songTypeId);
        songType.setStatus(0);
        songType.setIsDeleted(0);
        songType.setCreateTime(LocalDateTime.now());
        songType.setUpdateTime(LocalDateTime.now());
        int row = songMapper.addSongType(songType);
        if(row>0){
            return ResponseResult.success();
        }
        return ResponseResult.fail(HttpStatusCode.ERROR);
    }
    /**
     * 修改歌曲类型信息
     * */
    @Override
    public ResponseResult editSongType(SongType songType) {
        //判断该歌曲类型信息是否存在
        SongTypeMusicList songTypeById = songMapper.getSongTypeById(songType.getId());
        if(songTypeById==null){
            return ResponseResult.fail(HttpStatusCode.SONGTYPE_NOT_EXIST);
        }
        songType.setUpdateTime(LocalDateTime.now());
        int row = songMapper.updateSongType(songType);
        if(row>0){
            return ResponseResult.success();
        }
        return ResponseResult.fail(HttpStatusCode.ERROR);
    }
    /**
     * 获取某一页的歌手列表数据，每页四条数据
     * */
    @Override
    public ResponseResult getSingerList(SearchCondition searchCondition) {
        System.out.println(searchCondition);
        PageHelper.startPage(searchCondition.getCurrentPage(), 4);
        List<Singer> singerList = singerMapper.getSingerLikeSingerName(searchCondition.getName());
        PageInfo<Singer> singerPageInfo = new PageInfo<>(singerList);
        return ResponseResult.success(singerPageInfo);
    }
    /**
     * 获取某一页的歌曲类型列表数据，每页四条数据
     * */
    @Override
    public ResponseResult getSongTypeList(SearchCondition searchCondition) {
        PageHelper.startPage(searchCondition.getCurrentPage(), 4);
        List<SongType> songTypeList = songMapper.getSongTypeList(searchCondition.getName());
        PageInfo<SongType> songTypePageInfo = new PageInfo<>(songTypeList);
        return ResponseResult.success(songTypePageInfo);
    }
    /**
     * 将歌手的图片添加到minio文件系统中
     * */
    @Override
    public ResponseResult uploadSingerAvatar(MultipartFile file) {
        String avatarUrl = minioUtil.uploadSingerAvatar(file);
        return ResponseResult.success(avatarUrl);
    }
    /**
     * 添加歌手
     * */
    @Override
    public ResponseResult addSinger(Singer singer) {
        //查看新的歌手名称是否已存在
        Singer singer1 = singerMapper.getSingerBySingerName(singer.getSingerName());
        if(singer1!=null){
            return ResponseResult.fail(HttpStatusCode.NAME_EXIST);
        }
        //获取歌手id，开始将新的歌手添加到数据库中
        Integer singerId = getSingerId();
        singer.setId(singerId);
        singer.setStatus(0);
        singer.setIsDeleted(0);
        singer.setUpdateTime(LocalDateTime.now());
        singer.setCreateTime(LocalDateTime.now());
        int row = singerMapper.addSinger(singer);
        if(row>0){
            return ResponseResult.fail(HttpStatusCode.SUCCESS);
        }
        return ResponseResult.fail(HttpStatusCode.ERROR);

    }
    /**
     * 根据歌手id获取歌手
     * */
    @Override
    public ResponseResult getSingerById(Integer singerId) {
        //判断该歌手信息是否存在
        SingerMusicList singerById = singerMapper.getSingerById(singerId);
        if(singerById==null){
            return ResponseResult.fail(HttpStatusCode.SINGER_NOT_EXIST);
        }
        Singer singer=new Singer();
        BeanUtils.copyProperties(singerById,singer);
        return ResponseResult.success(singer);
    }
    /**
     * 更新歌手信息
     * */
    @Override
    public ResponseResult updateSinger(Singer singer) {
        //判断该歌手信息是否存在
        SingerMusicList singerById = singerMapper.getSingerById(singer.getId());
        if(singerById==null){
            return ResponseResult.fail(HttpStatusCode.SINGER_NOT_EXIST);
        }
        //判断该歌手名称是否已存在
        if(singer.getSingerName()!=null) {
            Singer singerBySingerName = singerMapper.getSingerBySingerName(singer.getSingerName());
            if (singerBySingerName != null && !singerBySingerName.getId().equals(singer.getId())) {
                return ResponseResult.fail(HttpStatusCode.NAME_EXIST);
            }
        }
        singer.setUpdateTime(LocalDateTime.now());
        //开始更新歌手信息
        int row= singerMapper.updateSinger(singer);
        if(row>0){
            return ResponseResult.success(HttpStatusCode.UPDATE_SUCCESS);
        }
        return ResponseResult.fail(HttpStatusCode.UPDATE_ERROR);

    }
    private final static Object singerLock=new Object();
    private final static String SINGED_ID="singerId";
    /**
     * 获取歌手id
     * */
    private Integer getSingerId(){
        Integer singerId = Integer.valueOf(redisTemplate.opsForValue().get(SINGED_ID));
        synchronized (singerLock){
            singerId++;
            redisTemplate.opsForValue().set(SINGED_ID,singerId+"");
        }
        return singerId;
    }
    /**
     * 获取歌曲id
     * */
    private final static Object songLock=new Object();
    private final static String SONG_ID="songId";
    private Integer getSongId(){
        Integer songId = Integer.valueOf(redisTemplate.opsForValue().get(SONG_ID));
        synchronized (songLock){
            songId++;
            redisTemplate.opsForValue().set(SONG_ID,songId+"");
        }
        return songId;
    }
    /**
     * 获取歌曲类型id
     * */
    private final static Object songTypeLock=new Object();
    private final static String SONGTYPE_ID="typeId";
    private Integer getSongTypeId(){
        Integer songId = Integer.valueOf(redisTemplate.opsForValue().get(SONGTYPE_ID));
        synchronized (songTypeLock){
            songId++;
            redisTemplate.opsForValue().set(SONGTYPE_ID,songId+"");
        }
        return songId;
    }

}
