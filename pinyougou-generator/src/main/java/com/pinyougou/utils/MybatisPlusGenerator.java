package com.pinyougou.utils;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class MybatisPlusGenerator {

    public static void main(String[] args) {

        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) // 是否支持AR模式
                .setAuthor("栾宏志") // 作者
                .setOutputDir("D:\\java_exercise\\IDEA-workspace\\pinyougou\\pinyougou-generator\\src\\main\\java") // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                //自定义文件命名，注意 %s 会自动填充表实体属性！
                // 设置生成的service接口的名字的首字母是否为I
                .setServiceName("%sService")
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)
                .setBaseColumnList(true);

        //2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/pinyougoudb")
                .setUsername("root")
                .setPassword("123456");

        //3. 策略配置
        StrategyConfig stConfig = new StrategyConfig();

        stConfig.setCapitalMode(true) //全局大写命名
                // 指定表名 字段名是否使用下划线
                .setDbColumnUnderline(true)
                // 数据库表映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix("tb")// 表名生成策略
                .setInclude();  // 需要生成的表(默认全部，即可以不写)
        //.setExclude(new String[]{"test"}); // 排除生成的表

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.pinyougou")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("entity")
                .setXml("mapper");

        //5. 整合配置
        // 引擎，默认 Veloctiy
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);

        //6. 执行
        ag.execute();

    }
}
