package com.ruoyi.unit.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.unit.mapper.UnitMapper;
import com.ruoyi.unit.domain.Unit;
import com.ruoyi.unit.service.IUnitService;

/**
 * 单位Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-01-31
 */
@Service
public class UnitServiceImpl implements IUnitService 
{
    @Autowired
    private UnitMapper unitMapper;

    /**
     * 查询单位
     * 
     * @param uid 单位主键
     * @return 单位
     */
    @Override
    public Unit selectUnitByUid(Long uid)
    {
        return unitMapper.selectUnitByUid(uid);
    }

    /**
     * 查询单位列表
     * 
     * @param unit 单位
     * @return 单位
     */
    @Override
    public List<Unit> selectUnitList(Unit unit)
    {
        return unitMapper.selectUnitList(unit);
    }

    /**
     * 新增单位
     * 
     * @param unit 单位
     * @return 结果
     */
    @Override
    public int insertUnit(Unit unit)
    {
        return unitMapper.insertUnit(unit);
    }

    /**
     * 修改单位
     * 
     * @param unit 单位
     * @return 结果
     */
    @Override
    public int updateUnit(Unit unit)
    {
        return unitMapper.updateUnit(unit);
    }

    /**
     * 批量删除单位
     * 
     * @param uids 需要删除的单位主键
     * @return 结果
     */
    @Override
    public int deleteUnitByUids(Long[] uids)
    {
        return unitMapper.deleteUnitByUids(uids);
    }

    /**
     * 删除单位信息
     * 
     * @param uid 单位主键
     * @return 结果
     */
    @Override
    public int deleteUnitByUid(Long uid)
    {
        return unitMapper.deleteUnitByUid(uid);
    }
}
