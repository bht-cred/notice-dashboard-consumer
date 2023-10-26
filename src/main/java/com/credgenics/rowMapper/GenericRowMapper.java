package com.credgenics.rowMapper;

import com.credgenics.dto.GenericDaoResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericRowMapper implements RowMapper<GenericDaoResponseDto> {

    Logger logger = LoggerFactory.getLogger(GenericRowMapper.class);
    Boolean count_exists;
    Boolean notice_type_exists;

    public GenericDaoResponseDto mapRow(ResultSet resultSet, int i) throws SQLException {

        GenericDaoResponseDto responseDto = new GenericDaoResponseDto();
        if(isThere(resultSet,"count")){
            responseDto.setCount(resultSet.getInt("count"));
        }
        if(isThere(resultSet,"notice_type")){
            responseDto.setNotice_type(resultSet.getString("notice_type"));
        }
        return responseDto;
    }

    private boolean isThere(ResultSet rs, String column){
        try{
            rs.findColumn(column);
            return true;
        } catch (SQLException sqlex){
            logger.debug("column doesn't exist {}", column);
        }
        return false;
    }
}