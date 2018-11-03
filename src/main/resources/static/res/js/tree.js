



$(function(){
	$.ajaxSetup({cache:false});//ajax调用不使用缓存 		
	$('#ruler_tree').jstree({
 		"plugins" : [ "contextmenu", "state" ],
 		"core" : {
 			"check_callback" : true,
 		      "data" :  {
 		    	 "url" : "../category/getRulerTree",
 		    	 "dataType" : "JSON",
 		    	 "type" : "GET"
 		      }
 		},
 		"contextmenu" : {
			 "items":{
				 "create":{
					 "label":"创建",
					 "action":function(obj){
						 createNode();
					 }
				 },
				 "modify":{
					 "label":"修改",
					 "action":function(obj){
						 renameNode();
					 }
				 },
				 "remove":{
					 "label":"删除",
					 "action":function(obj){
						 deleteNode();
					 }
				 }
			 }
	 	 }    
	 }).on("select_node.jstree", function (e, data) {
		 var node = $('#ruler_tree').jstree(true).get_selected(true)[0];

		 tab_category.ajax.data.queryVal = node.id;
		 tab_category.reloadAjax();
	});		
	
});
		
		

function createNode() {
	layer.open({
		title:"创建节点", 
		content:$("#cateForm").clone().find("form").attr("name", "category").parent().html(),
		yes: function(layero, index){
			var frm = document.category;
			var ref = $('#ruler_tree').jstree(true),
			sel = ref.get_selected();
			if(!sel.length) { return false; }
			sel = sel[0];
			sel = ref.create_node(sel, {"type":"file"});
			var node = ref.get_node({id:sel});
			if(node.parent == '#')	node.parent = '0';
			frm.parent_id.value = node.parent;
			$.getJSON("../category/saveCategory", $(frm).serialize(), function(data){
				if(data.res){
					ref.set_text({id:sel}, frm.display.value);
					node.data = frm.name.value;
					ref.open_node({id:node.parent});	
				}else{
					layer.msg("操作失败");
				}
				layer.close(layero);
			})
		}
	});
	
	
}

function renameNode() {
	
	var ref = $('#ruler_tree').jstree(true),
	sel = ref.get_selected(true);
	if(!sel.length) { return false; }
	var node = sel[0];
	
	layer.open({
		title:"修改节点", 
		content:$("#cateForm").clone().find("form").attr("name", "category").parent().html(),
		yes: function(layero, index){
			var frm = document.category;
			frm.id.value = node.id;
			if(node.parent == '#')	node.parent = '0';
			frm.parent_id.value = node.parent;
			$.getJSON("../category/updateCategory", $(frm).serialize(), function(data){
				if(data.res){
					ref.refresh();
//					node.text = frm.display.value;
//					ref.set_text(node, frm.display.value);
//					node.data = frm.name.value;
//					ref.open_node({id:node.parent});	
				}else{
					layer.msg("操作失败");
				}
				layer.close(layero);
			})
		}
	});
	
	var frm = document.category;
	frm.display.value = node.text;
	frm.name.value = node.data;
}

function deleteNode() {
	var ref = $('#ruler_tree').jstree(true),
	sel = ref.get_selected();
	if(!sel.length) { return false; }
	var node = ref.get_node({id:sel});
	layer.confirm('确认删除节点'+node.text, {icon: 3, title:'删除'}, function(index){
		 //do something
		$.getJSON("../category/updateCategory", {id:node.id}, function(data){
			if(data.res){
				ref.delete_node(sel);	
			}else{
				layer.msg("操作失败");
			}
			layer.close(index);
		})
		
		
	});
}
		
		