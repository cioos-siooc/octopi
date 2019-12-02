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

@Entity
@Table(name = "layer_description")
@ApiModel(
    value = "Layer Description",
    description = "Human readable layer descriptions"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LayerDescription {

  @Id
  @GenericGenerator(name = "IdOrGenerate", strategy = "ca.ogsl.octopi.dao.IdOrGenerateGenerator")
  @GeneratedValue(generator = "IdOrGenerate")
  @Column(name = "id")
  @Null(groups = PostCheck.class)
  @NotNull(groups = PutCheck.class)
  @ApiModelProperty()
  private Integer id;

  @Basic
  @Column(name = "description")
  @NotBlank
  @SafeHtml
  @ApiModelProperty(
      required = true,
      value = "A human readable description for the layer"
  )
  private String description;

  @Basic
  @Column(name = "layer_id", unique = true)
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "The ID of the layer being described"
  )
  private Integer layerId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
    LayerDescription that = (LayerDescription) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(description, that.description) &&
        Objects.equals(layerId, that.layerId);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, description, layerId);
  }
}
