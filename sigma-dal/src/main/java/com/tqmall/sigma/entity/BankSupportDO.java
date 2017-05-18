package com.tqmall.sigma.entity;
import lombok.Data;

/**
 * 
 * 通联支持银行字典表
 * 
 **/

@Data
public class BankSupportDO {

	/**自增ID**/
	private Integer id;

	/**是否删除,N:未删除，Y:删除**/
	private String isDeleted;

	/**记录创建时间**/
	private java.util.Date gmtCreate;

	/**记录修改时间,如果时间是1970年则表示纪录未修改**/
	private java.util.Date gmtModified;

	/**创建人，0表示无创建人值**/
	private Integer creator;

	/**修改人,如果为0则表示纪录未修改**/
	private Integer modifier;

	/**银行名称**/
	private String bankName;

	/**银行代码**/
	private String bankCode;

	/**是否支持信用卡（0:否 1:是）**/
	private Integer isSupportCredit;

	/**是否支持借记卡（0:否 1:是）**/
	private Integer isSupportDebit;

	/**业务类型编码**/
	private String bizType;

	/**银行图标app端**/
	private String bankIconApp;

	/**银行图标web端**/
	private String bankIconWeb;

	/**排序规则:升序**/
	private Integer sortOrder;


}
