package com.algorithm.algorithm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.algorithm.algorithm.entity.Inventory;

@Mapper
public interface InventoryMapper {

    /**
     * 功能： 根据itemCode查询物料主文件
     * @return  物料主文件
     */
    @Select(" select * from inventory where itemCode = #{itemCode} ")
    Inventory selectByItemCode(String itemCode);
}
