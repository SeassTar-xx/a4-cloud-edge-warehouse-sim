package com.ruoyi.commodity.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.commodity.mapper.CommodityMapper;
import com.ruoyi.commodity.domain.Commodity;
import com.ruoyi.commodity.service.ICommodityService;

/**
 * 商品信息Service业务层处理
 *
 * @author ruoyi
 * @date 2024-02-07
 */
@Service
public class CommodityServiceImpl implements ICommodityService
{
    @Autowired
    private CommodityMapper commodityMapper;

    /**
     * 查询商品信息
     *
     * @param id 商品信息主键
     * @return 商品信息
     */
    @Override
    public Commodity selectCommodityById(Integer id)
    {
        return commodityMapper.selectCommodityById(id);
    }

    /**
     * 查询商品信息列表
     *
     * @param commodity 商品信息
     * @return 商品信息
     */
    @Override
    public List<Commodity> selectCommodityList(Commodity commodity)
    {
        return commodityMapper.selectCommodityList(commodity);
    }

    /**
     * 新增商品信息
     *
     * @param commodity 商品信息
     * @return 结果
     */
    @Override
    public int insertCommodity(Commodity commodity)
    {
        return commodityMapper.insertCommodity(commodity);
    }

    /**
     * 查询商品信息
     *
     * @param itemCode 商品信息主键
     * @return 商品信息
     */
    public Commodity selectCommodityByItemCode(String itemCode){
        return commodityMapper.selectCommodityByItemCode(itemCode);
    }

    /**
     * 修改商品信息
     *
     * @param commodity 商品信息
     * @return 结果
     */
    @Override
    public int updateCommodity(Commodity commodity)
    {
        return commodityMapper.updateCommodity(commodity);
    }

    /**
     * 批量删除商品信息
     *
     * @param ids 需要删除的商品信息主键
     * @return 结果
     */
    @Override
    public int deleteCommodityByIds(Integer[] ids)
    {
        return commodityMapper.deleteCommodityByIds(ids);
    }

    /**
     * 删除商品信息信息
     *
     * @param id 商品信息主键
     * @return 结果
     */
    @Override
    public int deleteCommodityById(Integer id)
    {
        return commodityMapper.deleteCommodityById(id);
    }

    /**
     * 自动生成商品编码
     */
    @Override
    public String autoItemCode(){
        String n="sp";
        for(int i=0;i<1000;i++)
        {
            String tem=n;
            int count=String.valueOf(i).length();
            for(int z=3-count;z>0;z--){
                tem=tem+"0";
            }
            tem=tem+i;
            if(commodityMapper.selectCommodityByItemCode(tem)==null){
                return tem;
            }
        }
        return "error";
    }

}
