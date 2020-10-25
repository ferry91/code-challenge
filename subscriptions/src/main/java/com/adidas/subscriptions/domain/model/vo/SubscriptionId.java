package com.adidas.subscriptions.domain.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id"
})
public class SubscriptionId implements Serializable
{

    @JsonProperty("id")
    private UUID id;
    private final static long serialVersionUID = -5990995357630594959L;
    /**
     * No args constructor for use in serialization
     *
     */
    public SubscriptionId() {
    }

    /**
     * @param id
     */
    public SubscriptionId(UUID id) {
        super();
        this.id = id;
    }

    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(UUID id) {
        this.id = id;
    }
}