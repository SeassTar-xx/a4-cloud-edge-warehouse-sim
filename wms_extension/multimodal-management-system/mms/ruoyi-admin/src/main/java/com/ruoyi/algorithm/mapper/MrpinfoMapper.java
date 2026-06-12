package com.ruoyi.algorithm.mapper;

import java.util.List;
import com.ruoyi.algorithm.domain.Mrpinfo;

/**
 * mrpinfoMapper接口
 * 
 * @author diaoaonan
 * @date 2024-02-16
 */
public interface MrpinfoMapper 
{
    /**
     * 查询mrpinfo
     * 
     * @param itemCode mrpinfo主键
     * @return mrpinfo
     */
    public Mrpinfo selectMrpinfoByItemCode(String itemCode);

    /**
     * 查询mrpinfo列表
     * 
     * @param mrpinfo mrpinfo
     * @return mrpinfo集合
     */
    public List<Mrpinfo> selectMrpinfoList(Mrpinfo mrpinfo);

    /**
     * 新增mrpinfo
     * 
     * @param mrpinfo mrpinfo
     * @return 结果
     */
    public int insertMrpinfo(Mrpinfo mrpinfo);

    /**
     * 修改mrpinfo
     * 
     * @param mrpinfo mrpinfo
     * @return 结果
     */
    public int updateMrpinfo(Mrpinfo mrpinfo);

    /**
     * 删除mrpinfo
     * 
     * @param itemCode mrpinfo主键
     * @return 结果
     */
    public int deleteMrpinfoByItemCode(String itemCode);

    /**
     * 批量删除mrpinfo
     * 
     * @param itemCodes 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMrpinfoByItemCodes(String[] itemCodes);
}
