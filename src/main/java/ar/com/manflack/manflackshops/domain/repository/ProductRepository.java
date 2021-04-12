package ar.com.manflack.manflackshops.domain.repository;

import ar.com.manflack.manflackshops.app.dto.ProductDTO;
import ar.com.manflack.manflackshops.domain.exception.ArticlesConflictException;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@PropertySource("classpath:/application.properties")
public class ProductRepository
{
    private static final List<ProductDTO> storageData = new ArrayList<>();

    @Value("${database.directory:dbProducts.csv}")
    private String PATH_DATABASE;

    @PostConstruct
    private void setup() throws Exception
    {
        List<String[]> localData = readDatabase();

        for (int i = 1; i < localData.size(); i++)
        {
            String[] data = localData.get(i);

            Integer productId = Integer.valueOf(data[0]);
            String name = data[1];
            String category = data[2];
            String brand = data[3];
            Integer price = Integer.valueOf(data[4].replace("$", "").replace(".", ""));
            Integer quantity = Integer.valueOf(data[5]);
            Boolean freeShipping = Boolean.valueOf(data[6]);
            String prestige = data[7];

            storageData.add(new ProductDTO(productId, name, category, brand, price, quantity, freeShipping, prestige));
        }
    }

    public List<ProductDTO> getAll()
    {
        return storageData;
    }

    public ProductDTO getByProductIdAndNameAndBrandAndQuantity(Integer productId, String name, String brand)
            throws ArticlesConflictException
    {
        List<ProductDTO> productsFiltered = storageData.stream()
                .filter(product -> product.getProductId().equals(productId) && product.getName().equals(name)
                        && product.getBrand().equals(brand))
                .collect(Collectors.toList());

        if (productsFiltered.isEmpty())
            return null;
        else if (productsFiltered.size() > 1)
            throw new ArticlesConflictException();

        return productsFiltered.get(0);

    }

    private List<String[]> readDatabase() throws Exception
    {
        File fileReader = ResourceUtils.getFile(PATH_DATABASE);
        CSVReader csvReader = new CSVReader(new FileReader(fileReader));
        List<String[]> list = csvReader.readAll();
        csvReader.close();
        return list;
    }
}
