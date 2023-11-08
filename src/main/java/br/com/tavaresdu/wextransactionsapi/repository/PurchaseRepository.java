package br.com.tavaresdu.wextransactionsapi.repository;

import br.com.tavaresdu.wextransactionsapi.model.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
}
