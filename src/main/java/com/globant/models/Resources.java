package com.globant.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resources {
    private String name;
    private String trademark;
    private int stock;
    private float price;
    private String description;
    private boolean tags;
    private String id;
}
