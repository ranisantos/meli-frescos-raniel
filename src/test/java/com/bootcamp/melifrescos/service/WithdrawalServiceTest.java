package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.WithdrawalOrderDTO;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.IBuyerService;
import com.bootcamp.melifrescos.interfaces.IWarehouseService;
import com.bootcamp.melifrescos.model.Buyer;
import com.bootcamp.melifrescos.model.Warehouse;
import com.bootcamp.melifrescos.model.WithdrawalOrder;
import com.bootcamp.melifrescos.repository.IWithdrawalRepo;
import com.google.maps.GeoApiContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WithdrawalServiceTest {
    @InjectMocks
    WithdrawalService service;

    @Mock
    IBuyerService buyerService;

    @Mock
    IWarehouseService warehouseService;

    @Mock
    IWithdrawalRepo repo;

    @Mock
    GeoApiContext context;

    private Buyer buyer;
    private Warehouse warehouse;
    private WithdrawalOrderDTO withdrawalOrderDTO;
    private WithdrawalOrder withdrawalOrder;

    @BeforeEach
    public void Setup() {
        buyer = new Buyer(1L, "Raniel", "124123123123", "Rua 1, jardim morumbi", "email@email.com",null, null);
        warehouse = new Warehouse(1L, "Armazem 01", "Rua 01", null, null, null);
        withdrawalOrderDTO = new WithdrawalOrderDTO(1L, LocalDateTime.now(), 1L, 1L);
        withdrawalOrder = new WithdrawalOrder(1L, LocalDateTime.now(), buyer, warehouse);
    }

    @Test
    public void create_givenAValidWithdrawalDTO_returnsWithdrawalDTO() {
        when(buyerService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(buyer));
        when(warehouseService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(warehouse));

        when(repo.save(ArgumentMatchers.any()))
                .thenReturn(withdrawalOrder);

        WithdrawalOrderDTO result = service.create(withdrawalOrderDTO);

        assertThat(result).isEqualTo(withdrawalOrderDTO);
        assertThat(result.getId()).isPositive();
    }

    @Test
    public void create_givenAInvalidBuyerId_returnsNotFoundException() {
        when(warehouseService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(warehouse));
        when(buyerService.getById(ArgumentMatchers.anyLong()))
                .thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,() ->  {
            service.create(withdrawalOrderDTO);
        });
    }

    @Test
    public void create_givenAInvalidWareHouseId_returnsNotFoundException() {
        when(warehouseService.getById(ArgumentMatchers.anyLong()))
                .thenThrow(NotFoundException.class);

        assertThrows(NotFoundException.class,() ->  {
            service.create(withdrawalOrderDTO);
        });
    }

    @Test
    public void updateDate_givenAValidWithdrawalId_returnsWithdrawalDTO() {
        when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(withdrawalOrder));

        when(repo.save(ArgumentMatchers.any()))
                .thenReturn(withdrawalOrder);

        WithdrawalOrderDTO result = service.updateDate(withdrawalOrderDTO);

        assertThat(result).isEqualTo(withdrawalOrderDTO);
        assertThat(result.getId()).isPositive();
    }

    @Test
    public void updateDate_givenAInvalidWithdrawalId_returnsNotFoundException() {
        when(repo.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,() ->  {
            service.updateDate(withdrawalOrderDTO);
        });
    }

}
