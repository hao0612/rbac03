<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>字典目录</title>
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
                    $("input[name=title]").val(json.title);
                    $("input[name=sn]").val(json.sn);
                    $("input[name=intro]").val(json.intro);
                }
                $('#editModal').modal('show');
            });
            //删除
            $('.btn-delete').click(function () {
                //获取当前id
                var id = $(this).data('id');
                //提示确认框
                $.messager.confirm("警告", "是否确认删除?", function () {
                    $.post('/SystemDictionary/delete.do', {id: id}, handlerMessage);
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
    <!--菜单回显 -->
    <#assign currentMenu="Systemdictionary"/>
    <#include "../common/menu.ftl" >
    <div class="content-wrapper">
        <section class="content-header">
            <h1>字典目录管理</h1>
        </section>
        <section class="content">
            <div class="box">
                <!--高级查询--->
                <form class="form-inline" id="searchForm" action="/SystemDictionary/list.do" method="post">
                    <input type="hidden" name="currentPage" id="currentPage" value="1">
                    <@shiro.hasPermission name="Systemdictionary:input">
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
                            <th>标题</th>
                            <th>编码 </th>
                            <th>描述 </th>
                            <th>操作</th>
                        </tr>
                        <#list PageInfo.list as SystemDictionary>
                            <tr>
                                <#--索引从0开始-->
                                <td>${SystemDictionary_index+1}</td>
                                <td>${SystemDictionary.title}</td>
                                <td>${SystemDictionary.sn}</td>
                                <td>${SystemDictionary.intro}</td>
                                <td>
                                     <@shiro.hasPermission name="SystemDictionary:saveOrUpdata">
                                        <a href="#" class="btn btn-info btn-xs btn-input" data-json='${SystemDictionary.json}'>
                                            <span class="glyphicon glyphicon-pencil"></span> 编辑
                                        </a>
                                        </@shiro.hasPermission>
                                       <@shiro.hasPermission name="SystemDictionary:delect">
                                        <a href="#" class="btn btn-danger btn-xs btn-delete" data-id='${SystemDictionary.id}'>
                                            <span class="glyphicon glyphicon-trash"></span> 删除
                                        </a>
                                      </@shiro.hasPermission>
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
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">新增/编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/SystemDictionary/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="name" class="col-sm-3 control-label">标题：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="title"
                                   placeholder="请输入字典目录标题">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">编码：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sn" name="sn"
                                   placeholder="请输入字典目录编码">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">描述：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sn" name="intro"
                                   placeholder="请输入字典目录描述">
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary btn-submit">保存</button>
            </div>
        </div>
    </div>
</div>


</body>
</html>
