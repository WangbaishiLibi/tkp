

var page;
var rulerTree;

$(function() {
	$("#head").load("head.html", function(){	initHead(2); });
	$("#foot").load("foot.html");
	
	$(document).on("click", "#btn_last", function(){	forward(page.que_no-1); });
	$(document).on("click", "#btn_next", function(){	forward(page.que_no+1); });
	$(document).on("click", "#btn_start", function(){	
		$(this).addClass("am-disabled").text("重新开始");
		$("#btn_submit").removeAttr("submit").removeClass("am-disabled").text("提交");
		clearInterval(thr);
		$("#btn_review").click();
		initQuestions($("#que_type").val());	//初始化题目
		timeClock();		//初始化时钟

	});
	
	$(document).on("click", "#btn_submit", function(){
		clearInterval(thr);
		if(!$(this).attr("submit")){
			submit(); 
		}else{
			$("#tab_panel").slideUp();
			$("#result").slideDown();
		}
		$(this).attr("submit", "true").text("结果");
		$("#btn_start").removeClass("am-disabled");
		forward(-1);
	});
	$(document).on("click", "#btn_review", function(){
		$("#result").slideUp();
		$("#tab_panel").slideDown();
		forward(page.que_no);
	});

	$(document).on("click", "#collect", function(){
		if(window.user){
			$(this).toggleClass("am-icon-star").toggleClass("am-icon-star-o");
			collectQuestion($(this).hasClass("am-icon-star"));
		}else{
			layer.confirm('用户未登录，前往登录？', {icon:3}, function(index){
				layer.close(index);
				$("#btn_login").click();
			});
		}
	})
	
	init();
});

function init(){
	page = new Vue({
		el : '#main',
		data : {
			selected : "1",
			que_no : 1,
			list : [],
			answers : [],
			cnt : 1,
			cnt_cor : 0,
			cnt_err : 0,
			accuracy : 0,
			curTime : 0,	//单位秒
			totalTime : 120,
			doc_val : "defalut_0",
			docs : [{id:"defalut_0", text:"全部"}],
			chapter_val : "defalut_0",
			chapters : [{id:"defalut_0", text:"全部"}],
			isActive : false
		},
		computed: {
			curTimes:{
				get:function(){
					return timeFormat(this.curTime);
				}
			},
			totalTimes:{
				get:function(){
					return timeFormat(this.totalTime);
				}
			},
			lists:{
				get:function(){
					return handleList(this.list);
				}
			},
			doc_vals:{
				get:function(){
					return this.doc_val;
				},
				set:function(val){
					this.doc_val = val;
					handleScope();
				}
			}
			
		}
	});

	$.getJSON("../ruler/rulerTree", function(data){
		rulerTree = data;
		var tmp = [{id:"defalut_0", text:"全部"}];
		for(var i in data){
			tmp.push({id:data[i].id, text:data[i].text});
		}
		page.docs = tmp;
	})
}


function forward(i){
	var len = page.list.length;
	
	if(i == -1){
		$("#btn_next").addClass("am-disabled");
		$("#btn_last").addClass("am-disabled");
		return;
	}
	
	$("#btn_last").removeClass("am-disabled");
	$("#btn_next").removeClass("am-disabled");
	
	if(i<=1){
		$("#btn_last").addClass("am-disabled");
	}else if(i >= len){
		$("#btn_next").addClass("am-disabled");
	}
	
	$("#tab_panel").tabs('open', i-1);
	var pc = parseInt(i*100/len);
	$("#progress").text(pc + "%").css("width", pc + "%");
	page.que_no = i;
}

function initQuestions(type){
	//先清空原有数据
	$(".desc .am-collapse").text("");
	$(".desc").hide();
	forward(1);

	var doc_id = page.doc_val.split("_")[1];
	var chapter_id = page.chapter_val.split("_")[1];
	type = type ? type : 1;
	$.getJSON("../question/randomQuestions",{cnt:5, type:type, doc_id:doc_id, chapter_id:chapter_id}, function(data){
		$("#main").hide();

		page.list = data;
		page.cnt = data.length;
		page.curTime = 0;
		page.que_no = 1;
		
		setTimeout(function(){
			$("#panels").find(".am-active:gt(0)").removeClass("am-active");
			$("#tabs").find(".am-active:gt(0)").removeClass("am-active");
			$("#tab_panel").tabs('refresh');
			//清理样式
			$(".am-radio").removeClass("wrong right");
			$(".am-checkbox").removeClass("wrong right half");
			$(".am-form").find("input[name=ans]").attr("checked", false).removeAttr("disabled");

			$("#result").slideUp();
			$("#tab_panel").slideDown();
		}, 100)
		
		var pc = parseInt(1*100/data.length);
		$("#progress").text(pc + "%").css("width", pc + "%");
		$("#main").show();
	})
}

