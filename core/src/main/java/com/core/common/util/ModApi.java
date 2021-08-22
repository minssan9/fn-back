package com.core.common.util;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"rawtypes","unchecked"})
@Component("modApi")
public class ModApi {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${holdings.api.host}")
    private String API_SERVER_HOST;

    private RestTemplate getRestTemplate() {
        HttpComponentsClientHttpRequestFactory factory  = new HttpComponentsClientHttpRequestFactory();
        factory.setConnectTimeout(10*60*1000); // 10분
        factory.setReadTimeout(10*60*1000); // 10분
        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        return restTemplate;
    }

    public Map fetchAPICall(Map paramMap, String mode) throws IOException {
        String urlPath = StringUtil.isNullToString(paramMap.get("urlPath"));
        Map rtnMap = new HashMap();
        try {
            String queryString = "";
            RestTemplate restTemplate = this.getRestTemplate();
            HttpHeaders headers = new HttpHeaders();
            /******************************************************************************
             * 헤더 추가정보 입력
             ******************************************************************************/
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

            if(mode.equals("PAGE")){
                String direction = StringUtil.isNullToString(paramMap.get("direction"));
                String page = StringUtil.isNullToString(paramMap.get("page"));
                String size = StringUtil.isNullToString(paramMap.get("size"));
                queryString = "?direction=" + direction + "&page=" + page + "&size=" + size;
            }else{
            	queryString = "";
            }

            URI url = URI.create(API_SERVER_HOST+urlPath+queryString);
            RequestEntity<String> rq = new RequestEntity<>(headers, HttpMethod.GET, url);
            log.debug("-----------------request-------------------");
            log.debug(rq.toString());
            log.debug("-----------------requestEnd----------------");
            /******************************************************************************
             * 호출
             ******************************************************************************/
            ResponseEntity<String> re = restTemplate.exchange(rq, String.class);

            if(mode.equals("PAGE")){
                JSONParser jsonParse = new JSONParser(); //JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
                JSONObject jsonObj = (JSONObject) jsonParse.parse(re.getBody());
                rtnMap.put("rtn", (Map) jsonObj);
            }else {
                JSONParser jsonParse = new JSONParser(); //JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
                if ("{".equals(re.getBody().substring(0,1))) {
                    JSONObject jsonObj = (JSONObject) jsonParse.parse(re.getBody());
                    rtnMap.put("rtn", (Map) jsonObj);
                } else {
                    JSONArray jsonArray = (JSONArray) jsonParse.parse(re.getBody());
                    rtnMap.put("rtn", (List) jsonArray);
                }
            }


            rtnMap.put("resultCode", "0");
            rtnMap.put("message", "");
            return rtnMap;

        } catch (HttpClientErrorException e) {
            log.error(e.getResponseBodyAsString());
            rtnMap.put("resultCode", "-999");
            rtnMap.put("message", e.getResponseBodyAsString());
            return rtnMap;
        } catch (HttpServerErrorException e) {
            log.error(e.getResponseBodyAsString());
            rtnMap.put("resultCode", "-999");
            rtnMap.put("message", e.getResponseBodyAsString());
            return rtnMap;
        } catch (Exception e) {
            log.error(e.toString());
            rtnMap.put("resultCode", "-999");
            rtnMap.put("message", e.getMessage());
            return rtnMap;
        }
    }

