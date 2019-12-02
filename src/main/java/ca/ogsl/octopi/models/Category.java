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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@ApiModel(value = "Category", description = "Organizes layers into hierarchies for navigation")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Category {

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
  @Length(min = 1, max = 100)
  @ApiModelProperty(
      required = true,
      value = "A human readable label for the category",
      example = "Ocean Physics"
  )
  private String label;

  @Basic
  @Column(name = "type")
  @Pattern(regexp = "root|category|layer")
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "Discriminator specifying whether the category represents a root, a category, or a"
          + " layer",
      example = "category",
      allowableValues = "root,category,layer"
  )
  private String type;

  @Basic
  @Column(name = "layer_id")
  @ApiModelProperty(
      value = "The ID of the layer represented by this category. Only relevant for type: 'layer'"
  )
  private Integer layerId;

  @Basic
  @Column(name = "is_expanded")
  @ApiModelProperty(
      value = "Specifies whether or not the category should be expanded by default"
  )
  private Boolean isExpanded;

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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Integer getLayerId() {
    return layerId;
  }

  public void setLayerId(Integer layerId) {
    this.layerId = layerId;
  }

  @JsonProperty(value = "isExpanded")
  public Boolean isExpanded() {
    return isExpanded;
  }

  @JsonProperty(value = "isExpanded")
  public void setExpanded(Boolean expanded) {
    isExpanded = expanded;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Category category = (Category) o;
    return Objects.equals(id, category.id) &&
        Objects.equals(label, category.label) &&
        Objects.equals(type, category.type) &&
        Objects.equals(layerId, category.layerId) &&
        Objects.equals(isExpanded, category.isExpanded);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, label, type, layerId, isExpanded);
  }
}
