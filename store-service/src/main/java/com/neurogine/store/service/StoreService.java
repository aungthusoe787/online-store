package com.neurogine.store.service;

import com.neurogine.store.dto.StoreRequest;
import com.neurogine.store.dto.StoreResponse;
import com.neurogine.store.model.Store;
import com.neurogine.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreResponse createStore(StoreRequest storeRequest){
        Store store = Store.builder()
                .name(storeRequest.name())
                .description(storeRequest.description())
                .email(storeRequest.email())
                .image(storeRequest.image())
                .category(storeRequest.category())
                .address(storeRequest.address())
                .geolocation(storeRequest.geolocation()).build();
        storeRepository.save(store);
        log.info("Store save successfully");
        return new StoreResponse(store.getId().toString(),store.getName(),store.getDescription(),store.getEmail(),store.getImage(),store.getCategory(),store.getAddress(),store.getGeolocation());
    }

    public List<StoreResponse> getAllStores(){
        List<StoreResponse> storeResponses = storeRepository.findAll()
                .stream()
                .map(store -> new StoreResponse(store.getId().toString(),store.getName(),store.getDescription(),store.getEmail(),store.getImage(),store.getCategory(),store.getAddress(),store.getGeolocation()))
                .toList();

        return storeResponses;
    }

    public Map<String,Object> searchStoreByNameAndCategory(String keyword, Integer page, Integer size) throws Exception {
        try{
            //default sorting by name
            Sort sort= Sort.by("name");
            Pageable paging = PageRequest.of(page, size,sort);

            if(keyword.isEmpty() || keyword == null){
                Page<Store>  pageStores = storeRepository.findAll(paging);
                return this.getStoreMap(pageStores);
            }else{
                log.info("full text search keyword -> "+keyword);
                TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingAny(keyword);
                Page<Store> pageStores = storeRepository.findAllBy(textCriteria,paging);
                return this.getStoreMap(pageStores);
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public List<StoreResponse> searchStoreByName(String name){
        if(name.isEmpty() || name == null){
            return storeRepository.findAll()
                    .stream()
                    .map(store -> new StoreResponse(store.getId().toString(),store.getName(),store.getDescription(),store.getEmail(),store.getImage(),store.getCategory(),store.getAddress(),store.getGeolocation()))
                    .toList();
        }else {
            return storeRepository.findByNameContainingIgnoreCase(name)
                    .stream()
                    .map(store -> new StoreResponse(store.getId().toString(), store.getName(), store.getDescription(), store.getEmail(), store.getImage(), store.getCategory(), store.getAddress(), store.getGeolocation()))
                    .toList();
        }
    }


    public Map<String,Object> searchStoreByName(String name, Integer currentPage, Integer pageSize) throws Exception {
        try{
            //default sorting by name
            Sort sort= Sort.by("name");
            Pageable paging = PageRequest.of(currentPage, pageSize,sort);

            if(name.isEmpty() || name == null){
                Page<Store>  pageStores = storeRepository.findAll(paging);
                return this.getStoreMap(pageStores);
            }else{
                Page<Store> pageStores = storeRepository.findByNameContainingIgnoreCase(name,paging);
                return this.getStoreMap(pageStores);
            }
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }

    private Map<String,Object> getStoreMap(Page<Store> pageStores){

        List<StoreResponse> storeResponses = pageStores.getContent().stream()
                .map(store -> new StoreResponse(store.getId().toString(),store.getName(),store.getDescription(),store.getEmail(),store.getImage(),store.getCategory(),store.getAddress(),store.getGeolocation()))
                .toList();

        Map<String,Object> storesMap = new HashMap<>();
        storesMap.put("stores",storeResponses);
        storesMap.put("currentPage",pageStores.getNumber());
        storesMap.put("totalItems", pageStores.getTotalElements());
        storesMap.put("totalPages", pageStores.getTotalPages());

        return storesMap;
    }

    public StoreResponse getStoreById(BigInteger Id){
        Optional<Store> store = storeRepository.findById(Id);
        if(store.isPresent()){
            return new StoreResponse(store.get().getId().toString(),store.get().getName(),store.get().getDescription(),store.get().getEmail(),store.get().getImage(),store.get().getCategory(),store.get().getAddress(),store.get().getGeolocation());
        }

        return null;
    }


    public StoreResponse updateStore(String Id,StoreRequest request){
        Optional<Store> storeResponse = storeRepository.findById(new BigInteger(Id));

        if(storeResponse.isPresent()){
            Store store = storeResponse.get();
            store.setName(request.name());
            store.setDescription(request.description());
            store.setEmail(request.email());
            store.setImage(request.image());
            store.setCategory(request.category());
            store.setAddress(request.address());
            store.setGeolocation(request.geolocation());

            storeRepository.save(store);
            log.info("Store update successfully");
            return new StoreResponse(store.getId().toString(),store.getName(),store.getDescription(),store.getEmail(),store.getImage(),store.getCategory(),store.getAddress(),store.getGeolocation());
        }
        return null;
    }

    public void deleteStoreById(String Id){
        storeRepository.deleteById(new BigInteger(Id));
    }

    public void deleteStoreAll(){
        storeRepository.deleteAll();
    }
}
