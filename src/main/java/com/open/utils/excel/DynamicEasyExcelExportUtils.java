package com.open.utils.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 动态导出excel工具类
 * @author zc
 * @createTime 2021/4/20 10:39
 * @return
 */
public class DynamicEasyExcelExportUtils {

    private static final Logger log = LoggerFactory.getLogger(DynamicEasyExcelExportUtils.class);

    private static final String DEFAULT_SHEET_NAME = "sheet1";

    /**
     * 动态生成导出模版(单表头)
     * @param headColumns 列名称
     * @return excel文件流
     */
    public static byte[] exportTemplateExcelFile(List<String> headColumns) {

        List<List<String>> excelHead = Lists.newArrayList();
        headColumns.forEach(columnName -> {
            excelHead.add(Lists.newArrayList(columnName));
        });
        byte[] stream = createExcelFile(excelHead, new ArrayList<>());
        return stream;
    }

    /**
     * 动态生成模版(复杂表头)
     * @param excelHead   列名称
     * @return
     */
    public static byte[] exportTemplateExcelFileCustomHead(List<List<String>> excelHead) {
        byte[] stream = createExcelFile(excelHead, new ArrayList<>());
        return stream;
    }

    /**
     * 动态导出文件
     * @param headColumnMap  有序列头部
     * @param dataList       数据体
     * @return
     */
    public static byte[] exportExcelFile(LinkedHashMap<String, String> headColumnMap, List<Map<String, Object>> dataList) {
        //获取列名称
        List<List<String>> excelHead = new ArrayList<>();
        if (MapUtils.isNotEmpty(headColumnMap)) {
            //key为匹配符，value为列名，如果多级列名用逗号隔开
            headColumnMap.entrySet().forEach(entry -> {
                excelHead.add(Lists.newArrayList(entry.getValue().split(",")));
            });
        }
        List<List<Object>> excelRows = new ArrayList<>();
        if (MapUtils.isNotEmpty(headColumnMap) && CollectionUtils.isNotEmpty(dataList)) {
            for (Map<String, Object> dataMap : dataList) {
                List<Object> rows = new ArrayList<>();
                headColumnMap.entrySet().forEach(headColumnEntry -> {
                    if (dataMap.containsKey(headColumnEntry.getKey())) {
                        Object data = dataMap.get(headColumnEntry.getKey());
                        rows.add(data);
                    }
                });
                excelRows.add(rows);
            }
        }
        byte[] stream = createExcelFile(excelHead, excelRows);
        return stream;
    }

    /**
     * 生成文件
     * @param excelHead
     * @param excelRows
     * @return
     */
    private static byte[] createExcelFile(List<List<String>> excelHead, List<List<Object>> excelRows) {
        try {
            if (CollectionUtils.isNotEmpty(excelHead)) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                EasyExcel.write(outputStream).registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                        .head(excelHead)
                        .sheet(DEFAULT_SHEET_NAME)
                        .doWrite(excelRows);
                return outputStream.toByteArray();
            }
        } catch (Exception e) {
            log.error("动态生成excel文件失败，headColumns：" + JSONArray.toJSONString(excelHead) + "，excelRows：" + JSONArray.toJSONString(excelRows), e);
        }
        return null;
    }

    /**
     * 导出文件测试
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //导出包含数据内容的文件
        LinkedHashMap<String, String> headColumnMap = Maps.newLinkedHashMap();
        headColumnMap.put("className", "班级");
        headColumnMap.put("name", "学生信息,姓名");
        headColumnMap.put("sex", "学生信息,性别");
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Map<String, Object> dataMap = Maps.newHashMap();
            dataMap.put("className", "一年级");
            dataMap.put("name", "张三" + i);
            dataMap.put("sex", "男");
            dataList.add(dataMap);
        }
        byte[] stream = exportExcelFile(headColumnMap, dataList);
        FileOutputStream outputStream = new FileOutputStream(new File("C:/Users/djzc/Downloads/Programs/easyexcel-export-user5.xlsx"));
        outputStream.write(stream);
        outputStream.close();
    }
}