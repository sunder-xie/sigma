package com.tqmall.sigma.entity;
import lombok.Data;

/**
 * 
 * 会员银行卡表
 * 
 **/

@Data
public class MemberBankCardDO {

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

	/**银行卡号**/
	private String bankCardNo;

	/**银行预留手机号**/
	private String bankPhone;

	/**卡bin**/
	private String cardBin;

	/**发卡行代码**/
	private String bankCode;

	/**发卡行名称**/
	private String bankName;

	/**卡片状态（0:无效 1:有效）**/
	private Integer cardState;

	/**绑定时间**/
	private java.util.Date bindTime;

	/**解绑时间**/
	private java.util.Date unbindTime;

	/**申请绑定时间**/
	private String applyTime;

    /**绑卡流水号**/
    private String tranceNum;

	/**银行卡类型（1:借记卡 2:信用卡）**/
	private Integer cardType;

	/**绑定状态（0:未绑定 1:已绑定 2:已解除）**/
	private Integer bindState;

	/**是否安全卡（0:否 1:是）**/
	private Integer isSafeCard;


}
