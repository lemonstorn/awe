package com.zh.awe.web.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.zh.awe.common.enums.BaseEnum;
import com.zh.awe.common.model.R;
import com.zh.awe.common.utils.EnumUtils;
import com.zh.awe.web.web.WebProperties;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.reflections.Reflections;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zh 2023/8/26 22:18
 */
@RestController
public class AweCommonController {
    @Resource
    private WebProperties webProperties;
    private static final Map<String, List<Map<String, Object>>> ENUM_MAP = new ConcurrentHashMap<>();

    @PostConstruct
    @SuppressWarnings({"rawtypes"})
    public void init(){
        List<String> enumPath = webProperties.getEnumPath();
        if (ENUM_MAP.isEmpty() && CollectionUtil.isNotEmpty(enumPath)){
            enumPath.add("com.zh.awe.enums");
            enumPath.stream()
                    .distinct()
                    .forEach(path->{
                        int length = path.length()+1;
                        //获取该路径下所有类
                        Reflections reflections = new Reflections(path);
                        //获取继承了ISuperClass的所有类
                        Set<Class<? extends BaseEnum>> classSet = reflections.getSubTypesOf(BaseEnum.class);

                        for (Class<? extends BaseEnum> clazz : classSet){
                            // 实例化获取到的类
                            if (clazz.isEnum()){
                                List<Map<String, Object>> mapList = EnumUtils.enumToListMap(clazz);
                                ENUM_MAP.put(clazz.getName().substring(length),mapList);
                            }
                        }
                    });
        }
    }

    @GetMapping("/getEnumsInfo")
    public R<List<Map<String, Object>>> getEnumsInfo(String enumName){
        return R.ok(ENUM_MAP.get(webProperties.getEnumPath()+"."+enumName));
    }

    @GetMapping("/initEnumsInfo")
    public R<Map<String, List<Map<String, Object>>>> initEnumsInfo(){
        return R.ok(ENUM_MAP);
    }
}
