package com.tqmall.sigma.entity;
import lombok.Data;

/**
 * 
 * 企业会员信息表
 * 
 **/

@Data
public class CompanyInfoDO {

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

	/**企业名称**/
	private String companyName;

	/**企业地址**/
	private String companyAddress;

	/**营业执照号**/
	private String businessLicense;

	/**组织机构代码**/
	private String organizationCode;

	/**联系电话**/
	private String companyTelephone;

	/**法人姓名**/
	private String legalName;

	/**法人证件类型（1:身份证 2:护照 3:军官证 4:回乡证 5:台胞证 6:警官证 7:士兵证 99:其它证件）**/
	private Integer identityType;

	/**法人证件号码**/
	private String legalIds;

	/**法人手机号**/
	private String legalPhone;

	/**企业对公账户**/
	private String accountNo;

	/**开户银行名称**/
	private String parentBankName;

	/**开户行地区代码**/
	private String bankCityNo;

	/**开户行支行名称**/
	private String bankName;

	/**支付行号**/
	private String unionBank;

	/**备注**/
	private String companyRemark;

	/**云账户审核状态（0:待审核 2:审核成功 3:审核失败）**/
	private Integer cloudCheckStatus;

	/**云账户审核时间**/
	private java.util.Date cloudCheckTime;

	/**云账户审核失败原因**/
	private String cloudFailReason;


}
