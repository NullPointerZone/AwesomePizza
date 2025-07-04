package com.awesomepizzasrl.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pizza {
    @JsonProperty("type")
    private String type;
    @JsonProperty("quantity")
    private int quantity;
}
