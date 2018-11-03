/*
 * table.js
 */

var table;				//表格对象
var lay_form;



$(function(){

	layui.use('form', function(){
		lay_form = layui.form();
	});
	
	
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
		table.attribute.order = {
			"orderCol" : $(this).attr("col"),
			"orderVal" : val
		};
		queryTableData();
	});
	
	
	$(document).on("click", "#btn_add", function(){
		$("#addModal").find("input[name=tbname]").val(table.tbname);
		updateColumnInfo($("#addModal"));
	});
	
	$(document).on("keyup", "#btn_query_val", function(evt){if(evt.keyCode==13){$("#btn_query").click();}});
	$(document).on("click", "#btn_query", function(){
		if(!$("#btn_query_val").val())	return;
		
		table.attribute.query = {
			"queryCol" : $("#btn_query_col").val(),
			"queryVal" : $("#btn_query_val").val()
		};
		queryTableData();
	})

	
	$(document).on("click", ".op_modify", function(){
		$("#modifyModal").find("input[name=tbname]").val(table.tbname);
		updateColumnInfo($("#modifyModal"), $(this).attr("rowid"),$(this).parents("tr").eq(0).attr("no"));
		$("#modifyModal").modal('show');
	})
	
	$(document).on("click", ".op_delete", function(){
		$("#deleteModal").find("input[name=tbname]").val(table.tbname);
		$("#deleteModal").modal("show");
		updateColumnInfo($("#deleteModal"), $(this).attr("rowid"));
	})
	
	
	$(".save").click(function(){
		var modalId = $(this).parents(".modal").attr("id");
		modifyEntity($("#"+modalId))
	});
})

function initTableData(tbname){
	
	//有外键的表需要查关联表信息
	var params = {};
	
	var dataTable = new DataTable({
		tbname : tbname,
		tabledom : $("#table"),
		ajax : {
			url:"../table/entityList/get/" + tbname,
			data:params
		},
		columns : tableFields[tbname].columns,
		page : {
			page_index : 1,		
			page_total : 10	//每页记录数
		},
		attribute : {
			foreign : tableFields[tbname].foreign
		}
	});
	
	table = dataTable;
	updateTableInfo(tbname);
	
	return dataTable;
}


function queryTableData(){
	if(table){
		var params = {};
		var fgn = table.attribute.foreign;
		var ord = table.attribute.order;
		var qry = table.attribute.query;
		
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
		table.reloadAjax("../table/entityList/get/" + table.tbname, params);
	}
}



/*
 * 更新数据表信息
 */
function updateTableInfo(tbname){
	$("#btn_query_col").empty();
	$.getJSON("../table/entityInfo/get/"+tbname, function(data){
		table.col_info = data;
	});
	//根据用户列配置来插入查询列
	for(var i in table.columns){
		if(typeof table.columns[i].field == "string")
		$("#btn_query_col").append("<option value='"+table.columns[i].field+"'>"+table.columns[i].name+"</option>")
	}
}


/*
 * 更新弹出框字段信息
 */
function updateColumnInfo($modal, rowid, rowno){
	var tbname = $modal.find("input[name=tbname]").val();
	var method = $modal.find("input[name=_method]").val();
	if(!tbname || !method)	return;
	
	
	if(table.col_info){
		var data = table.col_info;
		var parms = '<input type="hidden"  name="tbname" value="'+tbname+'">';
		$modal.find(".modal-body").html(parms);
		
		for(var i in data){
			
			if(data[i].COLUMN_KEY == "PRI"){
				if(method == "delete" || method == "update"){
					var tmp = '<input type="hidden"  name="'+data[i].COLUMN_NAME+'" value="'+rowid+'">';
					$modal.find(".modal-body").append(tmp);
				}
				
			}else if(data[i].COLUMN_KEY == "MUL"){
				//新增和更新时需要加载外键信息
				if(method == "save" || method == "update"){
					var str = '<input type="text" name="'+data[i].COLUMN_NAME+'" readonly="readonly" class="form-control" />';
					if(data[i].COLUMN_NAME == "category_id"){
						str = '<input type="text" id="category_id" readonly="readonly" class="form-control" />';
						str += '<input type="hidden" name="'+data[i].COLUMN_NAME+'" />';
					}
					var tmp = '<div class="form-group">' +
					'<div class="col-sm-2 control-label">'+data[i].COLUMN_COMMENT+'</div>' +
					'<div class="col-sm-10">' +
						str
					//	'<select name="'+data[i].COLUMN_NAME+'" lay-verify="required" lay-search=""><option></option></select>' +
					'</div></div>';
					$modal.find(".modal-body").append(tmp);
					//加载外键参数和值
				//	loadForeignValue(tbname, data[i].COLUMN_NAME);
				}
			}else if(!data[i].COLUMN_KEY){
				if(method == "save" || method == "update"){
					
					var inputType = "text";
					if(data[i].COLUMN_NAME=="path" || data[i].COLUMN_NAME=="img"){
						inputType = "file";
					}else if(data[i].COLUMN_NAME=="seq"){
						inputType = "number";
					}
					
					var str = '<input type="'+inputType+'" class="form-control" dataType="'+data[i].DATA_TYPE+'" name="'+data[i].COLUMN_NAME+'" required />';
					if("text" == data[i].DATA_TYPE){
						str = '<textarea rows="3" class="form-control" dataType="'+data[i].DATA_TYPE+'" name="'+data[i].COLUMN_NAME+'" required></textarea>';
					}
					
					var tmp = '<div class="form-group">' +
					'<div class="col-sm-2 control-label">'+data[i].COLUMN_COMMENT+'</div>' +
					'<div class="col-sm-10">' + str +
					'</div></div>';
					$modal.find(".modal-body").append(tmp);
					$modal.find("input[type=file]").attr({"multiple":""});
				}
				
			}
			
		}
		
		if(method == "delete"){
			$modal.find(".modal-body").append("确认删除记录" + rowid + "?");
		}

		//修改时，需要加载所有字段的值
		if(method == "update"){
			var entity = table.rows[rowno];
			for(var i in entity){
				$modal.find("input[name="+i+"]").val(entity[i]);
				$modal.find("textarea[name="+i+"]").val(entity[i]);

				if(i == "category_id"){
					$("#category_id").val(entity.display);
				}
			
			}

	
		}
		
	}
	
}



