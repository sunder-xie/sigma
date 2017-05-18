package com.tqmall.sigma.entity;
import lombok.Data;

/**
 * 
 * 个人会员信息表
 * 
 **/

@Data
public class PersonalInfoDO {

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

	/**会员ID**/
	private Integer memberId;

	/**真实姓名**/
	private String realName;

	/**证件类型（1:身份证 2:护照 3:军官证 4:回乡证 5:台胞证 6:警官证 7:士兵证 99:其它证件）**/
	private Integer identityType;

	/**证件号码**/
	private String identityNo;

	/**实名认证时间**/
	private java.util.Date realNameTime;

	/**是否进行实名认证（0:否 1:是）**/
	private Integer isIdentityChecked;

	/**是否已设置支付密码（0:否 1:是）**/
	private Integer isSetPayPwd;


}
