package com.ruoyi.mpsPlus.mapper;

import java.util.Date;
import java.util.List;
import com.ruoyi.mpsPlus.domain.Mps;

/**
 * mpsMapper接口
 *
 * @author ruoyi
 * @date 2024-02-17
 */
public interface MpsMapper
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
     * 通过需求时间查询mps列表
     *
     * @param  demandTime
     * @return mps集合
     */
    public List<Mps> selectMpsByTime(Date demandTime);

    /**
     * 新增mps
     *
     * @param mps mps
     * @return 结果
     */
    public int insertMps(Mps mps);

    /**
     * 修改mps
     *
     * @param mps mps
     * @return 结果
     */
    public int updateMps(Mps mps);

    /**
     * 删除mps
     *
     * @param id mps主键
     * @return 结果
     */
    public int deleteMpsById(Integer id);

    /**
     * 批量删除mps
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMpsByIds(Integer[] ids);
}
