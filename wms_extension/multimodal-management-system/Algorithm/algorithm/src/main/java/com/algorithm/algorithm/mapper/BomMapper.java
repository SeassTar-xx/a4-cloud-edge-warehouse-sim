package com.algorithm.algorithm.mapper;

import com.algorithm.algorithm.entity.BOM;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BomMapper {
    /**
     *根据itemCode查询bom表
     * @param itemCode
     * @return BOM
     */
    @Select(" select * from bom where itemCode = #{itemCode} ")
    BOM selectByItemCode(String itemCode);

    /**
     * 查找全部BOM表
     * @return
     */
    @Select(" select * from bom ")
    List<BOM> selectAll();
}
