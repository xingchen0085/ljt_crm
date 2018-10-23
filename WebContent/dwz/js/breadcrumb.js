/*
* 面包屑效果  cretead by chenxinghua
* */
var breadCrumbArray = new Array();

//加面包屑
function addBreadCrumb(id, url, text) {
    var isEx = false;
    $.each(breadCrumbArray, function (i, e) {
        if (e.id == id) {
            isEx = true;
            return false;
        }
    })
    if (!isEx) {
        breadCrumbArray.push({'id': id, 'url': url, 'text': text});
    }
}

//显示： ul id为breadCrumbBox
/**
 * @param eleId 展示面包屑的组件ID
 * @param breadCrumbId 面包屑的ID，不同页面使用唯一
 * @param url 该页面的地址
 * @param text 面包屑的展示文本
 */
function showBreadCrumb(eleId, breadCrumbId, url, text) {
    addBreadCrumb(breadCrumbId, url, text);
    $("#" + eleId).html("<li><span style=\"line-height:20px\">当前位置:&nbsp;&nbsp;</span></li>");
    var isEnd = false;
    $.each(breadCrumbArray, function (i, e) {
        //到当前的面包屑时，之后的面包屑删除
        if (e.id == breadCrumbId) {
            breadCrumbArray.splice(i + 1, breadCrumbArray.length);
            isEnd = true;
        }
        if (i == breadCrumbArray.length - 1) {
            $("#" + eleId).append("<li style=\"margin-left:5px\"><span style=\"line-height:20px\">" + e.text + "</span></li>");
        } else {
            $("#" + eleId).append("<li style=\"margin-left:5px\">" +
                "<span style=\"line-height:20px\">" + e.text + "</span> <span style=\"line-height:20px;margin-left:5px\">/</span></li>");
        }
        if (isEnd) {
            return false;
        }
    });
}