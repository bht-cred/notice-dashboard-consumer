package com.credgenics.service;

import com.credgenics.Constants;
import com.credgenics.dao.UtilDao;
import com.credgenics.dto.DashboardPayloadDto;
import com.credgenics.dto.GenericDaoResponseDto;
import com.credgenics.rowMapper.GenericRowMapper;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Repository
public class DashboardService {

    Logger logger = LoggerFactory.getLogger(DashboardService.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UtilDao utilDao;

    @Autowired
    private UtilService utilService;

    public JSONObject getNoticeTypeAndTotalPhysicalNoticeSent(DashboardPayloadDto dashboardPayloadDto){
        logger.info("DashboardDao.getNoticeTypeAndTotalPhysicalNoticeSent()");

//        add columns
        ArrayList<String> select = new ArrayList<String>(3);
        select.add("COUNT(loan_id) AS count");
        select.add("notice_type");

//        group by
        String group_by = "notice_type";

//        add where
        HashMap<String , Object> where = new HashMap<String,Object>();
        where.put("company_id = '%s'",dashboardPayloadDto.getCompany_id());
        where.put("document_type = '%s'", Constants.DocumentTypes.Speedpost.toString());
        where.put("is_deleted = %s",Constants.False);

        if (dashboardPayloadDto.getAllocation_month()!=null){
            where.put("allocation_month = '%s'",dashboardPayloadDto.getAllocation_month());
        }
        if (dashboardPayloadDto.getAllocated_loan_ids()!=null){
            where.put("loan_id in %s",dashboardPayloadDto.getAllocated_loan_ids());
        }
        if (dashboardPayloadDto.getPhysical_notice_type()!=null){
            where.put("data->>'cg_article_type' = '%s'",dashboardPayloadDto.getPhysical_notice_type());
        }
        String table = "case_links";
        String query = utilService.selectQueryBuilder(select,table,where,group_by);

        List<GenericDaoResponseDto> response = utilDao.getDashboardDataForList(query,new GenericRowMapper());
        if (response==null){
            return null;
        }
        JSONObject result = process_getNoticeTypeAndTotalPhysicalNoticeSentResult(response,dashboardPayloadDto.getNotice_type());
        logger.debug("DashboardService.getNoticeTypeAndTotalPhysicalNoticeSent.result :: " + result.toJSONString());
        return  result;
    }

    public JSONObject process_getNoticeTypeAndTotalPhysicalNoticeSentResult(List<GenericDaoResponseDto> response, String noticeType){
        logger.info("DashboardService.process_getNoticeTypeAndTotalPhysicalNoticeSentResult()");
        JSONObject json = new JSONObject();
        for (GenericDaoResponseDto genericDaoResponseDto : response) {
            if (noticeType!=null){
                if(genericDaoResponseDto.getNotice_type().toLowerCase().equals(noticeType.toLowerCase())){
                    json.put("count", genericDaoResponseDto.getCount());
                }
            }
        }
        json.put("result",response);
        return json;
    }




    public Integer TotalLoanAccountsWithPhysicalNoticesSent(DashboardPayloadDto dashboardPayloadDto){
        logger.info("DashboardDao.TotalLoanAccountsWithPhysicalNoticesSent()");

//        add columns
        ArrayList<String> select = new ArrayList<String>(3);
        select.add("COUNT(DISTINCT(loan_id)) AS count");
        select.add("notice_type");

//        group by
        String group_by = null;

//        add where
        HashMap<String , Object> where = new HashMap<String,Object>();
        where.put("company_id = '%s'",dashboardPayloadDto.getCompany_id());
        where.put("document_type = '%s'", Constants.DocumentTypes.Speedpost.toString());
        where.put("is_deleted = %s",Constants.False);

        if (dashboardPayloadDto.getAllocation_month()!=null){
            where.put("allocation_month = '%s'",dashboardPayloadDto.getAllocation_month());
        }
        if (dashboardPayloadDto.getNotice_type()!=null){
            where.put("allocation_month = '%s'",dashboardPayloadDto.getNotice_type());
        }
        if (dashboardPayloadDto.getAllocated_loan_ids()!=null){
            where.put("loan_id in %s",dashboardPayloadDto.getAllocated_loan_ids());
        }
        if (dashboardPayloadDto.getPhysical_notice_type()!=null){
            where.put("data->>'cg_article_type' = '%s'",dashboardPayloadDto.getPhysical_notice_type());
        }
        String table = "case_links";
        String query = utilService.selectQueryBuilder(select,table,where,group_by);

        GenericDaoResponseDto response = utilDao.getDashboardDataForSingleObject(query,new GenericRowMapper());
        if (response==null){
            return null;
        }
//        process result
//        JSONObject result;
//        logger.debug("DashboardService.getNoticeTypeAndTotalPhysicalNoticeSent.result :: " + result.toJSONString());
        return response.getCount();
    }



    public Integer GetTotalLoanCountVsSpeedpostPhysicalNoticeStatus(DashboardPayloadDto dashboardPayloadDto){
        logger.info("DashboardDao.TotalLoanAccountsWithPhysicalNoticesSent()");

//        add columns
        ArrayList<String> select = new ArrayList<String>(3);
        select.add("count(DISTINCT loan_id) as y");
//        select.add("notice_type");

//        group by
        String group_by = "name";
        String table;

//        add where
        HashMap<String , Object> where = new HashMap<String,Object>();
        if (dashboardPayloadDto.getAllocation_month()==null){
            select.add("speedpost_delivery_status as name");

            String cg_article_type_condition = "";
            String notice_type_condition = "";

            if (dashboardPayloadDto.getPhysical_notice_type()!=null){
                cg_article_type_condition = String.format("data->>'cg_article_type' = '%s'",dashboardPayloadDto.getPhysical_notice_type());
            }
            if (dashboardPayloadDto.getNotice_type()!=null){
                notice_type_condition = String.format("notice_type = '%s'",dashboardPayloadDto.getNotice_type());
            }
            table = String.format("(SELECT DISTINCT ON(loan_id) loan_id, data->>'speedpost_delivery_status' as speedpost_delivery_status FROM case_links WHERE company_id = '%s' AND document_type = '%s' %s %s AND is_deleted = false ORDER BY loan_id, created DESC) as foo",dashboardPayloadDto.getCompany_id(),Constants.DocumentTypes.Speedpost.toString(),
                    cg_article_type_condition,notice_type_condition);

        }
        else{
            table = "case_links";
            select.add("data->>'speedpost_delivery_status' as name");
            where.put("company_id = '%s'",dashboardPayloadDto.getCompany_id());
            where.put("document_type = '%s'", Constants.DocumentTypes.Speedpost.toString());
            where.put("is_deleted = %s",Constants.False);
            where.put("allocation_month = '%s'",dashboardPayloadDto.getAllocation_month());
            if (dashboardPayloadDto.getNotice_type()!=null){
                where.put("notice_type = '%s'",dashboardPayloadDto.getNotice_type());
            }
            if (dashboardPayloadDto.getPhysical_notice_type()!=null){
                where.put("data->>'cg_article_type' = '%s'",dashboardPayloadDto.getPhysical_notice_type());
            }
        }



        if (dashboardPayloadDto.getAllocation_month()!=null){
            where.put("allocation_month = '%s'",dashboardPayloadDto.getAllocation_month());
        }

        if (dashboardPayloadDto.getAllocated_loan_ids()!=null){
            where.put("loan_id in %s",dashboardPayloadDto.getAllocated_loan_ids());
        }
        if (dashboardPayloadDto.getPhysical_notice_type()!=null){
            where.put("data->>'cg_article_type' = '%s'",dashboardPayloadDto.getPhysical_notice_type());
        }

        table = "case_links";
        String query = utilService.selectQueryBuilder(select,table,where,group_by);

        GenericDaoResponseDto response = utilDao.getDashboardDataForSingleObject(query,new GenericRowMapper());
        if (response==null){
            return null;
        }
//        process result
//        JSONObject result;
//        logger.debug("DashboardService.getNoticeTypeAndTotalPhysicalNoticeSent.result :: " + result.toJSONString());
        return response.getCount();
    }




}
