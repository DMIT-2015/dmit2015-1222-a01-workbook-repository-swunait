package dmit2015.model;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CanadianIncomeTaxManager {

    public List<String> fetchIncomeTaxData() {
        List<String> incomeTaxData = new ArrayList<>();
        try {
            Path csvPath = Path.of(
                    getClass()
                            .getClassLoader()
                            .getResource("data/CanadianPersonalIncomeTaxRates.csv").toURI());
            incomeTaxData = Files.readAllLines(csvPath);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
        return incomeTaxData;
    }
}
