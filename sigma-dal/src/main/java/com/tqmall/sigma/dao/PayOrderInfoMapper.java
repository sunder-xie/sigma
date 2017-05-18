package com.tqmall.sigma.dao;

import com.tqmall.sigma.entity.PayOrderInfoDO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

/**
 * 
 * PayOrderInfoMapper数据库操作接口类
 * 
 **/

public interface PayOrderInfoMapper{


	/**
	 * 
	 * 删除（根据主键ID删除）
	 * 
	 **/
	Integer deleteByPrimaryKey ( @Param("id") Integer id );

	/**
	 * 
	 * 添加 （匹配有值的字段）
	 * 
	 **/
	Integer insertSelective( PayOrderInfoDO record );

	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	PayOrderInfoDO  selectByPrimaryKey ( @Param("id") Integer id );

	/**
	 * 
	 * 修改 （匹配有值的字段）
	 * 
	 **/
	Integer updateByPrimaryKeySelective( PayOrderInfoDO record );

	/**
	 * 
	 * 批量添加
	 * 
	 **/
	Integer batchInsert ( @Param("payOrderInfoDOList") List<PayOrderInfoDO> payOrderInfoDOList );

	/**
	 * 
	 * 动态条件查询总数目
	 * 
	 **/
	Integer countByBaseCondition ( Map<String,Object> map );

	/**
	 * 
	 * 动态条件查询（支持分页）
	 * 
	 **/
	List<PayOrderInfoDO> selectByBaseConditionPageable ( Map<String,Object> map );

	/**
	 * 
	 * 批量更新
	 * 
	 **/
	Integer batchUpdateById ( @Param("payOrderInfoDOList") List<PayOrderInfoDO> payOrderInfoDOList );

}