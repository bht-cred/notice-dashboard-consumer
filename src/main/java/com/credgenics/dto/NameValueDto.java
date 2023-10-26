package com.credgenics.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NameValueDto {
    private String name;
    private Integer y;

}
