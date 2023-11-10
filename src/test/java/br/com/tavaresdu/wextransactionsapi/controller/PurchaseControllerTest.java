package br.com.tavaresdu.wextransactionsapi.controller;

import br.com.tavaresdu.wextransactionsapi.model.Purchase;
import br.com.tavaresdu.wextransactionsapi.model.PurchaseInput;
import br.com.tavaresdu.wextransactionsapi.service.PurchaseService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PurchaseControllerTest {

    @MockBean
    private PurchaseService service;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    private PurchaseInput input;

    @Autowired
    public PurchaseControllerTest(MockMvc mockMvc, ObjectMapper mapper) {
        this.mockMvc = mockMvc;
        this.mapper = mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static Stream<Arguments> getPurchaseInputTestCases() {
        return Stream.of(
                Arguments.of(new PurchaseInput(null, LocalDate.now(), BigDecimal.valueOf(123.45))),
                Arguments.of(new PurchaseInput("Test", null, BigDecimal.valueOf(123.45))),
                Arguments.of(new PurchaseInput("Test", LocalDate.now(), null))

        );
    }

    @BeforeEach
    void setup() {
        this.input = new PurchaseInput();
        this.input.setDescription("Dinner");
        this.input.setAmount(BigDecimal.valueOf(50.23));
        this.input.setTransactionDate(LocalDate.of(2023, 11, 9));

        when(service.savePurchase(any(PurchaseInput.class))).then(i -> {
            PurchaseInput input = i.getArgument(0);
            Purchase purchase = new Purchase(
                    input.getDescription(),
                    input.getTransactionDate(),
                    input.getAmount()
            );
            purchase.setId(1L);
            return purchase;
        });
    }

    @Test
    void savePurchaseSuccessfulTest() throws Exception {
        mockMvc.perform(post("/purchases")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.description").value("Dinner"))
                .andExpect(jsonPath("$.transactionDate").value("2023-11-09"))
                .andExpect(jsonPath("$.amount").value(50.23));
    }

    @Test
    void savePurchaseNullReturnTest() throws Exception {
        when(service.savePurchase(any(PurchaseInput.class))).thenReturn(null);

        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().is5xxServerError());
    }

    @ParameterizedTest
    @MethodSource("getPurchaseInputTestCases")
    void savePurchaseWithInputErrorTest(PurchaseInput input) throws Exception {
        mockMvc.perform(post("/purchases")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(input)))
                .andExpect(status().is4xxClientError());
    }
}