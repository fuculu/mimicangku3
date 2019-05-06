function upOneOrMany(url, datagridId, smg) {
    var datagrid = $("#" + datagridId).datagrid('getSelections');
    if (datagrid.length <= 0) {
        $.messager.alert('提示', '请选择要' + smg + '的记录！', 'warning');
        return false;
    }
    var ids = "";
    for (var i = 0; i < datagrid.length; i++) {
        ids += ids == "" ? datagrid[i].id : "," + datagrid[i].id;
    }
    $.ajax({
        url: url,
        type: "post",
        data: {ids: ids},
        datatype: "json",
        success: function (data) {
            if (data) {
                $("#" + datagridId).datagrid('reload');
            } else {
                $.messager.alert('提示', smg + '失败！', 'error');
            }
        }
    })
}

function delOneOrMany(url, datagridId, flag) {
    var datagrid = $("#" + datagridId).datagrid('getSelections');
    if (datagrid.length <= 0) {
        $.messager.alert('提示', '请选择要删除的记录！', 'warning');
        return false;
    }
    if (flag) {
        $.messager.confirm('确认', '确认要删除这条信息吗？', function (r) {
            if (r) {
                var ids = "";
                for (var i = 0; i < datagrid.length; i++) {
                    ids += ids == "" ? datagrid[i].id : "," + datagrid[i].id;
                }
                $.ajax({
                    url: url,
                    type: "post",
                    data: {ids: ids},
                    datatype: "json",
                    success: function (data) {
                        if (data) {
                            $("#" + datagridId).datagrid('reload');
                        } else {
                            $.messager.alert('提示', '删除失败！', 'error');
                        }
                    }
                })
            }
        })
    } else {
        var id = "";
        for (var i = 0; i < datagrid.length; i++) {
            ids += ids == "" ? datagrid[i].id : "," + datagrid[i].id;
        }
        $.ajax({
            url: url,
            type: "post",
            data: {ids: ids},
            datatype: "json",
            success: function (data) {
                if (data) {
                    $("#" + datagridId).datagrid('reload');
                } else {
                    $.messager.alert('提示', '删除失败！', 'error');
                }
            }
        })
    }
}

function delOneUtil(url, datagridId, ids, flag) {
    if (flag) {
        $.messager.confirm('确认', '确认要删除这条信息吗？', function (r) {
            if (r) {
                $.ajax({
                    url: url,
                    type: "post",
                    data: {ids: ids},
                    datatype: "json",
                    success: function (data) {
                        if (data) {
                            $("#" + datagridId).datagrid('reload');
                        } else {
                            $.messager.alert('提示', '删除失败！', 'error');
                        }
                    }
                })
            }
        })
    } else {
        $.ajax({
            url: url,
            type: "post",
            data: {ids: ids},
            datatype: "json",
            success: function (data) {
                if (data) {
                    $("#" + datagridId).datagrid('reload');
                } else {
                    $.messager.alert('提示', '删除失败！', 'error');
                }
            }
        })
    }
}

function upOneUtil(url, datagridId, id, smg) {
    $.ajax({
        url: url,
        type: "post",
        data: {id: id},
        datatype: "json",
        success: function (data) {
            if (data) {
                $("#" + datagridId).datagrid('reload');
            } else {
                $.messager.alert('提示', smg + '失败！', 'error');
            }
        }
    })
}