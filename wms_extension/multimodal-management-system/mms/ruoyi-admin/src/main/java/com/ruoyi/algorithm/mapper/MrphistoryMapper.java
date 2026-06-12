package com.ruoyi.algorithm.mapper;

import java.util.List;
import com.ruoyi.algorithm.domain.Mrphistory;

/**
 * mrphistoryMapper接口
 * 
 * @author diaoaonan
 * @date 2024-02-16
 */
public interface MrphistoryMapper 
{
    /**
     * 查询mrphistory
     * 
     * @param id mrphistory主键
     * @return mrphistory
     */
    public Mrphistory selectMrphistoryById(String id);

    /**
     * 查询mrphistory列表
     * 
     * @param mrphistory mrphistory
     * @return mrphistory集合
     */
    public List<Mrphistory> selectMrphistoryList(Mrphistory mrphistory);

    /**
     * 新增mrphistory
     * 
     * @param mrphistory mrphistory
     * @return 结果
     */
    public int insertMrphistory(Mrphistory mrphistory);

    /**
     * 修改mrphistory
     * 
     * @param mrphistory mrphistory
     * @return 结果
     */
    public int updateMrphistory(Mrphistory mrphistory);

    /**
     * 删除mrphistory
     * 
     * @param id mrphistory主键
     * @return 结果
     */
    public int deleteMrphistoryById(String id);

    /**
     * 批量删除mrphistory
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMrphistoryByIds(String[] ids);
}
