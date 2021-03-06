package com.upgrad.HireWheels.controllers;

import com.upgrad.HireWheels.dto.VehicleDTO;
import com.upgrad.HireWheels.exceptions.advice.GlobalExceptionHandler;
import com.upgrad.HireWheels.responsemodel.CustomResponse;
import com.upgrad.HireWheels.service.RequestService;
import com.upgrad.HireWheels.validator.RequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class RequestController {

    @Autowired
    RequestService requestService;

    @Autowired
    RequestValidator requestValidator;

    private static final Logger logger = LoggerFactory.getLogger(com.upgrad.HireWheels.controllers.RequestController.class);


    @PostMapping("/vehicles")
    public ResponseEntity addVehicleRequest(@RequestBody VehicleDTO vehicleDTO){
        ResponseEntity responseEntity = null;
        try{
            requestValidator.validateAddVehicleRequest(vehicleDTO);
            requestService.addVehicleRequest(vehicleDTO);
            String message = null;
            if (vehicleDTO.getUserId() != 1){
                message = "Vehicle Added Successfully. Waiting for Admin to Approve.";
            } else {
                message = "Vehicle Added Successfully.";
            }
            CustomResponse response = new CustomResponse(new Date(), message, 200);
            responseEntity =  new ResponseEntity(response, HttpStatus.OK);
        } catch (GlobalExceptionHandler e){
            logger.error(e.getMessage());
        }
        return responseEntity;
    }
    }


