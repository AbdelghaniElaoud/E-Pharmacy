package com.epharmacy.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImageRequestDTO {
    private String name;
    private String filePath;
    private Long productId;
    private Long prescriptionId;
}
