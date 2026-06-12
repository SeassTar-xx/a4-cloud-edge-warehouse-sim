package com.ruoyi.returnorder.controller;

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

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.returnorder.domain.Returnorder;
import com.ruoyi.returnorder.service.IReturnorderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * returnorderController
 * 
 * @author diaoaonan
 * @date 2024-02-06
 */
@RestController
@RequestMapping("/returnorder/returnorder")
public class ReturnorderController extends BaseController
{
    @Autowired
    private IReturnorderService returnorderService;

    /**
     * 查询returnorder列表
     */
    @PreAuthorize("@ss.hasPermi('returnorder:returnorder:list')")
    @GetMapping("/list")
    public TableDataInfo list(Returnorder returnorder)
    {
        startPage();
        List<Returnorder> list = returnorderService.selectReturnorderList(returnorder);
        return getDataTable(list);
    }

    /**
     * 导出returnorder列表
     */
    @PreAuthorize("@ss.hasPermi('returnorder:returnorder:export')")
    @Log(title = "returnorder", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Returnorder returnorder)
    {
        List<Returnorder> list = returnorderService.selectReturnorderList(returnorder);
        ExcelUtil<Returnorder> util = new ExcelUtil<Returnorder>(Returnorder.class);
        util.exportExcel(response, list, "returnorder数据");
    }

    /**
     * 获取returnorder详细信息
     */
    @PreAuthorize("@ss.hasPermi('returnorder:returnorder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(returnorderService.selectReturnorderById(id));
    }

    /**
     * 新增returnorder
     */
    @PreAuthorize("@ss.hasPermi('returnorder:returnorder:add')")
    // @Anonymous
    @Log(title = "returnorder", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Returnorder returnorder)
    {
        return toAjax(returnorderService.insertReturnorder(returnorder));
    }

    /**
     * 修改returnorder
     */
    @PreAuthorize("@ss.hasPermi('returnorder:returnorder:edit')")
    @Log(title = "returnorder", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Returnorder returnorder)
    {
        return toAjax(returnorderService.updateReturnorder(returnorder));
    }

    /**
     * 删除returnorder
     */
    @PreAuthorize("@ss.hasPermi('returnorder:returnorder:remove')")
    @Log(title = "returnorder", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(returnorderService.deleteReturnorderByIds(ids));
    }
}
