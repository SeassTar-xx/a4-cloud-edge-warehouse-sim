package com.ruoyi.arriveorder.mapper;

import java.util.List;
import com.ruoyi.arriveorder.domain.Arriveorder;

/**
 * 采购入库表Mapper接口
 *
 * @author ruoyi
 * @date 2024-02-16
 */
public interface ArriveorderMapper
{
    /**
     * 查询采购入库表
     *
     * @param id 采购入库表主键
     * @return 采购入库表
     */
    public Arriveorder selectArriveorderById(Integer id);

    /**
     * 查询采购入库表列表
     *
     * @param arriveorder 采购入库表
     * @return 采购入库表集合
     */
    public List<Arriveorder> selectArriveorderList(Arriveorder arriveorder);

    /**
     * 新增采购入库表
     *
     * @param arriveorder 采购入库表
     * @return 结果
     */
    public int insertArriveorder(Arriveorder arriveorder);

    /**
     * 修改采购入库表
     *
     * @param arriveorder 采购入库表
     * @return 结果
     */
    public int updateArriveorder(Arriveorder arriveorder);

    /**
     * 删除采购入库表
     *
     * @param id 采购入库表主键
     * @return 结果
     */
    public int deleteArriveorderById(Integer id);

    /**
     * 批量删除采购入库表
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteArriveorderByIds(Integer[] ids);
}
