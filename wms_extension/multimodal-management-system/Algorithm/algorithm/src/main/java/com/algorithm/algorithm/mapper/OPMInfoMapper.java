package com.algorithm.algorithm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.algorithm.algorithm.entity.OPMInfo;

@Mapper
public interface OPMInfoMapper {

    /**
     * 功能： 根据物料编码(itemCode)查询订货点法信息
     * @return  订货点法信息
     */
    @Select(" select * from opminfo where itemCode = #{itemCode} ")
    OPMInfo selectByItemCode(String itemCode);

    /**
     * 功能： 根据物料编码（itemCode）更新 订货点法信息表(opminfo) 中的 平均日用量(dailyUsage) 和 订货点(orderPoint)
     * @param itemCode 物料编码
     * @param dailyUsage 平均日用量
     * @param orderPoint 订货点
     */
    @Update(" update opminfo set dailyUsage = #{dailyUsage}, orderPoint = #{orderPoint} where itemCode = #{itemCode} ")
    void updateAverageAndOP(String itemCode, double dailyUsage, double orderPoint);


}
