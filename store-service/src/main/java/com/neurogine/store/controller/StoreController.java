package com.neurogine.store.controller;


import com.neurogine.store.dto.StoreRequest;
import com.neurogine.store.dto.StoreResponse;
import com.neurogine.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.security.cert.Extension;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class StoreController {

    private final StoreService storeService;

    //create store
    @PostMapping
    public ResponseEntity<?> createStore(@RequestBody StoreRequest storeRequest){
        try{
            StoreResponse storeResponse = storeService.createStore(storeRequest);
            return new ResponseEntity<>(storeResponse,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //retrieve stories data
    @GetMapping
    public ResponseEntity<List<StoreResponse>> getAllStores(@RequestParam(required = false) String keyword){
        try {
            List<StoreResponse> storeResponses = storeService.searchStoreByName(keyword);
            if(storeResponses.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(storeResponses,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //retrieve store by keyword ( search name & category indexing) with Paging
    @GetMapping("/search")
    public ResponseEntity<Map<String,Object>> searchStoreByNameAndCategory(@RequestParam(name = "keyword",required = false) String keyword,
                                                              @RequestParam(name = "currentPage")  int currentPage,
                                                              @RequestParam(name = "pageSize")  int pageSize){
        try{
            log.info("page "+currentPage +" || size"+pageSize);
            Map<String,Object> storeResponses = storeService.searchStoreByNameAndCategory(keyword, currentPage,pageSize);

            if(storeResponses == null || storeResponses.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(storeResponses,HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //retrieve store by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getStoreById(@PathVariable("id") String Id){
        StoreResponse storeResponse = storeService.getStoreById(new BigInteger(Id));
        if(storeResponse != null){
            return new ResponseEntity<>(storeResponse,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Store Not Found",HttpStatus.NOT_FOUND);
        }
    }

    //update store by id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStore(@PathVariable("id")String Id,@RequestBody StoreRequest storeRequest){
        StoreResponse storeResponse = storeService.updateStore(Id,storeRequest);
        if(storeResponse !=null ){
            return new ResponseEntity<>(storeResponse,HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Store Not Found",HttpStatus.NOT_FOUND);
        }
    }

    //delete store by id
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteStoreById(@PathVariable("id")String Id){
        try{
             storeService.deleteStoreById(Id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //delete store by id
    @DeleteMapping
    public ResponseEntity<HttpStatus> deleteAll(){
        try{
            storeService.deleteStoreAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
