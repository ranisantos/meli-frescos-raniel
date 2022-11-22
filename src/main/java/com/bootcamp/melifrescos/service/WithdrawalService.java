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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class WithdrawalService implements IWithdrawalService {

    @Autowired
    private IWithdrawalRepo repo;

    @Autowired
    private IBuyerService buyerService;

    @Autowired
    private IWarehouseService warehouseService;

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
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCTxzdD9SK7KvrwvTK1nt1n9HGT0-E91-I")
                .build();

        GeocodingResult[] results =  GeocodingApi.geocode(context, address).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        System.out.println(gson.toJson(results[0].geometry.location));

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
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyCTxzdD9SK7KvrwvTK1nt1n9HGT0-E91-I")
                .build();

        GeocodingResult[] results =  GeocodingApi.reverseGeocode(context, coordinate).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        context.shutdown();

        return gson.toJson(results[0].formattedAddress);
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
        WithdrawalOrder result = repo.findById(withdrawalOrder.getId()).orElse(null);

        if (withdrawalOrder == null) {
            throw new NotFoundException("O peiddo de retirada de id: " + withdrawalOrder.getId() + " não existe");
        }

        result.setDate(withdrawalOrder.getDate());

        repo.save(result);

        return withdrawalOrder;
    }
}
