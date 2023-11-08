package br.com.tavaresdu.wextransactionsapi.service;

import br.com.tavaresdu.wextransactionsapi.model.Purchase;
import br.com.tavaresdu.wextransactionsapi.model.PurchaseInput;
import br.com.tavaresdu.wextransactionsapi.repository.PurchaseRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

    private final PurchaseRepository repository;

    public PurchaseService(PurchaseRepository repository) {
        this.repository = repository;
    }

    public Purchase savePurchase(PurchaseInput purchaseInput) {
        return repository.save(new Purchase(
                purchaseInput.getDescription(),
                purchaseInput.getTransactionDate(),
                purchaseInput.getAmount()
        ));
    }
}
