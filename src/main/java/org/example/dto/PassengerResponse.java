package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor(staticName = "of")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PassengerResponse {
    private int trips;
    @JsonProperty("__v")
    @Builder.Default
    private int v = 0;
    private String name;
    @JsonProperty("_id")
    private String id;
    private List<AirlineItem> airline;
}