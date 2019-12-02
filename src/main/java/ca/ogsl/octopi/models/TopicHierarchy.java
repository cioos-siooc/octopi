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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "topic")
@ApiModel(
    value = "Topic Hierarchy",
    description = "A top level grouping of categories, used to identify the root of the"
        + " hierarchy of categories. Useful for creating themes. Contains full hierarchy."
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicHierarchy {

  @Id
  @GenericGenerator(name = "IdOrGenerate", strategy = "ca.ogsl.octopi.dao.IdOrGenerateGenerator")
  @GeneratedValue(generator = "IdOrGenerate")
  @Null(groups = PostCheck.class)
  @NotNull(groups = PutCheck.class)
  @Column(name = "id")
  @ApiModelProperty(required = true)
  private Integer id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "root")
  @Valid
  @ApiModelProperty(
      required = true,
      value = "The ID of the root category identified by this topic."
  )
  private CategoryHierarchy root;
  @Basic
  @Column(name = "code")
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "A human readable code used to relate this topic to other topics. Commonly used to "
          + "link two copies of the same topic in different languages."
  )
  private String code;
  @ApiModelProperty(
      required = true,
      value = "The identifier of the language for this topic"
  )
  @NotNull
  @Column(name = "language_code")
  private String languageCode;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public CategoryHierarchy getRoot() {
    return root;
  }

  public void setRoot(CategoryHierarchy root) {
    this.root = root;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }
}
