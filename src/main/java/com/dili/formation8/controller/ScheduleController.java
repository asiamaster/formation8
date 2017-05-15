package com.dili.formation8.controller;

import com.dili.formation8.component.OrderScanComponent;
import com.dili.formation8.dao.ScheduleJobMapper;
import com.dili.utils.domain.BaseOutput;
import com.dili.utils.quartz.domain.ScheduleJob;
import com.dili.utils.quartz.service.JobTaskService;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 调度控制器
 * Created by asiam on 2017/5/15 0015.
 */
@Controller
@RequestMapping("/schedule")
public class ScheduleController {

    private static final Logger log = LoggerFactory.getLogger(ScheduleController.class);
    @Autowired
    JobTaskService jobTaskService;

    @Autowired
    ScheduleJobMapper scheduleJobMapper;

    @RequestMapping("/refresh.aspx")
    public @ResponseBody BaseOutput refresh(){
        List<ScheduleJob> scheduleJobs = scheduleJobMapper.selectAll();
        for(ScheduleJob scheduleJob : scheduleJobs){
            try {
                jobTaskService.updateJob(scheduleJob);
            } catch (SchedulerException e) {
                e.printStackTrace();
                log.error(e.getMessage());
                return BaseOutput.failure("调度失败, jobName:"+scheduleJob.getJobName()+", jobId:"+scheduleJob.getId());
            }
        }
        return BaseOutput.success("调度器刷新完成");
    }
}
