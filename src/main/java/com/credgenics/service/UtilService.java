package com.credgenics.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Component
public class UtilService {

    Logger logger = LoggerFactory.getLogger("UtilService.class");

    public String selectQueryBuilder(ArrayList<String> selectColumns, String table, Map<String,Object> where, String groupBy){
        logger.info("UtilService.selectQueryBuilder");
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        String selectColumnsString = String.join(", ", selectColumns);
        stringBuilder.append(selectColumnsString + " " + "FROM " + table + " ");

        //        where condition
        if (where.size() > 0) {
            stringBuilder.append("WHERE ");

            Iterator<Map.Entry<String, Object>> itr = where.entrySet().iterator();
            String whereCondition = "";
            while (itr.hasNext()) {
                Map.Entry<String, Object> entry = itr.next();
                whereCondition+= String.format(entry.getKey(), (String) entry.getValue()) + " AND ";
            }
            whereCondition = whereCondition.substring(0,whereCondition.length()-4);
            stringBuilder.append(whereCondition);
        }

//        groupBy
        if (groupBy!=null){
            stringBuilder.append("group by " + groupBy + " ");
        }
        stringBuilder.append(" ;");
        String query = stringBuilder.toString();
        logger.debug("UtilService.selectQueryBuilder.query:: " + query);
        return query;
    }

}
