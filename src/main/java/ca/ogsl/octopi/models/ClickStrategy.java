/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.models;

import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import org.hibernate.annotations.GenericGenerator;

@Entity
@ApiModel(
    value = "Click Strategy",
    discriminator = "type",
    description = "Abstract base type for all click strategies",
    subTypes = {WmsStrategy.class}
)
@Inheritance(strategy = InheritanceType.JOINED)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = WmsStrategy.class, name = "wms")}
)
@Table(name = "click_strategy")
public class ClickStrategy {

  @Id
  @GenericGenerator(name = "IdOrGenerate", strategy = "ca.ogsl.octopi.dao.IdOrGenerateGenerator")
  @GeneratedValue(generator = "IdOrGenerate")
  @Column(name = "id")
  @Null(groups = PostCheck.class)
  @NotNull(groups = PutCheck.class)
  @ApiModelProperty(required = true)
  private Integer id;

  @Column(name = "type")
  @Pattern(regexp = "wms")
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "Specifies the type of ClickStrategy",
      example = "wms",
      allowableValues = "wms"
  )
  private String type;
  @Column(name = "empty_validator_code")
  @NotNull
  @ApiModelProperty(
      required = true,
      value = "Specifies the code of the client handler validating whether the click payload"
          + " is empty",
      example = "wms-html",
      allowableValues = "wms-html"
  )
  private String emptyValidatorCode;

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

  public String getEmptyValidatorCode() {
    return emptyValidatorCode;
  }

  public void setEmptyValidatorCode(String emptyValidatorCode) {
    this.emptyValidatorCode = emptyValidatorCode;
  }
}
