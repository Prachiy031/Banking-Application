package org.create.bankingApplication.fund_transfer_service.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.create.bankingApplication.fund_transfer_service.model.TransactionStatus;
import org.create.bankingApplication.fund_transfer_service.model.TransferType;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FundTransfer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundTransferId;

    private String transactionReferenceId;
    
    private String fromAccount;      //Sender's account number

    private String toAccount;       //Receiver's account number

    private BigDecimal amount;      //amount to transfer

    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    private TransferType transferType;

    @CreationTimestamp
    private LocalDateTime transferredOn;
    
}
