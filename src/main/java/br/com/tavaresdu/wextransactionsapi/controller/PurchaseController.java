package br.com.tavaresdu.wextransactionsapi.controller;

import br.com.tavaresdu.wextransactionsapi.model.Purchase;
import br.com.tavaresdu.wextransactionsapi.model.PurchaseInput;
import br.com.tavaresdu.wextransactionsapi.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService service;

    public PurchaseController(PurchaseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Purchase> savePurchaseTransaction(@RequestBody PurchaseInput purchaseInput) {
        Purchase purchase = service.savePurchase(purchaseInput);
        if (purchase == null) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().location(URI.create("/purchases/"+purchase.getId())).body(purchase);
    }
}
