package com.credgenics.logger;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.contrib.json.classic.JsonLayout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Map;

public class CustomLogbackJsonLayout extends JsonLayout {
    private static final Logger logger = LoggerFactory.getLogger(CustomLogbackJsonLayout.class);

    public CustomLogbackJsonLayout() {
        this.setIncludeMDC(true);
    }

    @Override
    protected void addCustomDataToJsonMap(Map<String, Object> map, ILoggingEvent event) {
        map.put("module", event.getLoggerName());
        map.put("module", event.getLoggerName());
        map.put("trace_id", MDC.get("trace_id"));
        map.put("span_id", MDC.get("span_id"));
        if (event.hasCallerData()) {
            map.put("function", event.getCallerData());
        }
        try {
            Object value = new ObjectMapper().readValue(event.getMDCPropertyMap().getOrDefault("json_message", "{}"), JSONObject.class);
            map.put("json_message", value);
        } catch (JsonProcessingException e) {
            logger.error("JSON processing exception", e);
        }
        map.put("request_id", event.getMDCPropertyMap().get("request_id"));
        super.addCustomDataToJsonMap(map, event);
    }
}
