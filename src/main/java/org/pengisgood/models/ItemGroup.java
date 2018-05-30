package org.pengisgood.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ItemGroup {
    private List<Item> items;

    private String groupName;

    private Float subtotal;
}
