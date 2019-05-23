package com.ibanity.apis.client.jsonapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class LinksApiModel {

    private String self;
    private String first;
    private String prev;
    private String next;
    private String related;
    private String redirect;
}