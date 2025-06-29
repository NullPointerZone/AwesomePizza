package com.awesomepizzasrl.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Pizza {
    @JsonProperty("type")
    private String type;
    @JsonProperty("quantity")
    private int quantity;
}
