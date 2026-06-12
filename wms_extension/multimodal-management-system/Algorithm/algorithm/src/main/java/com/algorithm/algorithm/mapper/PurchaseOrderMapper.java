package com.algorithm.algorithm.mapper;

import com.algorithm.algorithm.entity.BOM;
import com.algorithm.algorithm.entity.MRP;
import com.algorithm.algorithm.entity.PurchaseOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PurchaseOrderMapper {

    /**
     * 通过itemCode查询得到采购订单表
     *
     * @param itemCode
     */
    @Select(" select * from purchaseorder where itemCode = #{itemCode} ")
    List<PurchaseOrder> selectByItemCode(String itemCode);
}
