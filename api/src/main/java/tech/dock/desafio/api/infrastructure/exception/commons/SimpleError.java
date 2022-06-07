package tech.dock.desafio.api.infrastructure.exception.commons;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleError {
    private String message;
    private String code;
    private String traceId;
    private Map<String, String> messageArgs = new HashMap<>();

}

