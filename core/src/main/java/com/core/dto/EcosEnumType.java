package com.core.dto;

import lombok.Data;

@Data
public class EcosEnumType {

    public enum CycleType {
        YY("YY"),
        QQ("QQ"),
        MM("MM"),
        DD("DD"),
        HY("HY"),           // 이거 뭐야..?
        HM("HM"),           // 이거 뭐야..?
        EMPTY("");
        private  String cycle;

        CycleType(String cycle) {
            this.cycle=cycle;
        }
        public static CycleType parse(String name) {
            for (CycleType type : CycleType.values()) {
                if (type.cycle.equals(name)) {
                    return type;
                }
            }
            return null;
        }

        public String getCycle() {
            return cycle;
        }

        @Override
        public String toString() {
            return this.getCycle();
        }
    }
    public enum SearchFlag{
        Y,
        N;
    }
}
