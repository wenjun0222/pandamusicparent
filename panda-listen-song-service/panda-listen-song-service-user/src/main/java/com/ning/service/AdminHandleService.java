package com.ning.service;

import com.ning.ResponseResult;
import com.ning.SearchCondition;
import com.ning.user.query.UserQuery;
public interface AdminHandleService {
    ResponseResult getUserList(SearchCondition searchCondition);
    ResponseResult updateUser(UserQuery userQuery);

    ResponseResult updateUserStatus(UserQuery userQuery);
}
