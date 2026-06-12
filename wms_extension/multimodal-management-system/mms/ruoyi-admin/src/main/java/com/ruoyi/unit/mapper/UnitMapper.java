package com.ruoyi.unit.mapper;

import java.util.List;
import com.ruoyi.unit.domain.Unit;

/**
 * 单位Mapper接口
 * 
 * @author ruoyi
 * @date 2024-01-31
 */
public interface UnitMapper 
{
    /**
     * 查询单位
     * 
     * @param uid 单位主键
     * @return 单位
     */
    public Unit selectUnitByUid(Long uid);

    /**
     * 查询单位列表
     * 
     * @param unit 单位
     * @return 单位集合
     */
    public List<Unit> selectUnitList(Unit unit);

    /**
     * 新增单位
     * 
     * @param unit 单位
     * @return 结果
     */
    public int insertUnit(Unit unit);

    /**
     * 修改单位
     * 
     * @param unit 单位
     * @return 结果
     */
    public int updateUnit(Unit unit);

    /**
     * 删除单位
     * 
     * @param uid 单位主键
     * @return 结果
     */
    public int deleteUnitByUid(Long uid);

    /**
     * 批量删除单位
     * 
     * @param uids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUnitByUids(Long[] uids);
}
