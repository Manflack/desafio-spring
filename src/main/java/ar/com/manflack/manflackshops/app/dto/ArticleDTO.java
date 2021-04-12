package ar.com.manflack.manflackshops.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO
{
    private Integer productId;
    private String name;
    private String brand;
    private Integer quantity;

    public ArticleDTO(ProductDTO productDTO)
    {
        this.productId = productDTO.getProductId();
        this.name = productDTO.getName();
        this.brand = productDTO.getBrand();
        this.quantity = productDTO.getQuantity();
    }
}
