package com.tqmall.sigma.dao;

import com.tqmall.sigma.entity.PersonalInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * PersonalInfoMapper数据库操作接口类
 */

public interface PersonalInfoMapper {


    /**
     * 删除（根据主键ID删除）
     */
    Integer deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * 添加 （匹配有值的字段）
     */
    Integer insertSelective(PersonalInfoDO record);

    /**
     * 查询（根据主键ID查询）
     */
    PersonalInfoDO selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 修改 （匹配有值的字段）
     */
    Integer updateByPrimaryKeySelective(PersonalInfoDO record);

    /**
     * 批量添加
     */
    Integer batchInsert(@Param("personalInfoDOList") List<PersonalInfoDO> personalInfoDOList);

    /**
     * 动态条件查询总数目
     */
    Integer countByBaseCondition(Map<String, Object> map);

    /**
     * 动态条件查询（支持分页）
     */
    List<PersonalInfoDO> selectByBaseConditionPageable(Map<String, Object> map);

    /**
     * 批量更新
     */
    Integer batchUpdateById(@Param("personalInfoDOList") List<PersonalInfoDO> personalInfoDOList);

    PersonalInfoDO selectByMemberId(@Param("memberId") Integer memberId);
}