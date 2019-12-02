/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@ApiModel(
    value = "Layer",
    discriminator = "type",
    description = "Abstract base type for all layers",
    subTypes = {WmsLayer.class, WfsLayer.class, GeojsonLayer.class, BingLayer.class}
)
@Inheritance(strategy = InheritanceType.JOINED)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = WmsLayer.class, name = "wms"),
    @JsonSubTypes.Type(value = GeojsonLayer.class, name = "geojson"),
    @JsonSubTypes.Type(value = BingLayer.class, name = "bing"),
    @JsonSubTypes.Type(value = WfsLayer.class, name = "wfs")}
)
public class Layer {

  @Id
  @GenericGenerator(name = "IdOrGenerate", strategy = "ca.ogsl.octopi.dao.IdOrGenerateGenerator")
  @GeneratedValue(generator = "IdOrGenerate")
  @Column(name = "id")
  @Null(groups = PostCheck.class)
  @NotNull(groups = PutCheck.class)
  @ApiModelProperty(required = true)
  private Integer id;

  @Column(name = "type")
  @Pattern(regexp = "wms|wfs|geojson|bing")
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "Specifies the type of layer",
      example = "wms",
      allowableValues = "wms,wfs,geojson,bing"
  )
  private String type;

  @Basic
  @Column(name = "z_index")
  @ApiModelProperty(
      name = "zIndex",
      value = "Specifies the visual level of the layer"
  )
  private Integer zIndex;

  @Basic
  @Column(name = "opacity")
  @ApiModelProperty(
      value = "Specifies the opacity of the layer"
  )
  private Double opacity;

  @Basic
  @Column(name = "title")
  @SafeHtml
  @ApiModelProperty(
      value = "A human readable, descriptive title for the layer"
  )
  private String title;

  @Basic
  @JsonProperty("isVisible")
  @Column(name = "is_visible")
  @NotNull
  private Boolean isVisible;

  @Basic
  @Column(name = "default_crs")
  @SafeHtml
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "The default CRS the layer is available in",
      example = "EPSG:4326"
  )
  private String defaultCrs;

  @Basic
  @Column(name = "url")
  @SafeHtml
  @URL
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "The URL where the layer can be accessed",
      example = "http://ec.oceanbrowser.net/emodnet/Python/web/wms"
  )
  private String url;

  @Basic
  @Column(name = "url_parameters")
  @ApiModelProperty(
      value = "A string representation of a JSON collection containing additional URL parameters "
          + "for the layer"
  )
  @JsonRawValue
  private String urlParameters;

  @Basic
  @Column(name = "language_code")
  @NotNull
  @SafeHtml
  @ApiModelProperty(
      required = true,
      value = "The identifier of the language for this layer"
  )
  private String languageCode;

  @Basic
  @Column(name = "code")
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "A human readable code used to relate this layer to other layers. Commonly used to "
          + "link two copies of the same layer in different languages"
  )
  private String code;
  @Basic
  @Column(name = "click_strategy_id")
  private Integer clickStrategyId;
  @Basic
  @Column(name = "click_formatter_id")
  private Integer clickFormatterId;

  @Basic
  @Column(name = "url_behaviors")
  @ApiModelProperty(
      value = "A string representation of a JSON collection containing the behaviors of the "
          + "dynamic url parameters"
  )
  @JsonRawValue
  private String urlBehaviors;
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getzIndex() {
    return zIndex;
  }

  public void setzIndex(Integer zIndex) {
    this.zIndex = zIndex;
  }

  public Double getOpacity() {
    return opacity;
  }

  public void setOpacity(Double opacity) {
    this.opacity = opacity;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @JsonProperty("isVisible")
  @ApiModelProperty(
      required = true,
      value = "Whether or not the layer should be visible"
  )
  public Boolean isVisible() {
    return isVisible;
  }

  public void setVisible(Boolean visible) {
    isVisible = visible;
  }

  public String getDefaultCrs() {
    return defaultCrs;
  }

  public void setDefaultCrs(String defaultCrs) {
    this.defaultCrs = defaultCrs;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getUrlParameters() {
    return urlParameters;
  }

  public void setUrlParameters(String urlParameters) {
    this.urlParameters = urlParameters;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageId) {
    this.languageCode = languageId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Integer getClickStrategyId() {
    return clickStrategyId;
  }

  public void setClickStrategyId(Integer clickStrategyId) {
    this.clickStrategyId = clickStrategyId;
  }

  public Integer getClickFormatterId() {
    return clickFormatterId;
  }

  public void setClickFormatterId(Integer clickFormatterId) {
    this.clickFormatterId = clickFormatterId;
  }

  public String getUrlBehaviors() {
    return urlBehaviors;
  }

  public void setUrlBehaviors(String urlBehaviors) {
    this.urlBehaviors = urlBehaviors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Layer layer = (Layer) o;
    return Objects.equals(id, layer.id) &&
        Objects.equals(type, layer.type) &&
        Objects.equals(zIndex, layer.zIndex) &&
        Objects.equals(opacity, layer.opacity) &&
        Objects.equals(title, layer.title) &&
        Objects.equals(isVisible, layer.isVisible) &&
        Objects.equals(defaultCrs, layer.defaultCrs) &&
        Objects.equals(url, layer.url) &&
        Objects.equals(urlParameters, layer.urlParameters) &&
        Objects.equals(urlBehaviors, layer.urlBehaviors) &&
        Objects.equals(code, layer.code) &&
        Objects.equals(clickStrategyId, layer.clickStrategyId) &&
        Objects.equals(clickFormatterId, layer.clickFormatterId) &&
        Objects.equals(languageCode, layer.languageCode);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, type, zIndex, opacity, title, isVisible, defaultCrs, url, urlParameters,
        urlBehaviors, languageCode, code, clickStrategyId, clickFormatterId);
  }
}
