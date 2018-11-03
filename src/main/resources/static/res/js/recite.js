/*
 * recite.js
 * 
 */
var page;
var rulerTree;

$(function() {
	$("#head").load("head.html", function(){
		initHead(3);
	});
	$("#foot").load("foot.html");
	
	
	
	$(document).on("click", "#btn_record", function(){
		$(this).toggleClass("am-active");
		var dom = $(this).find(".am-icon-spin");
		if(dom.hasClass("am-icon-spinner")){
			dom.removeClass("am-icon-spinner");
			$(this).find("span").text("开始录音")
			//stop record
			stopRecording();
			uploadAudio();
			$(this).addClass("am-disabled");
		}else{
			dom.addClass("am-icon-spinner");
			$(this).find("span").text("停止录音")
			//start record
			startRecording();
		}
	})
	
	$(document).on("click", "#op_expand", function(){
		var dom = $(this).find("a").toggleClass("am-icon-compress am-icon-expand");
		dom.text(" 收起"==dom.text()?" 展开":" 收起");
		$("#ruler_info").slideToggle();
	})
	
	
	init();
	
});


function init(){
	page = new Vue({
		el : '#main',
		data : {
			ruler_info : "",
			recite_info : "",
			progress_info : "",
			doc_val : "defalut_0",
			docs : [{id:"defalut_0", text:"全部"}],
			chapter_val : "defalut_0",
			chapters : [{id:"defalut_0", text:"全部"}]
		},
		computed: {
			recite_info_diff:{
				get:function(){
					return this.recite_info;
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

	randomEntry();
}

function randomEntry(){
	var doc_id = page.doc_val.split("_")[1];
	var chapter_id = page.chapter_val.split("_")[1];
	$.getJSON("../recite/questionContent", {doc_id:doc_id, chapter_id:chapter_id}, function(data){
		page.ruler_info = data.description;
	})
}

var recorder;

var audio = document.querySelector('audio');

function startRecording() {
	page.progress_info = "录音中...";
	page.recite_info = "";
    HZRecorder.get(function (rec) {
        recorder = rec;
        recorder.start();
    });
}

function stopRecording() {
    recorder.stop();
}

function playRecording() {
    recorder.play(audio);
}

function handleResult(result){
	if(result.length){
		var segments = result[0].split("，");	//每次语音停顿以‘，’分割
		var finalText = "";
		var corrCnt = 0;
		for(var i in segments){
			finalText += segments[i];
			if(page.ruler_info.indexOf(segments[i]) > -1){
				corrCnt += segments[i].length;
			}
		}
		page.recite_info = finalText;
		page.progress_info = "识别完成！正确率" + (corrCnt*100/page.ruler_info.length).toFixed(2) + "%";
	}
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

function uploadAudio() {
	page.progress_info = "正在识别，请稍后...";
    recorder.upload("../recite/translate?ajaxStatus=1", function (state, e) {
        switch (state) {
            case 'uploading':
            	page.progress_info = '识别进度' + Math.round(e.loaded * 100 / e.total) + '%';
            	if(e.loaded>=e.tota)	page.progress_info = "正在处理中，请稍后...";
                break;
            case 'ok':
                var data = JSON.parse(e.target.responseText);
                if(data.result){
                	handleResult(data.result);
                }
                break;
            case 'error':
                alert("上传失败");
                break;
            case 'cancel':
                alert("上传被取消");
                break;
        }
        
        $("#btn_record").removeClass("am-disabled");
    });
}
