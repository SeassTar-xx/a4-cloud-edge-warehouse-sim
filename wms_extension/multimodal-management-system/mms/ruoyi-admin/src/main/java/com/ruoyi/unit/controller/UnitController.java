package com.ruoyi.unit.controller;

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
import com.ruoyi.unit.domain.Unit;
import com.ruoyi.unit.service.IUnitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 单位Controller
 * 
 * @author ruoyi
 * @date 2024-01-31
 */
@RestController
@RequestMapping("/unit/unit")
public class UnitController extends BaseController
{
    @Autowired
    private IUnitService unitService;

    /**
     * 查询单位列表
     */
    @PreAuthorize("@ss.hasPermi('unit:unit:list')")
    @GetMapping("/list")
    public TableDataInfo list(Unit unit)
    {
        startPage();
        List<Unit> list = unitService.selectUnitList(unit);
        return getDataTable(list);
    }

    /**
     * 导出单位列表
     */
    @PreAuthorize("@ss.hasPermi('unit:unit:export')")
    @Log(title = "单位", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Unit unit)
    {
        List<Unit> list = unitService.selectUnitList(unit);
        ExcelUtil<Unit> util = new ExcelUtil<Unit>(Unit.class);
        util.exportExcel(response, list, "单位数据");
    }

    /**
     * 获取单位详细信息
     */
    @PreAuthorize("@ss.hasPermi('unit:unit:query')")
    @GetMapping(value = "/{uid}")
    public AjaxResult getInfo(@PathVariable("uid") Long uid)
    {
        return success(unitService.selectUnitByUid(uid));
    }

    /**
     * 新增单位
     */
    @PreAuthorize("@ss.hasPermi('unit:unit:add')")
    @Log(title = "单位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Unit unit)
    {
        return toAjax(unitService.insertUnit(unit));
    }

    /**
     * 修改单位
     */
    @PreAuthorize("@ss.hasPermi('unit:unit:edit')")
    @Log(title = "单位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Unit unit)
    {
        return toAjax(unitService.updateUnit(unit));
    }

    /**
     * 删除单位
     */
    @PreAuthorize("@ss.hasPermi('unit:unit:remove')")
    @Log(title = "单位", businessType = BusinessType.DELETE)
	@DeleteMapping("/{uids}")
    public AjaxResult remove(@PathVariable Long[] uids)
    {
        return toAjax(unitService.deleteUnitByUids(uids));
    }
}
