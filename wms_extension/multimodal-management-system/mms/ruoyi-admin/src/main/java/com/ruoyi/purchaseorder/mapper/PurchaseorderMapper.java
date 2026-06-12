package com.ruoyi.purchaseorder.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.purchaseorder.domain.Purchaseorder;

/**
 * 采购订单Mapper接口
 *
 * @author ruoyi
 * @date 2024-02-17
 */
public interface PurchaseorderMapper
{
    /**
     * 查询采购订单
     *
     * @param id 采购订单主键
     * @return 采购订单
     */
    public Purchaseorder selectPurchaseorderById(Integer id);

    /**
     * 查询采购订单列表
     *
     * @param purchaseorder 采购订单
     * @return 采购订单集合
     */
    public List<Purchaseorder> selectPurchaseorderList(Purchaseorder purchaseorder);

    /**
     * 通过采购时间查询mps列表
     *
     * @param  orderTime
     * @return mps集合
     */
    public List<Purchaseorder> selectPurchaseByTime(Date orderTime);

    /**
     * 新增采购订单
     *
     * @param purchaseorder 采购订单
     * @return 结果
     */
    public int insertPurchaseorder(Purchaseorder purchaseorder);

    /**
     * 修改采购订单
     *
     * @param purchaseorder 采购订单
     * @return 结果
     */
    public int updatePurchaseorder(Purchaseorder purchaseorder);

    /**
     * 删除采购订单
     *
     * @param id 采购订单主键
     * @return 结果
     */
    public int deletePurchaseorderById(Integer id);

    /**
     * 批量删除采购订单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePurchaseorderByIds(Integer[] ids);
}
