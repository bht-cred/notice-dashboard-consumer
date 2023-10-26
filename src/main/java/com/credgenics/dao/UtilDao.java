package com.credgenics.dao;

import com.credgenics.dto.GenericDaoResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UtilDao {

    Logger logger = LoggerFactory.getLogger(UtilDao.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    public GenericDaoResponseDto getDashboardDataForSingleObject(String query, RowMapper<GenericDaoResponseDto> rowMapper) {
        logger.info("UtilDao.getDashboardDataForSingleObject()");
        logger.debug("UtilDao.getDashboardDataForSingleObject.query : {} || rowMapper : {}",query,rowMapper.toString());
        try {
            logger.info("UtilsDao.getData.try");
            GenericDaoResponseDto genericDaoResponseDto = jdbcTemplate.queryForObject(query, rowMapper);
            return genericDaoResponseDto;
        }
        catch (Exception e){
            logger.error("Exception.UtilsDao.getDashboardDataForSingleObject :: " + e);
            e.printStackTrace();
        }
        return null;
    }

    public List<GenericDaoResponseDto> getDashboardDataForList(String query, RowMapper<GenericDaoResponseDto> rowMapper) {
        logger.info("UtilDao.getDashboardDataForList()");
        logger.debug("UtilDao.getDashboardDataForList.query : {} || rowMapper : {}",query,rowMapper.toString());
        try {
            logger.info("UtilsDao.getData.try");
            List<GenericDaoResponseDto> genericDaoResponseDto = jdbcTemplate.query(query, rowMapper);
            return genericDaoResponseDto;
        }
        catch (Exception e){
            logger.error("Exception.UtilsDao.getDashboardDataForList :: " + e);
            e.printStackTrace();
        }
        return null;
    }


}
