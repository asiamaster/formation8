package com.dili.formation8.component;

import com.dili.formation8.dao.ScheduleJobMapper;
import com.dili.formation8.service.UserService;
import com.dili.utils.quartz.domain.ScheduleMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * 平台给股东打款扫描组件，可以考虑配置为每天凌晨0点打款, cron:"0 0 0 * * ?"
 * Created by asiam on 2017/5/15 0015.
 */
@Component
public class PlatformTransferComponent {

    private static final Logger log = LoggerFactory.getLogger(PlatformTransferComponent.class);

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    /**
     * 运行PingServer机器地址
     */
    private String serverName;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        try {
            InetAddress inetAddress = Inet4Address.getLocalHost();
            if (StringUtils.isNotBlank(inetAddress.getHostAddress())) {
                serverName = "PlatformTransfer[" + inetAddress.getHostAddress() + "]";
            }
        } catch (Exception e) {
            log.warn("[PlatformTransfer]获取运行机器名失败，发生异常：" + e.getMessage());
            serverName = "";
        }
    }

    /**
     * 调度消息
     *
     * @param scheduleMessage
     */
    public void scan(ScheduleMessage scheduleMessage) {
        System.out.println("serverName:" + serverName + ",线程:" + Thread.currentThread().getName() + ",当前调度Job:" + scheduleMessage.getJobGroup() + scheduleMessage.getJobName() + "运行第" + scheduleMessage.getSheduelTimes() + "次.");
        try {
            userService.transferToShareholder();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
        }
    }

}
