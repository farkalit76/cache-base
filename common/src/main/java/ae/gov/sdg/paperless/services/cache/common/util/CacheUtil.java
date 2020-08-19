package ae.gov.sdg.paperless.services.cache.common.util;

import ae.gov.sdg.paperless.services.cache.common.models.*;
import ae.gov.sdg.paperless.services.common.exceptions.BaseResponse;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import static java.lang.String.valueOf;
import static java.util.Arrays.copyOf;

public class CacheUtil {

    public static ResponseEntity<CacheJsonResponse> populateResponse(JSONObject data, boolean isSuccess) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CacheJsonResponse response = new CacheJsonResponse();
        response.setSuccess(isSuccess);
        response.setData(data);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public static ResponseEntity<CacheJsonHashCollectionResponse> populateResponse(Map<String, JSONObject> data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CacheJsonHashCollectionResponse response = new CacheJsonHashCollectionResponse();
        response.setSuccess(false);
        if (!CollectionUtils.isEmpty(data)) {
            response.setSuccess(true);
            response.setData(data);
        }
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public static ResponseEntity<CacheHashKeysResponse> populateResponse(Set<String> data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CacheHashKeysResponse response = new CacheHashKeysResponse();
        response.setSuccess(false);
        if (!CollectionUtils.isEmpty(data)) {
            response.setSuccess(true);
            response.setData(data);
        }
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public static ResponseEntity<CacheJsonListResponse> populateListResponse(Collection<JSONObject> data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CacheJsonListResponse response = new CacheJsonListResponse();
        response.setSuccess(false);
        if (!CollectionUtils.isEmpty(data)) {
            response.setSuccess(true);
            response.setData(data.toArray(new JSONObject[data.size()]));
        }
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public static ResponseEntity<CacheStringListResponse> populateStringListResponse(Collection<String> data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CacheStringListResponse response = new CacheStringListResponse();
        response.setSuccess(false);
        if (!CollectionUtils.isEmpty(data)) {
            response.setSuccess(true);
            response.setData(data.toArray(new String[data.size()]));
        }
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public static ResponseEntity<CacheStringListResponse> populateStringArrayResponse(Object[] data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CacheStringListResponse response = new CacheStringListResponse();
        response.setSuccess(false);
        if (data != null && data.length != 0) {
            response.setSuccess(true);
            response.setData(copyOf(data, data.length, String[].class));
        }
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public static ResponseEntity<BaseResponse> populateSuccessResponse(boolean isSuccess) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        BaseResponse response = new BaseResponse();
        response.setSuccess(isSuccess);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public static ResponseEntity<CacheJsonResponse> populateResponse(Long numberOfRecords) {
        JSONObject data = new JSONObject();
        data.put("numberOfRecords", numberOfRecords);
        boolean success = false;
        if (numberOfRecords > 0) {
            success = true;
        }
        return populateResponse(data, success);
    }

    public static ResponseEntity<CacheStringResponse> populateStringResponse(Long numberOfRecords) {
        String data = valueOf(numberOfRecords);
        boolean success = false;
        if (numberOfRecords > 0) {
            success = true;
        }
        return populateStringResponse(data, success);
    }

    public static ResponseEntity<CacheStringResponse> populateStringResponse(String data, boolean success) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CacheStringResponse response = new CacheStringResponse();
        response.setSuccess(success);
        response.setData(data);
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    public static ResponseEntity<CacheStringHashCollectionResponse> populateStringResponse(Map<String, String> data) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        CacheStringHashCollectionResponse response = new CacheStringHashCollectionResponse();
        response.setSuccess(false);
        if (!CollectionUtils.isEmpty(data)) {
            response.setSuccess(true);
            response.setData(data);
        }
        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }
}