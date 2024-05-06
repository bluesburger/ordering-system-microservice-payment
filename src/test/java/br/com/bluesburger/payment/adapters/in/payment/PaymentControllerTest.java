package br.com.bluesburger.payment.adapters.in.payment;

import br.com.bluesburger.payment.adapters.in.payment.dto.PaymentRequest;
import br.com.bluesburger.payment.ports.PaymentPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static br.com.bluesburger.payment.mock.PaymentRequestMock.getPaymentRequestMock;
import static br.com.bluesburger.payment.mock.PaymentResponseMock.getPaymentMock;
import static br.com.bluesburger.payment.mock.PaymentResponseMock.getPaymentResponseMock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentPort paymentPort;

    @Test
    void testMakePayment_Success() throws Exception {
        final var paymentRequest = getPaymentRequestMock();
        final var payment = getPaymentMock();
        final var paymentResponse = getPaymentResponseMock();
        when(paymentPort.processPayment(any())).thenReturn(payment);

        MvcResult result = mockMvc.perform(post("/api/v1/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paymentRequest)))
                .andReturn();

        MockHttpServletResponse responseMvc = result.getResponse();
        verify(paymentPort, times(1)).processPayment(any());
        assertEquals(HttpStatus.OK.value(), responseMvc.getStatus());
        assertEquals(asJsonString(paymentResponse), result.getResponse().getContentAsString());
    }

    @Test
    void testMakePayment_MissingRequestBody() throws Exception {
        mockMvc.perform(post("/api/v1/payment").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(paymentPort, never()).processPayment(any());
    }

    @Test
    void testMakePayment_ExceptionDuringProcessing() throws Exception {
        final var paymentRequest = PaymentRequest.builder().build();

        ServletException exception = assertThrows(ServletException.class, () ->
                mockMvc.perform(post("/api/v1/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(paymentRequest))));

        assertTrue(exception.getCause() instanceof NullPointerException);
    }


    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
