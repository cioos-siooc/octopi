/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.Language;
import ca.ogsl.octopi.services.LanguageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("languages")
@Api(tags = {"Language"})
@Produces(MediaType.APPLICATION_JSON)
public class LanguageResource {

  private LanguageService languageService = new LanguageService();

  @GET
  @ApiOperation(
      value = "Get all languages",
      response = Language.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listLanguages() throws AppException {
    List<Language> languageList = this.languageService.listLanguages();
    return Response.status(200).entity(languageList).build();
  }

  @GET
  @Path("{code}")
  @ApiOperation(
      value = "Find language by code",
      response = Language.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "language not found")
  })
  public Response getLanguageForId(
      @ApiParam(value = "Code of language to be fetched", required = true) @PathParam("code") String code
  )
      throws AppException {
    Language databaseLanguage = this.languageService.getLanguage(code);
    return Response.status(200).entity(databaseLanguage).build();
  }

}
