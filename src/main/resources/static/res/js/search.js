





$(function(){

	$("#btn_search").click(function(){
		page.scrollTop = 0;
		$("#welcome_info").slideUp(1000, function(){
			$("#main").slideDown();
			query();
		});
	})
	$("#btn_key").keyup(function(evt){if(evt.keyCode == 13) $("#btn_search").click();});


	$("#note").click(function(){
		window.layer_id = layer.open({
			type: 1,
			content: '<div id="note_content"></div>',
			area: ['60px', '500px'],
			end : function () {

			}
		});
		$("#editorDiv").summernote({
			height : 450,
			callbacks: {
				onImageUpload : function(files, editor, welEditable) {
				//	sendFile(files[0], editor, welEditable);
				}
			}
		});
	})

	$("#collect").click(function(){
		if(window.user){
			$(this).toggleClass("am-icon-star").toggleClass("am-icon-star-o");
			collectEntry($(this).hasClass("am-icon-star"));
		}else{
			layer.confirm('用户未登录，前往登录？', {icon:3}, function(index){
				layer.close(index);
				$("#btn_login").click();
			});
		}
	});

	var key = web.getUrlParam("key");
	if(key){
		$("#welcome_info").hide();
		$("#btn_key").val(key);
		$("#btn_search").click();
	}

	init();
})

var page = {};

function init(){
	page = new Vue({
		el : '#main',
		data : {
			info: "",
			resultList : []
		}
	});
}

function query(){
	var key = $("#btn_key").val();
	if(key){
	//	key = encodeURI(key);
		page.info = "加载中，稍后...";
		$.post("/s", {wd:key}, function(data){
			page.info = "";
			page.resultList = data;
		})
	}
}

function handle(str){
	var key = $("#btn_key").val();
	var reg = new RegExp(key, "g");
	if(str){
		var tmp = str.replace(reg, '<em class="search-key">' + key + '</em>')
		if(tmp.length>500){
			return tmp.substr(0, 500) + "...";
		}else{
			return tmp;
		}
	}else{
		return "暂无描述";
	}
}

function handleTime(ts){
	return new Date(ts).Format("yyyy-MM-dd HH:mm:ss");
}

function collectEntry(coll){
	var entry = page.resultList[page.rid];
	if(!entry)	return;
	if(coll){
		$.getJSON("../user/collEntry", {entryId:entry.id}, function(data){
			if(data.res){
				$("#collect").removeClass("am-icon-star-o").addClass("am-icon-star");
				layer.msg("收藏成功！")
			}
		})
	}else{
		$.getJSON("../user/cancelCollecting", {otherId:entry.id, type:"entry"}, function(data){
			if(data.res){
				$("#collect").removeClass("am-icon-star").addClass("am-icon-star-o");
				layer.msg("取消成功！")
			}
		})
	}
}

