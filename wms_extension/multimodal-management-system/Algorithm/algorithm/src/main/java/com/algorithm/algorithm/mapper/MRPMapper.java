package com.algorithm.algorithm.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.algorithm.algorithm.entity.MRP;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MRPMapper {
    /**
     * 功能：插入mrp订单
     * @param mrp
     */
    @Insert(" insert into mrp(ID, itemCode, planedAmount, orderTime, demandTime) values (#{id}, #{itemCode}, #{planedAmount}, #{orderTime}, #{demandTime}) ")
    void insertMRP(MRP mrp);

    /**
     * 查找全部MRP表
     * @return
     */
    @Select("select * from mrp")
    List<MRP> selectall();

    /**
     * 通过id查找mrp表
     * @param id
     * @return
     */
    @Select("select * from mrp where id = #{id}")
    MRP selectById(String id);

    /**
     * 修改mrp表的计划发出量
     * @param mrp
     */
    @Update("update mrp set planedAmount = #{planedAmount} where id = #{id}")
    void updateMRP(MRP mrp);
}
