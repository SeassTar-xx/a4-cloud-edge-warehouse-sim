package com.ruoyi.stock.service;

import java.util.List;

import com.ruoyi.bom.domain.ResultBom;
import com.ruoyi.algorithm.domain.Mrp;
import com.ruoyi.stock.domain.Stock;

/**
 * 初级库存录入Service接口
 *
 * @author ruoyi
 * @date 2024-02-17
 */
public interface IStockService
{
    /**
     * 查询初级库存录入
     *
     * @param id 初级库存录入主键
     * @return 初级库存录入
     */
    public Stock selectStockById(Integer id);

    /**
     * 模糊查询初级库存录入
     *
     * @param itemCode 初级库存录入
     * @return 初级库存录入
     */
    public List<Stock> selectStockByItemCode(String itemCode);

    /**
     * 查询初级库存录入列表
     *
     * @param stock 初级库存录入
     * @return 初级库存录入集合
     */
    public List<Stock> selectStockList(Stock stock);

    /**
     * 新增初级库存录入
     *
     * @param stock 初级库存录入
     * @return 结果
     */
    public int insertStock(Stock stock);

    /**
     * 修改初级库存录入
     *
     * @param stock 初级库存录入
     * @return 结果
     */
    public int updateStock(Stock stock);

    /**
     * 批量删除初级库存录入
     *
     * @param ids 需要删除的初级库存录入主键集合
     * @return 结果
     */
    public int deleteStockByIds(Integer[] ids);

    /**
     * 删除初级库存录入信息
     *
     * @param id 初级库存录入主键
     * @return 结果
     */
    public int deleteStockById(Integer id);

    /**
     * 更新已分配量
     * @param resultBom
     */
    void updateAssignMount(ResultBom resultBom);

    /**
     * 回滚某条MRP计划占用的已分配量
     *
     * @param mrp MRP计划
     */
    void rollbackAssignMount(Mrp mrp);
}
