/*
 * 规章修改
 * entry_modify.js
 */
var table;

$(function(){


	$('#tmp_tree').jstree({
 		"plugins" : [ "state" ],
 		"core" : {
 			"check_callback" : true,
 		      "data" :  {
 		    	 "url" : "../category/getRulerTree",
 		    	 "dataType" : "JSON",
 		    	 "type" : "GET"
 		      }
 		}   
	}).bind("select_node.jstree", function (e, data) {
		 var node = $('#tmp_tree').jstree(true).get_selected(true)[0];
		 $(document.modify.category_id).val(node.id);
	}).bind("ready.jstree", function (e, data) {
		$('#tmp_tree').jstree(true).deselect_all();
		if(window.entity && window.entity.category_id){
			$('#tmp_tree').jstree(true).select_node({id:window.entity.category_id})
		}
	});	
	



	
	$(".save").click(function(){
		var modalId = $(this).parents(".modal").attr("id");
		modifyEntity($("#"+modalId))
	});
	
	$(".back").click(function(){
		tab('entry');
	})

	
	if(window.entity){
		var entity = window.entity;
		$(document.modify).append('<input type="hidden" name="id" />');
		document.modify._method.value = "update";

		for(var i in entity){
			if(document.modify[i]){
				document.modify[i].value = entity[i];
			}
		}
		
		$("#editorDiv").summernote('code', entity.description);
	}else{
		document.modify.reset();
		document.modify._method.value = "save";
	}
})


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
	var frm = document.modify;
	var method = frm._method.value;
	var url = "../table/entity/"+method;	
	
	if(!validateForm(method, frm))	return;
	var formData = new FormData(frm);
	formData.append('description', $("#editorDiv").summernote('code'));

	$.ajax({
        url: url,
        type : "POST",
        async: false,
        data : formData,	//$modal.find("form").serialize()
        contentType : false, //这两个参数需要被定义，否则报错
        processData : false,
        success : function(data){
            if(data.res){
            	layer.msg("提交成功！");
				tab('entry');
            }
            else layer.msg("提交失败！");
        },
        dataType : "json",
        error : function(data){
        	layer.msg("提交出错！");
        }
    });

}



