package com.ruoyi.algorithm.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.algorithm.domain.Mrp;
import com.ruoyi.algorithm.service.IMrpService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 物料需求计划Controller
 */
@RestController
@RequestMapping("/algorithm/mrp")
public class MrpController extends BaseController
{
    @Autowired
    private IMrpService mrpService;

    @PreAuthorize("@ss.hasPermi('algorithm:mrp:list')")
    @GetMapping("/list")
    public TableDataInfo list(Mrp mrp)
    {
        startPage();
        List<Mrp> list = mrpService.selectMrpList(mrp);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('algorithm:mrp:export')")
    @Log(title = "物料需求计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Mrp mrp)
    {
        List<Mrp> list = mrpService.selectMrpList(mrp);
        ExcelUtil<Mrp> util = new ExcelUtil<Mrp>(Mrp.class);
        util.exportExcel(response, list, "物料需求计划数据");
    }

    @PreAuthorize("@ss.hasPermi('algorithm:mrp:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(mrpService.selectMrpById(id));
    }

    @PreAuthorize("@ss.hasPermi('algorithm:mrp:add')")
    @Log(title = "物料需求计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Mrp mrp)
    {
        return toAjax(mrpService.insertMrp(mrp));
    }

    @PreAuthorize("@ss.hasPermi('algorithm:mrp:edit')")
    @Log(title = "物料需求计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Mrp mrp)
    {
        return toAjax(mrpService.updateMrp(mrp));
    }

    @PreAuthorize("@ss.hasPermi('algorithm:mrp:remove')")
    @Log(title = "物料需求计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(mrpService.deleteMrpByIds(ids));
    }
}
