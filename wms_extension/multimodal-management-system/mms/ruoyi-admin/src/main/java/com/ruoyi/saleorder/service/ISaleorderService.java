package com.ruoyi.saleorder.service;

import java.util.List;
import com.ruoyi.saleorder.domain.Saleorder;

/**
 * 销售订单Service接口
 *
 * @author ruoyi
 * @date 2024-02-16
 */
public interface ISaleorderService
{
    /**
     * 查询销售订单
     *
     * @param id 销售订单主键
     * @return 销售订单
     */
    public Saleorder selectSaleorderById(Integer id);

    /**
     * 查询销售订单列表
     *
     * @param saleorder 销售订单
     * @return 销售订单集合
     */
    public List<Saleorder> selectSaleorderList(Saleorder saleorder);

    /**
     * 新增销售订单
     *
     * @param saleorder 销售订单
     * @return 结果
     */
    public int insertSaleorder(Saleorder saleorder);

    /**
     * 修改销售订单
     *
     * @param saleorder 销售订单
     * @return 结果
     */
    public int updateSaleorder(Saleorder saleorder);

    /**
     * 修改销售订单编码
     *
     * @param id 主键
     * @return 结果
     */
    public int updateSaleCode(Integer id);

    /**
     * 批量删除销售订单
     *
     * @param ids 需要删除的销售订单主键集合
     * @return 结果
     */
    public int deleteSaleorderByIds(Integer[] ids);

    /**
     * 删除销售订单信息
     *
     * @param id 销售订单主键
     * @return 结果
     */
    public int deleteSaleorderById(Integer id);
}