function submit(){
	
	var answers = [];
	$("#panels").find("form").each(function(i){
		if(page.list[i].type == '2'){	//处理多选值
			var multi = document.forms["form" + i].ans;
			var tmp = [];
			for(var i in multi){
				if(multi[i].checked)	tmp.push(multi[i].value);
			}
			answers.push(tmp);
		}else{
			answers.push(document.forms["form" + i].ans.value);
		}
		
	})
	
	
	var cnt_cor=0, cnt_err=0;
	$.getJSON("../question/answer", function(data){
		page.answers = data;
		loadResult(data, answers);
	})
	
}

/*
 * -- 校验答案
 * data,标准答案
 * answers,用户答案
 */
function loadResult(data, answers){
	$("#tab_panel").slideUp();
	$("#result").slideDown();
	
	var cnt_cor=0, cnt_err=0;
	for(var i in data){
		var ans = data[i];
		if(ans.type == "1"){			//单选题
			var radio = $("#panels").find("form").eq(i).find(".am-radio");
			if(i<answers.length && ans.anskey == answers[i]){
				cnt_cor++;
				radio.eq(parseInt(answers[i])-1).addClass("right");
			}else{
				cnt_err++;
				radio.eq(parseInt(ans.anskey)-1).addClass("right");
				radio.eq(parseInt(answers[i])-1).addClass("wrong");
			}
			radio.find("input").attr("disabled", "disabled");
			page.lists[i].queInfo = '正确答案： '+ans.anskey+'  <br> 你的答案：'+answers[i];
		}else if(ans.type == "2"){		//多选题
			var radio = $("#panels").find("form").eq(i).find(".am-checkbox");
			var keys = [];
			if(ans.anskey)	keys = ans.anskey.split(',');
			if(i<answers.length && ans.anskey == answers[i]){
				cnt_cor++;
				for(var k in answers[i])
					radio.eq(parseInt(answers[i][k])-1).addClass("right");
			}else{
				cnt_err++;
				for(var k in keys)
					radio.eq(parseInt(keys[k])-1).addClass("right");
				for(var k in answers[i])
					radio.eq(parseInt(answers[i][k])-1).addClass("wrong");
			}
			radio.find("input").attr("disabled", "disabled");
			radio.each(function(k){
				console.log(this);
				if(radio.eq(k).hasClass('right') && radio.eq(k).hasClass('wrong')){
					radio.eq(k).removeClass("right");
					radio.eq(k).removeClass("wrong");
					radio.eq(k).addClass("half");
				}	
			})
			
			page.lists[i].queInfo = '正确答案： '+ans.anskey+'  <br> 你的答案：'+answers[i];
		}else if(ans.type == "3"){		//判断题
			var radio = $("#panels").find("form").eq(i).find(".am-radio");
			var tmp_i = ans.anskey == "N" ? 0:1;
			if(i<answers.length && ans.anskey == answers[i]){
				cnt_cor++;
				radio.eq(tmp_i).addClass("right");
			}else{
				cnt_err++;
				radio.eq(1-tmp_i).addClass("right");
				radio.eq(tmp_i).addClass("wrong");
			}
			radio.find("input").attr("disabled", "disabled");
			page.list[i].queInfo = '正确答案： '+ans.anskey+'  <br> 你的答案：'+answers[i];
		}else if(ans.type == "4"){		//简答题
			var radio = $("#panels").find("form").eq(i).find(".am-textarea");
			radio.find("textarea").attr("disabled", "disabled");
			if(i<answers.length && ans.anskey == answers[i]){
				cnt_cor++;
			}else{
				cnt_cor--;
			}

			page.list[i].colCls = page.list[i].cid ? 'am-icon-star' : 'am-icon-star-o';
			page.list[i].queInfo = '正确答案： '+ans.answer+'  <br> 你的答案：'+answers[i];
		}
		
		
		
		//条例说明
		$("#desc" + i).text("    " + data[i].explanation);
	}
	$(".desc").show();
	page.cnt_cor = cnt_cor;
	page.cnt_err = cnt_err;
	page.accuracy = (cnt_cor * 100 / page.cnt).toFixed(2);
	
}

