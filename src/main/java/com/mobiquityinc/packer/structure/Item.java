package com.mobiquityinc.packer.structure;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Represents item in knapsack
 */
@Data
@AllArgsConstructor
public class Item {
    private int id;
    private int weight;
    private double value;

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}
