package ua.eva.wims.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private int id;
    @NotNull
    private String productName;
    @NotNull
    private String description;
    @NotNull
    private String producingCountry;
    @NotNull
    @Positive
    @Digits(integer=8, fraction=2)
    private Double price;
    @NotNull
    @Min(0)
    private Integer amount;
}
