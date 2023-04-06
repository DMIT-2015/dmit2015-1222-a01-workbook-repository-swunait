
package io.ipgeolocation.api;

import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.annotation.Generated;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;
import jakarta.json.bind.annotation.JsonbTransient;

@JsonbPropertyOrder({
    "name",
    "offset",
    "current_time",
    "current_time_unix",
    "is_dst",
    "dst_savings"
})
@Generated("jsonschema2pojo")
public class TimeZone {

    @JsonbProperty("name")
    private String name;
    @JsonbProperty("offset")
    private Integer offset;
    @JsonbProperty("current_time")
    private String currentTime;
    @JsonbProperty("current_time_unix")
    private Double currentTimeUnix;
    @JsonbProperty("is_dst")
    private Boolean isDst;
    @JsonbProperty("dst_savings")
    private Integer dstSavings;
    @JsonbTransient
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonbProperty("name")
    public String getName() {
        return name;
    }

    @JsonbProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonbProperty("offset")
    public Integer getOffset() {
        return offset;
    }

    @JsonbProperty("offset")
    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @JsonbProperty("current_time")
    public String getCurrentTime() {
        return currentTime;
    }

    @JsonbProperty("current_time")
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    @JsonbProperty("current_time_unix")
    public Double getCurrentTimeUnix() {
        return currentTimeUnix;
    }

    @JsonbProperty("current_time_unix")
    public void setCurrentTimeUnix(Double currentTimeUnix) {
        this.currentTimeUnix = currentTimeUnix;
    }

    @JsonbProperty("is_dst")
    public Boolean getIsDst() {
        return isDst;
    }

    @JsonbProperty("is_dst")
    public void setIsDst(Boolean isDst) {
        this.isDst = isDst;
    }

    @JsonbProperty("dst_savings")
    public Integer getDstSavings() {
        return dstSavings;
    }

    @JsonbProperty("dst_savings")
    public void setDstSavings(Integer dstSavings) {
        this.dstSavings = dstSavings;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
