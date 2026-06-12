package com.ruoyi.mpsPlus.service;

import java.util.Date;
import java.util.List;
import com.ruoyi.mpsPlus.domain.Mps;

/**
 * mpsService接口
 *
 * @author ruoyi
 * @date 2024-02-17
 */
public interface IMpsService
{
    /**
     * 查询mps
     *
     * @param id mps主键
     * @return mps
     */
    public Mps selectMpsById(Integer id);

    /**
     * 查询mps列表
     *
     * @param mps mps
     * @return mps集合
     */
    public List<Mps> selectMpsList(Mps mps);

    /**
     * 新增mps
     *
     * @param mps mps
     * @return 结果
     */
    public int insertMps(Mps mps);

    /**
     * 给主生产计划表编码
     *
     * @return
     */
    public int updateMPSCode(Integer id);

    /**
     * 修改mps
     *
     * @param mps mps
     * @return 结果
     */
    public int updateMps(Mps mps);


    /**
     * 批量删除mps
     *
     * @param ids 需要删除的mps主键集合
     * @return 结果
     */
    public int deleteMpsByIds(Integer[] ids);

    /**
     * 删除mps信息
     *
     * @param id mps主键
     * @return 结果
     */
    public int deleteMpsById(Integer id);
}
