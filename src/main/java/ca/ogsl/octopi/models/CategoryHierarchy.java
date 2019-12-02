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
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "category")
@ApiModel(
    value = "Category Hierarchy",
    description = "Organizes layers into hierarchies for navigation. Include full hierarchy."
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryHierarchy {

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

  @JoinTable(name = "category_child", joinColumns = {
      @JoinColumn(name = "parent_id", referencedColumnName = "id", nullable = false)}, inverseJoinColumns = {
      @JoinColumn(name = "child_id", referencedColumnName = "id", nullable = false)})
  @OrderBy("type ASC")
  @ManyToMany(fetch = FetchType.LAZY)
  @Valid
  @ApiModelProperty(
      value = "The nested hierarchy of categories within the current category"
  )
  private Collection<CategoryHierarchy> categories;

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

  public Collection<CategoryHierarchy> getCategories() {
    return categories;
  }

  public void setCategories(Collection<CategoryHierarchy> children) {
    this.categories = children;
  }
}
