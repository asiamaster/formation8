package com.dili.formation8.dao;

import com.dili.formation8.domain.User;
import com.dili.formation8.vo.UserVo;
import com.dili.utils.base.MyMapper;

public interface UserMapper extends MyMapper<User> {

    //余额调整，转帐
    public int transfer(UserVo userVo);

    //查询指定层级的推荐人
    public Long getParentReferrer(UserVo userVo);
}