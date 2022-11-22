package com.bootcamp.melifrescos.service;

import com.bootcamp.melifrescos.dto.WithdrawalOrderDTO;
import com.bootcamp.melifrescos.exceptions.NotFoundException;
import com.bootcamp.melifrescos.interfaces.IBuyerService;
import com.bootcamp.melifrescos.interfaces.IWarehouseService;
import com.bootcamp.melifrescos.interfaces.IWithdrawalService;
import com.bootcamp.melifrescos.model.Buyer;
import com.bootcamp.melifrescos.model.Warehouse;
import com.bootcamp.melifrescos.model.WithdrawalOrder;
import com.bootcamp.melifrescos.repository.IWithdrawalRepo;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class WithdrawalService implements IWithdrawalService {

    @Autowired
    private IWithdrawalRepo repo;

    @Autowired
    private IBuyerService buyerService;

    @Autowired
    private IWarehouseService warehouseService;

    private GeoApiContext context;

    public WithdrawalService(){
        context = com.bootcamp.melifrescos.util.GeoApiContext.getContext();
    }

    /**
     * Método responsável por converteer endereço em coordenadas
     * @param address endereço a ser convertido
     * @return retorna o endereço em coordenadas
     * @throws InterruptedException
     * @throws ApiException
     * @throws IOException
     */
    @Override
    public LatLng convertAddressToCoordinate(String address) throws InterruptedException, ApiException, IOException {
        GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();

        context.shutdown();

        return results[0].geometry.location;
    }

    /**
     * Método responsavel por converter as coordenadas de um endereço em um endereço
     * @param coordinate coordenadas a ser convertido
     * @return retorna o endereço das coordenadas
     * @throws IOException
     * @throws InterruptedException
     * @throws ApiException
     */
    @Override
    public String convertCoordinateToAddress(LatLng coordinate) throws IOException, InterruptedException, ApiException {
        GeocodingResult[] results =  GeocodingApi.reverseGeocode(context, coordinate).await();

        return results[0].formattedAddress;
    }

    /**
     * Método responsavel por criar uma ordem de retirada
     * @param orderDTO dto da ordem de retirada
     * @return a ordem de retirada criada
     */
    @Override
    public WithdrawalOrderDTO create(WithdrawalOrderDTO orderDTO){
        Warehouse warehouse = warehouseService.getById(orderDTO.getWarehouseId()).orElse(null);
        Buyer buyer = buyerService.getById(orderDTO.getBuyerId()).orElse(null);

        WithdrawalOrder withdrawalOrder = new WithdrawalOrder(orderDTO.getDate(), warehouse, buyer);

        WithdrawalOrder result = repo.save(withdrawalOrder);
        orderDTO.setId(result.getId());
        return orderDTO;
    }

    /**
     * Método responsavel por atualizar a ordem de retirada
     * @param withdrawalOrder ordem a ser atualizada
     * @return retorna a ordem atualizada
     */
    @Override
    public WithdrawalOrderDTO updateDate(WithdrawalOrderDTO withdrawalOrder){
        Optional<WithdrawalOrder> result = repo.findById(withdrawalOrder.getId());

        if (result.isEmpty()) {
            throw new NotFoundException("O peiddo de retirada de id: " + withdrawalOrder.getId() + " não existe");
        }

        result.get().setDate(withdrawalOrder.getDate());

        repo.save(result.get());

        return withdrawalOrder;
    }
}
