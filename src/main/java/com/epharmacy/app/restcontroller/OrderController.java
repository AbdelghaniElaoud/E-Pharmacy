package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.order.OrderDTO;
import com.epharmacy.app.dto.prescription.PrescriptionDTO;
import com.epharmacy.app.dto.response.ResponseDTO;
import com.epharmacy.app.mappers.OrderMapper1;
import com.epharmacy.app.mappers.OrderMapper2;
import com.epharmacy.app.mappers.PrescriptionMapper;
import com.epharmacy.app.model.Order;
import com.epharmacy.app.model.Prescription;
import com.epharmacy.app.repository.OrderRepository;
import com.epharmacy.app.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RestController
@RequestMapping("/api/orders")
@Slf4j
public class OrderController {
    private final OrderRepository orderRepository;

    private final OrderService orderService;

    public OrderController(OrderService orderService,
                           OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping("/{cartId}/place-order")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseDTO placeOrder(@PathVariable Long cartId){
        return orderService.placeOrder(cartId);
    }
    @PutMapping("/{orderId}/update-status")
    @PreAuthorize("hasRole('PHARMACIST') or hasRole('DELIVERY_MAN')")
    public ResponseEntity<HttpStatus> changeOrderStatus(@PathVariable Long orderId, @RequestBody String status){
        orderService.updateStatus(orderId,status);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{deliveryManId}/all-orders/delivery")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PHARMACIST') or hasRole('DELIVERY_MAN')")
    public List<OrderDTO> getAllOrdersByDeliveryManId(@PathVariable Long deliveryManId){
        return orderService.getOrdersForDelivery(deliveryManId);
    }



    @GetMapping("{pharmacistId}/all-orders/pharmacist")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PHARMACIST') or hasRole('DELIVERY_MAN')")
    public List<OrderDTO> getAllOrdersByPharmacistId(@PathVariable Long pharmacistId){
        return orderService.getOrdersByPharmacist(pharmacistId);
    }

    @GetMapping("{customerId}/all-canceled-orders")
    @PreAuthorize("hasRole('CUSTOMER') or hasRole('PHARMACIST')")
    public List<OrderDTO> getAllCanceledOrders(@PathVariable Long customerId){
        return orderService.getAllCanceledOrders(customerId);
    }

    @GetMapping("/{orderId}/prescriptions")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PHARMACIST')")
    public ResponseEntity<List<PrescriptionDTO>> getPrescriptionsByOrderId(@PathVariable Long orderId) {
        Set<Prescription> prescriptions = orderService.getPrescriptionsByOrderId(orderId);

        List<PrescriptionDTO> prescriptionDTOs = new ArrayList<>();
        for (Prescription prescription : prescriptions) {
            PrescriptionDTO dto = PrescriptionMapper.INSTANCE.toDTO(prescription);
            prescriptionDTOs.add(dto);
        }

        return ResponseEntity.ok(prescriptionDTOs);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PHARMACIST')")
    public ResponseDTO getOrderById(@PathVariable Long orderId){
        return orderRepository.findById(orderId)
                .map(order -> ResponseDTO.builder().ok(true).content(OrderMapper1.toOrderDTO(order)).build())
                .orElseGet(() -> ResponseDTO.builder().ok(false).content("There is no order with the id " + orderId).build());
    }

    @PutMapping("/{orderId}/confirm-order")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PHARMACIST')")
    public ResponseDTO confirm(@PathVariable Long orderId){
        orderService.updateStatus(orderId,"CONFIRMED");
        return orderRepository.findById(orderId)
                .map(order -> ResponseDTO.builder().ok(true).content(OrderMapper2.toOrderDTO(order)).build())
                .orElseGet(() -> ResponseDTO.builder().ok(false).content("There is no order with the id " + orderId).build());

    }


    @PutMapping("/{orderId}/cancel-order")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PHARMACIST')")
    public void cancel(@PathVariable Long orderId){
        orderService.updateStatus(orderId,"CANCELED");
    }

    @PutMapping("/{orderId}/issue")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PHARMACIST')")
    public void issue(@PathVariable Long orderId){
        orderService.updateStatus(orderId,"ISSUE");
    }

    @PutMapping("/{orderId}/delivered")
    @PreAuthorize("hasRole('DELIVERY_MAN')")
    public void delivered(@PathVariable Long orderId){
        orderService.delivered(orderId);
    }

    @PutMapping("/{orderId}/issue-delivery")
    @PreAuthorize("hasRole('DELIVERY_MAN')")
    public void issueInDelivery(@PathVariable Long orderId){
        orderService.issue(orderId);
    }



}
