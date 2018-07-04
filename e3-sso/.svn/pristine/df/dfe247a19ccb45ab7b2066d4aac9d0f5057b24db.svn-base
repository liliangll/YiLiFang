package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.util.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.sso.service.RegisterService;
@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private TbUserMapper userMapper;
	/**
	 * 数据效验
	 * <p>Title: checkData</p>
	 * <p>Description: </p>
	 * @param param
	 * @param type
	 * @return
	 * @see cn.e3mall.sso.service.RegisterService#checkData(java.lang.String, int)
	 */
	@Override
	public E3Result checkData(String param, int type) {
		//根据不同的type生成不同的查询条件
		TbUserExample example=new TbUserExample();
		Criteria criteria=example.createCriteria();
		//1.用户名，2.手机号，3.邮箱
		if (type==1) {
			criteria.andUsernameEqualTo(param);
		}else if(type==2)
		{
			criteria.andPhoneEqualTo(param);
		}else if (type==3) {
			criteria.andEmailEqualTo(param);
		}else {
			return E3Result.build(400, "数据类型错误");
		}
		//执行查询
		List<TbUser> list=userMapper.selectByExample(example);
		//判断结果中是否包含数据
		if (list!=null&&list.size()>0) {
			//如果有数据返回false
			return E3Result.ok(false);
		}
			return E3Result.ok(true);
	}
	/**
	 * 注册
	 * <p>Title: register</p>
	 * <p>Description: </p>
	 * @param tbUser
	 * @return
	 * @see cn.e3mall.sso.service.RegisterService#register(cn.e3mall.pojo.TbUser)
	 */
	@Override
	public E3Result register(TbUser tbUser) {
		//数据有效效验
		if (StringUtils.isBlank(tbUser.getUsername())||StringUtils.isBlank(tbUser.getPassword())
				||StringUtils.isBlank(tbUser.getPhone())) {
			return E3Result.build(400, "数据不完整，注册失败");
		}
		//1.用户名，2.手机号，3.邮箱
		E3Result result = checkData(tbUser.getUsername(), 1);
		if (!(boolean) result.getData()) {
			return E3Result.build(400, "此用户名被占用了");
		}
		result=checkData(tbUser.getPhone(), 2);
		if (!(boolean) result.getData()) {
			return E3Result.build(400, "手机号被占用了");
		}
		//不全pojo属性
		tbUser.setCreated(new Date());
		tbUser.setUpdated(new Date());
		//对密码进行加密
		String md5Pass = DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes());
		tbUser.setPassword(md5Pass);
		//把用户数据插入数据库中去
		userMapper.insert(tbUser);
		return E3Result.ok();
	}

}
