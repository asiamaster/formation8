package com.dili.formation8.utils.beetl;

import com.dili.formation8.service.CommonService;
import org.beetl.core.Context;
import org.beetl.core.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 根据id获取用户
 * Created by asiam on 2017/5/12 0012.
 */
@Component("getById")
public class GetByIdFunction implements Function {

    @Autowired
    CommonService commonService;
    @Override
    public Object call(Object[] objects, Context context) {
        Object id = objects[0];
        Object table = objects[1];
        Object attr = objects[2];
        if (table != null && attr != null)
        {
            try
            {
                context.byteWriter.writeObject(commonService.getById(table.toString(), Long.parseLong(id.toString()), attr.toString()));
            }
            catch (Exception e)
            {
                e.printStackTrace();
//                throw new RuntimeException(e);
                return "GetByIdFunction失败,参数1:"+objects[0]+", 参数2:"+objects[1]+", 参数3:"+objects[2];
            }
        }
        return "";
    }
}