package com.rest.request;

import com.rest.model.Address;
import lombok.Data;

@Data
public class OrderRequest {
    private long restaurantId;
    private Address deliveryAdress;
}
