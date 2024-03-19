package com.epharmacy.app.dto.image;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImageDTO {
    private Long id;
    private String name;
    private String filePath;
    private Long productId;
    private Long prescriptionId;
}
