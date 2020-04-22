<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>潜在客户</title>
    <!--freemarker引入模板文件 使用相对路径来引入-->
    <#include "../common/link.ftl" >
    <script>
        $(function () {
            //编辑/增加
            $('.btn-input').click(function () {

                //清除模态框的数据
                $("#editForm input").val('');
                //获取事件源绑定的数据 使用data方法可以很方便获取data-*开头的属性的数据
                var json = $(this).data("json");
                if (json) { //json有数据代表是编辑
                    $("input[name=id]").val(json.id);
                    $("input[name=name]").val(json.name);
                    $("input[name=sn]").val(json.sn);
                }
                $('#editModal').modal('show');
            });
            //删除
            $('.btn-delete').click(function () {
                //获取当前id
                var id = $(this).data('id');
                //提示确认框
                $.messager.confirm("警告", "是否确认删除?", function () {
                    $.post('/customer/delete.do', {id: id}, handlerMessage);
                })
            });

            //提交
            $('.btn-submit').click(function () {
                //提交异步表单

                $("#editForm").ajaxSubmit(handlerMessage)
            })
        })
    </script>
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <!--freemarker引入模板文件 使用相对路径来引入-->
    <#include "../common/navbar.ftl" >
    <#assign currentMenu="customer"/>
    <#include "../common/menu.ftl" >
    <div class="content-wrapper">
        <section class="content-header">
            <h1>潜在客户</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/customer/potentialList.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">

                    <div class="form-group">
                        <label for="keyword">关键字:</label>
                        <input type="text" class="form-control" id="keyword" name="keyword" value="${(qo.keyword)!}"
                               placeholder="请输入姓名/电话">
                    </div>
                    <div class="form-group">
                        <@shiro.hasAnyRoles name="Admin,Market_Manager">
                        <label for="dept">销售人员:</label>
                        <select class="form-control" id="seller" name="sellerId">
                            <option value="-1">全部</option>
                            <#list sellers as s>
                                <option value="${s.id}">${s.name}</option>
                            </#list>
                        </select>
                            <script>
                                $("#seller").val(${qo.sellerId})
                            </script>
                        </@shiro.hasAnyRoles>
                    </div>
                    <button id="btn_query" class="btn btn-primary"><span class="glyphicon glyphicon-search"></span>
                        查询
                    </button>
                    <@shiro.hasPermission name="customer:input">
                        <a href="#" class="btn btn-success btn-input" style="margin: 10px">
                            <span class="glyphicon glyphicon-plus"></span> 添加
                        </a>
                    </@shiro.hasPermission>
                </form>
                <!--编写内容-->
                <div class="box-body table-responsive no-padding ">
                    <table class="table table-hover table-bordered">
                        <tr>
                            <th>编号</th>
                            <th>姓名</th>
                            <th>电话</th>
                            <th>QQ</th>
                            <th>职业</th>
                            <th>来源</th>
                            <th>销售员</th>
                            <th>状态</th>
                            <th>录入时间</th>
                            <th>操作</th>
                        </tr>
                        <#list PageInfo.list as customer>
                            <tr>
                                <#--索引从0开始-->
                                <td>${customer_index+1}</td>
                                <td>${customer.name!}</td>
                                <td>${customer.tel!}</td>
                                <td>${customer.qq!}</td>
                                <td>${(customer.job.title)!}</td>
                                <td>${(customer.source.title)!}</td>
                                <td>${(customer.seller.name)!}</td>
                                <td>${customer.statusName!}</td>
                                <td>${customer.inputTime?string('yyyy-MM-dd   hh:mm:ss')}</td>
                                <td>
                                        <a href="#" class="btn btn-info btn-xs btn-input" data-json='${customer.json!}'>
                                            <span class="glyphicon glyphicon-pencil"></span> 编辑
                                        </a>
                                       <#-- <a href="#" class="btn btn-danger btn-xs btn-delete" data-id='${customer.id}'>
                                            <span class="glyphicon glyphicon-trash"></span> 删除
                                        </a>-->
                                </td>
                            </tr>
                        </#list>
                    </table>
                    <!--分页-->
                    <#include "../common/page.ftl" >
                </div>
            </div>
        </section>
</div>
    <#include "../common/footer.ftl" >
</div>
<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title inputTitle">客户编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customer/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" value="" name="id">
                    <div class="form-group" >
                        <label  class="col-sm-3 control-label">客户名称：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="name"
                                   placeholder="请输入客户姓名"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户年龄：</label>
                        <div class="col-sm-6">
                            <input type="number" class="form-control" name="age"
                                   placeholder="请输入客户年龄"/>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label  class="col-sm-3 control-label">客户性别：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="gender">
                                <option value="1">男</option>
                                <option value="0">女</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户电话：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="tel"
                                   placeholder="请输入客户电话"/>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label  class="col-sm-3 control-label">客户QQ：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" name="qq"
                                   placeholder="请输入客户QQ"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户工作：</label>
                        <div class="col-sm-6">
                            <select class="form-control" name="job.id">
                              <#list jobs as j>
                                    <option value="${j.id}">${j.title}</option>
                                </#list>

                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label  class="col-sm-3 control-label">客户来源：</label>
                        <div class="col-sm-6">
                           <select class="form-control" name="source.id">
                                 <#list source1 as s>
                                        <option value="${s.id}">${s.title}</option>
                                 </#list>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary btn-submit" >保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal" >取消</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
