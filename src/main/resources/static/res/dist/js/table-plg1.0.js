/*
 * table-plugin1.0.js
 * require : jquery
 */




DataTable = function(_opts){
	this.init(_opts);
	return this;
}

DataTable.fn = DataTable.prototype = {
	cache : false,		//是否缓存。缓存情况下分页查询将不请求后台，可以用来优化小数据表的访问效率
	tbname : "表名",		//表名	
	tabledom : null,	//表格dom对象
	rows : [],			//原始行数据
	columns : [],		//表格列信息: [name:表列名, field:字段名]
	ajax : {},
	data : {},			//原始数据
	srcName : null,		//数据源名称，与后台对应
	page : {			//当前页数据
		total : 0,			//总记录数
		page_index : 1,		//当前页码(从1开始)
		page_total : 0,		//每页记录数
		page_count : 0,		//总页数  = [总记录数/每页记录数]
		segment_index : 1,	//当前页段，(从1开始)
		segment_count : 10	//页段数，每次显示最多页码个数
	},
	attribute : {
		
	},
	_events : {			//事件集
		reload : [],			//加载事件
		pageChange : []			//页码事件
	}
}

/*
 * 初始化 
 */
DataTable.fn.init = function(_opts){
	if(!_opts.tabledom)	throw "表格元素不能为空！";
	if(typeof _opts.cache == "boolean")	this.cache = _opts.cache;
	this.tbname = _opts.tbname;	
	this.tabledom = _opts.tabledom;	
	this.columns = _opts.columns?_opts.columns:[];
	this.srcName = _opts.srcName?_opts.srcName:"data";
	this.attribute = _opts.attribute?_opts.attribute:{};
	
	if(_opts.page){
		this.page.page_index = _opts.page.page_index;
		this.page.page_total = _opts.page.page_total;
		this.page.segment_index = 1;
		this.page.segment_count = _opts.page.segment_count?_opts.page.segment_count:10;
	}
	
	if(_opts.ajax){
		this.ajax.type = _opts.ajax.type?_opts.ajax.type:"GET";
		this.ajax.dataType = _opts.ajax.dataType?_opts.ajax.dataType:"JSON";
		this.ajax.data = _opts.ajax.data?_opts.ajax.data:{};
		if(_opts.ajax.url){
			this.ajax.url = _opts.ajax.url;
			this.reloadAjax();
		}
	}else{	//ajax默认配置
		this.ajax.type = "GET";
		this.ajax.dataType = "JSON";
		this.data = {};
	}
	
	
}

/*
 * 加载数据 
 */
DataTable.fn.loadData = function(_data, type){
	this.data = _data;
	this.rows = _data[this.srcName]?_data[this.srcName]:_data;
	if(_data.page){
		_data.page.segment_index = this.page.segment_index;
		_data.page.segment_count = this.page.segment_count;
		this.page = _data.page;
	}
	if(this.cache){		//缓存情况下需要重新计算分页参数
		this.page.total = this.rows.length;
		this.page.page_count = Math.ceil(this.page.total/this.page.page_total);
	}
	this.show(type);
	
	this._events._reloadEvent(_data);
}


DataTable.fn.reload = function(){
	if(this.cache)	this.loadData(this.data);
	else this.reloadAjax();
}

/*
 * ajax请求 
 */
DataTable.fn.reloadAjax = function(_url, _data){

	
	this.ajax.url = _url?_url:this.ajax.url;
	var type = "all";	//更新区域
	if(_data){
		this.ajax.data = _data;
		type = "body_foot";
	}

	this.ajax.data.page_index = this.page.page_index;
	this.ajax.data.page_total = this.page.page_total;

	if(this.cache || this.page.page_total<=0){
		delete this.ajax.data.page_index;
		delete this.ajax.data.page_total;
	}
	
	//加入attribute的额外参数
	var fgn = this.attribute.foreign;
	var ord = this.attribute.order;
	var qry = this.attribute.query;
	
	if(fgn){
		for(var i in fgn){
			this.ajax.data[i] = fgn[i];
		}
	}
	if(ord){
		for(var i in ord){
			this.ajax.data[i] = ord[i];
		}
	}
	if(qry){
		for(var i in qry){
			this.ajax.data[i] = qry[i];
		}
	}
	
	var self = this;
	this.ajax.success = function(_data){
		self.loadData(_data, type);
	}
	$.ajax(this.ajax);
}

/*
 * 显示表信息 
 */
DataTable.fn.show = function(type){
//	this.tabledom.html("<thead></thead><tbody></tbody><tfoot></tfoot>");
	if(type=="all" || type=="head")	this._loadHead();
	
	if(this.cache){
		var tmp = [];
		var page = this.page;
		var min = (page.page_index-1)*page.page_total;
		var max = page.page_index*page.page_total;
		max = max < this.rows.length ? max : this.rows.length;
		for(var i=min; i<max; i++)
			tmp.push(this.rows[i]);
		this._loadBody(tmp);
	}else{
		this._loadBody(this.rows);
	}
	
	this._loadFoot();
}
/*
 * 表格头部
 */
