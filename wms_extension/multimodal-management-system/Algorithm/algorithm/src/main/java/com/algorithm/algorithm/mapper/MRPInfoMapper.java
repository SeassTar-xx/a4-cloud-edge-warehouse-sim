package com.algorithm.algorithm.mapper;

import com.algorithm.algorithm.entity.MRPInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MRPInfoMapper {
    /**
     * 功能：插入mrp信息
     *
     * @param mrpInfo
     */
    @Insert(" insert into mrpinfo(itemCode, dailyUsage) values ( #{itemCode}, #{dailyUsage}) ")
    void insertMRPInfo(MRPInfo mrpInfo);

    @Select(" SELECT averageAvailability  FROM mrpinfo where itemCode = #{itemCode} ")
    double selectAverageAvailabilityByItemCode(String itemCode);


}
