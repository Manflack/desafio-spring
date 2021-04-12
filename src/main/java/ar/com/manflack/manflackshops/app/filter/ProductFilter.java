package ar.com.manflack.manflackshops.app.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductFilter
{
    private Integer productId;
    private String name;
    private String category;
    private String brand;
    private Integer price;
    private Boolean freeShipping;
    private String prestige;
    private Integer order;
}
