package org.pengisgood.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ShoppingCart {
    private List<ItemGroup> itemGroups;
}
