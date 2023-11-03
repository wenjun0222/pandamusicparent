package com.ning;

public enum HttpStatusCode {
    /**
    * 操作成功或者失败后需要在前端进行通知
     */
    SUCCESS(200,"操作成功"),
    CODE_SEND_SUCCESS(200,"验证码发送成功"),
    DELETE_SUCCESS(200,"删除成功"),
    REGISTER_SUCCESS(200,"注册成功"),
    UPDATE_PASSWORD_SUCCESS(200,"修改密码成功"),
    UPDATE_SUCCESS(200,"修改信息成功"),
    ERROR(201,"操作失败"),
    UPDATE_PASSWORD_ERROR(201,"修改密码失败"),
    USER_FORBIDDEN(201,"该账号已经被禁止"),
    USER_NOT_EXIST(201,"该用户账号不存在"),
    ACCOUNT_REGISTER(201,"该账号已注册"),
    ADMIN_NOT_EXIST(201,"该管理员账号不存在"),
    SONG_NOT_EXIST(201,"该歌曲数据不存在"),
    SONGTYPE_NOT_EXIST(201,"该歌曲类型数据不存在"),
    PASSWORD_ERROR(201,"密码错误"),
    CODE_ERROR(201,"验证码错误"),
    MUSIC_NULL(201,"未搜索到相关歌曲"),
    REGISTER_ERROR(201,"注册失败"),
    UPDATE_ERROR(201,"修改信息失败"),
    DELETE_ERROR(201,"删除失败"),
    NAME_EXIST(201,"该名称已存在"),
    MUSIC_FILE_NOT_MP3(201,"该文件不是mp3格式"),
    FILE_NULL(201,"文件不得为空"),
    SINGER_NOT_EXIST(201,"该歌手信息不存在"),
    TOKEN_NOT_EXIST(404,"您的登录信息已过期");
    int code;
    String message;
    HttpStatusCode(int code,String message){
        this.code=code;
        this.message=message;
    }
    public int getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
