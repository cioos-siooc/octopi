/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@ApiModel(
    value = "WFS layer",
    description = "A child inheriting from Layer with specialized attributes for WFS.",
    parent = Layer.class
)
@Table(name = "wfs_layer")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WfsLayer extends Layer {

  @Basic
  @Column(name = "identifier")
  @NotBlank
  @ApiModelProperty(
      required = true,
      value = "A layer identifier to be included when querying the WFS URL.",
      example = "emodnet:route_ap_temp_7d"
  )
  private String identifier;

  @Basic
  @Column(name = "named_style")
  @ApiModelProperty(
      value = "A named style to be included when querying the WFS URL."
  )
  private String namedStyle;

  @Basic
  @Column(name = "crs")
  @ApiModelProperty(
      value = "The name of the CRS which should be used for the layer.",
      example = "urn:ogc:def:crs:EPSG::4326"
  )
  private String crs;

  @Basic
  @Column(name = "version")
  @ApiModelProperty(
      value = "The version number to be included when querying the WFS URL."
  )
  private String version;

  public String getIdentifier() {
    return identifier;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public String getNamedStyle() {
    return namedStyle;
  }

  public void setNamedStyle(String namedStyle) {
    this.namedStyle = namedStyle;
  }

  public String getCrs() {
    return crs;
  }

  public void setCrs(String crs) {
    this.crs = crs;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WfsLayer wfsLayer = (WfsLayer) o;
    return Objects.equals(identifier, wfsLayer.identifier) &&
        Objects.equals(namedStyle, wfsLayer.namedStyle) &&
        Objects.equals(crs, wfsLayer.crs) &&
        Objects.equals(version, wfsLayer.version);
  }

  @Override
  public int hashCode() {

    return Objects.hash(identifier, namedStyle, crs, version);
  }
}
