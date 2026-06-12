package com.ruoyi.purchaseorder.service;

import java.util.List;
import com.ruoyi.purchaseorder.domain.Purchaseorder;

/**
 * 采购订单Service接口
 *
 * @author ruoyi
 * @date 2024-02-17
 */
public interface IPurchaseorderService
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
     * 修改采购订单编码
     *
     * @param id 主键
     * @return 结果
     */
    public int updateOrderCode(Integer id);

    /**
     * 批量删除采购订单
     *
     * @param ids 需要删除的采购订单主键集合
     * @return 结果
     */
    public int deletePurchaseorderByIds(Integer[] ids);

    /**
     * 删除采购订单信息
     *
     * @param id 采购订单主键
     * @return 结果
     */
    public int deletePurchaseorderById(Integer id);
}
