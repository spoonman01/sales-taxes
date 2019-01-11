package com.spoonman01.salestaxes.service;

import com.spoonman01.salestaxes.pojo.PartialTax;
import com.spoonman01.salestaxes.repository.ExcludedTaxesDAO;
import lombok.extern.slf4j.Slf4j;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaxesService {

    private static final DecimalFormat format2decimals = new DecimalFormat("###.00", new DecimalFormatSymbols(Locale.US));
    private String importWord;
    private Double importPercentage;
    private Double basicPercentage;

    private ExcludedTaxesDAO excludedTaxesDAO;

    public TaxesService(ExcludedTaxesDAO excludedTaxesDAO,
                        @Value("${import.search.word}") String importWord,
                        @Value("${import.percentage}") Double importPercentage,
                        @Value("${basic.percentage}") Double basicPercentage) {
        this.excludedTaxesDAO = excludedTaxesDAO;
        this.importWord = importWord;
        this.importPercentage = importPercentage;
        this.basicPercentage = basicPercentage;
    }

    public String calculateTaxes(List<String> inputList) {
        Double taxed = 0.0, total = 0.0;
        List<PartialTax> outputList = inputList.stream()
                                                .map(this::calculatePartialTaxes)
                                                .collect(Collectors.toList());

        StringBuilder outputBuilder = new StringBuilder();
        for(PartialTax p : outputList){
            outputBuilder.append(p.getPartialOutput());
            outputBuilder.append("\n");
            taxed += p.getPartialTax();
            total += p.getPartialTotal();
        }

        outputBuilder.append("Sales Taxes: ");
        outputBuilder.append(format2decimals.format(DoubleRounder.round(taxed,2)));
        outputBuilder.append("\n");
        outputBuilder.append("Total: ");
        outputBuilder.append(format2decimals.format(DoubleRounder.round(total,2)));

        return outputBuilder.toString();
    }

    /**
     * Any formatting problem in a line will result in a failure
     * @param line
     * @return an output line and partial data that will be added to "taxed" and "total"
     */
    private PartialTax calculatePartialTaxes(String line) {
        Double lineTaxedAmount = 0.0, lineTotal, lineTaxedPercentage = 0.0;
        String outputLine;
        try {
            String[] lineSplit = line.split(" ");
            Integer quantity = Integer.parseInt(lineSplit[0]);
            Double price = Double.parseDouble(lineSplit[lineSplit.length-1]);

            //if the line does not match any word (e.g. book,chocolate,pills) in the database, add the basicPercentage
            if (!excludedTaxesDAO.existsByDescriptionContaining(line)){
                lineTaxedPercentage += basicPercentage;
            }

            //if the item is imported, add the importPercentage
            if (line.contains(importWord)) {
                lineTaxedPercentage += importPercentage;
            }

            if(lineTaxedPercentage > 0.0) {
                lineTaxedAmount = DoubleRounder.round((price * quantity * lineTaxedPercentage), 2);
            }
            lineTotal = DoubleRounder.round((price * quantity + lineTaxedAmount),2);

            lineSplit[lineSplit.length-1] = String.valueOf(lineTotal);
            outputLine = Arrays.stream(lineSplit).collect(Collectors.joining(" "));
            outputLine = outputLine.replace(" at",":");

            return  PartialTax.builder()
                    .partialTax(lineTaxedAmount)
                    .partialTotal(lineTotal)
                    .partialOutput(outputLine)
                    .build();

        } catch (PatternSyntaxException pe){
            throw new IllegalArgumentException("Input not formatted correctly");
        } catch (NumberFormatException ne){
            throw new IllegalArgumentException("Quantity not formatted correctly");
        }
    }

}
