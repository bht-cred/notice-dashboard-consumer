package com.credgenics.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class DashboardPayloadDto {
    private String company_id;
    private String allocation_month;
    private String notice_type;
    private String physical_notice_type;
    private String allocated_loan_ids;
}
