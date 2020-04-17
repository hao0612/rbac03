<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>员工管理</title>
    <#include "../common/link.ftl">
    <script>
        function moveSelected(src, target) {//获取选中的数据并且移动
            $("." + target).append($("." + src + " option:selected"))
        }

        function moveAll(src, target) {//获取所有的数据并且移动
            $("." + target).append($("." + src + " option"))
        }

        //监听复选框点击事件
        $(function () {
            var roleDiv;
            //监听超管复选框点击事件
            $("#admin").click(function () {
                var checked = $(this).prop("checked");
                if (checked) {
                    //不显示角色编辑区域
                    roleDiv = $("#role").detach();//datach() 会保留所有绑定的事件,附加的数据
                } else {
                    //恢复角色复选框编辑区域(放在超管后面)
                    $("#adminDiv").after(roleDiv);
                }
            })
            //页面加载完毕后恢复当前复选框状态
            var checked = $("#admin").prop("checked");
            if (checked) {
                //不显示角色编辑区域
                roleDiv = $("#role").detach();
            }
            /* $("#submitBtn").click(function () {
                   //设置右边所有角色为选中状态
                 $(".selfRoles option").prop('selected' , true);
                 //提交表单
                 $("#editForm").submit();
             })*/

            //获取右边用户已经有的角色
            var ids = [];
            $(".selfRoles option").each(function (index, ele) {
                console.log(ele);
                ids.push($(ele).val());//把角色的id存放在数组中
                console.log(ids);
            })
            //获取左边的角色
            $(".allRoles option").each(function (index, ele) {
                console.log(ele);
                var id = $(ele).val();
                //判断当前id是是否存在
                if ($.inArray(id, ids) > -1) {
                    //移除数据
                    $(ele).remove();
                }
            })
            //表单数据验证
            $("#editForm").bootstrapValidator({

                feedbackIcons: {//图标
                    valid: 'glyphicon glyphicon-ok',
                    invalid: 'glyphicon glyphicon-remove',
                    validating: 'glyphicon glyphicon-refresh'
                }, fields: { //配置要验证的字段
                    <#--  <#if !employee??>-->
                    name: {
                        validators: { //验证的规则
                            notEmpty: { //不能为空
                                message: "用户名必填" //错误时的提示信息
                            },
                            stringLength: { //字符串的长度范围
                                min: 1,
                                max: 10,
                                message: "请输入范围1~10"
                            },
                            remote: { //远程验证
                                type: 'POST', //请求方式
                                url: '/employee/checkName.do', //请求地址
                                data: function () {  //自定义提交参数，默认只会提交当前用户名input的参数
                                    return {
                                        id: $('[name="id"]').val(),
                                        name: $('[name="name"]').val()
                                    };
                                },
                                message: '用户名已经存在', //验证不通过时的提示信息
                                delay: 1000 //输入内容1秒后发请求
                            },
                        }
                    }
                    <#-- </#if>-->
                    /* password:{
                         validators:{
                             notEmpty:{ //不能为空
                                 message:"密码必填" //错误时的提示信息
                             },
                         }
                     },
                     repassword:{
                         validators:{
                             notEmpty:{ //不能为空
                                 message:"密码必填" //错误时的提示信息
                             },
                             identical: {//两个字段的值必须相同
                                 field: 'password',
                                 message: '两次输入的密码必须相同'
                             },
                         }
                     },
                     email: {
                         validators: {
                             emailAddress: {} //邮箱格式
                         }
                     },
                     age:{
                         validators: {
                             between: { //数字的范围
                                 min: 18,
                                 max: 60,
                                 message:"请输入范围18~60"


                             }
                         }
                     }*/
                }
            }).on('success.form.bv', function (e) { //表单所有数据验证通过后执行里面的代码
                //禁止原本的表单提交
                e.preventDefault();
                //设置右边的所有option为选中的状态
                $(".selfRoles option").prop('selected', true);
                //提交异步表单
                $("#editForm").ajaxSubmit(function (data) {
                    if (data.success) {
                        $.messager.alert("温馨提示", '操作成功2秒后自动跳转');
                        setTimeout(function () {
                            window.location.href = "/employee/list.do";
                        }, 1000)

                    } else {
                        $.messager.popup(data.msg);
                    }
                });
            });
        });


    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <#include "../common/navbar.ftl">
    <!--菜单回显-->
    <#assign currentMenu="employee" />
    <#include "../common/menu.ftl">
    <div class="content-wrapper">
        <section class="content-header">
            <h1>员工编辑</h1>
        </section>
        <section class="content">
            <div class="box">
                <form class="form-horizontal" action="/employee/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" value="${(employee.id)!}" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-2 control-label">用户名：</label>
                        <div class="col-sm-6">
                            <input
                                    <#-- <#if employee??>
                                         readonly
                                     </#if>-->

                                    type="text" class="form-control" id="name" name="name" value="${(employee.name)!}"
                                    placeholder="请输入用户名">
                        </div>
                    </div>

                    <#if !employee??>
                        <div class="form-group">
                            <label for="password" class="col-sm-2 control-label">密码：</label>
                            <div class="col-sm-6">
                                <input type="password" class="form-control" id="password" name="password"
                                       placeholder="请输入密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="repassword" class="col-sm-2 control-label">验证密码：</label>
                            <div class="col-sm-6">
                                <input type="password" class="form-control" id="repassword" name="repassword"
                                       placeholder="再输入一遍密码">
                            </div>
                        </div>
                    </#if>
                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">电子邮箱：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="email" name="email" value="${(employee.email)!}"
                                   placeholder="请输入邮箱">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="age" class="col-sm-2 control-label">年龄：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="age" name="age" value="${(employee.age)!}"
                                   placeholder="请输入年龄">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dept" class="col-sm-2 control-label">部门：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="dept" name="dept.id">
                                <option value="-1">全部</option>
                                <#list  departments as d>
                                    <option value="${d.id}">${d.name}</option>
                                </#list>
                            </select>
                            <script>
                                ${'dept'}.val(${("employee.dept.id")!});
                            </script>
                        </div>
                    </div>
                    <div class="form-group" id="adminDiv">
                        <label for="admin" class="col-sm-2 control-label">超级管理员：</label>
                        <div class="col-sm-6" style="margin-left: 15px;">
                            <input type="checkbox" id="admin" name="admin" class="checkbox">
                            <#if employee?? && employee.admin>
                                <script>
                                    $("#admin").prop("checked", true);
                                </script>
                            </#if>
                        </div>
                    </div>
                    <div class="form-group " id="role">
                        <label for="role" class="col-sm-2 control-label">分配角色：</label><br/>
                        <div class="row" style="margin-top: 10px">
                            <div class="col-sm-2 col-sm-offset-2">
                                <select multiple class="form-control allRoles" size="15">
                                    <#list roles as r>
                                        <option value="${r.id}">${r.name}</option>
                                    </#list>
                                </select>
                            </div>

                            <div class="col-sm-1" style="margin-top: 60px;" align="center">
                                <div>

                                    <a type="button" class="btn btn-primary  " style="margin-top: 10px" title="右移动"
                                       onclick="moveSelected('allRoles', 'selfRoles')">
                                        <span class="glyphicon glyphicon-menu-right"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="左移动"
                                       onclick="moveSelected('selfRoles', 'allRoles')">
                                        <span class="glyphicon glyphicon-menu-left"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全右移动"
                                       onclick="moveAll('allRoles', 'selfRoles')">
                                        <span class="glyphicon glyphicon-forward"></span>
                                    </a>
                                </div>
                                <div>
                                    <a type="button" class="btn btn-primary " style="margin-top: 10px" title="全左移动"
                                       onclick="moveAll('selfRoles', 'allRoles')">
                                        <span class="glyphicon glyphicon-backward"></span>
                                    </a>
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <select multiple class="form-control selfRoles" size="15" name="ids">
                                    <#list (employee.roles)! as r>
                                        <option value="${r.id}">${r.name}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-6">
                            <button id="submitBtn" type="submit" class="btn btn-primary">保存</button>
                            <button type="reset" class="btn btn-danger">重置</button>
                        </div>

                    </div>

                </form>

            </div>
        </section>
    </div>
    <#include "../common/footer.ftl">
</div>
</body>
</html>
