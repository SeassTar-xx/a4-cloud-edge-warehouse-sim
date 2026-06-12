package com.algorithm.algorithm.mapper;


import com.algorithm.algorithm.entity.MRPHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface MRPHistoryMapper {
    /**
     * 根据itemCode和周期号查找历史周期表
     * @param itemCode
     * @param cycleNum
     * @return MRP历史周期表
     */
    @Select(" select * from mrphistory where itemCode = #{itemCode} and cycleNum = #{cycleNum} ")
    MRPHistory selectByItemCodeAndCycleNum(String itemCode,int cycleNum);
}
