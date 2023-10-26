package com.credgenics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Builder
@NoArgsConstructor
public class GenericDaoResponseDto {

    public Integer count;
    public String notice_type;
}
