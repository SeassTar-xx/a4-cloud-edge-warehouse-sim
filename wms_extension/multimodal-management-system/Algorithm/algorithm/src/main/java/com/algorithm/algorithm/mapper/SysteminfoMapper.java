package com.algorithm.algorithm.mapper;

import com.algorithm.algorithm.entity.SystemInfoN;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SysteminfoMapper {
    /**
     * 用于得到系统信息
     *
     * @return
     */
    @Select(" select * from systeminfo ")
    SystemInfoN selectSysteminfo();
}
