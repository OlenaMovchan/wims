package ua.eva.wims.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductCreateDto {

    @NotNull
    @NotBlank
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
