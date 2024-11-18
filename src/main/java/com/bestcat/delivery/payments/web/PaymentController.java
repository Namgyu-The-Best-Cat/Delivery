package com.bestcat.delivery.payments.web;

import static com.bestcat.delivery.common.type.ResponseMessage.*;

import com.bestcat.delivery.common.dto.SuccessResponse;
import com.bestcat.delivery.payments.dto.PaymentRequest;
import com.bestcat.delivery.payments.dto.PaymentResponse;
import com.bestcat.delivery.payments.dto.UpdatePaymentStatusRequest;
import com.bestcat.delivery.payments.service.PaymentService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 생성
    @PostMapping
    public ResponseEntity<SuccessResponse<PaymentResponse>> createPayment(
            @RequestBody PaymentRequest request) {
        PaymentResponse response = paymentService.createPayment(request);
        return ResponseEntity.ok(SuccessResponse.of(PAYMENT_SUCCESS,response));
    }


    // 결제 내역 상세 조회
    @GetMapping("/{paymentId}")
    public ResponseEntity<SuccessResponse<PaymentResponse>> getPaymentDetail(
            @PathVariable UUID paymentId) {
        PaymentResponse paymentResponse = paymentService.getPaymentDetails(paymentId);
        return ResponseEntity.ok(SuccessResponse.of(PAYMENT_DETAIL_SUCCESS, paymentResponse));
    }

    // 결제 상태 변경
    @PostMapping("/{paymentId}/status")
    public ResponseEntity<SuccessResponse<PaymentResponse>> changePaymentStatus(
            @RequestBody UpdatePaymentStatusRequest request,
            @PathVariable UUID paymentId) {
        PaymentResponse paymentResponse = paymentService.updatePaymentStatus(paymentId, request);
        return ResponseEntity.ok(SuccessResponse.of(PAYMENT_STATUS_CHANGE_SUCCESS, paymentResponse));
    }


}
