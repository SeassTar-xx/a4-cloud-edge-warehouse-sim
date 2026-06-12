package com.ruoyi.algorithm.service;

import java.util.List;
import com.ruoyi.algorithm.domain.Mrp;
import com.ruoyi.bom.domain.ResultBom;

/**
 * mrpService接口
 * 
 * @author diaoaonan
 * @date 2024-02-14
 */
public interface IMrpService 
{
    /**
     * 查询mrp
     * 
     * @param id mrp主键
     * @return mrp
     */
    public Mrp selectMrpById(Long id);

    /**
     * 查询mrp列表
     * 
     * @param mrp mrp
     * @return mrp集合
     */
    public List<Mrp> selectMrpList(Mrp mrp);

    /**
     * 按 MPS/MRP 来源编码查询mrp列表
     *
     * @param mrpid 来源编码
     * @return mrp集合
     */
    public List<Mrp> selectMrpByMrpid(String mrpid);

    /**
     * 新增mrp
     * 
     * @param mrp mrp
     * @return 结果
     */
    public int insertMrp(Mrp mrp);

    /**
     * 修改mrp
     * 
     * @param mrp mrp
     * @return 结果
     */
    public int updateMrp(Mrp mrp);

    /**
     * 批量删除mrp
     * 
     * @param ids 需要删除的mrp主键集合
     * @return 结果
     */
    public int deleteMrpByIds(Long[] ids);

    /**
     * 按 MPS/MRP 来源编码删除mrp
     *
     * @param mrpid 来源编码
     * @return 结果
     */
    public int deleteMrpByMrpid(String mrpid);

    /**
     * 删除mrp信息
     * 
     * @param id mrp主键
     * @return 结果
     */
    public int deleteMrpById(Long id);

    /**
     * 将订单放入mrp表中
     *
     * @param sum
     * @param bom
     * @param mps
     * @param itemMaster
     */
    public Long setOrder(ResultBom resultBom, Double demandNum);

     /**
     * 功能： 主模块
     *
     * @param
     * @return
     */
    public Long mrp(ResultBom resultBom);
}
