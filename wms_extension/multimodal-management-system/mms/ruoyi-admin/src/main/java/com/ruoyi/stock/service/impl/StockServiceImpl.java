package com.ruoyi.stock.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.algorithm.domain.Mrp;
import com.ruoyi.stock.mapper.StockMapper;
import com.ruoyi.bom.domain.ResultBom;
import com.ruoyi.stock.domain.Stock;
import com.ruoyi.stock.service.IStockService;

/**
 * 初级库存录入Service业务层处理
 *
 * @author ruoyi
 * @date 2024-02-17
 */
@Service
public class StockServiceImpl implements IStockService {
    @Autowired
    private StockMapper stockMapper;

    /**
     * 查询初级库存录入
     *
     * @param id 初级库存录入主键
     * @return 初级库存录入
     */
    @Override
    public Stock selectStockById(Integer id) {
        return stockMapper.selectStockById(id);
    }

    /**
     * 模糊查询初级库存录入
     *
     * @param itemCode 初级库存录入
     * @return 初级库存录入
     */
    @Override
    public List<Stock> selectStockByItemCode(String itemCode) {
        return stockMapper.selectStockByItemCode(itemCode);
    }

    /**
     * 查询初级库存录入列表
     *
     * @param stock 初级库存录入
     * @return 初级库存录入
     */
    @Override
    public List<Stock> selectStockList(Stock stock) {
        return stockMapper.selectStockList(stock);
    }

    /**
     * 新增初级库存录入
     *
     * @param stock 初级库存录入
     * @return 结果
     */
    @Override
    public int insertStock(Stock stock) {
        return stockMapper.insertStock(stock);
    }

    /**
     * 修改初级库存录入
     *
     * @param stock 初级库存录入
     * @return 结果
     */
    @Override
    public int updateStock(Stock stock) {
        return stockMapper.updateStock(stock);
    }

    /**
     * 批量删除初级库存录入
     *
     * @param ids 需要删除的初级库存录入主键
     * @return 结果
     */
    @Override
    public int deleteStockByIds(Integer[] ids) {
        return stockMapper.deleteStockByIds(ids);
    }

    /**
     * 删除初级库存录入信息
     *
     * @param id 初级库存录入主键
     * @return 结果
     */
    @Override
    public int deleteStockById(Integer id) {
        return stockMapper.deleteStockById(id);
    }

    /**
     * 更新已分配量
     * @param resultBom
     */
    @Override
    public void updateAssignMount(ResultBom resultBom) {
        Stock newStock = stockMapper.selectOneStockByItemCode(resultBom.getItemCode());
        if (newStock == null) {
            return;
        }
        // 多出来的已分配量即是毛需求
        double assignMount = newStock.getAssignMount() == null ? 0D : newStock.getAssignMount();
        double demandNum = resultBom.getDemandNum() == null ? 0D : resultBom.getDemandNum();
        newStock.setAssignMount(assignMount + demandNum);
        stockMapper.updateStock(newStock);
    }

    @Override
    public void rollbackAssignMount(Mrp mrp) {
        Stock stock = stockMapper.selectOneStockByItemCode(mrp.getItemCode());
        if (stock == null) {
            return;
        }
        double assignMount = stock.getAssignMount() == null ? 0D : stock.getAssignMount();
        double demandAmount = mrp.getDemandAmount() == null ? 0D : mrp.getDemandAmount();
        stock.setAssignMount(Math.max(assignMount - demandAmount, 0D));
        stockMapper.updateStock(stock);
    }
}
