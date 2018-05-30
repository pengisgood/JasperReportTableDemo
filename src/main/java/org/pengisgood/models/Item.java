package org.pengisgood.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String code;
    private String desc;
    private Float price;
    private Float discount;
}
