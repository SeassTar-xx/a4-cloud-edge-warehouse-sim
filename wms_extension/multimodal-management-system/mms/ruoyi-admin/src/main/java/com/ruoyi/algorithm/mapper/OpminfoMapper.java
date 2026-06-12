package com.ruoyi.algorithm.mapper;

import java.util.List;
import com.ruoyi.algorithm.domain.Opminfo;

/**
 * opminfoMapper接口
 * 
 * @author diaoaonan
 * @date 2024-02-16
 */
public interface OpminfoMapper 
{
    /**
     * 查询opminfo
     * 
     * @param itemCode opminfo主键
     * @return opminfo
     */
    public Opminfo selectOpminfoByItemCode(String itemCode);

    /**
     * 查询opminfo列表
     * 
     * @param opminfo opminfo
     * @return opminfo集合
     */
    public List<Opminfo> selectOpminfoList(Opminfo opminfo);

    /**
     * 新增opminfo
     * 
     * @param opminfo opminfo
     * @return 结果
     */
    public int insertOpminfo(Opminfo opminfo);

    /**
     * 修改opminfo
     * 
     * @param opminfo opminfo
     * @return 结果
     */
    public int updateOpminfo(Opminfo opminfo);

    /**
     * 删除opminfo
     * 
     * @param itemCode opminfo主键
     * @return 结果
     */
    public int deleteOpminfoByItemCode(String itemCode);

    /**
     * 批量删除opminfo
     * 
     * @param itemCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpminfoByItemCodes(String[] itemCodes);
}
