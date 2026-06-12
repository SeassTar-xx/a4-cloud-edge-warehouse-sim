package com.ruoyi.algorithm.mapper;

import java.util.List;
import com.ruoyi.algorithm.domain.Opmhistory;

/**
 * opmhistoryMapper接口
 * 
 * @author diaoaonan
 * @date 2024-02-16
 */
public interface OpmhistoryMapper 
{
    /**
     * 查询opmhistory
     * 
     * @param id opmhistory主键
     * @return opmhistory
     */
    public Opmhistory selectOpmhistoryById(String id);

    /**
     * 查询opmhistory列表
     * 
     * @param opmhistory opmhistory
     * @return opmhistory集合
     */
    public List<Opmhistory> selectOpmhistoryList(Opmhistory opmhistory);

    /**
     * 新增opmhistory
     * 
     * @param opmhistory opmhistory
     * @return 结果
     */
    public int insertOpmhistory(Opmhistory opmhistory);

    /**
     * 修改opmhistory
     * 
     * @param opmhistory opmhistory
     * @return 结果
     */
    public int updateOpmhistory(Opmhistory opmhistory);

    /**
     * 删除opmhistory
     * 
     * @param id opmhistory主键
     * @return 结果
     */
    public int deleteOpmhistoryById(String id);

    /**
     * 批量删除opmhistory
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOpmhistoryByIds(String[] ids);

    /**
     * 根据cycle and itemCode 来查找OpmHistory
     * @param itemCode
     * @param l
     * @return
     */
    public Opmhistory selectOneOpmHistory(Opmhistory opmhistory);
}
