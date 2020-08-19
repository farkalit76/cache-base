package ae.gov.sdg.paperless.services.cache.common.controller;

import ae.gov.sdg.paperless.services.cache.common.models.*;
import ae.gov.sdg.paperless.services.cache.provider.store.CacheService;
import ae.gov.sdg.paperless.services.common.exceptions.BaseResponse;
import ae.gov.sdg.paperless.services.common.exceptions.ErrorResponse;
import ae.gov.sdg.paperless.services.common.tracing.TraceLog;
import io.swagger.annotations.*;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static ae.gov.sdg.paperless.services.cache.common.util.CacheUtil.*;


@RestController
@RequestMapping("/api/cachestore/plaintext")
@Api(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class CacheStorePlainTextController {
    @Autowired
    @Qualifier("stringCacheService")
    private CacheService<String> cacheService;

    @ApiOperation(value = "Fetch the configured cache provider for plaintext",
            notes = "Fetch the configured cache provider for plaintext",
            tags = "cache-store-plain-text-controller",
            response = String.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/welcome")
    @TraceLog
    public String cacheProvider() {
        return "Welcome to " + cacheService.cacheProvider() + " plaintext cache";
    }

    @ApiOperation(value = "Persist the key value for plaintext",
            notes = "Persist the key value for plaintext",
            tags = "cache-store-plain-text-controller",
            response = BaseResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @PostMapping("/add")
    @TraceLog
    public ResponseEntity<BaseResponse> add(@ApiParam(value = "Cache input request.", required = true) @RequestBody CacheStringRequest request) {
        if (request.getTimeInMinutes() == null || request.getTimeInMinutes() == 0) {
            cacheService.add(request.getKey(), request.getValue());
        } else {
            cacheService.add(request.getKey(), request.getValue(), request.getTimeInMinutes());
        }
        return populateSuccessResponse(true);
    }

    @GetMapping("/metadata")
    @TraceLog
    public ResponseEntity<CacheJsonResponse> metadata(@ApiParam(value = "Get Meta data.", required = true, defaultValue = "user") @RequestParam String key) {
        JSONObject data = new JSONObject();
        data.put("metadata", cacheService.metadata(key));
        return populateResponse(data, true);
    }

    @GetMapping("/exists")
    @TraceLog
    public ResponseEntity<CacheJsonResponse> exists(@ApiParam(value = "Check Key exists.", required = true, defaultValue = "user") @RequestParam String key) {
        JSONObject data = new JSONObject();
        data.put("exists", cacheService.exists(key));
        return populateResponse(data, true);
    }

    @ApiOperation(value = "Return all keys based on pattern",
            notes = "Return all keys based on pattern",
            tags = "cache-store-plain-text-controller",
            response = CacheStringListResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })

    @GetMapping("/dbKeys")
    @TraceLog
    public ResponseEntity<CacheStringListResponse> allKeys(@ApiParam(value = "Pattern for fetching the data.", required = true, defaultValue = "") @RequestParam String pattern) {
        final Set<String> data = cacheService.dbKeys(pattern);
        return populateStringArrayResponse(data.toArray());
    }

    @ApiOperation(value = "Get value for the key for plaintext",
            notes = "Get value for the key for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @GetMapping
    @TraceLog
    public ResponseEntity<CacheStringResponse> get(@ApiParam(value = "Key for fetching the data.", required = true, defaultValue = "john") @RequestParam String key) {
        String data = cacheService.get(key);
        boolean success = false;
        if (data != null) {
            success = true;
        }
        return populateStringResponse(data, success);
    }

    @ApiOperation(value = "Delete the cache collection for plaintext",
            notes = "Delete the cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = BaseResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @DeleteMapping
    @TraceLog
    public ResponseEntity<BaseResponse> deleteCollection(@ApiParam(value = "Delete the key from cache.", required = true, defaultValue = "john") @RequestParam String keySpace) {
        boolean isSuccess = cacheService.delete(keySpace);
        return populateSuccessResponse(isSuccess);
    }

    @ApiOperation(value = "Persist the key value in cache collection for plaintext",
            notes = "Persist the key value in cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = BaseResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @PostMapping("/addkeyvalue")
    @TraceLog
    public ResponseEntity<BaseResponse> addKeyValue(@ApiParam(value = "Cache input request.", required = true) @RequestBody CacheStringHashRequest request) {
        if (request.getTimeInMinutes() == null || request.getTimeInMinutes() == 0) {
            cacheService.keyValuePut(request.getKeySpace(), request.getKey(), request.getValue());
        } else {
            cacheService.keyValuePut(request.getKeySpace(), request.getKey(), request.getValue(), request.getTimeInMinutes());
        }
        return populateSuccessResponse(true);
    }

    @ApiOperation(value = "Persist the list of key values in cache collection for plaintext",
            notes = "Persist the list of key values in cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = BaseResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @PostMapping("/addkeyvalues")
    @TraceLog
    public ResponseEntity<BaseResponse> addKeyValue(@ApiParam(value = "Cache input request.", required = true) @RequestBody CacheStringHashCollectionRequest request) {
        if (request.getTimeInMinutes() == null || request.getTimeInMinutes() == 0) {
            cacheService.keyValuePut(request.getKeySpace(), request.getCacheMap());
        } else {
            cacheService.keyValuePut(request.getKeySpace(), request.getCacheMap(), request.getTimeInMinutes());
        }
        return populateSuccessResponse(true);
    }

    @ApiOperation(value = "Fetch the value for the hashkey from the cache collection for plaintext",
            notes = "Fetch the value for the key from the cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/getkeyvalue")
    @TraceLog
    public ResponseEntity<CacheStringResponse> fetchKeyValue(@ApiParam(value = "Collection from which data has to be retrieved.", required = true, defaultValue = "users_otp") @RequestParam String keySpace, @ApiParam(value = "Key for which data has to be retrieved.", required = true, defaultValue = "john") @RequestParam String key) {
        String data = cacheService.keyValueGet(keySpace, key);
        boolean success = false;
        if (data != null) {
            success = true;
        }
        return populateStringResponse(data, success);
    }

    @ApiOperation(value = "Fetch the list of entries from the cache collection for plaintext",
            notes = "Fetch the list of entries from the cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringHashCollectionResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/entries")
    @TraceLog
    public ResponseEntity<CacheStringHashCollectionResponse> fetchEntries(@ApiParam(value = "Collection from which entries has to be retrieved.", required = true, defaultValue = "users_otp") @RequestParam String keySpace) {
        Map<String, String> data = cacheService.keyValueEntries(keySpace);
        return populateStringResponse(data);
    }

    @ApiOperation(value = "Fetch the list of hashkeys for the provided key from the cache collection for plaintext",
            notes = "Fetch the list of hashkeys for the provided key from the cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheHashKeysResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/keys")
    @TraceLog
    public ResponseEntity<CacheHashKeysResponse> getKeys(@ApiParam(value = "Collection from which keys has to be retrieved.", required = true, defaultValue = "users_otp") @RequestParam String keySpace) {
        Set<String> data = cacheService.keys(keySpace);
        return populateResponse(data);
    }

    @ApiOperation(value = "Delete the hashkey from cache collection for plaintext",
            notes = "Delete the hashkey from cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @DeleteMapping("/key")
    @TraceLog
    public ResponseEntity<CacheStringResponse> deleteKey(@ApiParam(value = "Collection from which key has to be deleted.", required = true, defaultValue = "users_otp") @RequestParam String keySpace, @ApiParam(value = "Key which has to be removed.", required = true, defaultValue = "john") @RequestParam String key) {
        Long numberOfRecords = cacheService.delete(keySpace, key);
        return populateStringResponse(numberOfRecords);
    }

    @ApiOperation(value = "Persist the key value in list collection for plaintext",
            notes = "Persist the key value in list collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @PostMapping("/queue/push")
    @TraceLog
    public ResponseEntity<BaseResponse> pushInQueue(@ApiParam(value = "Data to be persisted.", required = true) @RequestBody CacheStringListRequest request) {
        if (request.getTimeInMinutes() == null || request.getTimeInMinutes() == 0) {
            cacheService.pushInQueue(request.getKey(), request.getValue());
        } else {
            cacheService.pushInQueue(request.getKey(), request.getTimeInMinutes(), request.getValue());
        }
        return populateSuccessResponse(true);
    }

    @ApiOperation(value = "Pop the first value of the key from start of cache collection for plaintext",
            notes = "Pop the first value of the key from start of cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @DeleteMapping("/queue/popfromstart")
    @TraceLog
    public ResponseEntity<CacheStringResponse> popFromStart(@ApiParam(value = "Pop the value from start for the key of the cache list.", required = true, defaultValue = "technologies") @RequestParam String key) {
        String data = cacheService.popFromStart(key);
        return populateStringResponse(data, data != null);
    }

    @ApiOperation(value = "Pop the first value of the key from end of cache collection for plaintext",
            notes = "Pop the first value of the key from end of cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @DeleteMapping("/queue/popfromend")
    @TraceLog
    public ResponseEntity<CacheStringResponse> popFromEnd(@ApiParam(value = "Pop the value from end for the key of the cache list.", required = true, defaultValue = "technologies") @RequestParam String key) {
        String data = cacheService.popFromEnd(key);
        return populateStringResponse(data, data != null);
    }

    @ApiOperation(value = "Fetch the first value for the key from the cache collection from the specified position for plaintext",
            notes = "Fetch the first value for the key from the cache collection from the specified position for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/queue/fetchfromindex")
    @TraceLog
    public ResponseEntity<CacheStringResponse> fetchFromIndex(@ApiParam(value = "Fetch the value from the key of the cache list.", required = true, defaultValue = "technologies") @RequestParam String key, @ApiParam(value = "Index value from which data has to be fetched from the cache list.", required = true, defaultValue = "2") @RequestParam Integer index) {
        String data = cacheService.fetchFromIndex(key, index);
        return populateStringResponse(data, data != null);
    }

    @ApiOperation(value = "Fetch all values of the key from the cache collection for plaintext",
            notes = "Fetch all values of the key from the cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringListResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/queue/fetchall")
    @TraceLog
    public ResponseEntity<CacheStringListResponse> fetchAllFromQueue(@ApiParam(value = "Fetch the value from the key of the cache list.", required = true, defaultValue = "technologies") @RequestParam String key) {
        List<String> data = cacheService.fetchAllFromQueue(key);
        return populateStringListResponse(data);
    }

    @ApiOperation(value = "Delete the number of occurences of the value specified for the key from cache collection for plaintext",
            notes = "Delete the number of occurences of the value specified for the key from cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @DeleteMapping("/queue")
    @TraceLog
    public ResponseEntity<CacheStringResponse> delete(@ApiParam(value = "Remove the value from the key of the cache list.", required = true, defaultValue = "technologies") @RequestParam String key, @ApiParam(value = "Number of values to be removed.", required = true, defaultValue = "1") @RequestParam(required = false) Integer numberOfValues, @ApiParam(value = "The value which has to be removed from the cache list.", required = true, defaultValue = "java") @RequestBody String value) {
        Long numberOfRecords = cacheService.removeFromQueue(key, value, numberOfValues);
        return populateStringResponse(numberOfRecords);
    }

    @ApiOperation(value = "Persist the key value in cache collection for plaintext",
            notes = "Persist the key value in cache collection for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @PostMapping("/set")
    @TraceLog
    public ResponseEntity<CacheStringResponse> addToSet(@ApiParam(value = "Cache input request.", required = true) @RequestBody CacheStringListRequest request) {
        Long numberOfRecords;
        if (request.getTimeInMinutes() == null || request.getTimeInMinutes() == 0) {
            numberOfRecords = cacheService.addToSet(request.getKey(), request.getValue());
        } else {
            numberOfRecords = cacheService.addToSet(request.getKey(), request.getTimeInMinutes(), request.getValue());
        }
        return populateStringResponse(numberOfRecords);
    }

    @ApiOperation(value = "Remove the specified member from the set value stored at keyand return the number of removed elements for plaintext.",
            notes = "Remove the specified member from the set value stored at keyand return the number of removed elements for plaintext.",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @DeleteMapping("/set")
    @TraceLog
    public ResponseEntity<CacheStringResponse> removeFromSet(@ApiParam(value = "Cache value input for delete.", required = true) @RequestBody CacheStringListRequest request) {
        Long numberOfRecords = cacheService.removeFromSet(request.getKey(), request.getValue());
        return populateStringResponse(numberOfRecords);
    }

    @ApiOperation(value = "Remove and return value from set for the key for plaintext",
            notes = "Remove and return value from set for the key for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @DeleteMapping("/set/pop")
    @TraceLog
    public ResponseEntity<CacheStringResponse> popFromSet(@ApiParam(value = "Pop the value from the key of the cache set.", required = true, defaultValue = "countries") @RequestParam String key) {
        String data = cacheService.popFromSet(key);
        boolean success = false;
        if (data != null) {
            success = true;
        }
        return populateStringResponse(data, success);
    }

    @ApiOperation(value = "Get all elements of set for the key for plaintext",
            notes = "Get all elements of set for the key for plaintext",
            tags = "cache-store-plain-text-controller",
            response = CacheStringListResponse.class)
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request error received from the client", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Unauthorized. The client is not authorized to call this API", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/set")
    @TraceLog
    public ResponseEntity<CacheStringListResponse> fetchAllFromSet(@ApiParam(value = "Fetch the value from the key of the cache set.", required = true, defaultValue = "countries") @RequestParam String key) {
        Set<String> data = cacheService.fetchAllFromSet(key);
        return populateStringListResponse(data);
    }

}
