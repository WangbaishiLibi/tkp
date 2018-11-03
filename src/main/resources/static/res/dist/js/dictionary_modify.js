/*
 * 规章修改
 * entry_modify.js
 */
var table;

$(function(){


	$(".save").click(function(){
		var modalId = $(this).parents(".modal").attr("id");
		modifyEntity($("#"+modalId));
	});
	
	$(".back").click(function(){
		tab('dictionary');
	});


	$(document.modify.type).change(function(){
		var type = $(this).val();
		$(".div_option").hide();
		if(type){
			document.modify.tabname.value = "";
			$(".div_option").find("select").removeAttr("name");
			if(type == "book"){
				document.modify.tabname.value = "book";
				$(".div_" + type).find("select").attr("name", "entity_id");
			}else if(type == "chapter"){
				document.modify.tabname.value = "chapter";
				$(".div_" + type).find("select").attr("name", "entity_id");
			}

			$(".div_" + type).show();
		}
	});

	$.getJSON("../dict/bookData", function(data){
		for(var i in data.books){
			$(".div_book").find("select").append('<option value="'+data.books[i].id+'">'+data.books[i].title+'</option>');
		}
		for(var i in data.chapters){
			$(".div_chapter").find("select").append('<option value="'+data.chapters[i].id+'">'+data.chapters[i].title+'</option>');
		}

		if(window.entity){
			var entity = window.entity;
			$(document.modify).append('<input type="hidden" name="id" />');
			document.modify._method.value = "update";
			document.modify.type.value = entity.type;
			$(document.modify.type).change();
			for(var i in window.entity){
				if(document.modify[i]){
					document.modify[i].value = entity[i];
				}
			}
			$("#editorDiv").summernote('code', entity.intro);

		}else{
			document.modify.reset();
			document.modify._method.value = "save";
		}
	});
	

})


function validateForm(method, frm){
	
	if(method == "save"){
		$(frm.id).remove();
	}

	if(!frm.cnt.value)	$(frm.cnt).remove();
	if(!frm.seq.value)	$(frm.seq).remove();

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
	formData.append('intro', $("#editorDiv").summernote('code'));

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
				tab('dictionary');
            }
            else layer.msg("提交失败！");
        },
        dataType : "json",
        error : function(data){
        	layer.msg("提交出错！");
        }
    });

}



