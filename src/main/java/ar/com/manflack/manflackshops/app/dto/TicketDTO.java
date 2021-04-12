package ar.com.manflack.manflackshops.app.dto;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class TicketDTO
{
    public int getId() {
        return id.incrementAndGet();
    }
    private static AtomicInteger id = new AtomicInteger(0);
    private List<ArticleDTO> articles;
    private Integer total;
}
