package com.ruoyi.commodity.controller;

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
import com.ruoyi.commodity.domain.Commodity;
import com.ruoyi.commodity.service.ICommodityService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品信息Controller
 *
 * @author ruoyi
 * @date 2024-02-07
 */
@RestController
@RequestMapping("/commodity/commodity")
public class CommodityController extends BaseController
{
    @Autowired
    private ICommodityService commodityService;

    /**
     * 查询商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('commodity:commodity:list')")
    @GetMapping("/list")
    public TableDataInfo list(Commodity commodity)
    {
        startPage();
        List<Commodity> list = commodityService.selectCommodityList(commodity);
        return getDataTable(list);
    }

    /**
     * 导出商品信息列表
     */
    @PreAuthorize("@ss.hasPermi('commodity:commodity:export')")
    @Log(title = "商品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Commodity commodity)
    {
        List<Commodity> list = commodityService.selectCommodityList(commodity);
        ExcelUtil<Commodity> util = new ExcelUtil<Commodity>(Commodity.class);
        util.exportExcel(response, list, "商品信息数据");
    }

    /**
     * 获取商品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('commodity:commodity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(commodityService.selectCommodityById(id));
    }

    /**
     * 新增商品信息
     */
    @PreAuthorize("@ss.hasPermi('commodity:commodity:add')")
    @Log(title = "商品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Commodity commodity)
    {
        return toAjax(commodityService.insertCommodity(commodity));
    }

    /**
     * 生成商品信息
     */
    @PreAuthorize("@ss.hasPermi('commodity:commodity:auto')")
    @GetMapping("/auto")
    public AjaxResult auto()
    {
        return success(commodityService.autoItemCode());
    }

    /**
     * 修改商品信息
     */
    @PreAuthorize("@ss.hasPermi('commodity:commodity:edit')")
    @Log(title = "商品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Commodity commodity)
    {
        return toAjax(commodityService.updateCommodity(commodity));
    }

    /**
     * 删除商品信息
     */
    @PreAuthorize("@ss.hasPermi('commodity:commodity:remove')")
    @Log(title = "商品信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(commodityService.deleteCommodityByIds(ids));
    }
}
