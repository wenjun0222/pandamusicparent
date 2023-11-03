package com.ning;

import com.ning.entity.Lrc;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
public class MusicFileUtil {
    /**
     * 获取歌曲长度
     * */
    public static int getMusicPlayLength(File file) {
        AudioInputStream audioInputStream = null;
        AudioFormat audioFormat = null;// 文件格式
        int musicLength = 0;
        int len;
        byte[] bytes = new byte[1024];
        try {
            audioInputStream = AudioSystem.getAudioInputStream(file);
            audioFormat = audioInputStream.getFormat();
            // 转换mp3文件编码
            if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                        audioFormat.getSampleRate(), 16, audioFormat
                        .getChannels(), audioFormat.getChannels() * 2,
                        audioFormat.getSampleRate(), false);
            }
            //再次重新获得文件输入流
            audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
            while ((len = audioInputStream.read(bytes, 0, bytes.length)) != -1) {
                musicLength += len;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(audioInputStream!=null){
                try {
                    audioInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return musicLength;
    }
    /**
     * 将multipartFile类型转化为File类型
     * */
    public static File multiPartFileToFile(MultipartFile resourceFile){
        File file=new File(resourceFile.getOriginalFilename());
        FileOutputStream fileOutputStream=null;
        InputStream inputStream=null;
        try {
            inputStream=resourceFile.getInputStream();
            fileOutputStream=new FileOutputStream(file);
            byte[] bytes=new byte[1024*6];
            int len=0;
            while ((len=inputStream.read(bytes,0,bytes.length))!=-1){
                fileOutputStream.write(bytes,0,len);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
    /**
     * 获取歌曲播放时长
     * */
    public static String getSongUTCDuration(File file){
        int songDuration = getSongDuration(file);
        songDuration=songDuration/1000;
        int minutes=songDuration/60;
        int seconds=songDuration%60;
        String songUTCDuration="";
        if(seconds<10){
            songUTCDuration=minutes+":0"+seconds;
        }else{
            songUTCDuration=minutes+":"+seconds;
        }
        return songUTCDuration;
    }
    private static int getSongDuration(File file){
        MP3File mp3File= null;
        int trackLength=0;
        try {
            mp3File = new MP3File(file);
            MP3AudioHeader mp3AudioHeader = mp3File.getMP3AudioHeader();
            trackLength = mp3AudioHeader.getTrackLength()*1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return trackLength;
    }
    /**
     * 获取歌曲名称
     * */
    public static String getSongName(String originalName){
        int index = originalName.indexOf(".");
        String songName = originalName.substring(0,index);
        return songName;
    }
    /**
     * 获取文件里面的歌词列表
     * */
    private  static List<Lrc> getLrcLists(File file){
        List<Lrc> lrcList=new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String prefixTime;
        String content;
        Date date;
        long time;
        try {
            BufferedReader bufferedReader= new BufferedReader(new FileReader(file));
            String str;
            while((str=bufferedReader.readLine())!=null){
                int index=str.lastIndexOf("]");
                prefixTime=str.substring(1,index);
                date = sdf.parse("1970-01-01 00:" + prefixTime);
                time=date.getTime();
                content=str.substring(index+1,str.length());
                content=content.replaceAll(" ","");
                Lrc lrc=new Lrc(time,content);
                lrcList.add(lrc);
            }
            bufferedReader.close();
        } catch (Exception e) {

            e.printStackTrace();
        }
        return lrcList;
    }
    /**
     * 获取歌曲时长(毫秒)
     * */
    private static Integer getSongMillisecond(String  songUTCDuration){
        int index = songUTCDuration.indexOf(":");
        int length=songUTCDuration.length();
        Integer minutes=Integer.valueOf(songUTCDuration.substring(0,index));
        Integer seconds=Integer.valueOf(songUTCDuration.substring(index+1,length));
        Integer songDuration=minutes*60*1000+seconds*1000;
        return songDuration;
    }
    /**
     * 歌词文件转换
     * */
    public static File getLrcFile(File sourceFile,String songUTCDuration,Integer songPlayLength){
        Integer songMillisecond = getSongMillisecond(songUTCDuration);
        try{
            List<Lrc> lrcList= getLrcLists(sourceFile);
            FileOutputStream fileOutputStream=new FileOutputStream(sourceFile);
            int rate =  songPlayLength / songMillisecond;
            for (Lrc lrc : lrcList) {
                Long prefixTime = (long) (lrc.getPrefixTime() * rate);
                String content = lrc.getContent();
                String lrc1 = "[" + prefixTime + "]" + content + "\r\n";
                byte[] bytes = lrc1.getBytes();
                fileOutputStream.write(bytes, 0, bytes.length);
                fileOutputStream.flush();
            }
            String lrc = "[200000000]";
            byte[] bytes = lrc.getBytes();
            fileOutputStream.write(bytes, 0, bytes.length);
            fileOutputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        return sourceFile;
    }
}
