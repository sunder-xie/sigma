package com.tqmall.sigma.entity;
import lombok.Data;

/**
 * 
 * 支付订单表
 * 
 **/

@Data
public class PayOrderInfoDO {

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

	/**当前支付订单号**/
	private String payOrderSn;

	/**淘汽业务订单号**/
	private String bizOrderSn;

	/**订单类型（1:充值订单 2:提现订单 3:消费订单 4:退款订单）**/
	private Integer orderType;

	/**订单支付金额**/
	private java.math.BigDecimal payOrderAmount;

	/**手续费**/
	private java.math.BigDecimal handingFee;

	/**支付状态（0:未支付 1:支付中 2:已支付）**/
	private Integer payStatus;

	/**付款方ID**/
	private Integer fromMemberId;

	/**付款方名称**/
	private String fromMemberName;

	/**收款方ID**/
	private Integer toMemberId;

	/**收款方名称**/
	private String toMemberName;

	/**支付类型，参考fc_payment**/
	private Integer payId;

	/**外部平台ID**/
	private Integer platformId;

	/**云账户订单号**/
	private String cloudOrderNo;

	/**云账户交易编码**/
	private String cloudTradeNo;


}
