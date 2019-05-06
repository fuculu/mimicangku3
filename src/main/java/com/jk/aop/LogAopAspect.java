package com.jk.aop;
//import com.jk.model.UserBean;
import com.jk.model.LoginBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;



@Aspect  //声明这是切面层
@Component //让注解可以扫描到该层
public class LogAopAspect {
    @Autowired
    MongoTemplate mongoTemplate;
    @Pointcut(value="execution(* com.jk.service..*Impl.*(..))")
    //，定义切入点 申明哪些方法需要日志记录（MyLog）  del
    public void LoginLogaop() {

    }


    @AfterReturning(value="LoginLogaop()",returning="revalue")
    public void delValue(JoinPoint joinPoint, Object revalue) {
        LoginBean longbean=new LoginBean();
        //方法名 methodName
        String name = joinPoint.getSignature().getName();
        longbean.setMethodName(name);
        //类名   className
        String simpleName = joinPoint.getTarget().getClass().getSimpleName();
        longbean.setClassName(simpleName);

//        //用户ID   userid  需登录后去Session 里找
//        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = requestAttributes.getRequest();

//        UserBean usersBean = (UserBean) request.getSession().getAttribute(request.getSession().getId());
//        //，如果没登录，sessionID里没值
//        if(usersBean.getId()!=null) {
//            //，userBean里的ID 放到 long 里的userid;
//            longbean.setUserid(usersBean.getId());
//        }

        //创建时间 createtime
        longbean.setCreatetime(new Date());

        //请求参数   param  是数组因为  参数不确定数量，所以取出  需循环
        Object[] args = joinPoint.getArgs();
        String argStr="";
        for (Object object : args) {
            argStr+= argStr==""?object:","+object;
        }
        //String param = argStr.split(",").toString();
        longbean.setParam(argStr);

        //返回值   returnValue
        if(revalue!=null) {
            longbean.setReturnValue(revalue.toString());
        }

        //最后一步把获取到的值 保存到MongoDB 数据库的Long 表里
        mongoTemplate.save(longbean);
    }
}
