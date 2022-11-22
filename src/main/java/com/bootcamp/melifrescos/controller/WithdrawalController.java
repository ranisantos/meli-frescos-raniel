package com.bootcamp.melifrescos.controller;

import com.bootcamp.melifrescos.dto.WithdrawalOrderDTO;
import com.bootcamp.melifrescos.interfaces.IWithdrawalService;
import com.bootcamp.melifrescos.model.WithdrawalOrder;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.LatLng;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/fresh-products/withdrawal")
public class WithdrawalController {

    @Autowired
    private IWithdrawalService service;

    @GetMapping("/coordinate")
    public ResponseEntity<LatLng> getAddresConvert(@RequestParam String address) throws IOException, InterruptedException, ApiException {
        return  new ResponseEntity<>(service.convertAddressToCoordinate(address), HttpStatus.OK);
    }

    @GetMapping("/address")
    public ResponseEntity<String> getCoodinateConvert(@RequestParam double lat, double lng) throws IOException, InterruptedException, ApiException {
        LatLng coodinate = new LatLng(lat, lng);
        return  new ResponseEntity<>(service.convertCoordinateToAddress(coodinate), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WithdrawalOrderDTO> create(@RequestBody WithdrawalOrderDTO withdrawalOrder){
        return new ResponseEntity<>(service.create(withdrawalOrder), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<WithdrawalOrderDTO> updateDate(@RequestBody WithdrawalOrderDTO withdrawalOrder){
        return new ResponseEntity<>(service.updateDate(withdrawalOrder), HttpStatus.OK);
    }

    @GetMapping("/check-distance")
    public ResponseEntity<DistanceMatrix> checkWarehouseDistance(@RequestParam String address) throws IOException, InterruptedException, ApiException {
        return new ResponseEntity<>(service.checkDistanceWarehouse(address), HttpStatus.OK);
    }
}
