package com.ruoyi.bom.service;

import java.util.List;
import com.ruoyi.bom.domain.Bom;
import com.ruoyi.bom.domain.ResultBom;

/**
 * bomService接口
 * 
 * @author diaoaonan
 * @date 2024-02-06
 */
public interface IBomService 
{
    /**
     * 查询bom
     * 
     * @param bomid bom主键
     * @return bom
     */
    public Bom selectBomByBomid(Long bomid);

    /**
     * 查询bom列表
     * 
     * @param bom bom
     * @return bom集合
     */
    public List<Bom> selectBomList(Bom bom);

    /**
     * 新增bom
     * 
     * @param bom bom
     * @return 结果
     */
    public void insertBom(List<Bom> boms);

    /**
     * 修改bom
     * 
     * @param bom bom
     * @return 结果
     */
    public void updateBom(List<Bom> boms);

    /**
     * 批量删除bom
     * 
     * @param bomids 需要删除的bom主键集合
     * @return 结果
     */
    public int deleteBomByBomids(Long[] bomids);

    /**
     * 删除bom信息
     * 
     * @param bomid bom主键
     * @return 结果
     */
    public int deleteBomByBomid(Long bomid);

    /**
     * 生成BOMid
     * 
     * @return 结果
     */
    public String generateBOMid();


    // /**
    //  * 树形删除BOM信息（剪除枝干）
    //  * 
    //  * @param bomid BOM主键
    //  * @return 结果
    //  */
    // public void deleteBomTreeBybomid(Long bomid);

    /**
     * 查询树状BOM列表
     * 
     * @param bom BOM
     * @return BOM
     */
    public Bom selectBomTreeList(String bomCode);

    /**
     * 构建前端所需要下拉树结构
     * 
     * @param bom BOM列表
     * @return 下拉树列表
     */
    public Bom buildBomTreeSelect(List<Bom> list);

    /**
     * 迭代遍历
     * 
     * @return
     */
    public void recursionFn(List<Bom> list, Bom bom);

    /**
     * 取得子列表
     * 
     * @param list
     * @param bom
     * @return
     */
    public List<Bom> getChildList(List<Bom> list, Bom bom);

    /**
     * 检测是否有子列表
     * 
     * @param list
     * @param bom
     * @return
     */
    public boolean hasChildList(List<Bom> list, Bom bom);

    // /**
    //  * 递归删除
    //  * @param bomid BOM主键
    //  * @return 结果
    //  */
    // public void recursionDelete(Bom bom);

    /**
     * Bom 分解
     * @param id
     * @return
     */
    public List<ResultBom> bomDecompose(int id);


    /**
     * Bom分解递归
     * 
     * @param id
     * @return
     */
    public void recursionBom(List<ResultBom> resultBoms, Bom bom, ResultBom resultBom);


    /**
     * 删除bom信息
     *
     * @param bomid bom主键
     * @return 结果
     */
    public void deleteBom(String bomCode);
}
