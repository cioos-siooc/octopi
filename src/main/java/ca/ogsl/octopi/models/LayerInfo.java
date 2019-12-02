/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "layer_info")
@ApiModel(
    value = "Layer Info",
    description = "Stores information about layers as label:value pairs"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LayerInfo {

  @Id
  @GenericGenerator(name = "IdOrGenerate", strategy = "ca.ogsl.octopi.dao.IdOrGenerateGenerator")
  @GeneratedValue(generator = "IdOrGenerate")
  @Column(name = "id")
  @Null(groups = PostCheck.class)
  @NotNull(groups = PutCheck.class)
  @ApiModelProperty(required = true)
  private Integer id;

  @Basic
  @Column(name = "label")
  @NotBlank
  @SafeHtml
  @ApiModelProperty(
      required = true,
      example = "DFO - Frequency of updates"
  )
  private String label;

  @Basic
  @Column(name = "value")
  @SafeHtml
  @ApiModelProperty(
      example = "3 min"
  )
  private String value;

  @Basic
  @Column(name = "url")
  @URL
  @SafeHtml
  @ApiModelProperty(
      value = "A URL for more information about the current LayerInfo entry."
  )
  private String url;

  @Basic
  @Column(name = "layer_id")
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "The ID of the layer described by the current LayerInfo entry."
  )
  private Integer layerId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Integer getLayerId() {
    return layerId;
  }

  public void setLayerId(Integer layerId) {
    this.layerId = layerId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LayerInfo layerInfo = (LayerInfo) o;
    return Objects.equals(id, layerInfo.id) &&
        Objects.equals(label, layerInfo.label) &&
        Objects.equals(value, layerInfo.value) &&
        Objects.equals(url, layerInfo.url) &&
        Objects.equals(layerId, layerInfo.layerId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, label, value, url, layerId);
  }
}
