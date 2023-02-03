package dmit2015.model;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CanadianIncomeTaxManager {

    // Define a private constructor to implement Single pattern
    private CanadianIncomeTaxManager() {
        loadDataFromFile();
    }

    // Define a single instance of this class
    private static CanadianIncomeTaxManager INSTANCE;

    // Define a static class-level to access the singleton
    public static CanadianIncomeTaxManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CanadianIncomeTaxManager();
        }
        return INSTANCE;
    }

    @Getter
    private List<CanadianPersonalIncomeTaxRate> incomeTaxRates;

    public int[] availableTaxYears() {
        return incomeTaxRates.stream()
                .map(item -> item.getTaxYear())
                .distinct()
                .sorted(Comparator.reverseOrder())
                .mapToInt(item -> item)
                .toArray();
    }
    public void loadDataFromFile() {
        try {
            try (var reader = new BufferedReader(new InputStreamReader(
                    getClass().getResourceAsStream("/data/csv/CanadianPersonalIncomeTaxRates.csv")))) {
                // Skip the first line as it contains headers
                reader.readLine();
                String line;
                incomeTaxRates = new ArrayList<>();
                while ( (line = reader.readLine()) != null) {
                    var optionalCanadianPersonalIncomeTaxRate = CanadianPersonalIncomeTaxRate.parseCsv(line);
                    if (optionalCanadianPersonalIncomeTaxRate.isPresent()) {
                        CanadianPersonalIncomeTaxRate currentCanadianPersonalIncomeTaxRate
                                = optionalCanadianPersonalIncomeTaxRate.orElseThrow();
                        incomeTaxRates.add(currentCanadianPersonalIncomeTaxRate);
                    }
                }

            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