    public Map postAPICall(Map paramMap) throws IOException {
        String urlPath = StringUtil.isNullToString(paramMap.get("urlPath"));
        Map rtnMap = new HashMap();
        try {
            String queryString = "";
            RestTemplate restTemplate = this.getRestTemplate();

            HttpHeaders headers = new HttpHeaders();
            /******************************************************************************
             * 헤더 추가정보 입력
             ******************************************************************************/
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8");
            // headers.setContentType(MediaType.APPLICATION_JSON);

            queryString = "";

            URI url = URI.create(API_SERVER_HOST + urlPath + queryString);
            log.debug("-----------------request-------------------");
            log.debug(url.toString());
            HttpEntity param= new HttpEntity(paramMap.get("json"), headers);
            log.debug("-----------------param----------------");
            log.debug(paramMap.get("json").toString());
            log.debug("-----------------param----------------");
            /******************************************************************************
             * 호출
             ******************************************************************************/
            //String res = restTemplate.postForObject(url, param, String.class);
            String re = restTemplate.postForObject(url, param, String.class);
            log.debug("-----------------return----------------");
            log.debug(re.toString());
            log.debug("-----------------return----------------");
            log.debug("-----------------requestEnd----------------");

            JSONParser jsonParse = new JSONParser(); //JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
            if ("{".equals(re.substring(0,1))) {
                JSONObject jsonObj = (JSONObject) jsonParse.parse(re);
                rtnMap.put("rtnMap", (Map) jsonObj);
            } else {
                JSONArray jsonArray = (JSONArray) jsonParse.parse(re);
                rtnMap.put("rtnList", (List) jsonArray);
            }

            rtnMap.put("resultCode", "0");
            rtnMap.put("message", "");

            return rtnMap;

        } catch (HttpClientErrorException e) {
            log.error(e.toString());
            rtnMap.put("resultCode", "-999");
            rtnMap.put("message", e.getMessage());
            return rtnMap;
        } catch (HttpServerErrorException e) {
            log.error(e.getResponseBodyAsString());
            rtnMap.put("resultCode", "-999");
            rtnMap.put("message", e.getResponseBodyAsString());
            return rtnMap;
        } catch (Exception e) {
            log.error(e.toString());
            rtnMap.put("resultCode", "-999");
            rtnMap.put("message", e.getMessage());
            return rtnMap;
        }
    }


    public Map putAPICall(Map paramMap) throws IOException {
        String urlPath = StringUtil.isNullToString(paramMap.get("urlPath"));
        Map rtnMap = new HashMap();
        try {
            String queryString = "";
            RestTemplate restTemplate = this.getRestTemplate();

            HttpHeaders headers = new HttpHeaders();
            /******************************************************************************
             * 헤더 추가정보 입력
             ******************************************************************************/
            headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
            headers.add("Content-Type", MediaType.APPLICATION_JSON + ";charset=UTF-8");
            // headers.setContentType(MediaType.APPLICATION_JSON);

            queryString = "";

            URI url = URI.create(API_SERVER_HOST + urlPath + queryString);
            log.debug("-----------------request-------------------");
            log.debug(url.toString());
            HttpEntity param= new HttpEntity(paramMap.get("json"), headers);
            log.debug("-----------------param----------------");
            log.debug(paramMap.get("json").toString());
            log.debug("-----------------param----------------");
            /******************************************************************************
             * 호출
             ******************************************************************************/
            //String res = restTemplate.postForObject(url, param, String.class);
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, param, String.class);
            String re = responseEntity.getBody();
            log.debug("-----------------return----------------");
            log.debug(re.toString());
            log.debug("-----------------return----------------");
            log.debug("-----------------requestEnd----------------");

            JSONParser jsonParse = new JSONParser(); //JSONParse에 json데이터를 넣어 파싱한 다음 JSONObject로 변환한다.
            if ("{".equals(re.substring(0,1))) {
                JSONObject jsonObj = (JSONObject) jsonParse.parse(re);
                rtnMap.put("rtnMap", (Map) jsonObj);
            } else {
                JSONArray jsonArray = (JSONArray) jsonParse.parse(re);
                rtnMap.put("rtnList", (List) jsonArray);
            }

            rtnMap.put("resultCode", "0");
            rtnMap.put("message", "");

            return rtnMap;

        } catch (HttpClientErrorException e) {
            log.error(e.toString());
            rtnMap.put("resultCode", "-999");
            rtnMap.put("message", e.getMessage());
            return rtnMap;
        } catch (HttpServerErrorException e) {
            log.error(e.getResponseBodyAsString());
            rtnMap.put("resultCode", "-999");
            rtnMap.put("message", e.getResponseBodyAsString());
            return rtnMap;
        } catch (Exception e) {
            log.error(e.toString());
            rtnMap.put("resultCode", "-999");
            rtnMap.put("message", e.getMessage());
            return rtnMap;
        }
    }
}
