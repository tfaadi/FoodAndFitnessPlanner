package com.planner.util;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class IdGeneratorUtil {
    public static String generateQueryId() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        return "QRY" + LocalDateTime.now().format(formatter);
    }
}

