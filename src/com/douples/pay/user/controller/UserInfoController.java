package com.douples.pay.user.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douples.pay.common.controller.BaseController;
import com.douples.pay.common.dwz.DwzAjax;
import com.douples.pay.common.enums.PropertyAddFlagEnum;
import com.douples.pay.common.enums.StatusEnum;
import com.douples.pay.common.page.PageBean;
import com.douples.pay.common.page.PageParam;
import com.douples.pay.common.utils.EncryptUtil;
import com.douples.pay.common.utils.PropertyUtils;
import com.douples.pay.user.entity.UserInfo;
import com.douples.pay.user.service.UserInfoFacade;

/**
 * 用户控制器
 * <p>Title: UserInfoController</p>
 * <p>Description: 用户控制器</p>
 *
 * @author hexuefeng
 * @date 2017-12-1
 * <p>
 * update chenxinghua
 */
@Controller
@RequestMapping("/user/info")
public class UserInfoController extends BaseController {

    @Autowired
    private UserInfoFacade userInfoFacade;

    /**
     * 查询用户信息
     * <p>Title: list</p>
     * <p>Description: </p>
     *
     * @param userInfo
     * @param pageParam
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(UserInfo userInfo, PageParam pageParam, Model model) {
        PageBean pageBean = userInfoFacade.listPage(pageParam, userInfo);
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("userInfo", userInfo);
        return "user/info/list";
    }

    /**
     * 跳转添加
     * <p>Title: addUI</p>
     * <p>Description: </p>
     *
     * @return
     */
    @RequiresPermissions("user:userInfo:add")
    @RequestMapping(value = "/addUI", method = RequestMethod.GET)
    public String addUI(Model model) {
        return "user/info/add";
    }

    /**
     * 保存
     * <p>Title: add</p>
     * <p>Description: </p>
     *
     * @param model
     * @param userName
     * @param mobile
     * @param password
     * @param dwz
     * @return
     */
    @RequiresPermissions("user:userInfo:add")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(Model model, UserInfo userInfo, DwzAjax dwz) {
        UserInfo userData = userInfoFacade.getDataByMobile(userInfo.getMobile());
        if (null != userData) {
            return operateError("手机号码已存在，无法添加用户", model);
        }
        PropertyUtils.addDefaultProperty(userInfo, PropertyAddFlagEnum.INSERT,
                getOperator().getId(), getOperator().getRealName());
        userInfoFacade.registerOffline(userInfo);
        return operateSuccess(model, dwz);
    }

    /**
     * 函数功能说明 ： 前往修改用户页面
     */
    @RequestMapping(value = "/editUI", method = {RequestMethod.POST, RequestMethod.GET})
    public String editUI(UserInfo userInfo, Model model) {
        UserInfo userInfoById = userInfoFacade.getById(userInfo.getId());
        model.addAttribute("userInfo", userInfoById);
        return "user/info/edit";
    }

    /**
     * 函数功能说明 ： 修改用户!!!!!密码不支持修改；仅能重置，且重置后密码为123456
     */
    @RequestMapping(value = "/edit", method = {RequestMethod.POST, RequestMethod.GET})
    public String edit(UserInfo userInfo, Model model, DwzAjax dwz) {
        UserInfo userData = userInfoFacade.getDataByMobile(userInfo.getMobile());
        if (!userInfo.getId().equals(userData.getId())) {
            return operateError("手机号码已存在", model);
        }
        PropertyUtils.addDefaultProperty(userInfo, PropertyAddFlagEnum.UPDATE,
                getOperator().getId(), getOperator().getRealName());


        userInfoFacade.updateUserInfo(userInfo);

        //返回成功信息提示
        return operateSuccess(model, dwz);
    }

    /**
     * 删除
     *
     * @param model
     * @param dwz
     * @return
     */
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
    public String delete(UserInfo userInfo, Model model, DwzAjax dwz) {
        //当用户为关联商家时可以删除,否返回失败
        UserInfo userInfoData = userInfoFacade.getById(userInfo.getId());

        userInfoFacade.deleteUserInfo(userInfoData.getId());

        //返回成功信息提示
        return operateSuccess(model, dwz);
    }

    /**
     * 查看单条信息
     *
     * @return
     */
    @RequestMapping(value = "/infoUI", method = {RequestMethod.POST, RequestMethod.GET})
    public String infoUI(UserInfo userInfo, Model model) {
        //查询
        UserInfo userInfoByData = userInfoFacade.getById(userInfo.getId());
        model.addAttribute("userInfo", userInfoByData);
        return "user/info/info";
    }


    /**
     * 更改状态
     *
     * @return
     */
    @RequestMapping(value = "/changeStatus", method = {RequestMethod.POST, RequestMethod.GET})
    public String changeStatus(UserInfo userInfo, DwzAjax dwz, Model model) {
        UserInfo userInfoFacadeById = userInfoFacade.getById(userInfo.getId());
        if (StatusEnum.ENABLE.getKey().equals(userInfoFacadeById.getStatus())) {
            userInfoFacadeById.setStatus(StatusEnum.DISABLE.getKey());
        } else {
            userInfoFacadeById.setStatus(StatusEnum.ENABLE.getKey());
        }

        userInfoFacade.updateUserInfo(userInfoFacadeById);

        return operateSuccess(model, dwz);
    }

    /**
     * 查询用户信息 查找带回
     * <p>Title: lookupList</p>
     * <p>Description: </p>
     *
     * @param userInfo
     * @param pageParam
     * @param isBinding 当值未非空字符串时，查询的是未被绑定商户的用户，如果只查出状态可用的用户，该值不传即可
     * @return
     */
    @RequestMapping(value = "/lookupList", method = {RequestMethod.POST, RequestMethod.GET})
    public String lookupList(UserInfo userInfo, PageParam pageParam, Model model, String isBinding) {

        PageBean pageBean = null;
        //只查可用的用户出去
        userInfo.setStatus(StatusEnum.ENABLE.getKey());
        if (!StringUtils.isEmpty(isBinding)) {
            //只查出未被绑定的用户
            pageBean = userInfoFacade.listPageWithStatusAndUserNo(pageParam, userInfo);
            model.addAttribute("isBinding", isBinding);
        } else {
            pageBean = userInfoFacade.listPageWithStatus(pageParam, userInfo);
        }
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("pageParam", pageParam);
        model.addAttribute("userInfo", userInfo);
        return "user/info/lookupList";
    }

    /**
     * 重置密码 重置后密码为123456
     */
    @RequestMapping(value = "resetPwd", method = {RequestMethod.GET, RequestMethod.POST})
    public String resetPwd(String userId, Model model, DwzAjax dwz) {

        if (StringUtils.isEmpty(userId)) {
            return operateError("数据不完整", model);
        }

        UserInfo userInfo = userInfoFacade.getById(userId);
        //加密：
        String enPwd = EncryptUtil.encodeMD5String("123456");
        userInfo.setPassword(enPwd);

        userInfoFacade.reSetPwd(userInfo);

        return operateSuccess(model, dwz);
    }


}









