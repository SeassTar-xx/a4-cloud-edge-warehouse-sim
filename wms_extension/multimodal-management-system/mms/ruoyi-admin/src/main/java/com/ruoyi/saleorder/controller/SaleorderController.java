package com.ruoyi.saleorder.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.mpsPlus.domain.Mps;
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
import com.ruoyi.saleorder.domain.Saleorder;
import com.ruoyi.saleorder.service.ISaleorderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 销售订单Controller
 *
 * @author ruoyi
 * @date 2024-02-16
 */
@RestController
@RequestMapping("/saleorder/saleorder")
public class SaleorderController extends BaseController
{
    @Autowired
    private ISaleorderService saleorderService;

    /**
     * 查询销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('saleorder:saleorder:list')")
    @GetMapping("/list")
    public TableDataInfo list(Saleorder saleorder)
    {
        startPage();
        List<Saleorder> list = saleorderService.selectSaleorderList(saleorder);
        return getDataTable(list);
    }

    /**
     * 导出销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('saleorder:saleorder:export')")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Saleorder saleorder)
    {
        List<Saleorder> list = saleorderService.selectSaleorderList(saleorder);
        ExcelUtil<Saleorder> util = new ExcelUtil<Saleorder>(Saleorder.class);
        util.exportExcel(response, list, "销售订单数据");
    }

    /**
     * 获取销售订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('saleorder:saleorder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(saleorderService.selectSaleorderById(id));
    }

    /**
     * 新增销售订单
     */
    @PreAuthorize("@ss.hasPermi('saleorder:saleorder:add')")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Saleorder saleorder)
    {
        saleorderService.insertSaleorder(saleorder);
        List<Saleorder> saleorders= saleorderService.selectSaleorderList(saleorder);
        saleorderService.updateSaleCode(saleorders.get(saleorders.size()-1).getId());
        return success();
    }

    /**
     * 修改销售订单
     */
    @PreAuthorize("@ss.hasPermi('saleorder:saleorder:edit')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Saleorder saleorder)
    {
        return toAjax(saleorderService.updateSaleorder(saleorder));
    }

    /**
     * 系统更改销售订单编码
     */
    @PreAuthorize("@ss.hasPermi('saleorder:saleorder:orderCode')")
    @GetMapping("/orderCode/{id}")
    public AjaxResult updateSaleCode(@PathVariable Integer id)
    {
        saleorderService.updateSaleCode(id);
        return success();
    }

    /**
     * 删除销售订单
     */
    @PreAuthorize("@ss.hasPermi('saleorder:saleorder:remove')")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(saleorderService.deleteSaleorderByIds(ids));
    }


}
