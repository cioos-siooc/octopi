/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "click_formatter")
@ApiModel(
    value = "Click Formatter",
    description = "The information necessary for formatting the payload of data"
        + " produced by a click on the map."
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClickFormatter {

  @Id
  @GenericGenerator(name = "IdOrGenerate", strategy = "ca.ogsl.octopi.dao.IdOrGenerateGenerator")
  @GeneratedValue(generator = "IdOrGenerate")
  @Column(name = "id")
  @Null(groups = PostCheck.class)
  @NotNull(groups = PutCheck.class)
  @ApiModelProperty(
      required = true
  )
  private Integer id;
  @Basic
  @ApiModelProperty(
      value = "The type of formatter to use on the client side"
  )
  private String type;
  @Basic
  @Column(name = "formatter_def")
  @ApiModelProperty(
      value = "JSON object containing the information to format the payload"
  )
  @JsonRawValue
  private String formatterDef;

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

  public String getFormatterDef() {
    return formatterDef;
  }

  public void setFormatterDef(String formatterDef) {
    this.formatterDef = formatterDef;
  }
}

