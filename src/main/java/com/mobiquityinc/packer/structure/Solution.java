package com.mobiquityinc.packer.structure;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * Represents the solution of knapsack packing task.
 */
@AllArgsConstructor
@Data
public class Solution {
    private static final String COMMA = ",";
    private static final String DASH = "-";
    public List<Item> items;
    public double value;

    @Override
    public String toString() {
        if (!items.isEmpty()) {
            return items.stream().map(Item::toString).collect(joining(COMMA));
        }
        return DASH;
    }
}
