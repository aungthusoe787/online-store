package com.neurogine.store.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigInteger;


@Document(value = "store")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Store {

    @Id
    private BigInteger id;
    @TextIndexed
    private String name;
    private String description;
    @Indexed(unique = true)
    private String email;
    private String image;
    @TextIndexed
    private String category;
    private String address;
    private String geolocation;
}
