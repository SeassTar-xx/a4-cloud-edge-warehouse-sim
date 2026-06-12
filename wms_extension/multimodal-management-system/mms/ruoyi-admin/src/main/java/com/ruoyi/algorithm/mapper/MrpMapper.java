package com.ruoyi.algorithm.mapper;

import java.util.List;
import com.ruoyi.algorithm.domain.Mrp;

/**
 * mrpMapper接口
 * 
 * @author diaoaonan
 * @date 2024-02-14
 */
public interface MrpMapper 
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
     * 删除mrp
     * 
     * @param id mrp主键
     * @return 结果
     */
    public int deleteMrpById(Long id);

    /**
     * 批量删除mrp
     * 
     * @param ids 需要删除的数据主键集合
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
}
