package com.tqmall.sigma.dao;

import com.tqmall.sigma.entity.CompanyInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * CompanyInfoMapper数据库操作接口类
 */

public interface CompanyInfoMapper {


    /**
     * 删除（根据主键ID删除）
     */
    Integer deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * 添加 （匹配有值的字段）
     */
    Integer insertSelective(CompanyInfoDO record);

    /**
     * 查询（根据主键ID查询）
     */
    CompanyInfoDO selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 修改 （匹配有值的字段）
     */
    Integer updateByPrimaryKeySelective(CompanyInfoDO record);

    /**
     * 批量添加
     */
    Integer batchInsert(@Param("companyInfoDOList") List<CompanyInfoDO> companyInfoDOList);

    /**
     * 动态条件查询总数目
     */
    Integer countByBaseCondition(Map<String, Object> map);

    /**
     * 动态条件查询（支持分页）
     */
    List<CompanyInfoDO> selectByBaseConditionPageable(Map<String, Object> map);

    /**
     * 批量更新
     */
    Integer batchUpdateById(@Param("companyInfoDOList") List<CompanyInfoDO> companyInfoDOList);

    CompanyInfoDO selectByMemberId(@Param("memberId") Integer memberId);
}