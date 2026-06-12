package com.ruoyi.stock.mapper;

import java.util.List;
import com.ruoyi.stock.domain.Stock;

/**
 * 初级库存录入Mapper接口
 *
 * @author ruoyi
 * @date 2024-02-17
 */
public interface StockMapper
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
     * 删除初级库存录入
     *
     * @param id 初级库存录入主键
     * @return 结果
     */
    public int deleteStockById(Integer id);

    /**
     * 批量删除初级库存录入
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteStockByIds(Integer[] ids);

    /**
     * 根据物料编码精确查询对应的库存信息
     *
     * @param itemCode 物料编码
     * @return 结果
     */
    public Stock selectOneStockByItemCode(String itemCode);
}
