/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@ApiModel(
    value = "WMS Strategy",
    description = "A child inheriting from Click Strategy with specialized attributes for WMS Strategy.",
    parent = ClickStrategy.class
)
@Table(name = "wms_strategy")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WmsStrategy extends ClickStrategy {

  @Basic
  @Column(name = "format")
  @ApiModelProperty(
      value = "A format string to be included when querying the WMS get feature info URL.",
      example = "application/json"
  )
  @NotNull
  private String format;
  @Basic
  @Column(name = "feature_count")
  @ApiModelProperty(
      value = "The number of features to retrieve",
      example = "5"
  )
  @NotNull
  private Integer featureCount;

  public String getFormat() {
    return format;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public Integer getFeatureCount() {
    return featureCount;
  }

  public void setFeatureCount(Integer featureCount) {
    this.featureCount = featureCount;
  }
}
