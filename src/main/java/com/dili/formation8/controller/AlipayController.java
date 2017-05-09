package com.dili.formation8.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 接收扫码付后支付宝的回调
 * Created by asiam on 2017/5/3 0003.
 */
public class AlipayController {

    protected static final Logger log = LoggerFactory.getLogger(AlipayController.class);

    /**
     * <B>服务器异步通知页面特性</B><br/>
     * 1. 必须保证服务器异步通知页面（notify_url）上无任何字符，如空格、HTML标签、开发系统自带抛出的异常提示信息等；<br/>
     * 2. 支付宝是用POST方式发送通知信息，因此该页面中获取参数的方式，如：request.Form(“out_trade_no”)、$_POST[‘out_trade_no’]；<br/>
     * 3. 支付宝主动发起通知，该方式才会被启用；<br/>
     * 4. 只有在支付宝的交易管理中存在该笔交易，且发生了交易状态的改变，支付宝才会通过该方式发起服务器通知（即时到账中交易状态为“等待买家付款”的状态默认是不会发送通知的）；<br/>
     * 5. 服务器间的交互，不像页面跳转同步通知可以在页面上显示出来，这种交互方式是不可见的；<br/>
     * 6. 第一次交易状态改变（即时到账中此时交易状态是交易完成）时，不仅会返回同步处理结果，而且服务器异步通知页面也会收到支付宝发来的处理结果通知；<br/>
     * 7. 程序执行完后必须打印输出“success”（不包含引号）。如果商户反馈给支付宝的字符不是success这7个字符，支付宝服务器会不断重发通知，直到超过24小时22分钟。一般情况下，25小时以内完成8次通知（通知的间隔频率一般是：4m,10m,10m,1h,2h,6h,15h）；<br/>
     * 8. 程序执行完成后，该页面不能执行页面跳转。如果执行页面跳转，支付宝会收不到success字符，会被支付宝服务器判定为该页面程序运行出现异常，而重发处理结果通知；<br/>
     * 9. ookies、session等在此页面会失效，即无法获取这些数据；<br/>
     * 10.该方式的调试与运行必须在服务器上，即互联网上能访问；<br/>
     * 11.该方式的作用主要防止订单丢失，即页面跳转同步通知没有处理订单更新，它则去处理；<br/>
     * 12.当商户收到服务器异步通知并打印出success时，服务器异步通知参数notify_id才会失效。也就是说在支付宝发送同一条异步通知时（包含商户并未成功打印出success导致支付宝重发数次通知），服务器异步通知参数notify_id是不变的<br/>
     * 参考:https://doc.open.alipay.com/docs/doc.htm?treeId=194&articleId=103296&docType=1
     */
    @RequestMapping(value = "/pay/notify", method = RequestMethod.POST)
    public @ResponseBody
    String notifyResult(HttpServletRequest request, HttpServletResponse response) {
        log.info("收到支付宝异步通知！");
        Map<String, String> params = new HashMap<String, String>();

        //取出所有参数是为了验证签名
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }
        boolean signVerified;
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, Configs.getAlipayPublicKey(), "UTF-8");
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return "failed";
        }
        if (signVerified) {
            String outtradeno = params.get("out_trade_no");
            log.info(outtradeno + "号订单回调通知。");
//            System.out.println("验证签名成功！");
            log.info("验证签名成功！");

            //若参数中的appid和填入的appid不相同，则为异常通知
            if (!Configs.getAppid().equals(params.get("app_id"))) {
                log.warn("与付款时的appid不同，此为异常通知，应忽略！");
                return "failed";
            }

            //=======================================================
//            //在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
//            BaobiaoOrder order = baobiaoOrderService.findOrderByOuttradeno(outtradeno);
//            if (order == null) {
//                log.warn(outtradeno + "查无此订单！");
//                return "failed";
//            }
//            if (order.getAmount() != Double.parseDouble(params.get("total_amount"))) {
//                log.warn("与付款时的金额不同，此为异常通知，应忽略！");
//                return "failed";
//            }
//
//            if (order.getStatus() == BaobiaoOrder.TRADE_SUCCESS) return "success"; //如果订单已经支付成功了，就直接忽略这次通知
//
//            String status = params.get("trade_status");
//            if (status.equals("WAIT_BUYER_PAY")) { //如果状态是正在等待用户付款
//                if (order.getStatus() != BaobiaoOrder.WAIT_BUYER_PAY) baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.WAIT_BUYER_PAY, outtradeno);
//            } else if (status.equals("TRADE_CLOSED")) { //如果状态是未付款交易超时关闭，或支付完成后全额退款
//                if (order.getStatus() != BaobiaoOrder.TRADE_CLOSED) baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.TRADE_CLOSED, outtradeno);
//            } else if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) { //如果状态是已经支付成功
//                if (order.getStatus() != BaobiaoOrder.TRADE_SUCCESS) baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.TRADE_SUCCESS, outtradeno);
//            } else {
//                baobiaoOrderService.modifyTradeStatus(BaobiaoOrder.UNKNOWN_STATE, outtradeno);
//            }
//            log.info(outtradeno + "订单的状态已经修改为" + status);
            //=======================================================
        } else { //如果验证签名没有通过
            return "failed";
        }
        return "success";
    }
}
