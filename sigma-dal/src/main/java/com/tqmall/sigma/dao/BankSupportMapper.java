package com.tqmall.sigma.dao;

import com.tqmall.sigma.entity.BankSupportDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * BankSupportMapper数据库操作接口类
 */

public interface BankSupportMapper {


    /**
     * 删除（根据主键ID删除）
     */
    Integer deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * 添加 （匹配有值的字段）
     */
    Integer insertSelective(BankSupportDO record);

    /**
     * 查询（根据主键ID查询）
     */
    BankSupportDO selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 修改 （匹配有值的字段）
     */
    Integer updateByPrimaryKeySelective(BankSupportDO record);

    /**
     * 批量添加
     */
    Integer batchInsert(@Param("bankSupportDOList") List<BankSupportDO> bankSupportDOList);

    /**
     * 动态条件查询总数目
     */
    Integer countByBaseCondition(Map<String, Object> map);

    /**
     * 动态条件查询（支持分页）
     */
    List<BankSupportDO> selectByBaseConditionPageable(Map<String, Object> map);

    /**
     * 批量更新
     */
    Integer batchUpdateById(@Param("bankSupportDOList") List<BankSupportDO> bankSupportDOList);

}