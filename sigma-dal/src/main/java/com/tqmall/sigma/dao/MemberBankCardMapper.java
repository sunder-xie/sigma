package com.tqmall.sigma.dao;

import com.tqmall.sigma.entity.MemberBankCardDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * MemberBankCardMapper数据库操作接口类
 */

public interface MemberBankCardMapper {


    /**
     * 删除（根据主键ID删除）
     */
    Integer deleteByPrimaryKey(@Param("id") Integer id);

    /**
     * 添加 （匹配有值的字段）
     */
    Integer insertSelective(MemberBankCardDO record);

    /**
     * 查询（根据主键ID查询）
     */
    MemberBankCardDO selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 修改 （匹配有值的字段）
     */
    Integer updateByPrimaryKeySelective(MemberBankCardDO record);

    /**
     * 批量添加
     */
    Integer batchInsert(@Param("memberBankCardDOList") List<MemberBankCardDO> memberBankCardDOList);

    /**
     * 动态条件查询总数目
     */
    Integer countByBaseCondition(Map<String, Object> map);

    /**
     * 动态条件查询（支持分页）
     */
    List<MemberBankCardDO> selectByBaseConditionPageable(Map<String, Object> map);

    /**
     * 批量更新
     */
    Integer batchUpdateById(@Param("memberBankCardDOList") List<MemberBankCardDO> memberBankCardDOList);

    MemberBankCardDO selectByMemberIdAndCardNo(@Param("memberId")Integer memberId,
                                               @Param("cardNo")String cardNo);
}