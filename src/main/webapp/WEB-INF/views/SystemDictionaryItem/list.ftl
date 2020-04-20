<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>字典明细管理</title>
    <!--freemarker引入模板文件 使用相对路径来引入-->
    <#include "../common/link.ftl" >
    <script>
        $(function () {
            //编辑/增加
            $('.btn-input').click(function () {

                //清除模态框的数据
                $("#editForm input").val('');
                //把目录的名称设置到模态框的目录名称input中
                $("input[name=parentTitle]").val($("#${qo.parentId}").text());
                $("input[name=parentId]").val(${qo.parentId});
                //获取事件源绑定的数据 使用data方法可以很方便获取data-*开头的属性的数据
                var json = $(this).data("json");
                if (json) { //json有数据代表是编辑
                    $("input[name=id]").val(json.id);
                    $("input[name=title]").val(json.title);
                    $("input[name=sequence]").val(json.sequence);

                }
                $('#editModal').modal('show');
            });
            //删除
            $('.btn-delete').click(function () {
                //获取当前id
                var id = $(this).data('id');
                //提示确认框
                $.messager.confirm("警告", "是否确认删除?", function () {
                    $.post('/SystemDictionaryItem/delete.do', {id: id}, handlerMessage);
                })
            });

            //提交
            $('.btn-submit').click(function () {

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
    <#assign currentMenu="SystemDictionaryItem"/>
    <#include "../common/menu.ftl" >
    <div class="content-wrapper">
        <section class="content-header">
            <h1>字典明细管理</h1>
        </section>
        <section class="content">
            <div class="box">





                <div class="row" style="margin:20px">
                    <div class="col-xs-3">
                        <div class="panel panel-default">
                            <div class="panel-heading">字典目录</div>
                            <div class="panel-body">
                                <div class="list-group">
                                    <#list systemDictionarys as d>
                                    <a id="${d.id}" href="/SystemDictionaryItem/list.do?parentId=${d.id}" class="list-group-item">${d.title}</a>
                                    </#list>
                                    <script>
                                       //获取指定的a连接,添加active
                                       $("#${qo.parentId}").addClass('active');
                                    </script>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-9">
                        <!--高级查询--->
                        <table>
                            <form class="form-inline" id="searchForm" action="/SystemDictionaryItem/list.do" method="post">
                                <input type="hidden" name="currentPage" id="currentPage" value="1">
                                <input type="hidden" name="parentId" value="${qo.parentId}"><#--解决分页查询丢失字典目录的问题-->
                                <@shiro.hasPermission name="SystemDictionaryItem:input">
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
                                        <th>标题 </th>
                                        <th>序列 </th>
                                        <th>操作</th>
                                    </tr>
                                    <#list PageInfo.list as SystemDictionaryItem>
                                        <tr>
                                            <#--索引从0开始-->
                                            <td>${SystemDictionaryItem_index+1}</td>
                                            <td>${SystemDictionaryItem.title}</td>
                                            <td>${SystemDictionaryItem.sequence}</td>
                                            <td>
                                                <@shiro.hasPermission name="SystemDictionaryItem:saveOrUpdata">
                                                    <a href="#" class="btn btn-info btn-xs btn-input" data-json='${SystemDictionaryItem.json}'>
                                                        <span class="glyphicon glyphicon-pencil"></span> 编辑
                                                    </a>
                                                </@shiro.hasPermission>
                                                <@shiro.hasPermission name="SystemDictionaryItem:delect">
                                                    <a href="#" class="btn btn-danger btn-xs btn-delete" data-id='${SystemDictionaryItem.id}'>
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
                        </table>
                    </div>
                </div>
            </div>
        </section>
</div>
    <#include "../common/footer.ftl" >
</div>
<!-- Modal模态框 -->
<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">新增/编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/SystemDictionaryItem/saveOrUpdate.do" method="post"
                      id="editForm">
                    <input type="hidden" name="id">
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="intro" class="col-sm-3 control-label">字典目录：</label>
                        <div class="col-sm-6">
                            <#--给用户看的-->
                            <input name="parentTitle" class="form-control" readonly>
                            <!-- 用来提交到后台关联的 -->
                            <#--提交表单-->
                            <input type="hidden" id="parentId" name="parentId">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="title" class="col-sm-3 control-label">明细标题：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="title" name="title"
                                   placeholder="请输入明细标题">
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 10px;">
                        <label for="sn" class="col-sm-3 control-label">明细序号：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="sequence" name="sequence"
                                   placeholder="请输入明细编码">
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
