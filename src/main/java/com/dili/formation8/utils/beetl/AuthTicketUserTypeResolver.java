package com.dili.formation8.utils.beetl;

import com.dili.formation8.domain.User;
import com.dili.formation8.passport.AuthTicket;
import com.dili.formation8.utils.Formation8Constants;
import com.dili.utils.beetl.VirtualAttributeResolver;
import org.springframework.stereotype.Component;

/**
 * 登录信息用户类型虚拟属性解析器示例
 * Created by asiamastor on 2017/1/23.
 */
@Component
public class AuthTicketUserTypeResolver implements VirtualAttributeResolver {
    @Override
    public String resolve(Object o, String attrName) {
        AuthTicket ticket = (AuthTicket) o;
        if(attrName.equals("_userType") && ticket.get_userType() != null)
            return Formation8Constants.UserType.getUserType(ticket.get_userType()).getDesc();
        return "";
    }

    @Override
    public Class resolveClass() {
        return AuthTicket.class;
    }
}
