package com.core.common.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class RestDto {
    String uri;
    Map<Object, Object> valueMap = new HashMap<>();
}
