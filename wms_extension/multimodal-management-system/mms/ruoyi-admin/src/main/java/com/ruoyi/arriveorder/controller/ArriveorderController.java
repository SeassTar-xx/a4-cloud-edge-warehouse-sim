package com.ruoyi.arriveorder.controller;

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
import com.ruoyi.arriveorder.domain.Arriveorder;
import com.ruoyi.arriveorder.service.IArriveorderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购入库表Controller
 *
 * @author ruoyi
 * @date 2024-02-16
 */
@RestController
@RequestMapping("/arriveorder/arriveorder")
public class ArriveorderController extends BaseController
{
    @Autowired
    private IArriveorderService arriveorderService;

    /**
     * 查询采购入库表列表
     */
    @PreAuthorize("@ss.hasPermi('arriveorder:arriveorder:list')")
    @GetMapping("/list")
    public TableDataInfo list(Arriveorder arriveorder)
    {
        startPage();
        List<Arriveorder> list = arriveorderService.selectArriveorderList(arriveorder);
        return getDataTable(list);
    }

    /**
     * 导出采购入库表列表
     */
    @PreAuthorize("@ss.hasPermi('arriveorder:arriveorder:export')")
    @Log(title = "采购入库表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Arriveorder arriveorder)
    {
        List<Arriveorder> list = arriveorderService.selectArriveorderList(arriveorder);
        ExcelUtil<Arriveorder> util = new ExcelUtil<Arriveorder>(Arriveorder.class);
        util.exportExcel(response, list, "采购入库表数据");
    }

    /**
     * 获取采购入库表详细信息
     */
    @PreAuthorize("@ss.hasPermi('arriveorder:arriveorder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(arriveorderService.selectArriveorderById(id));
    }

    /**
     * 新增采购入库表
     */
    @PreAuthorize("@ss.hasPermi('arriveorder:arriveorder:add')")
    @Log(title = "采购入库表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Arriveorder arriveorder)
    {
        return toAjax(arriveorderService.insertArriveorder(arriveorder));
    }

    /**
     * 系统更改入库编码
     */
    @PreAuthorize("@ss.hasPermi('arriveorder:arriveorder:orderCode')")
    @GetMapping("/orderCode/{id}")
    public AjaxResult updateOrderCode(@PathVariable Integer id)
    {
        arriveorderService.updateOrderCode(id);
        return success();
    }

    /**
     * 修改采购入库表
     */
    @PreAuthorize("@ss.hasPermi('arriveorder:arriveorder:edit')")
    @Log(title = "采购入库表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Arriveorder arriveorder)
    {
        return toAjax(arriveorderService.updateArriveorder(arriveorder));
    }

    /**
     * 删除采购入库表
     */
    @PreAuthorize("@ss.hasPermi('arriveorder:arriveorder:remove')")
    @Log(title = "采购入库表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(arriveorderService.deleteArriveorderByIds(ids));
    }
}
