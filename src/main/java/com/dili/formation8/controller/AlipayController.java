package com.dili.formation8.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
    @RequestMapping(value = "/pay/notify", method = RequestMethod.POST)
    public String notifyResult(HttpServletRequest request, HttpServletResponse response) {
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
