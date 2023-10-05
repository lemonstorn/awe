package com.zh.awe;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import com.baomidou.mybatisplus.generator.query.SQLQuery;
import com.zh.awe.model.AweBaseEntity;
import com.zh.awe.template.AweBaseController;
import com.zh.awe.template.AweBaseServiceImpl;
import com.zh.awe.template.IAweBaseService;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;

/**
 * @author zh 2023/6/22 17:31
 */
public class Generator extends BaseGenerator {
    private static final String projectName = "uims";
    private static final String projectPath = "D:\\java\\projects\\"+projectName;
    private static final String packageName = "com.zh.awe";
    private static final String jdbc_host = "localhost";
    private static final String jdbc_username= "root";
    private static final String jdbc_password= "123456";
    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder("jdbc:mysql://"+jdbc_host+":3306/"+projectName+"?serverTimezone=Asia/Shanghai", jdbc_username, jdbc_password)
            .schema(projectName)
            //	数据库类型转换器（配合SQLQuery）
            .typeConvert(new MySqlTypeConvert())
            //数据库关键字处理器
            .keyWordsHandler(new MySqlKeyWordsHandler())
            .databaseQueryClass(SQLQuery.class)
            .build();

    /**
     * 执行 run
     */
    public static void main(String[] args) {
        // 初始化数据库脚本
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator.packageInfo(packageConfig()
                .parent(packageName)
                .moduleName(projectName)
                .entity("model")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper.xml")
                .controller("controller")
                .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "\\src\\main\\resources\\mapper\\"))
                .build());
//        generator.injection(injectionConfig()
//                .customMap(Collections.singletonMap("test", "baomidou"))
//                .customFile(Collections.singletonMap("DTO.java", "/templates/dto.java.vm"))
//                .customFile(new CustomFile.Builder().fileName("DTO.java").templatePath("/templates/dto.java.vm").packageName("dto").build())
//                .build());
        generator.strategy(strategyConfig()
                //entity策略
                .entityBuilder()
                .superClass(AweBaseEntity.class)
                .disableSerialVersionUID()
                .enableChainModel()
                .enableLombok()
                .enableRemoveIsPrefix()
                .enableTableFieldAnnotation()
//                    即使用对象直接映射数据库记录
                .enableActiveRecord()
//                    .versionColumnName("version")
//                    .versionPropertyName("version")
//                    .logicDeleteColumnName("deleted")
//                    .logicDeletePropertyName("deleteFlag")
                .naming(NamingStrategy.underline_to_camel)
                .columnNaming(NamingStrategy.underline_to_camel)
                .addSuperEntityColumns("id", "created_by", "created_time", "updated_by", "updated_time")
//                    .addIgnoreColumns("age")
//                .addTableFills(new Column("create_time", FieldFill.INSERT))
//                .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                .idType(IdType.AUTO)
                .formatFileName("%s")
                //controller策略
                .controllerBuilder()
                .superClass(AweBaseController.class)
//                .enableHyphenStyle()
                .enableRestStyle()
                .formatFileName("%sController")
                //service生成策略
                .serviceBuilder()
                .superServiceClass(IAweBaseService.class)
                .superServiceImplClass(AweBaseServiceImpl.class)
                .formatServiceFileName("I%sService")
                .formatServiceImplFileName("%sServiceImp")
                //mapper生成策略
                .mapperBuilder()
                .superClass(BaseMapper.class)
                .mapperAnnotation(Mapper.class)
                .enableBaseResultMap()
                .enableBaseColumnList()
//                    .cache(MyMapperCache.class)
                .formatMapperFileName("%sMapper")
                .formatXmlFileName("%sXml")
                .build());
        generator.global(globalConfig()
//                生成完毕是否打开生成目录文件
                .disableOpenDir()
                .outputDir(projectPath + "\\src\\main\\java\\")
                .author("zh")
                .enableSpringdoc()
                .dateType(DateType.TIME_PACK)
                .commentDate("yyyy-MM-dd")
                .build());
        generator.execute();
    }


}
