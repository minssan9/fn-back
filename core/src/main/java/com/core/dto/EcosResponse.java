package com.core.dto;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class EcosResponse<T> {

    @SerializedName("StatisticTableList")
    private StatisticTableList statisticTableList;

    @Data
    public class StatisticTableList {
        @SerializedName("list_total_count")
        int list_total_count;
//        @SerializedName("row")
//        List<T> row = new ArrayList<T>();
        @SerializedName("row")
//        List<Map<String, Object>> row = new ArrayList<>();
        JsonArray row = new JsonArray();

        public List<T> getRows(){
            Gson gson = new Gson();
            Type typeMyType = new TypeToken<ArrayList<T>>(){}.getType();
            return gson.fromJson(gson.toJson(this.row.toString()), typeMyType);
        };
    }


}

