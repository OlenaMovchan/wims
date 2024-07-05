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
public class ProductFullDto {
    private int id;
    @NotNull
    private String productName;
    private String description;
    private String producingCountry;
    @NotNull
    @Digits(integer=8, fraction=2)
    @Positive
    private Double price;
    @NotNull
    @Min(0)
    private Integer amount;
}
