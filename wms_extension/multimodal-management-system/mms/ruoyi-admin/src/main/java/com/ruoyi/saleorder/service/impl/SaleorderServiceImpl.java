package com.ruoyi.saleorder.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ruoyi.arriveorder.domain.Arriveorder;
import com.ruoyi.mpsPlus.domain.Mps;
import com.ruoyi.stock.domain.Stock;
import com.ruoyi.stock.mapper.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.saleorder.mapper.SaleorderMapper;
import com.ruoyi.saleorder.domain.Saleorder;
import com.ruoyi.saleorder.service.ISaleorderService;

/**
 * 销售订单Service业务层处理
 *
 * @author ruoyi
 * @date 2024-02-16
 */
@Service
public class SaleorderServiceImpl implements ISaleorderService
{
    @Autowired
    private SaleorderMapper saleorderMapper;
    @Autowired
    private StockMapper stockMapper;

    /**
     * 查询销售订单
     *
     * @param id 销售订单主键
     * @return 销售订单
     */
    @Override
    public Saleorder selectSaleorderById(Integer id)
    {
        return saleorderMapper.selectSaleorderById(id);
    }

    /**
     * 查询销售订单列表
     *
     * @param saleorder 销售订单
     * @return 销售订单
     */
    @Override
    public List<Saleorder> selectSaleorderList(Saleorder saleorder)
    {
        return saleorderMapper.selectSaleorderList(saleorder);
    }

    /**
     * 新增销售订单
     *
     * @param saleorder 销售订单
     * @return 结果
     */
    @Override
    public int insertSaleorder(Saleorder saleorder)
    {
        Stock stock = stockMapper.selectOneStockByItemCode(saleorder.getItemCode());
        if(stock==null){
            return -1;
        }
        else {
            if(stock.getStock()<saleorder.getPlanMount()){
                return -2;
            }
            else {
                stock.setStock(stock.getStock() - saleorder.getPlanMount());
            }
            stockMapper.updateStock(stock);
        }
        return saleorderMapper.insertSaleorder(saleorder);
    }

    /**
     * 修改销售订单
     *
     * @param saleorder 销售订单
     * @return 结果
     */
    @Override
    public int updateSaleorder(Saleorder saleorder)
    {
        return saleorderMapper.updateSaleorder(saleorder);
    }

    /**
     * 修改销售订单编码
     *
     * @param id 主键
     * @return 结果
     */
    public int updateSaleCode(Integer id){
        if(saleorderMapper.selectSaleorderById(id)!=null){
            Saleorder saleorder = saleorderMapper.selectSaleorderById(id);
            String tem="XSD";
            Date date = saleorder.getSaleDate();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
            String year = sdf1.format(date);
            String month = sdf2.format(date);
            String day = sdf3.format(date);
            tem=tem+year+month+day;

            List<Saleorder> saleorders = saleorderMapper.selectSaleorderListByDate(saleorder.getSaleDate());
            int count = saleorders.size();
            for (int i = 0;i<3-String.valueOf(count).length();i++){
                tem=tem+"0";
            }
            tem=tem+count;
            saleorder.setSaleCode(tem);
            saleorderMapper.updateSaleorder(saleorder);
        }
        return 0;
    }

    /**
     * 批量删除销售订单
     *
     * @param ids 需要删除的销售订单主键
     * @return 结果
     */
    @Override
    public int deleteSaleorderByIds(Integer[] ids)
    {
        return saleorderMapper.deleteSaleorderByIds(ids);
    }

    /**
     * 删除销售订单信息
     *
     * @param id 销售订单主键
     * @return 结果
     */
    @Override
    public int deleteSaleorderById(Integer id)
    {
        return saleorderMapper.deleteSaleorderById(id);
    }
}
