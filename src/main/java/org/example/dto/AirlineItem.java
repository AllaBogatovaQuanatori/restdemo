package org.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(staticName = "of")
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AirlineItem {
    private String established;
    private String country;
    private String website;
    private String name;
    @JsonProperty("head_quaters")
    private String headQuaters;
    private String logo;
    private Long id;
    private String slogan;
}