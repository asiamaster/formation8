package com.dili.formation8.utils.beetl;

import com.dili.formation8.domain.User;
import com.dili.formation8.utils.Formation8Constants;
import com.dili.utils.beetl.VirtualAttributeResolver;
import org.springframework.stereotype.Component;

/**
 * 用户状态虚拟属性解析器示例
 * Created by asiamastor on 2017/1/23.
 */
@Component
public class UserTypeResolver implements VirtualAttributeResolver {
    @Override
    public String resolve(Object o, String attrName) {
        User user = (User) o;
        if(attrName.equals("type") && user.getType() != null)
            return Formation8Constants.UserType.getUserType(user.getType()).getDesc();
        return "";
    }

    @Override
    public Class resolveClass() {
        return User.class;
    }
}
