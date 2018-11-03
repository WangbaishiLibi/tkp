

var page;

$(function() {
	$("#head").load("head.html", function(){	initHead(4); });
	$("#foot").load("foot.html");

	
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

	init();
	initQuestions();

});

function init(){
	page = new Vue({
		el : '#main',
		data : {
			paper : {},
			lists : [],
			cnt : 1,
			cnt_cor : 0,
			cnt_err : 0,
			accuracy : 0,
			curTime : 0,	//单位秒
			totalTime : 120,
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
					this.totalTime = this.paper.duration*60;
					return timeFormat(this.totalTime);
				}
			},
			que_list:{
				get:function(){
					return handleList(this.paper.questions);
				}
			}
			
		}
	});
}


function initQuestions(){
	//先清空原有数据
	$.getJSON("../paper/randomPaper",function(data){
		page.paper = data;
		timeClock();
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
	var lists = [], lts = {};
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
		lts[que.type+que.id] = que;
	}

	//按type排序
	for(var i in lts){
		lists.push(lts[i]);
	}
	return lists;			
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