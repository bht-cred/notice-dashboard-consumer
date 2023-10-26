package com.credgenics.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountVsNoticeTypeDto {
    private Integer count;
    private String notice_type;
}
