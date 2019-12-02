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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.hibernate.annotations.GenericGenerator;

@Entity
@ApiModel(description = "A top level grouping of categories, used to identify the root of the"
    + " hierarchy of categories. Useful for creating themes.")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Topic {

  @Id
  @GenericGenerator(name = "IdOrGenerate", strategy = "ca.ogsl.octopi.dao.IdOrGenerateGenerator")
  @GeneratedValue(generator = "IdOrGenerate")
  @Null(groups = PostCheck.class)
  @NotNull(groups = PutCheck.class)
  @Column(name = "id")
  @ApiModelProperty(required = true)
  private Integer id;
  private Integer root;
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
  @Basic
  @ApiModelProperty(
      required = true,
      value = "A human readable descriptive label for the topic"
  )
  @NotNull
  @Column(name = "label")
  private String label;
  @Basic
  @ApiModelProperty(
      required = true,
      value = "URL for an image representation of the topic"
  )
  @NotNull
  @Column(name = "image_url")
  private String imageUrl;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getRoot() {
    return root;
  }

  public void setRoot(Integer root) {
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

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Topic topic = (Topic) o;
    return Objects.equals(id, topic.id) &&
        Objects.equals(root, topic.root) &&
        Objects.equals(languageCode, topic.languageCode) &&
        Objects.equals(label, topic.label) &&
        Objects.equals(imageUrl, topic.imageUrl) &&
        Objects.equals(code, topic.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, root, code, languageCode, imageUrl);
  }
}
