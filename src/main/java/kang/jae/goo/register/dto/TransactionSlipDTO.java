package kang.jae.goo.register.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionSlipDTO {
    @JsonProperty("seq")
    private Long seq;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("transaction_ok_date")
    private String transactionOkDate;
    @JsonProperty("comment")
    private String comment;

}
