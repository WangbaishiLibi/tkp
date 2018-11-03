/**
 * Created by Administrator on 2017/9/9.
 */

var tab_dict;
$(function(){

    $(document).on("click", "table > thead > tr > th", function(){
        if($(this).hasClass("sort_asc") || $(this).hasClass("sort_desc")){
            $(this).toggleClass("sort_asc").toggleClass("sort_desc");
        }else if($(this).hasClass("sort_both")){
            $("table .sort_asc").removeClass("sort_asc");
            $("table .sort_desc").removeClass("sort_desc");
            $(this).addClass("sort_asc");
        }else{
            return;
        }
        var val = $(this).hasClass("sort_asc") ? "ASC" : "DESC";
        tab_dict.attribute.order = {
            "orderCol" : $(this).attr("col"),
            "orderVal" : val
        };
        queryTableData();
    });


    $("#btn_add").click(function(){
        delete window.entity;
        tab("dictionary_modify");
    });

    $(document).on("keyup", "#btn_query_val", function(evt){if(evt.keyCode==13){$("#btn_query").click();}});
    $(document).on("click", "#btn_query", function(){
        if(!$("#btn_query_val").val())	return;

        tab_dict.attribute.query = {
            "queryCol" : $("#btn_query_col").val(),
            "queryVal" : $("#btn_query_val").val()
        };
        queryTableData();
    })

    $(document).on("click", ".op_modify", function(){
        var rid = parseInt($(this).parents("tr").eq(0).attr("no"));
        window.entity = tab_dict.rows[rid];
        tab("dictionary_modify");
    })

    $(document).on("click", ".op_delete", function(){
        layer.confirm('确认删除？', {icon: 3, title:'提示'}, function(index){
            layer.close(index);
        });
    })



    $(".save").click(function(){
        var modalId = $(this).parents(".modal").attr("id");
        modifyEntity($("#"+modalId))
    });



    var tbname = "dictionary";
    tab_dict = new DataTable({
        tbname : tbname,
        tabledom : $("#tab_dict"),
        ajax : {
            url:"../table/entityList/get/" + tbname
        },
        columns:[
            {name : "编号", field : "id"},
            {name : "标题", field : "title"},
            {name : "简介", field : "intro"},
            {name : "类型", field : "type"},
            {name : "来源", field : "origin"},
            {name : "操作", field : function(row){
                return '<span rowid="'+row.id+'" class="op_modify glyphicon glyphicon-edit">修改</span>' +
                    '<span rowid="'+row.id+'" class="op_delete glyphicon glyphicon-remove">删除</span>';
            }}
        ],
        page : {
            page_index : 1,
            page_total : 10	//每页记录数
        },
        attribute : {
        }
    });


});

function queryTableData(){
    if(tab_dict){
        var params = {};
        var fgn = tab_dict.attribute.foreign;
        var ord = tab_dict.attribute.order;
        var qry = tab_dict.attribute.query;

        if(fgn){
            for(var i in fgn){
                params[i] = fgn[i];
            }
        }
        if(ord){
            for(var i in ord){
                params[i] = ord[i];
            }
        }
        if(qry){
            for(var i in qry){
                params[i] = qry[i];
            }
        }
        tab_dict.reloadAjax("../table/entityList/get/" + tab_dict.tbname, params);
    }
}


function validateForm(method, frm){

    if(method == "save"){
        $(frm.id).remove();
    }

    if(!frm.category_id.value){
        $(frm.category_id).remove();
    }

    return true;
}


/*
 * 新增、修改、删除
 */
function modifyEntity(){
    var method = document.modify._method.value;
    var url = "../table/entity/"+method;

    if(!validateForm(method, document.modify))	return;

    var formData = new FormData(document.modify);
    $.ajax({
        url: url,
        type : "POST",
        async: false,
        data : formData,	//$modal.find("form").serialize()
        contentType : false, //这两个参数需要被定义，否则报错
        processData : false,
        success : function(data){
            if(data.res){
                tab_dict.reloadAjax();
                layer.msg("提交成功！");
            }
            else layer.msg("提交失败！");
        },
        dataType : "json",
        error : function(data){
            layer.msg("提交出错！");
        }
    });

    $("#modifyModal").modal('hide');
}
