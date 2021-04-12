package ar.com.manflack.manflackshops.domain;

import java.util.stream.Stream;

import ar.com.manflack.manflackshops.app.dto.ProductDTO;
import ar.com.manflack.manflackshops.app.filter.ProductFilter;
import ar.com.manflack.manflackshops.domain.exception.ManyFiltersException;
import ar.com.manflack.manflackshops.domain.exception.NotImplementedFilterOrder;
import org.springframework.stereotype.Component;

@Component
public class UtilitiesComponent
{
    public Stream<ProductDTO> filterProducts(ProductFilter filter, Stream<ProductDTO> filteredArray)
            throws ManyFiltersException, NotImplementedFilterOrder
    {
        if (countParams(filter) > 2)
            throw new ManyFiltersException();

        if (filter.getProductId() != null)
            filteredArray = filteredArray.filter(product -> product.getProductId().equals(filter.getProductId()));

        if (filter.getName() != null)
            filteredArray = filteredArray.filter(product -> product.getName().equals(filter.getName()));

        if (filter.getCategory() != null)
            filteredArray = filteredArray.filter(product -> product.getCategory().equals(filter.getCategory()));

        if (filter.getBrand() != null)
            filteredArray = filteredArray.filter(product -> product.getBrand().equals(filter.getBrand()));

        if (filter.getPrice() != null)
            filteredArray = filteredArray.filter(product -> product.getPrice().equals(filter.getPrice()));

        if (filter.getFreeShipping() != null)
            filteredArray = filteredArray.filter(product -> product.getFreeShipping().equals(filter.getFreeShipping()));

        if (filter.getPrestige() != null)
            filteredArray = filteredArray.filter(product -> product.getPrestige().equals(filter.getPrestige()));

        if (filter.getOrder() != null)
            filteredArray = filterByOrder(filter, filteredArray);

        return filteredArray;
    }

    public Stream<ProductDTO> filterByOrder(ProductFilter filter, Stream<ProductDTO> filteredArray)
            throws NotImplementedFilterOrder
    {
        switch (filter.getOrder())
        {
            case 0:
                filteredArray = filteredArray.sorted((o1, o2) -> o1.getName().compareTo(o2.getName()));
                break;
            case 1:
                filteredArray = filteredArray.sorted((o1, o2) -> o2.getName().compareTo(o1.getName()));
                break;
            case 2:
                filteredArray = filteredArray.sorted((o1, o2) -> o2.getPrice() - o1.getPrice());
                break;
            case 3:
                filteredArray = filteredArray.sorted((o1, o2) -> o1.getPrice() - o2.getPrice());
                break;
            default:
                throw new NotImplementedFilterOrder();
        }
        return filteredArray;
    }

    public int countParams(ProductFilter filter)
    {
        int countParams = 0;
        if (filter.getProductId() != null)
            countParams++;

        if (filter.getName() != null)
            countParams++;

        if (filter.getCategory() != null)
            countParams++;

        if (filter.getBrand() != null)
            countParams++;

        if (filter.getPrice() != null)
            countParams++;

        if (filter.getFreeShipping() != null)
            countParams++;

        if (filter.getPrestige() != null)
            countParams++;

        return countParams;
    }

    public ProductFilter getProductFilter(Integer productId, String name, String category, String brand, String price,
            String freeShipping, String prestige, String order)
    {
        return new ProductFilter(productId,
                name,
                category,
                brand,
                price != null ? (Integer.valueOf(price.replace("$", "").replace(".", ""))) : null,
                freeShipping != null
                        ? Boolean.valueOf(freeShipping.toUpperCase().replace("SI", "true").replace("NO", "false"))
                        : null,
                prestige,
                order != null ? Integer.valueOf(order) : null);
    }
}
