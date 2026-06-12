package com.ruoyi.mpsPlus.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.mpsPlus.domain.Mps;
import com.ruoyi.mpsPlus.service.IMpsService;
import com.ruoyi.algorithm.domain.Mrp;
import com.ruoyi.algorithm.service.IMrpService;
import com.ruoyi.algorithm.service.impl.StrategyContext;
import com.ruoyi.bom.domain.ResultBom;
import com.ruoyi.bom.service.IBomService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.stock.service.IStockService;

/**
 * mpsController
 *
 * @author ruoyi
 * @date 2024-02-17
 */
@RestController
@RequestMapping("/mpsPlus/mps")
public class MpsController extends BaseController
{
    @Autowired
    private IMpsService mpsService;
    @Autowired
    private IBomService bomService;
    @Autowired
    private StrategyContext strategyContext;
    @Autowired
    private IMrpService mrpService;
    @Autowired
    private IStockService stockService;

    /**
     * 查询mps列表
     */
    @PreAuthorize("@ss.hasPermi('mpsPlus:mps:list')")
    @GetMapping("/list")
    public TableDataInfo list(Mps mps)
    {
        startPage();
        List<Mps> list = mpsService.selectMpsList(mps);
        return getDataTable(list);
    }

    /**
     * 导出mps列表
     */
    @PreAuthorize("@ss.hasPermi('mpsPlus:mps:export')")
    @Log(title = "mps", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Mps mps)
    {
        List<Mps> list = mpsService.selectMpsList(mps);
        ExcelUtil<Mps> util = new ExcelUtil<Mps>(Mps.class);
        util.exportExcel(response, list, "mps数据");
    }

    /**
     * 获取mps详细信息
     */
    @PreAuthorize("@ss.hasPermi('mpsPlus:mps:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(mpsService.selectMpsById(id));
    }

    /**
     * 新增mps
     */
    @PreAuthorize("@ss.hasPermi('mpsPlus:mps:add')")
    @Log(title = "mps", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Mps mps)
    {
        mpsService.insertMps(mps);
        mpsService.updateMPSCode(mps.getId());
        return success();//
    }

    /**
     * 运行MPS对应的BOM分解、MRP/混合/订货点算法
     */
    @PreAuthorize("@ss.hasPermi('mpsPlus:mps:run')")
    @Log(title = "mps运算", businessType = BusinessType.OTHER)
    @PostMapping("/run/{id}")
    public AjaxResult run(@PathVariable Integer id)
    {
        Mps mps = mpsService.selectMpsById(id);
        if (mps == null) {
            return error("MPS不存在，无法运行算法");
        }
        if (mps.getMpsCode() == null || "".equals(mps.getMpsCode())) {
            mpsService.updateMPSCode(id);
            mps = mpsService.selectMpsById(id);
        }
        List<ResultBom> resultBoms;
        try {
            resultBoms = bomService.bomDecompose(id);
            List<Mrp> oldMrps = mrpService.selectMrpByMrpid(mps.getMpsCode());
            for (Mrp oldMrp : oldMrps) {
                stockService.rollbackAssignMount(oldMrp);
            }
            mrpService.deleteMrpByMrpid(mps.getMpsCode());
            strategyContext.setResult(resultBoms);
        } catch (Exception e) {
            return error("算法运行失败：" + e.getMessage());
        }

        AjaxResult ajax = success("算法运行完成");
        ajax.put("decomposedCount", resultBoms.size());
        ajax.put("mrpCode", mps.getMpsCode());
        return ajax;
    }

    /**
     * 系统更改mpsCode
     */
    @PreAuthorize("@ss.hasPermi('mpsPlus:mps:mpsCode')")
    @GetMapping("/mpsCode/{id}")
    public AjaxResult updateMpsCode(@PathVariable Integer id)
    {
        mpsService.updateMPSCode(id);
        return success();
    }

    /**
     * 修改mps
     */
    @PreAuthorize("@ss.hasPermi('mpsPlus:mps:edit')")
    @Log(title = "mps", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Mps mps)
    {
        return toAjax(mpsService.updateMps(mps));
    }

    /**
     * 删除mps
     */
    @PreAuthorize("@ss.hasPermi('mpsPlus:mps:remove')")
    @Log(title = "mps", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(mpsService.deleteMpsByIds(ids));
    }
}
