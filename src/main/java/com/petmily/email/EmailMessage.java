package com.petmily.email;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class EmailMessage {

    private String to;
    private String subject;
    private String message;
}
