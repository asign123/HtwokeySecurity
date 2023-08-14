package com.htwokey.htwokeysecurity.controller;

import com.htwokey.htwokeysecurity.entity.AdminDictData;
import com.htwokey.htwokeysecurity.entity.AdminDictType;
import com.htwokey.htwokeysecurity.entity.api.CommonPage;
import com.htwokey.htwokeysecurity.entity.api.CommonResult;
import com.htwokey.htwokeysecurity.service.AdminDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author hchbo
 * @since  2023/5/10 9:26
 */
@RestController
@RequestMapping("/admin/dict")
public class AdminDictController {

    @Autowired
    private AdminDictService dictService;
    /**
     * 获取数据字典列表
     */
    @GetMapping("/type/list")
    @ResponseBody
    public CommonResult<CommonPage<?>> list(AdminDictType dict,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize) {
        List<AdminDictType> dictList = dictService.selectDictTypeList(dict,pageNum,pageSize);
        return CommonResult.success(CommonPage.restPage(dictList));
    }

    /**
     * 新增字典类型
     */
    @PostMapping("/type/add")
    @ResponseBody
    public CommonResult<?> add(@RequestBody AdminDictType dict, Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized("");
        }
        dict.setCreateBy(principal.getName());
        int count = dictService.addDictType(dict);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("新增字典类型失败");
    }

    /**
     * 修改字典类型
     */
    @PutMapping("/type/update")
    @ResponseBody
    public CommonResult<?> update(@RequestBody AdminDictType dict, Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized("");
        }
        dict.setUpdateBy(principal.getName());
        int count = dictService.updateDictType(dict);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改字典类型失败");
    }

    /**
     * 删除字典类型
     */
    @DeleteMapping("/type/delete/{ids}")
    @ResponseBody
    public CommonResult<?> delete(@PathVariable Long[] ids) {
        int count = dictService.deleteDictTypeByIds(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除字典类型失败");
    }

    /**
     * 获取字典详细
     */
    @GetMapping("/data/list")
    @ResponseBody
    public CommonResult<CommonPage<?>> dataList(AdminDictData data,
                                    @RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize) {
    List<AdminDictData> dataList = dictService.selectDictDataList(data,pageNum,pageSize);
    return CommonResult.success(CommonPage.restPage(dataList));
}

    /**
     * 新增字典数据
     */
    @PostMapping("/data/add")
    @ResponseBody
    public CommonResult<?> addDictData(@RequestBody AdminDictData dict, Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized("");
        }
        dict.setCreateBy(principal.getName());
        int count = dictService.insertDictData(dict);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("新增字典数据失败");
    }

    /**
     * 修改字典数据
     */
    @PutMapping("/data/update")
    @ResponseBody
    public CommonResult<?> updateData(@RequestBody AdminDictData dict, Principal principal) {
        if (principal == null) {
            return CommonResult.unauthorized("");
        }
        dict.setUpdateBy(principal.getName());
        int count = dictService.updateDictData(dict);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("修改字典数据失败");
    }

    /**
     * 删除字典数据
     */
    @DeleteMapping("/data/delete/{ids}")
    @ResponseBody
    public CommonResult<?> deleteData(@PathVariable Long[] ids) {
        int count = dictService.deleteDictDataByIds(ids);
        if (count > 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed("删除字典数据失败");
    }

    /**
     * 根据字典名称查询字典数据信息
     */
    @GetMapping("/list")
    @ResponseBody
    public CommonResult<List<AdminDictData>> dictData(@RequestParam String dictName) {
        List<AdminDictData> dictData = dictService.selectDictDataByType(dictName);
        return CommonResult.success(dictData);
    }
}
