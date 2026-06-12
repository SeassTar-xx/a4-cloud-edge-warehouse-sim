package com.algorithm.algorithm.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.algorithm.algorithm.entity.ItemMaster;

@Mapper
public interface ItemMasterMapper {
    /**
     * 功能： 根据itemCode查询物料主文件
     * @param itemCode
     * @return 物料主文件信息
     */
    @Select(" select * from itemmaster where itemCode = #{itemCode} ")
    ItemMaster selectByItemCode(String itemCode);

    /**
     * 根据CGC查询物料主文件
     * @param CGC
     * @return 物料主文件信息
     */
    @Select(" select * from itemmaster where CGC = #{CGC} ")
    ItemMaster selectByCGC(String CGC);

    /**
     * 根据 分类等级码(CGC) 所有特定 分类等级码(CGC) 的 物料编码(itemCode)
     * @param CGC 分类等级码
     * @return 对应CGC的物料编码列表
     */
    @Select(" select itemCode from itemmaster where CGC = #{CGC} ")
    String[] selectItemCodeByCGC(String CGC);
}
