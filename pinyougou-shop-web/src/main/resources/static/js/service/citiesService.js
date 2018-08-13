//服务层
app.service('citiesService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../cities/findAll');		
	}
	//分页 
	this.findPage=function(page,rows){
		return $http.get('../cities/findPage?page='+page+'&rows='+rows);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../cities/findOne?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../cities/add',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../cities/update',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../cities/delete?ids='+ids);
	}
	//搜索
	this.search=function(page,rows,searchEntity){
		return $http.post('../cities/search?page='+page+"&rows="+rows, searchEntity);
	}    	
});