/*
 * 新增、修改、删除
 */
function modifyEntity($modal){
	var method = $modal.find("input[name=_method]").val();
	var url = "../table/entity/"+method;
	var frm = document.forms[$modal.find("form").eq(0).attr("name")]

	if(!validateForm(method, frm))	return;

	var formData = new FormData(frm);

	$.ajax({
        url: url,
        type : "POST",
        async: false,
        data : formData,	//$modal.find("form").serialize()
        contentType : false, //这两个参数需要被定义，否则报错
        processData : false,
        success : function(data){
            if(data.res){
            	table.reloadAjax();
            	layer.msg("提交成功！");
            }
            else layer.msg("提交失败！");
        },
        dataType : "json",
        error : function(data){
        	layer.msg("提交出错！");
        }
    });
    
    $modal.modal('hide');
}

function validateForm(method, frm){
	$(".input-danger").removeClass("input-danger");
	
	for(var i in frm.elements){
		var ele = frm.elements[i];
		if(ele.attributes && $(ele).attr("datatype") == "int"){
			if(!/^\d{1,}$/.test($(ele).val())){
				$(ele).addClass("input-danger");
				layer.msg("非有效数字！");
				return false;
			}
			
		}
	}

	if(!frm.department_id.value){
		$(frm.department_id).remove();
	}
	
	return true;
}





/*
 * 加载外键值
 */
function loadForeignValue(tbname, field){
	var foreignInfo = tableFields[tbname].foreign;
	if(foreignInfo){
		var url = "../table/entityList/get/"+foreignInfo.foreignTable+"?page_index=1&page_total=10";
		var sel = $("select[name="+field+"]");
		$.getJSON(url, function(data){
			sel.find("option").remove();
			var tb = data.data;
			sel.append("<option value='-1'></option>");		//外键默认值-1
			for(var i in tb){
				sel.append("<option value='"+tb[i][foreignInfo.foreignKey]+"'>"+tb[i][foreignInfo.showField]+"</option>");
			}
			lay_form.render();
		});
	}
}


/*
 * 表字段信息配置
 */
var tableFields = {
	"PaperBase" : {
		columns:[
			{name : "编号", field : "id"},       
			{name : "试卷库名", field : "baseName"},
			{name : "操作", field : function(row){
				return '<span rowid="'+row.id+'" class="op_modify glyphicon glyphicon-edit">修改</span>' + 
				'<span rowid="'+row.id+'" class="op_delete glyphicon glyphicon-remove">删除</span>';
			}}
		],
		foreign:{
			primaryColumn:"department_id",
			foreignTable:"u_department",
			foreignColumn:"id",
			showField:"name"
		}
	},
	"u_user" : {
		columns:[
			{name : "编号", field : "userid"},       
			{name : "用户名", field : "username"},
			{name : "密码", field : "password"},
			{name : "操作", field : function(row){
				return '<span rowid="'+row.id+'" class="op_modify glyphicon glyphicon-edit">修改</span>' + 
				'<span rowid="'+row.id+'" class="op_delete glyphicon glyphicon-remove">删除</span>';
			}}
		],
		foreign:{
			primaryColumn:"department_id",
			foreignTable:"u_department",
			foreignColumn:"id",
			showField:"name"
		}
	}
}










