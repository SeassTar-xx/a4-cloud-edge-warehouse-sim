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
import com.ruoyi.algorithm.domain.Opminfo;
import com.ruoyi.algorithm.mapper.OpminfoMapper;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 订货点参数Controller
 */
@RestController
@RequestMapping("/algorithm/opminfo")
public class OpminfoController extends BaseController
{
    @Autowired
    private OpminfoMapper opminfoMapper;

    @PreAuthorize("@ss.hasPermi('algorithm:opminfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(Opminfo opminfo)
    {
        startPage();
        List<Opminfo> list = opminfoMapper.selectOpminfoList(opminfo);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('algorithm:opminfo:export')")
    @Log(title = "订货点参数", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Opminfo opminfo)
    {
        List<Opminfo> list = opminfoMapper.selectOpminfoList(opminfo);
        ExcelUtil<Opminfo> util = new ExcelUtil<Opminfo>(Opminfo.class);
        util.exportExcel(response, list, "订货点参数数据");
    }

    @PreAuthorize("@ss.hasPermi('algorithm:opminfo:query')")
    @GetMapping(value = "/{itemCode}")
    public AjaxResult getInfo(@PathVariable("itemCode") String itemCode)
    {
        return success(opminfoMapper.selectOpminfoByItemCode(itemCode));
    }

    @PreAuthorize("@ss.hasPermi('algorithm:opminfo:add')")
    @Log(title = "订货点参数", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Opminfo opminfo)
    {
        return toAjax(opminfoMapper.insertOpminfo(opminfo));
    }

    @PreAuthorize("@ss.hasPermi('algorithm:opminfo:edit')")
    @Log(title = "订货点参数", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Opminfo opminfo)
    {
        return toAjax(opminfoMapper.updateOpminfo(opminfo));
    }

    @PreAuthorize("@ss.hasPermi('algorithm:opminfo:remove')")
    @Log(title = "订货点参数", businessType = BusinessType.DELETE)
    @DeleteMapping("/{itemCodes}")
    public AjaxResult remove(@PathVariable String[] itemCodes)
    {
        return toAjax(opminfoMapper.deleteOpminfoByItemCodes(itemCodes));
    }
}
