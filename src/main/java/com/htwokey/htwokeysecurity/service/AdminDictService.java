package com.htwokey.htwokeysecurity.service;

import com.htwokey.htwokeysecurity.entity.AdminDictData;
import com.htwokey.htwokeysecurity.entity.AdminDictType;

import java.util.List;

/**
 * @author hchbo
 * @since  2023/6/19 13:48
 */
public interface AdminDictService {
    /**
     * 获取数据字典列表
     * @param dict 数据字典
     * @param pageNum 当前页码
     * @param pageSize 每页条数
     * @return 数据字典集合
     */
    List<AdminDictType> selectDictTypeList(AdminDictType dict, Integer pageNum, Integer pageSize);

    /**
     * 新增字典类型
     * @param dict 字典类型
     * @return 结果
     */
    int addDictType(AdminDictType dict);

    /**
     * 修改字典类型
     * @param dict 字典类型
     * @return 结果
     */
    int updateDictType(AdminDictType dict);

    /**
     * 删除字典类型
     * @param ids 需要删除的字典类型ID
     * @return 结果
     */
    int deleteDictTypeByIds(Long[] ids);

    /**
     * 获取字典数据列表
     * @param data 字典数据
     * @param pageNum 当前页码
     * @param pageSize 每页条数
     * @return 字典数据集合
     */
    List<AdminDictData> selectDictDataList(AdminDictData data, Integer pageNum, Integer pageSize);

    /**
     * 新增字典数据
     * @param dict 字典数据
     * @return 结果
     */
    int insertDictData(AdminDictData dict);

    /**
     * 修改字典数据
     * @param dict 字典数据
     * @return 结果
     */
    int updateDictData(AdminDictData dict);

    /**
     * 批量删除字典数据
     * @param ids 需要删除的字典数据ID
     * @return 结果
     */
    int deleteDictDataByIds(Long[] ids);

    /**
     * 根据字典类型查询字典数据信息
     * @param dictName 字典类型
     * @return 字典数据集合信息
     */
    List<AdminDictData> selectDictDataByType(String dictName);
}
