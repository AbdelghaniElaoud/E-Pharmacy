package com.epharmacy.app.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Setter
@Getter
@Builder
public class ResponseDTO {
    private Object content;
    private List<String> errors;
    private boolean ok;
}
