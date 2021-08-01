package com.core.dto;

import com.core.domain.EcosSchema;
import com.core.domain.EcosSchemaDetail;
import com.core.dto.EcosEnumType.CycleType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Data
@NoArgsConstructor
public class EcosDto {
    private String url = "ECOS_API_URL";
    private String serviceName = "";
    private String authKey = "ECOS_API_KEY";
    private String requestType = "json";
    private String language = "kr";
    private Long reqStartCount = 1L;
    private Long reqEndCount = 10000L;
    private String statisticCode = "";
    private CycleType period;
    private String queryStartDate = "";
    private String queryEndDate = "";
    private String option1 = "";
    private String option2 = "";
    private String option3 = "";


    private String stockCode;
    private CycleType cycleType;

    public EcosDto( String queryStartDate, String queryEndDate, CycleType period) {
        this.period = period;
        this.queryStartDate = queryStartDate;
        this.queryEndDate = queryEndDate;
    }
    public EcosDto(String statisticCode, String option1, String option2, String option3, String queryStartDate, String queryEndDate, CycleType period, Long reqStartCount, Long reqEndCount) {
        this.url = "";
        this.statisticCode = statisticCode;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.period = period;
        this.queryStartDate = queryStartDate;
        this.queryEndDate = queryEndDate;
        this.reqStartCount = reqStartCount;
        this.reqEndCount = reqEndCount;
    }

    public EcosDto(EcosSchema ecosSchema) {
        this.statisticCode = ecosSchema.getStatcode();
//        this.option1 = ecosSchema;
    }

    public EcosDto(EcosSchemaDetail ecosSchemaDetail) {
        this.statisticCode = ecosSchemaDetail.getStatcode();
        this.option1 = ecosSchemaDetail.getItemcode();
    }

    //    http://ecos.bok.or.kr/api/StatisticTableList/sample/xml/kr/1/10/
    //    http://ecos.bok.or.kr/api/StatisticSearch/sample/xml/kr/1/10/010Y002/MM/201101/201101/AAAA11/



    public MultiValueMap<String, String> toMultiValueMap(){

        MultiValueMap<String, String> ecosMVM = new LinkedMultiValueMap<>();

        ecosMVM.add("serviceName", this.serviceName);
        ecosMVM.add("authKey", this.authKey);
        ecosMVM.add("requestType", this.requestType);
        ecosMVM.add("language", this.language);
        ecosMVM.add("reqStartCount", this.reqStartCount.toString());
        ecosMVM.add("reqEndCount", this.reqEndCount.toString());
        ecosMVM.add("statisticCode", this.statisticCode);
        ecosMVM.add("period", this.period.name());
        ecosMVM.add("queryStartDate", this.queryStartDate);
        ecosMVM.add("queryEndDate", this.queryEndDate);
        ecosMVM.add("option1", this.option1);
        ecosMVM.add("option2", this.option2);
        ecosMVM.add("option3", this.option3);
        return ecosMVM;
    }
}
