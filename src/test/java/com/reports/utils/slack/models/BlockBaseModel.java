package com.reports.utils.slack.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"type", "text", "fields"})
public class BlockBaseModel {
    @JsonProperty("type")
    private String type;

    @JsonProperty("elements")
    private List<Elements> elements;

    @JsonProperty("type")
    public String getType() {
        return this.type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public List<Elements> getElements() {
        return this.elements;
    }

    public void setElements(List<Elements> elements) {
        this.elements = elements;
    }
}
