package com.dili.formation8.controller;

import com.dili.formation8.service.BizNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 由MyBatis Generator工具自动生成
 * This file was generated on 2017-04-28 10:10:06.
 */
@Controller
@RequestMapping("/bizNumber")
public class BizNumberController {
    @Autowired
    BizNumberService bizNumberService;

    @RequestMapping("/getDepositOrderCode")
    public String getDepositOrderCode(ModelMap modelMap) {
        long start = System.currentTimeMillis();
        //测试10万条数据
        for(int i=0; i<100000; i++){
            bizNumberService.getDepositOrderCode();
        }
//        cost:193575ms
        System.out.println("=============cost:"+(System.currentTimeMillis()-start)+"ms");
        modelMap.put("code", bizNumberService.getDepositOrderCode());
        return "bizNumber/list";
    }

    @RequestMapping("/getProductOrderCode")
    public String getProductOrderCode(ModelMap modelMap) {
        modelMap.put("code", bizNumberService.getProductOrderCode());
        return "bizNumber/list";
    }

}