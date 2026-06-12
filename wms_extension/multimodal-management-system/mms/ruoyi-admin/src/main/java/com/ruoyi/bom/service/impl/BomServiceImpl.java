package com.ruoyi.bom.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.bom.mapper.BomMapper;
import com.ruoyi.bom.domain.Bom;
import com.ruoyi.bom.domain.BomHead;
import com.ruoyi.bom.domain.ResultBom;
import com.ruoyi.bom.service.IBomService;
import com.ruoyi.commodity.mapper.CommodityMapper;
import com.ruoyi.mpsPlus.domain.Mps;
import com.ruoyi.mpsPlus.mapper.MpsMapper;
/**
 * bomService业务层处理
 *
 * @author diaoaonan
 * @date 2024-02-06
 */
@Service
public class BomServiceImpl implements IBomService {
    private static BomMapper staticBomMapper;

    @Autowired
    private BomMapper bomMapper;
    @Autowired
    private MpsMapper mpsMapper;
    @Autowired
    private CommodityMapper commodityMapper;

    // todo 静态方法改写
    @PostConstruct
    public void init() {
        staticBomMapper = bomMapper;
    }

    /**
     * 查询bom
     * ! doNotuse
     * @param bomid bom主键
     * @return bom
     */
    @Override
    public Bom selectBomByBomid(Long bomid) {
        return bomMapper.selectBomByBomid(bomid);
    }

    /**
     * 查询bom列表
     * ! doNotuse
     * @param bom bom
     * @return bom
     */
    @Override
    public List<Bom> selectBomList(Bom bom) {
        return bomMapper.selectBomList(bom);
    }

    /**
     * 批量删除bom
     * ! doNotuse
     * @param bomids 需要删除的bom主键
     * @return 结果
     */
    @Override
    public int deleteBomByBomids(Long[] bomids) {
        return bomMapper.deleteBomByBomids(bomids);
    }

    /**
     * 删除bom信息
     * ! doNotuse
     * @param bomid bom主键
     * @return 结果
     */
    @Override
    public int deleteBomByBomid(Long bomid) {
        return bomMapper.deleteBomByBomid(bomid);
    }

/*------------------------------------------------------------------------------------------------------------- */

    /**
     * 批量插入BOM
     *
     * @param boms 需要插入的bom列表
     * @return 结果
     */
    // todo 一一循环插入效率低下，能否一步到位，跟数据库只进行一次交互
    @Override
    public void insertBom(List<Bom> boms) {
        boolean flag = true;
        for (Bom bom : boms) {
            if (flag) {
                BomHead bomHead = new BomHead(bom);
                bomMapper.insertBom_head(bomHead);
                flag = false;
            }
            bomMapper.insertBom(bom);

        }
        return;
    }

    /**
     * 删除bom信息
     *
     * @param bomid bom主键
     * @return 结果
     */
    @Override
    public void deleteBom(String bomCode) {
        bomMapper.deleteBomBybomCode(bomCode);
        bomMapper.deleteBom_headByBomCode(bomCode);
    }

    /**
     * 修改bom
     *
     * @param bom bom
     * @return 结果
     */
    @Override
    public void updateBom(List<Bom> boms) {
        deleteBom(boms.get(0).getBomCode());
        insertBom(boms);
        return;
    }



    /**
     * 生成BomCode
     * @return 结果
     */
    // todo bomCode生成方式改写
    @Override
    public String generateBOMid() {
        Bom bomForSelect = new Bom();
        bomForSelect.setParentBomId((long) 0);
        List<Bom> list = bomMapper.selectBomList(bomForSelect);
        List<String> collect = list.stream().map(Bom::getItemCode).collect(Collectors.toList());
        List<String> idList = new ArrayList<>();
        for (String str : collect) {
            String[] split = str.split("BOM");
            idList.add(split[1]);
        }
        List<Integer> idListInt = idList.stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        return "BOM" + idListInt.get(idListInt.size() - 1);
    };

    /**
     * 查询树状BOM列表
     * @param bom BOM
     * @return BOM
     */
    @Override
    public Bom selectBomTreeList(String bomCode) {
        Bom query = new Bom();
        query.setBomCode(bomCode);
        List<Bom> list = bomMapper.selectBomList(query);
        return buildBomTreeSelect(list);
    }

    /**
     * 构建前端所需要下拉树结构
     * @param bom BOM列表
     * @return 下拉树列表
     */
    @Override
    public Bom buildBomTreeSelect(List<Bom> list) {
        Bom returnBom = new Bom();
        for (Bom bom : list) {
            if (bom.getParentBomId().longValue() == 0) {
                recursionFn(list, bom);
                returnBom = bom;
            }
        }
        return returnBom;
    }

