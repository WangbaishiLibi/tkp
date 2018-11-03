/*
 * 规章修改
 * entry_modify.js
 */
var table;

$(function(){

	$(".save").click(function(){
		var modalId = $(this).parents(".modal").attr("id");
		modifyEntity($("#"+modalId))
	});
	
	$(".back").click(function(){
		tab('testpaper');
	})


	$.getJSON("../table/entityList/get/u_department", function(res){
		var data = res.data;
		for(var i in data){
			var tmp = '<option value="'+data[i].id+'">'+data[i].name+'</option>';
			$(document.modify.department_id).append(tmp);
		}

		if(window.entity && window.entity.department_id){
			$(document.modify.department_id).val(window.entity.department_id);
		}

	});

	
	if(window.entity){
		var entity = window.entity;
		$(document.modify).append('<input type="hidden" name="id" />');
		document.modify._method.value = "update";

		for(var i in entity){
			if(document.modify[i]){
				document.modify[i].value = entity[i];
			}
		}
	}else{
		document.modify.reset();
		document.modify._method.value = "save";
	}
})


function validateForm(method, frm){
	
	if(method == "save"){
		$(frm.id).remove();
	}

	if(!frm.department_id.value){
		$(frm.department_id).remove();
	}

	
	return true;
}


/*
 * 新增、修改、删除
 */
function modifyEntity(){
	var frm = document.modify;
	var method = frm._method.value;
	var url = "../paper/savePaperBase";
	
	if(!validateForm(method, frm))	return;
	var formData = new FormData(frm);
	if(frm.department_id.value)	formData.append("department.id", frm.department_id.value);

	$.ajax({
        url: url,
        type : "POST",
        async: false,
        data : formData,	//$modal.find("form").serialize()
        contentType : false,
        processData : false,
        success : function(data){
            if(data.res){
            	layer.msg("提交成功！");
				tab('paperbase');
            }
            else layer.msg("提交失败！");
        },
        dataType : "json",
        error : function(data){
        	layer.msg("提交出错！");
        }
    });

}



