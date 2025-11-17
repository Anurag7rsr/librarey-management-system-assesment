package com.Library.management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookRequest {

    @NotBlank(message = "Title required")
    private String title;

    @NotBlank(message = "Author required")
    private String author;

    @NotBlank(message = "ISBN required")
    private String isbn;

    @NotNull(message = "Price required")
    private BigDecimal price;
}