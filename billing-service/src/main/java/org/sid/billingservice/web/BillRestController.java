package org.sid.billingservice.web;

import org.sid.billingservice.entities.Bill;
import org.sid.billingservice.repositories.BillRepository;
import org.sid.billingservice.repositories.ProductItemRepository;
import org.sid.billingservice.service.CustomerRestClient;
import org.sid.billingservice.service.ProductRestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillRestController {
    private BillRepository billRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClient customerRestClient;
    private ProductRestClient productRestClient;

    public BillRestController(BillRepository billRepository, ProductItemRepository productItemRepository, CustomerRestClient customerRestClient, ProductRestClient productRestClient) {
        this.billRepository = billRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClient = customerRestClient;
        this.productRestClient = productRestClient;
    }

    @GetMapping("/fullBill/{id}")
    public Bill bill(@PathVariable Long id){
    Bill bill =billRepository.findById(id).get();
    bill.setCustomer(customerRestClient.findCustomerById(bill.getCustomerId()));
    bill.getProductItems().forEach(pi->{
        pi.setProduct(productRestClient.findProductById(pi.getProductId()));

    });
    return bill;
    }
}
