package com.open.other;

import com.open.utils.dingding.DingManage;
import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * 重复代码优化
 */
@Slf4j
public class RepeatCode {

    /**
     * 大小写转换
     * @param names
     * @param nameProcessor
     * @param processType
     */
    public void processNames(List<String> names, Function<String, String> nameProcessor, String processType) {
        System.out.println(processType + " Names:");
        for (String name : names) {
            String processedName = nameProcessor.apply(name);
            log.info(processedName + "hahhah");
//            System.out.println(processedName);
        }
    }

    /**
     * 测试大小写转换
     */
    @Test
    public void testCommonMethod() {
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David", "TianLuo");
        processNames(names, String::toUpperCase, "Uppercase");
        processNames(names, String::toLowerCase, "Lowercase");
    }
    @Test
    public void testGetResourceDir() {
        ClassLoader classLoader = DingManage.class.getClassLoader();
        String resourcePath = classLoader.getResource("application.properties").getPath();
        System.out.println(resourcePath);

        String path = resourcePath + "application.properties";
        File file = new File(path);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            int data;
            while( (data = fileInputStream.read()) != -1) {
                System.out.println((char)data);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
