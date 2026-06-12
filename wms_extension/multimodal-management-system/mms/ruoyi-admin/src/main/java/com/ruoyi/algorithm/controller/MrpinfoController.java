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
import com.ruoyi.algorithm.domain.Mrpinfo;
import com.ruoyi.algorithm.mapper.MrpinfoMapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * MRP可用率Controller
 */
@RestController
@RequestMapping("/algorithm/mrpinfo")
public class MrpinfoController extends BaseController
{
    @Autowired
    private MrpinfoMapper mrpinfoMapper;

    @PreAuthorize("@ss.hasPermi('algorithm:mrpinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Mrpinfo mrpinfo)
    {
        startPage();
        List<Mrpinfo> list = mrpinfoMapper.selectMrpinfoList(mrpinfo);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('algorithm:mrpinfo:export')")
    @Log(title = "MRP可用率", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Mrpinfo mrpinfo)
    {
        List<Mrpinfo> list = mrpinfoMapper.selectMrpinfoList(mrpinfo);
        ExcelUtil<Mrpinfo> util = new ExcelUtil<Mrpinfo>(Mrpinfo.class);
        util.exportExcel(response, list, "MRP可用率数据");
    }

    @PreAuthorize("@ss.hasPermi('algorithm:mrpinfo:query')")
    @GetMapping(value = "/{itemCode}")
    public AjaxResult getInfo(@PathVariable("itemCode") String itemCode)
    {
        return success(mrpinfoMapper.selectMrpinfoByItemCode(itemCode));
    }

    @PreAuthorize("@ss.hasPermi('algorithm:mrpinfo:add')")
    @Log(title = "MRP可用率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Mrpinfo mrpinfo)
    {
        return toAjax(mrpinfoMapper.insertMrpinfo(mrpinfo));
    }

    @PreAuthorize("@ss.hasPermi('algorithm:mrpinfo:edit')")
    @Log(title = "MRP可用率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Mrpinfo mrpinfo)
    {
        return toAjax(mrpinfoMapper.updateMrpinfo(mrpinfo));
    }

    @PreAuthorize("@ss.hasPermi('algorithm:mrpinfo:remove')")
    @Log(title = "MRP可用率", businessType = BusinessType.DELETE)
    @DeleteMapping("/{itemCodes}")
    public AjaxResult remove(@PathVariable String[] itemCodes)
    {
        return toAjax(mrpinfoMapper.deleteMrpinfoByItemCodes(itemCodes));
    }
}
