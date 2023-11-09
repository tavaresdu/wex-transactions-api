package br.com.tavaresdu.wextransactionsapi.service;

import br.com.tavaresdu.wextransactionsapi.model.Purchase;
import br.com.tavaresdu.wextransactionsapi.model.PurchaseInput;
import br.com.tavaresdu.wextransactionsapi.repository.PurchaseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Mock
    private PurchaseRepository repository;

    @InjectMocks
    private PurchaseService service;

    @Captor
    private ArgumentCaptor<Purchase> purchaseCaptor;

    @Test
    void savePurchaseTest() {
        PurchaseInput input = new PurchaseInput();
        input.setDescription("Dinner");
        input.setAmount(BigDecimal.valueOf(50));
        input.setTransactionDate(LocalDate.of(2023, 11, 9));

        when(repository.save(any(Purchase.class))).then(i -> {
            Purchase p = new Purchase(
                    input.getDescription(),
                    input.getTransactionDate(),
                    input.getAmount()
            );
            p.setId(1L);
            return p;
        });

        Purchase result = service.savePurchase(input);

        assertEquals(input.getDescription(), result.getDescription());
        assertEquals(input.getAmount(), result.getAmount());
        assertEquals(input.getTransactionDate(), result.getTransactionDate());
        assertEquals(1L, result.getId());

        verify(repository).save(purchaseCaptor.capture());
        Purchase captured = purchaseCaptor.getValue();

        assertEquals(input.getDescription(), captured.getDescription());
        assertEquals(input.getAmount(), captured.getAmount());
        assertEquals(input.getTransactionDate(), captured.getTransactionDate());
    }
}