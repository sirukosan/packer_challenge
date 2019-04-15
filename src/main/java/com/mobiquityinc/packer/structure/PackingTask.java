package com.mobiquityinc.packer.structure;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one knapsack packing task.
 */
@Data
@AllArgsConstructor
public class PackingTask {
    private int capacity;
    private List<Item> items;

    /**
     * Solving packing task using Dynamic Programming approach.
     * In case of multiple solutions choosing the one with less total items weight.
     *
     * @return Solution object - solution of packing task
     */
    public Solution solve() {
        int itemNumber = items.size();
        double[][] dpArr = new double[itemNumber + 1][capacity + 1];
        for (int i = 1; i <= itemNumber; i++) {
            for (int j = 0; j <= capacity; j++) {
                if (items.get(i - 1).getWeight() > j)
                    dpArr[i][j] = dpArr[i - 1][j];
                else
                    dpArr[i][j] = Math.max(dpArr[i - 1][j], dpArr[i - 1][j - items.get(i - 1).getWeight()]
                            + items.get(i - 1).getValue());
            }
        }

        double res = dpArr[itemNumber][capacity];
        int w = capacity;
        List<Item> itemsSolution = new ArrayList<>();
        for (int i = itemNumber; i > 0 && res > 0; i--) {
            while (res == dpArr[i][w - 1] && w >= 0) {
                res = dpArr[i][w - 1];
                w = w - 1;
            }
            if (res != dpArr[i - 1][w]) {
                itemsSolution.add(0, items.get(i - 1));
                res -= items.get(i - 1).getValue();
                w -= items.get(i - 1).getWeight();
            }
        }

        return new Solution(itemsSolution, dpArr[itemNumber][capacity]);
    }
}
