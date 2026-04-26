package tlu.web.doan.dto;

import lombok.Data;

@Data
public class ProductCategoryDTO {
    private Long id;
    private String name;
    private String slug;
    private Long parentId;
    private String imageUrl;
}