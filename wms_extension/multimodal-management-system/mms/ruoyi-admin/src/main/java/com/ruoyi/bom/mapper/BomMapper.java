package com.ruoyi.bom.mapper;

import java.util.List;
import com.ruoyi.bom.domain.Bom;
import com.ruoyi.bom.domain.BomHead;

/**
 * bomMapper接口
 * 
 * @author diaoaonan
 * @date 2024-02-06
 */
public interface BomMapper 
{
    /**
     * 查询bom
     * 
     * @param bomid bom主键
     * @return bom
     */
    public Bom selectBomByBomid(Long bomid);

    /**
     * 查询bom列表
     * 
     * @param bom bom
     * @return bom集合
     */
    public List<Bom> selectBomList(Bom bom);

    /**
     * 查询bom_head列表
     * 
     * @param bomHead bomHead
     * @return bom集合
     */
    public List<BomHead> selectBom_headList(BomHead bomHead);

    /**
     * 新增bom
     * 
     * @param bom bom
     * @return 结果
     */
    public int insertBom(Bom bom);

    /**
     * 新增bom_head
     * 
     * @param bomHead bomHead
     * @return
     */
    public int insertBom_head(BomHead bomHead);

    /**
     * 修改bom
     * 
     * @param bom bom
     * @return 结果
     */
    public int updateBom(Bom bom);

    /**
     * 修改bomHead
     * 
     * @param bomHead bomHead
     * @return 结果
     */
    public int updateBom_head(BomHead bomHead);

    /**
     * 删除bom
     * 
     * @param bomid bom主键
     * @return 结果
     */
    public int deleteBomByBomid(Long bomid);

    /**
     * 删除bom(byBomCode)
     * 
     * @param bomid bom主键
     * @return 结果
     */
    public int deleteBomBybomCode(String bomid);

    /**
     * 删除bom_head
     * 
     * @param bomCode bom_head主键
     * @return 结果
     */
    public int deleteBom_headByBomCode(String bomCode);

    /**
     * 批量删除bom
     * 
     * @param bomids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBomByBomids(Long[] bomids);

    /**
     * 根据ItemCode查询
     * @return 
     */
    public Bom selectBomByItemCode(String itemCode);
}