DataTable.fn._loadHead = function(){
	var thead = this.tabledom.find("thead").eq(0);
	thead.html("");
	//加载列信息
	var trObj = "<tr>";
	var self = this;
	if(self.columns.length){
		for(var i in self.columns){
			var attr = "";
			var cls = self.columns[i].foreign?"":"class='sort_both'";
			if(typeof self.columns[i].field == "string"){
				attr = cls + " col='"+self.columns[i].field+"'";
			}
			
			trObj += "<th " + attr + "'>" + self.columns[i].name + "</th>";
		}
	}else{
		if(self.rows && self.rows.length){
			for(var i in self.rows[0]){
				trObj += "<th>" + i + "</th>";
			}
		}
	}
	trObj += "</tr>";
	thead.append(trObj);
}
/*
 * 表格内容
 */
DataTable.fn._loadBody = function(rows){
	var tbody = this.tabledom.find("tbody").eq(0);
	tbody.html("");
	//加载数据
	var self = this;
	if(rows){
		for(var i in rows){
			trObj = "<tr no='" + i + "'>";
			if(self.columns.length){
				for(var j in self.columns){
					var td;
					if(typeof self.columns[j].field == "string"){
						td = rows[i][self.columns[j].field];
					}else if(typeof self.columns[j].field == "function")
						td = self.columns[j].field(rows[i]);
					td = td?td:"";
					trObj += "<td>" + td + "</td>";
				}
			}else{
				for(var j in rows[i]){
					var td = rows[i][j];
					td = td?td:"";
					trObj += "<td>" + td + "</td>";
				}
			}
			trObj += "</tr>"
			tbody.append(trObj);
		}
	}
}
/*
 * 表格脚注
 */
DataTable.fn._loadFoot = function(){
	var tfoot = this.tabledom.find("tfoot").eq(0);
	tfoot.html("");
	//加载分页
	var len = this.columns.length?this.columns.length:this.tabledom.find("thead").eq(0).find("th").length;
	tfoot.append('<tr><td colspan="'+len+'"><div class="row"><nav><ul class="pagination col-sm-10"></ul></nav><span class="info col-sm-2"></span></div></td></tr>');
	
	if(this.page.total>0){
		var info = tfoot.find(".info");
		info.text("第" + this.page.page_index + "页 / 共" + this.page.page_count + "页");
		
		var ul = tfoot.find("ul").eq(0);
		ul.append('<li class="last_segment"><a>&laquo;</a></li>');
		ul.append('<li class="last_page"><a>&lt;</a></li>');
			
		var min = (this.page.segment_index-1)*this.page.segment_count + 1;
		var max = min + this.page.segment_count;
		max = max < (this.page.page_count+1) ? max : this.page.page_count + 1;
		for(var i=min; i<max; i++)
			ul.append('<li><a>'+i+'</a></li>')
		ul.append('<li class="next_page"><a>&gt;</a></li>');
		ul.append('<li class="next_segment"><a>&raquo;</a></li>');
		var id = (this.page.page_index - 1) % this.page.page_total;
		ul.find("li").eq(id + 2).addClass("active");
		var self = this;
		/*
		 * 页码事件源
		 */
		ul.find("li").click(function(event){
			event.pageDom = ul;
			event.table = self;
			self._events._pageChangeEvent(event);
			self.reload();
		})
	}else{
		tfoot.find("tr > td").append("共0条记录。")
	}
	
}









/*
 * * * 事件部分 * * *
 */
/*
 * 事件绑定
 * 可选事件：reload(数据重新加载)、pageChange(页码变动时)
 */
DataTable.fn.bind = function(type, fun){
	if(!type || typeof fun != "function")	return;
	if(this._events[type]){	//支持该事件
		this._events[type].push(fun);
	}	
}

DataTable.fn._events._reloadEvent = function(event){
	for(var i in this.reload)
		this._events.reload[i](event);
}

DataTable.fn._events._pageChangeEvent = function(event){
	var ul = event.pageDom;
	var self = event.table;
	var target = event.currentTarget;
	ul.find("li").removeClass("active");
	$(target).addClass("active");
	

	var min = (self.page.segment_index-1)*self.page.segment_count + 1;
	if(/\d+/.test($(target).text())){
		self.page.page_index = parseInt($(target).text());
	}else if($(target).hasClass("last_page")){	
		if(self.page.page_index-1 >= min)	self.page.page_index--;
		else{
			$(target).addClass("last_segment");
		}	
	}else if($(target).hasClass("next_page")){
		var max = min + self.page.segment_count;
		max = max < (self.page.page_count+1) ? max : self.page.page_count + 1;
		if(self.page.page_index+1 < max)	self.page.page_index++;
		else{
			$(target).addClass("next_segment");
		}
	}
	
	
	if($(target).hasClass("last_segment")){
		if(self.page.segment_index-1 > 0){
			self.page.segment_index--;
			self.page.page_index = (self.page.segment_index-1)*self.page.segment_count + 1;
		}
	}else if($(target).hasClass("next_segment")){
		var segment_total = Math.ceil(self.page.page_count/self.page.segment_count);
		if(self.page.segment_index+1 <= segment_total){
			self.page.segment_index++;
			self.page.page_index = (self.page.segment_index-1)*self.page.segment_count + 1;
		}
	}
	
	
	for(var i in this.pageChange)
		this._events.pageChange[i](event);
}




