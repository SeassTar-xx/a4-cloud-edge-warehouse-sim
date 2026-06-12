package com.ruoyi.returnorder.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.returnorder.mapper.ReturnorderMapper;
import com.ruoyi.stock.domain.Stock;
import com.ruoyi.stock.mapper.StockMapper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.returnorder.domain.Returnorder;
import com.ruoyi.returnorder.service.IReturnorderService;

/**
 * returnorderService业务层处理
 * 
 * @author diaoaonan
 * @date 2024-02-06
 */
@Service
public class ReturnorderServiceImpl implements IReturnorderService 
{
    @Autowired
    private ReturnorderMapper returnorderMapper;

    @Autowired
    private StockMapper stockMapper;
    /**
     * 查询returnorder
     * 
     * @param id returnorder主键
     * @return returnorder
     */
    @Override
    public Returnorder selectReturnorderById(Long id)
    {
        return returnorderMapper.selectReturnorderById(id);
    }

    /**
     * 查询returnorder列表
     * 
     * @param returnorder returnorder
     * @return returnorder
     */
    @Override
    public List<Returnorder> selectReturnorderList(Returnorder returnorder)
    {
        return returnorderMapper.selectReturnorderList(returnorder);
    }

    /**
     * 新增returnorder
     * 
     * @param returnorder returnorder
     * @return 结果
     */
    @Override
    public int insertReturnorder(Returnorder returnorder)
    {
        return returnorderMapper.insertReturnorder(returnorder);
    }

    /**
     * 修改returnorder
     * 
     * @param returnorder returnorder
     * @return 结果
     */
    @Override
    public int updateReturnorder(Returnorder returnorder)
    {
        return returnorderMapper.updateReturnorder(returnorder);
    }

    /**
     * 批量删除returnorder
     * 
     * @param ids 需要删除的returnorder主键
     * @return 结果
     */
    @Override
    public int deleteReturnorderByIds(Long[] ids)
    {
        return returnorderMapper.deleteReturnorderByIds(ids);
    }

    /**
     * 删除returnorder信息
     * 
     * @param id returnorder主键
     * @return 结果
     */
    @Override
    public int deleteReturnorderById(Long id)
    {
        return returnorderMapper.deleteReturnorderById(id);
    }

    /*---------------------------------------------------------------------------------------------------- */

    /**
     * 新增returnorder
     * 
     * @param returnorder returnorder
     * @return 结果
     */
    @Override
    public int insertReturnorderBatch(Returnorder[] returnorders)
    {
        for (Returnorder returnorder : returnorders) {
            Stock tempStock = stockMapper.selectOneStockByItemCode(returnorder.getItemCode());
            // 如果说具体的退货程序在 出库单中完成， 那么一下判断则没有必要了
            // if(tempStock.getStock() < returnorder.getReturnNum()){
            //     // 此处是否应该有 判断 库存如果小于退货量则报错
            //     // TODO
            //     // 只是大量退货单中的一个物料库存不足， 如何定义异常， 还是同样添加标识
            //     // 初步解决方案： 1. 发现一个，报错一个
            //     //               2. 发现一个，报错一堆，（二次循环）彻底排查
            //     throw new ServiceException("物料编码为" + returnorder.getItemCode() + "的物料库存不足", 403);
            // }
            tempStock.setStock(tempStock.getStock() - returnorder.getReturnNum());  
            stockMapper.updateStock(tempStock);
            returnorderMapper.insertReturnorder(returnorder);
        }
        return 1;
    }
}
  