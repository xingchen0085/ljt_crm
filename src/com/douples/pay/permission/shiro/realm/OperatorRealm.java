package com.douples.pay.permission.shiro.realm;

import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.douples.pay.common.enums.PublicStatusEnum;
import com.douples.pay.permission.entity.Operator;
import com.douples.pay.permission.service.OperatorRoleFacade;
import com.douples.pay.permission.service.OperatorFacade;
import com.douples.pay.permission.service.RolePermissionFacade;
import com.douples.pay.permission.utils.Constants;

/**
 * 自定义安全处理器
 * <p>Title: OperatorRealm</p>  
 * <p>Description: </p>  
 * @author hexuefeng  
 * @date 2017-12-1
 */
public class OperatorRealm extends AuthorizingRealm {

	@Autowired
	private OperatorFacade operatorFacade;
	
	@Autowired
	private OperatorRoleFacade operatorRoleFacade;
	
	@Autowired
	private RolePermissionFacade rolePermissionFacade;

	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		
		String loginName = (String) principals.getPrimaryPrincipal();

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		Operator operator = (Operator) session.getAttribute(Constants.CURR_SESSION_OPERATOR);
		if (operator == null) {
			operator = operatorFacade.findOperatorByLoginName(loginName);
			session.setAttribute(Constants.CURR_SESSION_OPERATOR, operator);
		}
		// 根据登录名查询操作员
		Long operatorId = operator.getId();

		Set<String> roles = (Set<String>) session.getAttribute(Constants.CURR_SESSION_ROLES);
		if (roles == null || roles.isEmpty()) {
			roles = operatorRoleFacade.getRoleCodeByOperatorId(operatorId);
			session.setAttribute(Constants.CURR_SESSION_ROLES, roles);
		}
		// 查询角色信息
		authorizationInfo.setRoles(roles);

		Set<String> permisstions = (Set<String>) session.getAttribute(Constants.CURR_SESSION_PERMISSIONS);
		if (permisstions == null || permisstions.isEmpty()) {
			permisstions = rolePermissionFacade.getPermissionsByOperatorId(operatorId);
			session.setAttribute(Constants.CURR_SESSION_PERMISSIONS, permisstions);
		}
		// 根据用户名查询权限
		authorizationInfo.setStringPermissions(permisstions);
		return authorizationInfo;
	}

	@Override
	// 验证的核心方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		String username = (String) token.getPrincipal();
		if (StringUtils.isEmpty(username.trim())) {
			throw new UnknownAccountException();// 没找到帐号
		}

		// 根据登录名查询操作员
		Operator operator = operatorFacade.findOperatorByLoginName(username);

		if (operator == null) {
			throw new UnknownAccountException();// 没找到帐号
		}

		if (PublicStatusEnum.UNACTIVE.equals(operator.getStatus())) {
			throw new LockedAccountException(); // 帐号锁定
		}

		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(operator.getLoginName(), // 登录名
				operator.getLoginPwd(), // 密码
				ByteSource.Util.bytes(operator.getCredentialsSalt()), // salt=username+salt
				getName() // realm name
		);

		return authenticationInfo;
	}

	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

}
