package com.spoonman01.salestaxes.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartialTax {
    private String partialOutput;
    private Double partialTax;
    private Double partialTotal;
}
