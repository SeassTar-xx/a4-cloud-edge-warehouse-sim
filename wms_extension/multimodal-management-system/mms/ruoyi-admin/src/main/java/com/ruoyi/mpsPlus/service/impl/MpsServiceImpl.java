package com.ruoyi.mpsPlus.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.mpsPlus.mapper.MpsMapper;
import com.ruoyi.mpsPlus.domain.Mps;
import com.ruoyi.mpsPlus.service.IMpsService;

/**
 * mpsService业务层处理
 *
 * @author ruoyi
 * @date 2024-02-17
 */
@Service
public class MpsServiceImpl implements IMpsService
{
    @Autowired
    private MpsMapper mpsMapper;

    /**
     * 查询mps
     *
     * @param id mps主键
     * @return mps
     */
    @Override
    public Mps selectMpsById(Integer id)
    {
        return mpsMapper.selectMpsById(id);
    }

    /**
     * 查询mps列表
     *
     * @param mps mps
     * @return mps
     */
    @Override
    public List<Mps> selectMpsList(Mps mps)
    {
        return mpsMapper.selectMpsList(mps);
    }

    /**
     * 给主生产计划表编码
     *
     * @return
     */
    @Override
    public int updateMPSCode(Integer id){
        if(mpsMapper.selectMpsById(id)!=null){
            Mps mps = mpsMapper.selectMpsById(id);
            String tem="MPS";
            Date date = mps.getDemandTime();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
            String year = sdf1.format(date);
            String month = sdf2.format(date);
            String day = sdf3.format(date);
            tem=tem+year+month+day;

            List<Mps> mps1 = mpsMapper.selectMpsByTime(mps.getDemandTime());
            int count = mps1.size();
            for (int i = 0;i<3-String.valueOf(count).length();i++){
                tem=tem+"0";
            }
            tem=tem+count;
            mps.setMpsCode(tem);
            mpsMapper.updateMps(mps);
        }
        return 0;
    }

    /**
     * 新增mps
     *
     * @param mps mps
     * @return 结果
     */
    @Override
    public int insertMps(Mps mps)
    {
        return mpsMapper.insertMps(mps);
    }

    /**
     * 修改mps
     *
     * @param mps mps
     * @return 结果
     */
    @Override
    public int updateMps(Mps mps)
    {
        return mpsMapper.updateMps(mps);
    }

    /**
     * 批量删除mps
     *
     * @param ids 需要删除的mps主键
     * @return 结果
     */
    @Override
    public int deleteMpsByIds(Integer[] ids)
    {
        return mpsMapper.deleteMpsByIds(ids);
    }

    /**
     * 删除mps信息
     *
     * @param id mps主键
     * @return 结果
     */
    @Override
    public int deleteMpsById(Integer id)
    {
        return mpsMapper.deleteMpsById(id);
    }
}
