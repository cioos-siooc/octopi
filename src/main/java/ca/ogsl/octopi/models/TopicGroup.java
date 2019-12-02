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
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Entity
@Table(name = "topic_group", schema = "public", catalog = "octopi-dev")
@ApiModel(
    value = "Topic Group",
    description = "Groups together multiple topics."
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TopicGroup {

  @Id
  @GenericGenerator(name = "IdOrGenerate", strategy = "ca.ogsl.octopi.dao.IdOrGenerateGenerator")
  @GeneratedValue(generator = "IdOrGenerate")
  @Column(name = "id")
  @Null(groups = PostCheck.class)
  @NotNull(groups = PutCheck.class)
  @ApiModelProperty(required = true)
  private Integer id;

  @Basic
  @Column(name = "name")
  @SafeHtml
  @Length(min = 1, max = 75)
  @ApiModelProperty(
      value = "A human readable, descriptive name for the topic group"
  )
  private String name;

  @Basic
  @Column(name = "language_code")
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "The identifier of the language for this group."
  )
  private String languageCode;

  @ElementCollection
  @CollectionTable(name = "topic_group_member", joinColumns = @JoinColumn(name = "topic_group_id"))
  @Column(name = "topic_id")
  @ApiModelProperty(
      value = "A list of IDs for the topics contained within this group"
  )
  private Set<Integer> topicIds;
  @Basic
  @Column(name = "code")
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "A human readable code used to relate this layer to other layers. Commonly used to "
          + "link two copies of the same layer in different languages"
  )
  private String code;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLanguageCode() {
    return languageCode;
  }

  public void setLanguageCode(String languageId) {
    this.languageCode = languageId;
  }

  public Set<Integer> getTopicIds() {
    return topicIds;
  }

  public void setTopicIds(Set<Integer> topicIds) {
    this.topicIds = topicIds;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TopicGroup that = (TopicGroup) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(name, that.name) &&
        Objects.equals(topicIds, that.topicIds) &&
        Objects.equals(code, that.code) &&
        Objects.equals(languageCode, that.languageCode);
  }

  @Override
  public int hashCode() {

    return Objects.hash(id, name, languageCode, topicIds, code);
  }
}
