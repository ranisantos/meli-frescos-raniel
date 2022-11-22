package com.bootcamp.melifrescos.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawalOrderDTO {
    private Long id;

    @NotNull(message = "OA data de retirada é obrigatório.")
    private LocalDateTime date;

    @NotNull(message = "O ID do comprador é obrigatório.")
    private Long buyerId;

    @NotNull(message = "ID do armazém é obrigatório.")
    private Long warehouseId;
}
