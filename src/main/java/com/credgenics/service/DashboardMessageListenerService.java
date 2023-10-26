package com.credgenics.service;

import com.credgenics.dto.DashboardPayloadDto;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class DashboardMessageListenerService {

    Logger logger = LoggerFactory.getLogger(DashboardMessageListenerService.class);

    @Autowired
    DashboardService dashboardService;

    public void executePhysicalDashboardService(String message){
        logger.info("executePhysicalDashboardService.message: {}",message);
        JSONParser jsonParser = new JSONParser();
        try{
            HashMap<String, Object> valueMap =
                    (HashMap<String, Object>) jsonParser.parse(message);
            String company_id = (String) valueMap.get("company_id");
            String notice_type = (String) valueMap.get("notice_type");
            String allocation_month = (String) valueMap.get("allocation_month");
            String physical_notice_type = (String) valueMap.get("physical_notice_type");
            HashMap<String, Object> user =
                    (HashMap<String, Object>) jsonParser.parse((String) valueMap.get("x-cg-user"));
            HashMap<String, Object> company =
                    (HashMap<String, Object>) jsonParser.parse((String) valueMap.get("x-cg-company"));

            if (notice_type.toLowerCase().equals("overall")){
                notice_type = null;
            }
            if (allocation_month.toLowerCase().equals("overall")){
                allocation_month = null;
            }
//            bht
            physical_notice_type = null;
            String allocated_loan_ids = "('aug sanity 2')";

            DashboardPayloadDto payloadDto = DashboardPayloadDto.builder()
                    .company_id(company_id)
                    .notice_type(notice_type)
                    .allocation_month(allocation_month)
                    .physical_notice_type(physical_notice_type)
                    .allocated_loan_ids(allocated_loan_ids)
                    .build();

            dashboardService.getNoticeTypeAndTotalPhysicalNoticeSent(payloadDto);
            logger.info("executePhysicalDashboardService.message.valueMap : {}",valueMap);
        }
        catch (Exception e){
            logger.error("Exception.message.executePhysicalDashboardService ::" + e.getMessage());
            logger.error("Exception.executePhysicalDashboardService occurred while processing message: "+ e);
        }
    }
}
