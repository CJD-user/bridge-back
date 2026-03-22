package net.lab1024.sa.base.common.json.deserializer;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * LocalDate 反序列化器
 * 支持 yyyy-MM-dd 和 ISO 8601 格式
 *
 * @Author 1024创新实验室
 * @Date 2024-01-01
 * @Copyright 1024创新实验室
 */
public class FlexibleLocalDateDeserializer extends JsonDeserializer<LocalDate> {

    private static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter ISO_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private static final DateTimeFormatter NORM_DATE_FORMATTER = DatePattern.NORM_DATE_FORMAT.getDateTimeFormatter();

    @Override
    public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateStr = p.getValueAsString();
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }

        try {
            if (dateStr.contains("T")) {
                return LocalDate.parse(dateStr, ISO_DATE_TIME_FORMATTER);
            }
            return LocalDate.parse(dateStr, NORM_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(dateStr, ISO_DATE_FORMATTER);
            } catch (DateTimeParseException e2) {
                throw new IOException("无法解析日期格式: " + dateStr + "，支持的格式: yyyy-MM-dd 或 ISO 8601");
            }
        }
    }
}
