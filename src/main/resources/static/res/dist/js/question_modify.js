/*
 * 后台题库管理
 * question.js
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
	

	
	$(document).on("change", "#entry_sel select", function(){
		if(this.selectedOptions.length > 0){
			var txt = this.selectedOptions[0].innerText;
			$("#entry_sel input").val(txt);
		}
	});
	
	
	$(document).on("keyup", "#entry_sel input", function(){
		var sel = this;
		var key = $(sel).val();
		if(key && key != ' '){
			var title = encodeURI($(sel).val());
			$("#entry_sel select").empty();
			$("#entry_sel select").append('<option value="">无选项</option>')
			$.getJSON("../ruler/getEntryByTitle", {title:title}, function(data){
				for(var i in data){
					$("#entry_sel select").append('<option value="'+data[i].id+'" info="'+data[i].description+'" >'
							+data[i].title+'</option>');			
				}
			})
		}
	
	})
	
	$(".save").click(function(){
		var modalId = $(this).parents(".modal").attr("id");
		modifyEntity($("#"+modalId))
	});
	
	$(".back").click(function(){
		tab("question");
	})

	$(document.modify.type).change(function(){
		var ty = $(this).val();
		$(".type").hide();
		$(document.modify.anskey).removeAttr("name");
		if(ty == '4'){
			$(".type" + ty).show().find(".ans").attr("name", "answer");	
		}else{
			$(".type" + ty).show().find(".ans").attr("name", "anskey");	
		}
		
	})
	
	$(document.modify.type).change();
	
	
	if(window.entity){
		var entity = window.entity;
		$(document.modify).append('<input type="hidden" name="id" />');
		document.modify._method.value = "update";
		
		document.modify['type'].value = entity['type'];
		$(document.modify.type).change();
		for(var i in entity){
			if(document.modify[i]){
				if(i == 'anskey' && entity.type == '2'){
					$(document.modify[i]).val(entity.anskey.split(','));
				}else{
					document.modify[i].value = entity[i];
				}
			}
		}
		
		$("#editorDiv").summernote('code', entity.content);
	}else{
		document.modify.reset();
		document.modify._method.value = "save";
	}
})


function validateForm(method, frm){
	
	if(method == "save"){
		$(frm.id).remove();
	}
	
	if(!frm.entry_id.value){
		$(frm.entry_id).remove();
	}
	
	if(!frm.category_id.value){
		$(frm.category_id).remove();
	}
	
	if(frm.type.value == '2'){
		var muls = $(document.modify.anskey).val();
		if(!muls || muls.length == 0){
			layer.msg('多选题答案至少要有一个答案！');
			return false;
		}
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
	formData.append('content', $("#editorDiv").summernote('code'));
	if(frm.type.value == '2'){
		formData.set('anskey', $(frm.anskey).val().toString());
	}
	
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
            	tab("question");
            }
            else layer.msg("提交失败！");
        },
        dataType : "json",
        error : function(data){
        	layer.msg("提交出错！");
        }
    });
    
    //$("#modifyModal").modal('hide');
}


function showDetail(){
	var sel = document.modify.entry_id;
	var tx = '无选项';
	if(sel.value.length && sel.selectedOptions.length){
		tx = sel.selectedOptions[0].getAttribute("info");
		layer.open({
			title : '规章详情',
			content : tx
		});
	}else{
		layer.msg(tx);
	}
	
	
}


