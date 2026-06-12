package com.ruoyi.algorithm.mapper;

import com.ruoyi.algorithm.domain.Systeminfo;

/**
 * systeminfoMapper接口
 * 
 * @author diaoaonan
 * @date 2024-02-16
 */
public interface SysteminfoMapper 
{
    /**
     * 查询systeminfo
     * 
     * @param systeminfo systeminfo
     * @return systeminfo集合
     */
    public Systeminfo selectSysteminfo();

    /**
     * 新增systeminfo
     * 
     * @param systeminfo systeminfo
     * @return 结果
     */
    public int insertSysteminfo(Systeminfo systeminfo);

    /**
     * 修改systeminfo
     * 
     * @param systeminfo systeminfo
     * @return 结果
     */
    public int updateSysteminfo(Systeminfo systeminfo);

    /**
     * 删除systeminfo
     * 
     * @param currentCycleNum systeminfo主键
     * @return 结果
     */
    public int deleteSysteminfoByCurrentCycleNum(Long currentCycleNum);

    /**
     * 批量删除systeminfo
     * 
     * @param currentCycleNums 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysteminfoByCurrentCycleNums(Long[] currentCycleNums);
}
