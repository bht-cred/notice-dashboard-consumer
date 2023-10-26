package com.credgenics.dto;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ResponseDto {
    private Integer total_physical_notice_sent;
    private Integer total_loan_accounts_with_physical_notices_sent;
    private List<NameValueDto> total_loan_count_vs_speedpost_physical_notice_status;
    private List<NameValueDto> total_physical_notices_count_vs_speedpost_physical_notice_status;
    private List<NameValueDto> total_physical_notices_vs_undelivered_reasons;
    private List<CountVsNoticeTypeDto> total_physical_notices_vs_notice_type;
}