function handleList(list){
	var lists = [];
	for(var i in list){
		var que = list[i];
		if(que.type == '1'){	//单选题
			var tmp = '<div class="am-radio"><label><input type="radio" name="ans" value="1"/>A、'+que.option1+'</label></div>';
			tmp += '<div class="am-radio"><label><input type="radio" name="ans" value="2"/>B、'+que.option2+'</label></div>';
			tmp += '<div class="am-radio"><label><input type="radio" name="ans" value="3"/>C、'+que.option3+'</label></div>';
			tmp += '<div class="am-radio"><label><input type="radio" name="ans" value="4"/>D、'+que.option4+'</label></div>';
			que.ansArea = tmp;
			que.typeName = '单选题';
		}else if(que.type == '2'){	//多选题
			var tmp = '<div class="am-checkbox"><label><input type="checkbox" name="ans" value="1"/>A、'+que.option1+'</label></div>';
			tmp += '<div class="am-checkbox"><label><input type="checkbox" name="ans" value="2"/>B、'+que.option2+'</label></div>';
			tmp += '<div class="am-checkbox"><label><input type="checkbox" name="ans" value="3"/>C、'+que.option3+'</label></div>';
			tmp += '<div class="am-checkbox"><label><input type="checkbox" name="ans" value="4"/>D、'+que.option4+'</label></div>';
			que.ansArea = tmp;
			que.typeName = '多选题';
		}else if(que.type == '3'){	//判断题
			var tmp = '<div class="am-radio"><label><input type="radio" name="ans" value="N"/>A、错误</label></div>';
			tmp += '<div class="am-radio"><label><input type="radio" name="ans" value="Y"/>B、正确</label></div>';
			que.ansArea = tmp;
			que.typeName = '判断题';
		}else if(que.type == '4'){	//简答题
			var tmp = '<div class="am-textarea"><textarea name="ans" ></textarea></div>';
			que.ansArea = tmp;
			que.typeName = '简答题';
		}	
		
		lists.push(que);
	}
	
	return lists;			
}

function handleScope(){
	var id = page.doc_val;
	if(id == "defalut_0"){
		page.chapters = [{id:"defalut_0", text:"全部"}];
		page.chapter_val = "defalut_0";
	}else{
		var tmp = [{id:"defalut_0", text:"全部"}];
		for(var i in rulerTree){
			if(rulerTree[i].id == id){
				for(var j in rulerTree[i].children){
					tmp = tmp.concat(rulerTree[i].children[j].children);
				}
			}
		}
		page.chapters = tmp;
		page.chapter_val = "defalut_0";
	}

}

function collectQuestion(coll){
	var que = page.answers[page.que_no];
	if(!que)	return;
	if(coll){
		$.getJSON("../user/collQustion", {questionId:que.id}, function(data){
			if(data.res){
				$("#collect").removeClass("am-icon-star-o").addClass("am-icon-star");
				layer.msg("收藏成功！")
			}
		})
	}else{
		$.getJSON("../user/cancelCollecting", {otherId:que.id, type:"question"}, function(data){
			if(data.res){
				$("#collect").removeClass("am-icon-star").addClass("am-icon-star-o");
				layer.msg("取消成功！")
			}
		})
	}
}

var thr;
function timeClock(){
	thr = setInterval(function(){
		page.curTime += 1;
		if(page.curTime>=page.totalTime){
			clearInterval(thr);
			
			//
		}
	}, 1000)
}

//格式化时间（分秒）
function timeFormat(len){
	if(typeof len == "number"){
		var sec = (len%60);
		sec = sec<10?"0"+sec:sec;
		return parseInt(len/60)+":"+sec; 
	}else{
		return len;
	}
}