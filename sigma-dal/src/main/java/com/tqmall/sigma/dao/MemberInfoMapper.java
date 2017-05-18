package com.tqmall.sigma.dao;

import com.tqmall.sigma.entity.MemberInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * MemberInfoMapper数据库操作接口类
 */

public interface MemberInfoMapper {


    /**
     * 删除（根据主键ID删除）
     */
    Integer deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * 添加 （匹配有值的字段）
     */
    Integer insertSelective(MemberInfoDO record);

    /**
     * 查询（根据主键ID查询）
     */
    MemberInfoDO selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 修改 （匹配有值的字段）
     */
    Integer updateByPrimaryKeySelective(MemberInfoDO record);

    /**
     * 批量添加
     */
    Integer batchInsert(@Param("memberInfoDOList") List<MemberInfoDO> memberInfoDOList);

    /**
     * 动态条件查询总数目
     */
    Integer countByBaseCondition(Map<String, Object> map);

    /**
     * 动态条件查询（支持分页）
     */
    List<MemberInfoDO> selectByBaseConditionPageable(Map<String, Object> map);

    /**
     * 批量更新
     */
    Integer batchUpdateById(@Param("memberInfoDOList") List<MemberInfoDO> memberInfoDOList);

    MemberInfoDO selectByBizUserIdAndType(@Param("bizUserId") Integer bizUserId, @Param("bizUserType") Integer bizUserType);

    MemberInfoDO selectByEmail(@Param("email")String email);
}