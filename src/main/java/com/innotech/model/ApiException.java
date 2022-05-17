package com.innotech.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties
public class ApiException {
    @NonNull
    private Integer errorCode;
    @NonNull
    private String message;
    @NonNull
    private List<String> details;
}
