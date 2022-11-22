package com.bootcamp.melifrescos.interfaces;

import com.bootcamp.melifrescos.dto.WithdrawalOrderDTO;
import com.bootcamp.melifrescos.model.WithdrawalOrder;
import com.google.maps.errors.ApiException;
import com.google.maps.model.LatLng;

import java.io.IOException;

public interface IWithdrawalService {
    LatLng convertAddressToCoordinate(String address) throws InterruptedException, ApiException, IOException;

    String convertCoordinateToAddress(LatLng coordinate) throws IOException, InterruptedException, ApiException;

    WithdrawalOrderDTO create(WithdrawalOrderDTO withdrawalOrder);

    WithdrawalOrderDTO updateDate(WithdrawalOrderDTO withdrawalOrder);
}
