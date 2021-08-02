package com.core.ecos.domain;

import com.google.gson.annotations.SerializedName;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@Data
@Entity
@Table(name = "ecosdata")
public class EcosData {
        @Column(name = "id")
        @Id
        @GeneratedValue
        Long id;

        @Field("unitName")  @SerializedName( "UNIT_NAME")               @Column(name = "UNIT_NAME")         private String unitName;
        @Field("statName")  @SerializedName( "STAT_NAME")               @Column(name = "STAT_NAME")         private String statName;
        @Field("statCode")  @SerializedName( "STAT_CODE")               @Column(name = "STAT_CODE")         private String statCode;
        @Field("itemCode1")  @SerializedName( "ITEM_CODE1")              @Column(name = "ITEM_CODE1")         private String itemCode1;
        @Field("itemCode2")  @SerializedName( "ITEM_CODE2")              @Column(name = "ITEM_CODE2")         private String itemCode2;
        @Field("itemCode3")  @SerializedName( "ITEM_CODE3")              @Column(name = "ITEM_CODE3")         private String itemCode3;
        @Field("itemName1")  @SerializedName( "ITEM_NAME1")              @Column(name = "ITEM_NAME1")         private String itemName1;
        @Field("itemName2")  @SerializedName( "ITEM_NAME2")              @Column(name = "ITEM_NAME2")         private String itemName2;
        @Field("itemName3")  @SerializedName( "ITEM_NAME3")              @Column(name = "ITEM_NAME3")         private String itemName3;
        @Field("dataValue")  @SerializedName( "DATA_VALUE")              @Column(name = "DATA_VALUE")         private String dataValue;
        @Field("createdDate")  @SerializedName( "createdDate")        @Column(name = "createdDate")        private String  createdDate;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "P_STAT_CODE")
        private EcosSchema ecosSchemas;
}


//@Entity
//@Table(name = "krbankdata")
//class EcosMysqlData extends EcosData     {
//        @Column(name = "id")
//        @Id
//        @GeneratedValue
//        Long id;
//
//        @SerializedName("UNIT_NAME")  @Expose @Column(name = "UNIT_NAME")         String unitName;
//        @SerializedName("STAT_NAME")  @Expose @Column(name = "STAT_NAME")         String statName;
//        @SerializedName("STAT_CODE")  @Expose @Column(name = "STAT_CODE")         String statCode;
//        @SerializedName("ITEM_CODE1") @Expose @Column(name = "ITEM_CODE1")         String itemCode1;
//        @SerializedName("ITEM_CODE2") @Expose @Column(name = "ITEM_CODE2")         String itemCode2;
//        @SerializedName("ITEM_CODE3") @Expose @Column(name = "ITEM_CODE3")         String itemCode3;
//        @SerializedName("ITEM_NAME1") @Expose @Column(name = "ITEM_NAME1")         String itemName1;
//        @SerializedName("ITEM_NAME2") @Expose @Column(name = "ITEM_NAME2")         String itemName2;
//        @SerializedName("ITEM_NAME3") @Expose @Column(name = "ITEM_NAME3")         String itemName3;
//        @SerializedName("DATA_VALUE") @Expose @Column(name = "DATA_VALUE")         String dataValue;
//        @SerializedName("TIME")       @Expose @Column(name = "createdDate")        String  createdDate;
//}
