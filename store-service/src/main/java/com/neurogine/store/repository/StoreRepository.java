package com.neurogine.store.repository;

import com.neurogine.store.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface StoreRepository extends MongoRepository<Store, BigInteger> {
    Page<Store> findAllBy(TextCriteria textCriteria, Pageable pageable);
    Page<Store> findByNameContainingIgnoreCase(String name,Pageable pageable);
    List<Store> findByNameContainingIgnoreCase(String name);
}
