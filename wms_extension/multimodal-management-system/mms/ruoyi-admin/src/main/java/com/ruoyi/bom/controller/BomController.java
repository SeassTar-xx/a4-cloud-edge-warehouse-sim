package com.ruoyi.bom.controller;

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
import com.ruoyi.bom.domain.Bom;
import com.ruoyi.bom.service.IBomService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * bomController
 * 
 * @author diaoaonan
 * @date 2024-02-06
 */
@RestController
@RequestMapping("/bom/bom")
public class BomController extends BaseController
{
    @Autowired
    private IBomService bomService;

    /**
     * 查询bom列表
     */
    @PreAuthorize("@ss.hasPermi('bom:bom:list')")
    @GetMapping("/list")
    public AjaxResult list(Bom bom)
    {
        List<Bom> list = bomService.selectBomList(bom);
        return success(list);
    }

    /**
     * 导出bom列表
     */
    @PreAuthorize("@ss.hasPermi('bom:bom:export')")
    @Log(title = "bom", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Bom bom)
    {
        List<Bom> list = bomService.selectBomList(bom);
        ExcelUtil<Bom> util = new ExcelUtil<Bom>(Bom.class);
        util.exportExcel(response, list, "bom数据");
    }

    /**
     * 获取bom详细信息
     */
    @PreAuthorize("@ss.hasPermi('bom:bom:query')")
    @GetMapping(value = "/{bomid}")
    public AjaxResult getInfo(@PathVariable("bomid") Long bomid)
    {
        return success(bomService.selectBomByBomid(bomid));
    }

    /**
     * 新增bom
     */
    @PreAuthorize("@ss.hasPermi('bom:bom:add')")
    @Log(title = "bom", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody List<Bom> boms)
    {
        bomService.insertBom(boms);
        return success();
    }

    /**
     * 修改bom
     */
    @PreAuthorize("@ss.hasPermi('bom:bom:edit')")
    @Log(title = "bom", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody List<Bom> boms)
    {
        bomService.updateBom(boms);
        return success();
    }

    /**
     * 删除bom
     */
    @PreAuthorize("@ss.hasPermi('bom:bom:remove')")
    @Log(title = "bom", businessType = BusinessType.DELETE)
	@DeleteMapping("/{bomids}")
    public AjaxResult remove(@PathVariable Long[] bomids)
    {
        return toAjax(bomService.deleteBomByBomids(bomids));
    }
}