    /**
     * 迭代遍历
     *
     * @return
     */
    @Override
    public void recursionFn(List<Bom> list, Bom bom) {
        List<Bom> childList = getChildList(list, bom);
        bom.setChildren(childList);
        for (Bom tBom : childList) {
            if (hasChildList(list, tBom)) {
                recursionFn(list, tBom);
            }
        }
    }

    /**
     * 取得子列表
     *
     * @param list
     * @param bom
     * @return
     */
    @Override
    public List<Bom> getChildList(List<Bom> list, Bom bom) {
        List<Bom> returnList = new ArrayList<Bom>();
        Iterator<Bom> it = list.iterator();
        while (it.hasNext()) {
            Bom n = (Bom) it.next();
            if (n.getParentBomId().longValue() == bom.getBomid().longValue()) {
                returnList.add(n);
            }
        }
        return returnList;
    }

    /**
     * 检测是否有子列表
     *
     * @param list
     * @param bom
     * @return
     */
    @Override
    public boolean hasChildList(List<Bom> list, Bom bom) {
        return getChildList(list, bom).size() > 0;
    }

    /**
     * 工具函数， 将BomCode转化为对应的BomId
     *
     * @param bomCode
     * @return
     */
    public static long parseBomCode(String bomCode){
        Bom bom = staticBomMapper.selectBomByItemCode(bomCode);
        return bom.getBomid();
    }

    /**
     * Bom分解主体
     *
     * @param id
     * @return
     */
    @Override
    public List<ResultBom> bomDecompose(int id){
        List<ResultBom> resultBoms = new ArrayList<>();
        Mps mps = mpsMapper.selectMpsById(id);
        if (mps == null) {
            throw new IllegalArgumentException("MPS不存在");
        }
        Bom bom = selectBomTreeList(mps.getBomCode());
        if (bom == null || bom.getChildren() == null || bom.getChildren().isEmpty()) {
            throw new IllegalArgumentException("关联BOM不存在或没有明细");
        }
        ResultBom initResultBom = new ResultBom();
        initResultBom.setDemandTime(mps.getDemandTime());
        initResultBom.setDemandNum(mps.getDemandNum());
        initResultBom.setSourceCode(mps.getMpsCode());
        recursionBom(resultBoms, bom.getChildren().get(0), initResultBom);
        return resultBoms;
    }

    /**
     * Bom分解递归
     *
     * @param id
     * @return
     */
    @Override
    public void recursionBom(List<ResultBom> resultBoms, Bom bom, ResultBom resultBom){
        ResultBom newResultBom = new ResultBom(resultBom);
        if (bom.getChildren() == null || bom.getChildren().isEmpty()) {
            newResultBom.setItemCode(bom.getItemCode());
            if (commodityMapper.selectCommodityByItemCode(bom.getItemCode()) == null) {
                throw new IllegalArgumentException("BOM物料" + bom.getItemCode() + "未维护基础信息");
            }
            newResultBom.setCGC(commodityMapper.selectCommodityByItemCode(bom.getItemCode()).getCgc());
            resultBoms.add(newResultBom);
        }
        else{
            int num = bom.getNum() == null ? 1 : bom.getNum();
            int leaderTime = bom.getLeaderTime() == null ? 0 : bom.getLeaderTime();
            newResultBom.setDemandNum(newResultBom.getDemandNum() * num);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(newResultBom.getDemandTime());
            calendar.add(Calendar.DAY_OF_MONTH, -leaderTime);
            newResultBom.setDemandTime(calendar.getTime());
            for (Bom child : bom.getChildren()) {
                recursionBom(resultBoms, child, newResultBom);
            }
        }
    }

    // ? 以下是旧的bom数据结构的删除方式
    // /**
    //  * 树形删除BOM信息（剪除枝干）
    //  * @param bomid BOM主键
    //  * @return 结果
    //  */
    // @Override
    // public void deleteBomTreeBybomid(Long bomid) {
    //     Bom bom = selectBomTreeList(bomid);
    //     recursionDelete(bom);
    // }
    // /**
    //  * 递归删除
    //  * @param bomid BOM主键
    //  * @return 结果
    //  */
    // @Override
    // public void recursionDelete(Bom bom) {
    //     if (bom.getChildren().isEmpty()) {
    //         bomMapper.deleteBomByBomid(bom.getBomid());
    //     }
    //     else{
    //         for (Bom temBom : bom.getChildren()) {
    //             recursionDelete(temBom);
    //         }
    //         bomMapper.deleteBomByBomid(bom.getBomid());
    //     }
    // }
}
