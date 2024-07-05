package ua.eva.wims.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDto {
    @NotBlank
    @NotNull
    private String productName;
    @NotNull
    @NotBlank
    private String description;
    @NotNull
    @NotBlank
    private String producingCountry;
    @NotNull
    @Positive
    @Digits(integer=8, fraction=2)
    private Double price;
    @NotNull
    @Min(0)
    private Integer amount;
}
