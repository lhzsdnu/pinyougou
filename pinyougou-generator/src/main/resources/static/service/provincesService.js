//服务层
app.service('provincesService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../provinces/findAll');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../provinces/findPage?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../provinces/findOne?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../provinces/add',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../provinces/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../provinces/delete?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../provinces/search?page='+page+"&rows="+rows, searchEntity);
	}    	
});
