package com.ruoyi.algorithm.controller;

import java.util.Collections;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.algorithm.domain.Systeminfo;
import com.ruoyi.algorithm.mapper.SysteminfoMapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 算法系统参数Controller
 */
@RestController
@RequestMapping("/algorithm/systeminfo")
public class SysteminfoController extends BaseController
{
    @Autowired
    private SysteminfoMapper systeminfoMapper;

    @PreAuthorize("@ss.hasPermi('algorithm:systeminfo:list')")
    @GetMapping("/list")
    public TableDataInfo list()
    {
        Systeminfo systeminfo = systeminfoMapper.selectSysteminfo();
        return getDataTable(systeminfo == null ? Collections.emptyList() : Collections.singletonList(systeminfo));
    }

    @PreAuthorize("@ss.hasPermi('algorithm:systeminfo:export')")
    @Log(title = "算法系统参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response)
    {
        Systeminfo systeminfo = systeminfoMapper.selectSysteminfo();
        ExcelUtil<Systeminfo> util = new ExcelUtil<Systeminfo>(Systeminfo.class);
        util.exportExcel(response, systeminfo == null ? Collections.emptyList() : Collections.singletonList(systeminfo), "算法系统参数数据");
    }

    @PreAuthorize("@ss.hasPermi('algorithm:systeminfo:query')")
    @GetMapping
    public AjaxResult getInfo()
    {
        return success(systeminfoMapper.selectSysteminfo());
    }

    @PreAuthorize("@ss.hasPermi('algorithm:systeminfo:edit')")
    @Log(title = "算法系统参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult save(@RequestBody Systeminfo systeminfo)
    {
        Systeminfo old = systeminfoMapper.selectSysteminfo();
        if (old == null) {
            return toAjax(systeminfoMapper.insertSysteminfo(systeminfo));
        }
        if (!old.getCurrentCycleNum().equals(systeminfo.getCurrentCycleNum())) {
            systeminfoMapper.deleteSysteminfoByCurrentCycleNum(old.getCurrentCycleNum());
            return toAjax(systeminfoMapper.insertSysteminfo(systeminfo));
        }
        return toAjax(systeminfoMapper.updateSysteminfo(systeminfo));
    }
}
