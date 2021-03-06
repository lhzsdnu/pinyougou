//控制层
app.controller('goodsController', function ($scope, $controller, $location, goodsService, uploadService, itemCatService, typeTemplateService) {

    $controller('baseController', {$scope: $scope});//继承

    //读取列表数据绑定到表单中  
    $scope.findAll = function () {
        goodsService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        goodsService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    $scope.entity = {goods: {}, goodsDesc: {itemImagesStr: [], specificationItemsStr: []}};//定义页面实体结构


    //查询实体
    $scope.findOne = function () {
        var id = $location.search()['id'];//获取参数值
        if (id == null) {
            return;
        }
        goodsService.findOne(id).success(
            function (response) {
                $scope.entity = response;
                //向富文本编辑器添加商品介绍
                editor.html($scope.entity.goodsDesc.introduction);
                //显示图片列表
                $scope.entity.goodsDesc.itemImagesStr =
                    JSON.parse($scope.entity.goodsDesc.itemImages);
                //显示扩展属性
                $scope.entity.goodsDesc.customAttributeItemsStr=
                    JSON.parse($scope.entity.goodsDesc.customAttributeItems);
                //规格
                $scope.entity.goodsDesc.specificationItemsStr =
                    JSON.parse($scope.entity.goodsDesc.specificationItems);

                //SKU列表规格列转换
                for (var i = 0; i < $scope.entity.itemList.length; i++) {
                    $scope.entity.itemList[i].specStr = JSON.parse($scope.entity.itemList[i].spec);
                }
            }
        );

    }

    //保存
    $scope.save = function () {
        //提取kindeditor编辑器的内容
        $scope.entity.goodsDesc.introduction = editor.html();

        //转换列表
        $scope.entity.goodsDesc.itemImages = JSON.stringify($scope.entity.goodsDesc.itemImagesStr);
        $scope.entity.goodsDesc.customAttributeItems = JSON.stringify($scope.entity.goodsDesc.customAttributeItemsStr);
        $scope.entity.goodsDesc.specificationItems = JSON.stringify($scope.entity.goodsDesc.specificationItemsStr);

        for (var i = 0; i < $scope.entity.itemList.length; i++) {
            $scope.entity.itemList[i].spec = JSON.stringify($scope.entity.itemList[i].specStr);
        }

        var serviceObject;//服务层对象
        if ($scope.entity.goods.id != null) {//如果有ID
            serviceObject = goodsService.update($scope.entity); //修改
        } else {
            serviceObject = goodsService.add($scope.entity);//增加
        }


        serviceObject.success(
            function (response) {
                if (response.success) {
                    alert('保存成功');
                    location.href="goods.html";//跳转到商品列表页
                    //清空处理
                    $scope.entity = {};
                    editor.html('');//清空富文本编辑器

                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        goodsService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                    $scope.selectIds = [];
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    $scope.search = function (page, rows) {
        goodsService.search(page, rows, $scope.searchEntity).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }


    /**
     * 上传图片
     */
    $scope.uploadFile = function () {
        uploadService.uploadFile().success(function (response) {
            if (response.success) {//如果上传成功，取出url
                $scope.image_entity.url = response.message;//设置文件地址

            } else {
                alert(response.message);
            }
        }).error(function () {
            alert("上传发生错误");
        });
    };



    //添加图片列表
    $scope.add_image_entity = function () {
        $scope.entity.goodsDesc.itemImagesStr.push($scope.image_entity);
        document.getElementById("file").value = "";
        document.getElementById("uploadImg").src="";
    };

    //列表中移除图片
    $scope.remove_image_entity = function (index) {
        $scope.entity.goodsDesc.itemImagesStr.splice(index, 1);
    };

    $scope.itemCatList = [];//商品分类列表
    //加载商品分类列表
    $scope.findItemCatList = function () {
        itemCatService.findAll().success(
            function (response) {
                for (var i = 0; i < response.length; i++) {
                    $scope.itemCatList[response[i].id] = response[i].name;
                }
            }
        );
    }


    //读取一级分类
    $scope.selectItemCat1List = function () {
        itemCatService.findByParentId(0).success(
            function (response) {
                $scope.itemCat1List = response;
            }
        );
    }

    //读取二级分类
    $scope.$watch('entity.goods.category1Id', function (newValue, oldValue) {
        //根据选择的值，查询二级分类
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat2List = response;
            }
        );
    });

    $scope.$watch('entity.goods.category2Id', function (newValue, oldValue) {
        //根据选择的值，查询二级分类
        itemCatService.findByParentId(newValue).success(
            function (response) {
                $scope.itemCat3List = response;
            }
        );
    });

    //三级分类选择后  读取模板ID
    $scope.$watch('entity.goods.category3Id', function (newValue, oldValue) {
        itemCatService.findOne(newValue).success(
            function (response) {
                $scope.entity.goods.typeTemplateId = response.typeId; //更新模板ID
            }
        );
    });

    //模板ID选择后  更新品牌列表
    $scope.$watch('entity.goods.typeTemplateId', function (newValue, oldValue) {
        typeTemplateService.findOne(newValue).success(
            function (response) {
                $scope.typeTemplate = response;//获取类型模板
                $scope.typeTemplate.brandIds =
                    JSON.parse($scope.typeTemplate.brandIds);//品牌列表
                if ($location.search()['id'] == null) {
                    $scope.entity.goodsDesc.customAttributeItemsStr =
                        JSON.parse($scope.typeTemplate.customAttributeItems);//扩展属性
                }

            }
        );
        //查询规格列表
        typeTemplateService.findSpecList(newValue).success(
            function (response) {
                $scope.specList = response;
            }
        );

    });

    $scope.updateSpecAttribute = function ($event, name, value) {

        var object = $scope.searchObjectByKey(
            $scope.entity.goodsDesc.specificationItemsStr, 'attributeName', name);

        if (object != null) {
            if ($event.target.checked) {
                object.attributeValue.push(value);
            } else {//取消勾选
                //移除选项
                object.attributeValue.splice(object.attributeValue.indexOf(value), 1);
                //如果选项都取消了，将此条记录移除
                if (object.attributeValue.length == 0) {
                    $scope.entity.goodsDesc.specificationItemsStr.splice(
                        $scope.entity.goodsDesc.specificationItemsStr.indexOf(object), 1);
                }
            }
        } else {
            $scope.entity.goodsDesc.specificationItemsStr.push(
                {"attributeName": name, "attributeValue": [value]});
        }
    };


    $scope.entity.itemList = [];
    $scope.status = ['未审核', '已审核', '审核未通过', '关闭'];//商品状态

    //创建SKU列表
    $scope.createItemList = function () {

        var items = $scope.entity.goodsDesc.specificationItemsStr;

        if (items.length == 0) {
            $scope.entity.itemList = [];
        } else {
            //默认初始化初始
            $scope.entity.itemList = [{specStr: {}, price: 0, num: 99999, status: '0', isDefault: '0'}];
            for (var i = 0; i < items.length; i++) {
                $scope.entity.itemList = addColumn($scope.entity.itemList, items[i].attributeName, items[i].attributeValue);
            }
        }


    };

    //添加列值
    addColumn = function (list, columnName, conlumnValues) {
        var newList = [];//新的集合
        for (var i = 0; i < list.length; i++) {
            var oldRow = list[i];
            for (var j = 0; j < conlumnValues.length; j++) {
                var newRow = JSON.parse(JSON.stringify(oldRow));//深克隆
                newRow.specStr[columnName] = conlumnValues[j];
                newList.push(newRow);
            }
        }
        return newList;
    }

    //根据规格名称和选项名称返回是否被勾选
    $scope.checkAttributeValue = function (specName, optionName) {
        var items = $scope.entity.goodsDesc.specificationItemsStr;
        var object = $scope.searchObjectByKey(items, 'attributeName', specName);
        if (object == null) {
            return false;
        } else {
            if (object.attributeValue.indexOf(optionName) >= 0) {
                return true;
            } else {
                return false;
            }
        }
    }


});
