package com.ruoyi.stock.controller;

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
import com.ruoyi.stock.domain.Stock;
import com.ruoyi.stock.service.IStockService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 初级库存录入Controller
 *
 * @author ruoyi
 * @date 2024-02-17
 */
@RestController
@RequestMapping("/stock/stock")
public class StockController extends BaseController
{
    @Autowired
    private IStockService stockService;

    /**
     * 查询初级库存录入列表
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:list')")
    @GetMapping("/list")
    public TableDataInfo list(Stock stock)
    {
        startPage();
        List<Stock> list = stockService.selectStockList(stock);
        return getDataTable(list);
    }

    /**
     * 模糊查询初级库存录入列表
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:itemCode')")
    @GetMapping("/itemCode/{itemCode}")
    public AjaxResult moselect(@PathVariable String itemCode)
    {
        List<Stock> stocks = stockService.selectStockByItemCode(itemCode);
        if(stocks.size()!=0&&stocks.get(0)!=null){
            return success(stocks.get(0));
        }
        return success(null);
    }


    /**
     * 导出初级库存录入列表
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:export')")
    @Log(title = "初级库存录入", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Stock stock)
    {
        List<Stock> list = stockService.selectStockList(stock);
        ExcelUtil<Stock> util = new ExcelUtil<Stock>(Stock.class);
        util.exportExcel(response, list, "初级库存录入数据");
    }

    /**
     * 获取初级库存录入详细信息
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return success(stockService.selectStockById(id));
    }

    /**
     * 新增初级库存录入
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:add')")
    @Log(title = "初级库存录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Stock stock)
    {
        return toAjax(stockService.insertStock(stock));
    }

    /**
     * 修改初级库存录入
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:edit')")
    @Log(title = "初级库存录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Stock stock)
    {
        return toAjax(stockService.updateStock(stock));
    }

    /**
     * 删除初级库存录入
     */
    @PreAuthorize("@ss.hasPermi('stock:stock:remove')")
    @Log(title = "初级库存录入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(stockService.deleteStockByIds(ids));
    }
}
