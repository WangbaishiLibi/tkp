var page;
$(function() {
	$("#head").load("head.html", function(){
		initHead();
	});
	$("#foot").load("foot.html");

	init();
	$.getJSON("../user/allUserInfo", function(data){
		page.user = data.user;
		page.searchRecords = data.searchRecords;
		page.questionRecords = data.questionRecords;
		page.entryCollection = data.entryCollection;
		page.questionCollection = data.questionCollection;
	});
});

function init(){
	page = new Vue({
		el : '#main',
		data : {
			user:{username:''},
			searchRecords:[],
			questionRecords:[],
			entryCollection:[],
			questionCollection:[]
		},
		computed: {


		}
	});
}