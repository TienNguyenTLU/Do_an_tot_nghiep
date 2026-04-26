package tlu.web.doan.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ProductDTO {
    private Long id;
    private String sku;
    private String name;
    private String slug;
    private BigDecimal sellingPrice;
    private BigDecimal costPrice;
    private Integer stockQuantity;
    private String mainImageUrl;
    private String shortDescription;
    private String detailedDescription;
    private String status;
    private Boolean deleted;
    private Map<String, Object> specifications;
    private Long categoryId;
    private Long brandId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
