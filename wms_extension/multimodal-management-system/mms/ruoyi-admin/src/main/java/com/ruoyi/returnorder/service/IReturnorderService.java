package com.ruoyi.returnorder.service;

import java.util.List;
import com.ruoyi.returnorder.domain.Returnorder;

/**
 * returnorderService接口
 * 
 * @author diaoaonan
 * @date 2024-02-06
 */
public interface IReturnorderService 
{
    /**
     * 查询returnorder
     * 
     * @param id returnorder主键
     * @return returnorder
     */
    public Returnorder selectReturnorderById(Long id);

    /**
     * 查询returnorder列表
     * 
     * @param returnorder returnorder
     * @return returnorder集合
     */
    public List<Returnorder> selectReturnorderList(Returnorder returnorder);

    /**
     * 新增returnorder
     * 
     * @param returnorder returnorder
     * @return 结果
     */
    public int insertReturnorder(Returnorder returnorder);

    /**
     * 修改returnorder
     * 
     * @param returnorder returnorder
     * @return 结果
     */
    public int updateReturnorder(Returnorder returnorder);

    /**
     * 批量删除returnorder
     * 
     * @param ids 需要删除的returnorder主键集合
     * @return 结果
     */
    public int deleteReturnorderByIds(Long[] ids);

    /**
     * 删除returnorder信息
     * 
     * @param id returnorder主键
     * @return 结果
     */
    public int deleteReturnorderById(Long id);


    /**
     * 新增returnorder
     * 
     * @param returnorder returnorder
     * @return 结果
     */
    public int insertReturnorderBatch(Returnorder[] returnorders);
}
