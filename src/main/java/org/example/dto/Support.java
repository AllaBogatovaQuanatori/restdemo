package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(staticName = "of")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Support {
    @Builder.Default
    private String text = "To keep ReqRes free, contributions towards server costs are appreciated!";
    @Builder.Default
    private String url = "https://reqres.in/#support-heading";
}