package com.ruoyi.purchaseorder.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.purchaseorder.mapper.PurchaseorderMapper;
import com.ruoyi.purchaseorder.domain.Purchaseorder;
import com.ruoyi.purchaseorder.service.IPurchaseorderService;

/**
 * 采购订单Service业务层处理
 *
 * @author ruoyi
 * @date 2024-02-17
 */
@Service
public class PurchaseorderServiceImpl implements IPurchaseorderService
{
    @Autowired
    private PurchaseorderMapper purchaseorderMapper;

    /**
     * 查询采购订单
     *
     * @param id 采购订单主键
     * @return 采购订单
     */
    @Override
    public Purchaseorder selectPurchaseorderById(Integer id)
    {
        return purchaseorderMapper.selectPurchaseorderById(id);
    }

    /**
     * 查询采购订单列表
     *
     * @param purchaseorder 采购订单
     * @return 采购订单
     */
    @Override
    public List<Purchaseorder> selectPurchaseorderList(Purchaseorder purchaseorder)
    {
        return purchaseorderMapper.selectPurchaseorderList(purchaseorder);
    }

    /**
     * 新增采购订单
     *
     * @param purchaseorder 采购订单
     * @return 结果
     */
    @Override
    public int insertPurchaseorder(Purchaseorder purchaseorder)
    {
        return purchaseorderMapper.insertPurchaseorder(purchaseorder);
    }

    /**
     * 修改采购订单编码
     *
     * @param id 主键
     * @return 结果
     */
    public int updateOrderCode(Integer id){
        if(purchaseorderMapper.selectPurchaseorderById(id)!=null){
            Purchaseorder purchaseorder = purchaseorderMapper.selectPurchaseorderById(id);
            String tem="CGD";
            Date date = purchaseorder.getOrderTime();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
            String year = sdf1.format(date);
            String month = sdf2.format(date);
            String day = sdf3.format(date);
            tem=tem+year+month+day;

            List<Purchaseorder> purchaseorders = purchaseorderMapper.selectPurchaseByTime(purchaseorder.getOrderTime());
            int count = purchaseorders.size();
            for (int i = 0;i<3-String.valueOf(count).length();i++){
                tem=tem+"0";
            }
            tem=tem+count;
            purchaseorder.setOrderCode(tem);
            purchaseorderMapper.updatePurchaseorder(purchaseorder);
        }
        return 0;
    }

    /**
     * 修改采购订单
     *
     * @param purchaseorder 采购订单
     * @return 结果
     */
    @Override
    public int updatePurchaseorder(Purchaseorder purchaseorder)
    {
        return purchaseorderMapper.updatePurchaseorder(purchaseorder);
    }

    /**
     * 批量删除采购订单
     *
     * @param ids 需要删除的采购订单主键
     * @return 结果
     */
    @Override
    public int deletePurchaseorderByIds(Integer[] ids)
    {
        return purchaseorderMapper.deletePurchaseorderByIds(ids);
    }

    /**
     * 删除采购订单信息
     *
     * @param id 采购订单主键
     * @return 结果
     */
    @Override
    public int deletePurchaseorderById(Integer id)
    {
        return purchaseorderMapper.deletePurchaseorderById(id);
    }
}
