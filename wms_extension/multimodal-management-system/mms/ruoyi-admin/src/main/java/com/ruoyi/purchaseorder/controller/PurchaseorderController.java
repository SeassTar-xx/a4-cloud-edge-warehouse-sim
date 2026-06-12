package com.ruoyi.purchaseorder.controller;

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
import com.ruoyi.purchaseorder.domain.Purchaseorder;
import com.ruoyi.purchaseorder.service.IPurchaseorderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购订单Controller
 *
 * @author ruoyi
 * @date 2024-02-17
 */
@RestController
@RequestMapping("/purchaseorder/purchaseorder")
public class PurchaseorderController extends BaseController
{
    @Autowired
    private IPurchaseorderService purchaseorderService;

    /**
     * 查询采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('purchaseorder:purchaseorder:list')")
    @GetMapping("/list")
    public TableDataInfo list(Purchaseorder purchaseorder)
    {
        startPage();
        List<Purchaseorder> list = purchaseorderService.selectPurchaseorderList(purchaseorder);
        return getDataTable(list);
    }

    /**
     * 导出采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('purchaseorder:purchaseorder:export')")
    @Log(title = "采购订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Purchaseorder purchaseorder)
    {
        List<Purchaseorder> list = purchaseorderService.selectPurchaseorderList(purchaseorder);
        ExcelUtil<Purchaseorder> util = new ExcelUtil<Purchaseorder>(Purchaseorder.class);
        util.exportExcel(response, list, "采购订单数据");
    }

    /**
     * 获取采购订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('purchaseorder:purchaseorder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(purchaseorderService.selectPurchaseorderById(id));
    }

    /**
     * 系统更改orderCode
     */
    @PreAuthorize("@ss.hasPermi('purchaseorder:purchaseorder:orderCode')")
    @GetMapping("/orderCode/{id}")
    public AjaxResult updateOrderCode(@PathVariable Integer id)
    {
        purchaseorderService.updateOrderCode(id);
        return success();
    }

    /**
     * 新增采购订单
     */
    @PreAuthorize("@ss.hasPermi('purchaseorder:purchaseorder:add')")
    @Log(title = "采购订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Purchaseorder purchaseorder)
    {
        purchaseorderService.insertPurchaseorder(purchaseorder);
        List<Purchaseorder> purchaseorders= purchaseorderService.selectPurchaseorderList(purchaseorder);
        return toAjax(purchaseorderService.updateOrderCode(purchaseorders.get(purchaseorders.size()-1).getId()));
    }

    /**
     * 修改采购订单
     */
    @PreAuthorize("@ss.hasPermi('purchaseorder:purchaseorder:edit')")
    @Log(title = "采购订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Purchaseorder purchaseorder)
    {
        return toAjax(purchaseorderService.updatePurchaseorder(purchaseorder));
    }

    /**
     * 删除采购订单
     */
    @PreAuthorize("@ss.hasPermi('purchaseorder:purchaseorder:remove')")
    @Log(title = "采购订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(purchaseorderService.deletePurchaseorderByIds(ids));
    }
}
