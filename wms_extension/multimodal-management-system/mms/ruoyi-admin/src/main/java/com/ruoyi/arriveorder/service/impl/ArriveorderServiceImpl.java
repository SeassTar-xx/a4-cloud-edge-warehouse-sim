package com.ruoyi.arriveorder.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.arriveorder.mapper.ArriveorderMapper;
import com.ruoyi.arriveorder.domain.Arriveorder;
import com.ruoyi.arriveorder.service.IArriveorderService;

/**
 * 采购入库表Service业务层处理
 *
 * @author ruoyi
 * @date 2024-02-16
 */
@Service
public class ArriveorderServiceImpl implements IArriveorderService
{
    @Autowired
    private ArriveorderMapper arriveorderMapper;

    /**
     * 查询采购入库表
     *
     * @param id 采购入库表主键
     * @return 采购入库表
     */
    @Override
    public Arriveorder selectArriveorderById(Integer id)
    {
        return arriveorderMapper.selectArriveorderById(id);
    }

    /**
     * 查询采购入库表列表
     *
     * @param arriveorder 采购入库表
     * @return 采购入库表
     */
    @Override
    public List<Arriveorder> selectArriveorderList(Arriveorder arriveorder)
    {
        return arriveorderMapper.selectArriveorderList(arriveorder);
    }

    /**
     * 新增采购入库表
     *
     * @param arriveorder 采购入库表
     * @return 结果
     */
    @Override
    public int insertArriveorder(Arriveorder arriveorder)
    {
        return arriveorderMapper.insertArriveorder(arriveorder);
    }

    /**
     * 修改采购入库表
     *
     * @param arriveorder 采购入库表
     * @return 结果
     */
    @Override
    public int updateArriveorder(Arriveorder arriveorder)
    {
        return arriveorderMapper.updateArriveorder(arriveorder);
    }

    /**
     * 修改采购订单编码
     *
     * @param id 主键
     * @return 结果
     */
    public int updateOrderCode(Integer id){
        if(arriveorderMapper.selectArriveorderById(id)!=null){
            Arriveorder arriveorder = arriveorderMapper.selectArriveorderById(id);
            String tem="RKD";
            Date date = arriveorder.getArriveTime();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
            String year = sdf1.format(date);
            String month = sdf2.format(date);
            String day = sdf3.format(date);
            tem=tem+year+month+day;
            int count=String.valueOf(arriveorder.getId()).length();
            for (int i = 0;i<3-count;i++){
                tem=tem+"0";
            }
            tem=tem+arriveorder.getId();
            arriveorder.setArriveCode(tem);
            arriveorderMapper.updateArriveorder(arriveorder);
        }
        return 0;
    }

    /**
     * 批量删除采购入库表
     *
     * @param ids 需要删除的采购入库表主键
     * @return 结果
     */
    @Override
    public int deleteArriveorderByIds(Integer[] ids)
    {
        return arriveorderMapper.deleteArriveorderByIds(ids);
    }

    /**
     * 删除采购入库表信息
     *
     * @param id 采购入库表主键
     * @return 结果
     */
    @Override
    public int deleteArriveorderById(Integer id)
    {
        return arriveorderMapper.deleteArriveorderById(id);
    }
}
