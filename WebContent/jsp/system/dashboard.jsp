<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018\6\27 0027
  Time: 11:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<div layoutH="0" id="ruleBox">
    <div class="row">
        <div class="layui-fluid" style="background-color: #f2f2f2">
            <div class="layui-row layui-col-space15">

                <div class="layui-col-sm6 layui-col-md3">
                    <div class="layui-card">
                        <div class="layui-card-header">
                            订单总交易量
                            <span class="layui-badge layui-bg-blue layuiadmin-badge">ORDER</span>
                        </div>
                        <div class="layui-card-body layuiadmin-card-list">
                            <p class="layuiadmin-big-font">${TOTAL_ORDER_COUNT}</p>
                            <p>
                                今日订单交易量
                                <span class="layuiadmin-span-color">${TODAY_ORDER_COUNT} 笔<i
                                        class="layui-inline layui-icon layui-icon-flag"></i></span>
                            </p>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm6 layui-col-md3">
                    <div class="layui-card">
                        <div class="layui-card-header">
                            平台总流水
                            <span class="layui-badge layui-bg-green layuiadmin-badge">AMOUT</span>
                        </div>
                        <div class="layui-card-body layuiadmin-card-list">

                            <p class="layuiadmin-big-font"><fmt:formatNumber value="${TOTAL_ORDER_AMT}"
                                                                             pattern="#,##0.0#"/></p>
                            <p>
                                今日流水
                                <span class="layuiadmin-span-color"><fmt:formatNumber value="${TODAY_ORDER_AMT}"
                                                                                      pattern="#,##0.0#"/> 元<i
                                        class="layui-inline layui-icon layui-icon-dollar"></i></span>
                            </p>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm6 layui-col-md3">
                    <div class="layui-card">
                        <div class="layui-card-header">
                            平台总利润
                            <span class="layui-badge layui-bg-cyan layuiadmin-badge">PROFIT</span>
                        </div>
                        <div class="layui-card-body layuiadmin-card-list">
                            <p class="layuiadmin-big-font"><fmt:formatNumber value="${TOTAL_PLAT_PROFIT}"
                                                                             pattern="#,##0.0#"/></p>
                            <p>
                                今日平台利润
                                <span class="layuiadmin-span-color"><fmt:formatNumber value="${TODAY_PLAT_PROFIT}"
                                                                                      pattern="#,##0.0#"/> 元<i
                                        class="layui-inline layui-icon layui-icon-dollar"></i></span>
                            </p>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm6 layui-col-md3">
                    <div class="layui-card">
                        <div class="layui-card-header">
                            商户总数量
                            <span class="layui-badge layui-bg-orange layuiadmin-badge">MERCHANT</span>
                        </div>
                        <div class="layui-card-body layuiadmin-card-list">
                            <p class="layuiadmin-big-font">${TOTAL_MERCHANT_REG}</p>
                            <p>
                                今日入驻商户
                                <span class="layuiadmin-span-color">${TODAY_MERCHANT_REG} <i
                                        class="layui-inline layui-icon layui-icon-user"></i></span>
                            </p>
                        </div>
                    </div>
                </div>

                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">
                            交易统计
                            <div class="layui-btn-group layuiadmin-btn-group">
                                <%--<a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs">今年</a>
                                <a href="javascript:;" class="layui-btn layui-btn-primary layui-btn-xs">所有</a>--%>
                            </div>
                        </div>
                        <div class="layui-card-body">
                            <div class="layui-row">
                                <div class="layui-col-sm6">
                                    <div id="platLr" style="min-width:550px;height:400px"></div>
                                </div>
                                <div class="layui-col-sm6">
                                    <div id="channelLr" style="min-width:100%;height:400px"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        var dayArr = ${PLAT_STATIC_MONTH.dayArr};
        var totleMoneyArr = ${PLAT_STATIC_MONTH.totleMoneyArr};
        var moneyArr = ${PLAT_STATIC_MONTH.moneyArr};
        var channelArr = ${CHANNEL_STATIC_MONTH};
        show(dayArr, totleMoneyArr, moneyArr, channelArr);
    })

    function show(dayArr, totleMoneyArr, moneyArr, channelArr) {
        //平台统计
        Highcharts.chart(
            'platLr',
            {
                chart: {
                    type: 'column',
                    panning: true,
                    panKey: 'shift'
                },
                title: {
                    text: '平台交易统计'
                },
                subtitle: {
                    text: '统计时间：${DATA_PUBLISH_TIME}'
                },
                xAxis: {
                    categories: dayArr,
                    crosshair: true,
                },
                yAxis: {
                    min: 0,
                    title: {
                        text: '金额(元)'
                    }
                },
                tooltip: {
                    headerFormat: '<span style="font-size:10px">{point.key}</span><table>',
                    pointFormat: '<tr><td style="color:{series.color};padding:0">{series.name}: </td>'
                    + '<td style="padding:0"><b>{point.y} 元</b></td></tr>',
                    footerFormat: '</table>',
                    shared: true,
                    useHTML: true
                },
                plotOptions: {
                    column: {
                        pointPadding: 0.2,
                        borderWidth: 0
                    },
                    series: {
                        pointWidth: 10
                    }
                },
                series: [{
                    name: '流水',
                    data: totleMoneyArr

                }, {
                    name: '利润',
                    data: moneyArr

                }],
                credits: {
                    enabled: true, // 默认值，如果想去掉版权信息，设置为false即可
                    text: '产品研发中心', // 显示的文字
                    href: 'http://www.douples.com', // 链接地址
                }
            });

        //渠道交易统计
        Highcharts.chart('channelLr', {
            title: {
                text: '渠道交易单数统计'
            },
            subtitle: {
                text: '统计时间：${DATA_PUBLISH_TIME}'
            },
            tooltip: {
                headerFormat: '{series.name}<br>',
                pointFormat: '{point.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true // 设置饼图是否在图例中显示
                }
            },
            series: [{
                type: 'pie',
                name: '渠道交易单数占比',
                data: channelArr
            }],
            credits: {
                enabled: true, // 默认值，如果想去掉版权信息，设置为false即可
                text: '此页面数据将在 ${nextPublishTime} 更新', // 显示的文字
                href: '#', // 链接地址
            }
        });
    }
</script>