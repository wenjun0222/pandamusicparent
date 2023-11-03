package com.ning.mapper;
import com.ning.admin.entity.Admin;
import com.ning.admin.query.AdminQuery;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
@Mapper
public interface AdminMapper {
    int registerAdmin(Admin admin);
    int updateAdmin(AdminQuery adminQuery);
    Admin getAdminByCondition(AdminQuery adminQuery);
    int deleteAdminById(Integer id);
    List<Admin> getAdminList();
}
