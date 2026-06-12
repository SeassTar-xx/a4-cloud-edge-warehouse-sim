package com.ruoyi.returnorder.mapper;

import java.util.List;
import com.ruoyi.returnorder.domain.Returnorder;

/**
 * returnorderMapper接口
 * 
 * @author diaoaonan
 * @date 2024-02-06
 */
public interface ReturnorderMapper 
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
     * 删除returnorder
     * 
     * @param id returnorder主键
     * @return 结果
     */
    public int deleteReturnorderById(Long id);

    /**
     * 批量删除returnorder
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReturnorderByIds(Long[] ids);
}
