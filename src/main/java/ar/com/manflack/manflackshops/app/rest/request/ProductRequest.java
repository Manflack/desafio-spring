package ar.com.manflack.manflackshops.app.rest.request;

import java.util.List;

import ar.com.manflack.manflackshops.app.dto.ArticleDTO;
import lombok.Data;

@Data
public class ProductRequest
{
    private List<ArticleDTO> articles;
}
