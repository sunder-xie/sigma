package com.tqmall.sigma.entity;
import lombok.Data;

/**
 * 
 * 会员表
 * 
 **/

@Data
public class MemberInfoDO {

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

	/**淘汽业务用户ID**/
	private Integer bizUserId;

	/**淘汽业务用户类型（1:门店 2:商家）**/
	private Integer bizUserType;

	/**云账户编号**/
	private String cloudUserId;

    /**会员名称**/
    private String memberName;

	/**已绑定手机号码**/
	private String memberPhone;

	/**是否绑定手机（0:否 1:是）**/
	private Integer isPhoneChecked;

	/**会员类型（2:企业会员 3:个人会员）**/
	private Integer memberType;

	/**访问终端类型（1:Mobile 2:PC）**/
	private Integer visitSource;

	/**会员状态（0:待生效 1:已生效 2:关闭 3:冻结）**/
	private Integer memberStatus;

	/**会员登录邮箱**/
	private String memberEmail;

	/**会员登录密码**/
	private String memberPassword;

	/**上次登录时间**/
	private java.util.Date lastLoginTime;

	/**上次登录IP**/
	private String lastLoginIp;

	/**淘汽审核状态（0:待审核 1:审核通过 2:审核不通过）**/
	private Integer checkStatus;

	/**失败原因**/
	private String failReason;


}
