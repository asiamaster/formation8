package com.dili.formation8.utils;

/**
 * Formation8常量和枚举
 * Created by asiam on 2017/5/4 0004.
 */
public class Formation8Constants {
//    引领人费率
    public static final String SYSTEM_CONFIG_REFERRAL_RATE1 = "REFERRAL_RATE1";
//    引荐人费率
    public static final String SYSTEM_CONFIG_REFERRAL_RATE2 = "REFERRAL_RATE2";
//    提现金额下限
    public static final String SYSTEM_CONFIG_WITHDRAW_DEPOSIT_AMOUNT_FLOOR = "WITHDRAW_DEPOSIT_AMOUNT_FLOOR";
//    提现费率
    public static final String SYSTEM_CONFIG_WITHDRAW_DEPOSIT_RATE = "WITHDRAW_DEPOSIT_RATE";
//    平台用户id
    public static final Long PLATFORM_USER_ID = 1l;
//    平台域名
    public static final String WEBSITE_DOMAINS="formation8";
//    平台passport域名
    public static final String PASSPORT_DOMAINS = "passport.formation8.com";
//    平台主站域名
    public static final String WWW_DOMAIN = "http://www.formation8.com";
    //平台cookies域名
    public static final String cookieDomain = ".formation8.com";


    //用户信息编码
    public static final String USER_DATA_ENCODING = "UTF-16LE";
    //用户信息过期时间
    public static long EXPIRED_TIME = 2592000000L;
    /**
     * 是否可用
     */
    public enum Yn{
        YES(1,"是"),
        NO(0,"否");
        private Integer code ;
        private String desc;

        Yn(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static Yn getYn(Integer code) {
            for (Yn yn : Yn.values()) {
                if (yn.getCode()==code) {
                    return yn;
                }
            }
            return null;
        }
    }

    /**
     * 财务交易类型(1:充值;2:转帐)
     */
    public enum FinanciaTransactionType{
        DEPOSIT(1,"充值"),
        TRANSFER(2,"转帐");
        private Integer code ;
        private String desc;

        FinanciaTransactionType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static FinanciaTransactionType getFinanciaTransactionType(Integer code) {
            for (FinanciaTransactionType financiaTransactionType : FinanciaTransactionType.values()) {
                if (financiaTransactionType.getCode()==code) {
                    return financiaTransactionType;
                }
            }
            return null;
        }
    }

    /**
     * 支付方式: 1:支付宝;2:微信
     */
    public enum PaymentPattern{
        ALIPAY(1,"支付宝"),
        WECHAT(2,"微信");
        private Integer code ;
        private String desc;

        PaymentPattern(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static PaymentPattern getPaymentPattern(Integer code) {
            for (PaymentPattern paymentPattern: PaymentPattern.values()) {
                if (paymentPattern.getCode()==code) {
                    return paymentPattern;
                }
            }
            return null;
        }
    }

    /**
     * 银行卡类型
     */
    public enum CardType{
        DEPOSIT_CARD(1,"储蓄卡"),
        CREDIT_CARD(2,"信用卡");
        private Integer code ;
        private String desc;

        CardType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static CardType getCardType(Integer code) {
            for (CardType cardType: CardType.values()) {
                if (cardType.getCode()==code) {
                    return cardType;
                }
            }
            return null;
        }
    }

    /**
     * 提现状态
     */
    public enum WithdrawalState{
        PENDING(1,"待处理"),
        PROCESSED(2,"已处理");
        private Integer code ;
        private String desc;

        WithdrawalState(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static WithdrawalState getWithdrawalState(Integer code) {
            for (WithdrawalState withdrawalState: WithdrawalState.values()) {
                if (withdrawalState.getCode()==code) {
                    return withdrawalState;
                }
            }
            return null;
        }
    }

    /**
     * 产品状态,1:上架,2:过期下架,3:手动下架
     */
    public enum ProductStatus{
        PUTAWAY(1,"上架"),
        OVERDUE(2,"过期下架"),
        MANUAL(3,"手动下架");
        private Integer code ;
        private String desc;

        ProductStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static ProductStatus getProductStatus(Integer code) {
            for (ProductStatus productStatus: ProductStatus.values()) {
                if (productStatus.getCode()==code) {
                    return productStatus;
                }
            }
            return null;
        }
    }

    /**
     * 用户类型,1:普通用户,2:平台当前现金,3:平台抽成,4:股东,5:机器人
     */
    public enum UserType{
        NORMAL(1,"普通用户"),
        CURRENT_CASH(2,"平台当前现金"),
        ROYALTY(3,"平台抽成"),
        SHAREHOLDER(4,"股东"),
        ROBOT(5,"机器人");
        private Integer code ;
        private String desc;

        UserType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static UserType getUserType(Integer code) {
            for (UserType userType: UserType.values()) {
                if (userType.getCode()==code) {
                    return userType;
                }
            }
            return null;
        }
    }

    /**
     * 充值订单状态:1:待付款,2:付款成功;3:付款失败
     */
    public enum DepositStatus{
        PENDING(1,"待付款"),
        SUCCEED(2,"付款成功"),
        FAILURE(3,"付款失败");
        private Integer code ;
        private String desc;

        DepositStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static DepositStatus getDepositStatus(Integer code) {
            for (DepositStatus depositStatus: DepositStatus.values()) {
                if (depositStatus.getCode()==code) {
                    return depositStatus;
                }
            }
            return null;
        }
    }

    /**
     * 产品订单状态,1:众筹中;2:众筹成功;3:众筹失败
     */
    public enum OrderStatus{
        PROGRESSING(1,"众筹中"),
        SUCCEED(2,"众筹成功"),
        FAILURE(3,"众筹失败");
        private Integer code ;
        private String desc;

        OrderStatus(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static OrderStatus getOrderStatus(Integer code) {
            for (OrderStatus orderStatus: OrderStatus.values()) {
                if (orderStatus.getCode()==code) {
                    return orderStatus;
                }
            }
            return null;
        }
    }

    /**
     * 订单交易类型(1:钱;2:产品;  众筹成功后可以选产品)
     */
    public enum OrderTransactionType{
        MONEY(1,"钱"),
        PRODUCT(2,"产品");
        private Integer code ;
        private String desc;

        OrderTransactionType(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }
        public Integer getCode() {
            return code;
        }
        public void setCode(Integer code) {
            this.code = code;
        }
        public String getDesc() {
            return desc;
        }
        public void setDesc(String desc) {
            this.desc = desc;
        }
        public static OrderTransactionType getOrderTransactionType(Integer code) {
            for (OrderTransactionType orderTransactionType : OrderTransactionType.values()) {
                if (orderTransactionType.getCode()==code) {
                    return orderTransactionType;
                }
            }
            return null;
        }
    }

}
