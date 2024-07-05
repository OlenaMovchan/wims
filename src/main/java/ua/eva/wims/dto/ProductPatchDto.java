package ua.eva.wims.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPatchDto {
    @Positive
    @Digits(integer=8, fraction=2)
    private Double price;
    @Min(0)
    private Integer amount;
}
