package com.algorithm.algorithm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.algorithm.algorithm.entity.OPMHistory;

@Mapper
public interface OPMHistoryMapper {

    /**
     * 功能： 根据物料编码和周期号查找OPM历史信息
     * @param itemCode 物料编码
     * @param Cycle 周期号
     * @return
     */
    @Select("SELECT * FROM opmhistory WHERE cycleNum = #{Cycle} AND itemCode = #{itemCode}")
    OPMHistory selectOPMHistoryByCycleAndItemCode(@Param("itemCode") String itemCode,@Param("Cycle") int Cycle);
    
}
