package com.core.ecos.domain;

import com.core.ecos.dto.EcosEnumType;
import com.google.gson.annotations.SerializedName;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Entity
@NoArgsConstructor
@Table(name = "ecosschemadetail")
@Data
public class EcosSchemaDetail {
        @Column(name = "id")
        @Id @GeneratedValue
        Long id;
        @Field(name = "statcode") @Column(name = "STAT_CODE") @SerializedName("STAT_CODE")        private  String statcode;
        @Field(name = "statname") @Column(name = "STAT_NAME") @SerializedName("STAT_NAME")        private  String statname;
        @Field(name = "grpcode") @Column(name = "GRP_CODE") @SerializedName("GRP_CODE")        private  String grpcode;
        @Field(name = "grpname") @Column(name = "GRP_NAME") @SerializedName("GRP_NAME")        private  String grpname;
        @Field(name = "cycle") @Column(name = "CYCLE") @SerializedName("CYCLE")
//        @Enumerated(EnumType.STRING)
        private EcosEnumType.CycleType cycle;
        @Field(name = "pitemcode") @Column(name = "P_ITEM_CODE") @SerializedName("P_ITEM_CODE")        private  String pitemcode;
        @Field(name = "itemcode") @Column(name = "ITEM_CODE") @SerializedName("ITEM_CODE")        private  String itemcode;
        @Field(name = "itemname") @Column(name = "ITEM_NAME") @SerializedName("ITEM_NAME")        private  String itemname;
        @Field(name = "starttime") @Column(name = "START_TIME") @SerializedName("START_TIME")        private  String starttime;
        @Field(name = "endtime") @Column(name = "END_TIME") @SerializedName("END_TIME")        private  String endtime;
        @Field(name = "datacnt") @Column(name = "DATA_CNT") @SerializedName("DATA_CNT")        private  int datacnt;

//        @ManyToOne(fetch = FetchType.LAZY)
//        @JoinColumn(name = "STAT_CODE", insertable = false, updatable = false)
//        EcosSchema ecosSchema;

        //        상위통계표코드	P_STAT_CODE	8	000Y074	상위통계표코드
//        통계표코드	STAT_CODE	8	000Y702
//        통계명	STAT_NAME	200	1.2.2 본원통화 구성내역(평잔)	통계명
//        주기	CYCLE	2	YY, QQ, MM, DD	주기(년, 분기, 월, 일)
//        검색가능여부	SRCH_YN	1	Y/N	검색가능여부
//        출처	ORG_NAME	50	국제통화기금(IMF)	출처
}
