package com.awesomepizzasrl.platform.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PizzaVariantRequest {

    @JsonProperty("type")
    private String type;
}
