package com.reports.utils.slack.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"type", "text"})
public class Elements {
    @JsonProperty("type")
    private String type;

    @JsonProperty("alt_text")
    private String alt_text;

    @JsonProperty("image_url")
    private String image_url;

    @JsonProperty("text")
    private String text;

    @JsonProperty("type")
    public String getType() {
        return this.type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public String getAlt_text() {
        return this.alt_text;
    }

    public void setAlt_text(String alt_text) {
        this.alt_text = alt_text;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
