package cn.e3mall.cart.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.utils.StringUtils;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * 判断是不是登陆 拦截器Intercepter
 * <p>Title: LoginIntercepter</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
public class LoginIntercepter implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//前处理，执行Handler之前执行方法
		//返回true，放行，false拦截
		//1.从cookie中取token
		String token = CookieUtils.getCookieValue(request, "token");
		//2.如果没有token，未登录状态，直接放行
	 	if (StringUtils.isBlank(token)) {
			return true;
		}
		//3.取到token，需要调用sso系统的服务，根据token取用户信息
	 	E3Result e3Result = tokenService.getUserByToken(token);
	 	//4.没有取到用户信息，登陆过期，直接放行。
	 	if (e3Result.getStatus()!=200) {
			return true;
		}
		//5.取到用户信息.登陆状态
	 	TbUser user=(TbUser) e3Result.getData();
		//6.把用户信息放到request中，只需要Conroller中判断request中是否包含user信息。
	 	request.setAttribute("user", user);
	 	return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		//完成处理，返回ModelAndView之后。
		//可以再此处理异常

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
		
		//handler执行之后，返回ModeAndView之前
	}

	
}
