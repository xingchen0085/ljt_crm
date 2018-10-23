package com.douples.pay.user.dao.impl;

import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import org.springframework.stereotype.Repository;

import com.douples.pay.common.dao.impl.BaseDaoImpl;
import com.douples.pay.user.dao.UserInfoDao;
import com.douples.pay.user.entity.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息dao实现类
 * <p>Title: UserInfoDaoImpl</p>
 * <p>Description: </p>
 *
 * @author hexuefeng
 * @date 2017-12-1
 */
@Repository
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo> implements UserInfoDao {

    private static final String SQL_LISTPAGE_WITH_STATUS_USERNO = "listPageWithStatusAndUserNo";

    @Override
    public void reSetPwd(UserInfo userInfo) {
        getSessionTemplate().update("reSetPwd", userInfo);
    }

    @Override
    public PageBean listPageWithStatusAndUserNo(PageParam pageParam, Map<String, Object> paramMap) {
        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
        }
        // 统计总记录数
        Long totalCount = getSessionTemplate().selectOne(getStatement(SQL_LIST_PAGE_COUNT), paramMap);

        // 校验当前页数
        int currentPage = PageBean.checkCurrentPage(totalCount.intValue(), pageParam.getNumPerPage(), pageParam.getPageNum());
        pageParam.setPageNum(currentPage); // 为当前页重新设值
        // 校验页面输入的每页记录数numPerPage是否合法
        int numPerPage = PageBean.checkNumPerPage(pageParam.getNumPerPage()); // 校验每页记录数
        pageParam.setNumPerPage(numPerPage); // 重新设值

        // 根据页面传来的分页参数构造SQL分页参数
        paramMap.put("pageFirst", (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("pageSize", pageParam.getNumPerPage());
        paramMap.put("startRowNum", (pageParam.getPageNum() - 1) * pageParam.getNumPerPage());
        paramMap.put("endRowNum", pageParam.getPageNum() * pageParam.getNumPerPage());

        // 获取分页数据集
        List<Object> list = getSessionTemplate().selectList(getStatement(SQL_LISTPAGE_WITH_STATUS_USERNO), paramMap);

        Object isCount = paramMap.get("isCount"); // 是否统计当前分页条件下的数据：1:是，其他为否
        if (isCount != null && "1".equals(isCount.toString())) {
            Map<String, Object> countResultMap = getSessionTemplate().selectOne(getStatement(SQL_COUNT_BY_PAGE_PARAM), paramMap);
            return new PageBean(pageParam.getPageNum(), pageParam.getNumPerPage(), totalCount.intValue(), list, countResultMap);
        } else {
            // 构造分页对象
            return new PageBean(pageParam.getPageNum(), pageParam.getNumPerPage(), totalCount.intValue(), list);
        }
    }
}