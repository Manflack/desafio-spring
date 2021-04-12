package ar.com.manflack.manflackshops.app.dto;

public class ProductDTOFixture
{
    public static final Integer productId = 1;
    public static final String name = "name";
    public static final String category = "category";
    public static final String brand = "brand";
    public static final Integer price = 100;
    public static final Integer quantity = 10;
    public static final Boolean freeShipping = true;
    public static final String prestige = "*****";

    public static ProductDTO withDefaults1()
    {
        return new ProductDTO(productId, name, category, brand, price, quantity, freeShipping, prestige);
    }

    public static ProductDTO withDefaults2()
    {
        return new ProductDTO(2, "Shoes", "Clothes", "Adidas", 200, 5, true, prestige);
    }

    public static ProductDTO withDefaults3()
    {
        return new ProductDTO(3, "CPU", "Electronics", "Intel", 2000, 10, true, prestige);
    }

    public static ProductDTO withDefaults4()
    {
        return new ProductDTO(4, "Motherboard", "Electronics", "ASUS", 1000, 1, true, "****");
    }
}
