package com.ruoyi.commodity.mapper;

import java.util.List;
import com.ruoyi.commodity.domain.Commodity;

/**
 * 商品信息Mapper接口
 *
 * @author ruoyi
 * @date 2024-02-07
 */
public interface CommodityMapper
{

    /**
     * 查询商品信息
     *
     * @param id 商品信息主键
     * @return 商品信息
     */
    public Commodity selectCommodityById(Integer id);

    /**
     * 查询商品信息
     *
     * @param itemCode 商品信息主键
     * @return 商品信息
     */
    public Commodity selectCommodityByItemCode(String itemCode);

    /**
     * 查询商品信息列表
     *
     * @param commodity 商品信息
     * @return 商品信息集合
     */
    public List<Commodity> selectCommodityList(Commodity commodity);

    /**
     * 新增商品信息
     *
     * @param commodity 商品信息
     * @return 结果
     */
    public int insertCommodity(Commodity commodity);

    /**
     * 修改商品信息
     *
     * @param commodity 商品信息
     * @return 结果
     */
    public int updateCommodity(Commodity commodity);

    /**
     * 删除商品信息
     *
     * @param id 商品信息主键
     * @return 结果
     */
    public int deleteCommodityById(Integer id);

    /**
     * 批量删除商品信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCommodityByIds(Integer[] ids);

    /**
     * 根据CGC查找itemCode
     * 
     * @param string
     * @return
     */
    public List<Commodity> selectItemCodeByCGC(String cgc);
}
