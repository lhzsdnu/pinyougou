//商品详细页（控制层）
app.controller('itemController',function($scope){
    //数量操作
    $scope.addNum=function(x){
        $scope.num=$scope.num+x;
        if($scope.num<1){
            $scope.num=1;
        }
    }

    $scope.specificationItems={};//记录用户选择的规格
    //用户选择规格
    $scope.selectSpecification=function(name,value){
        $scope.specificationItems[name]=value;
    }
    //判断某规格选项是否被用户选中
    $scope.isSelected=function(name,value){
        if($scope.specificationItems[name]==value){
            return true;
        }else{
            return false;
        }
    }


});
