package com.ibanity.apis.client.jsonapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Map;
import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class DataApiModel {

    private UUID id;
    private Map<String, String> attributes;
    private LinksApiModel links;
    private String type;
}
