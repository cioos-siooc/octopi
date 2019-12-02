/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.ClickStrategy;
import ca.ogsl.octopi.services.ClickStrategyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("click-strategies")
@Api(tags = {"Click Strategy"})
@Produces(MediaType.APPLICATION_JSON)
public class ClickStrategyResource {

  private ClickStrategyService clickStrategyService = new ClickStrategyService();

  @GET
  @ApiOperation(
      value = "Get all click strategies",
      response = ClickStrategy.class,
      responseContainer = "List",
      notes = "Returns a list objects with the base type: ClickStrategy. Each click strategy "
          + "will be a child type (such as WmsStrategy)"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listClickStrategies() throws AppException {
    List<ClickStrategy> clickStrategies = this.clickStrategyService.listClickStrategies();
    return Response.status(200).entity(clickStrategies).build();
  }

  @GET
  @Path("{id}")
  @ApiOperation(
      value = "Find Click Strategy by ID",
      response = ClickStrategy.class,
      notes = "Returns an object of base type ClickStrategy. The Click Strategy"
          + " will be a child type such as WmsStrateggy"
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "Click Strategy not found")
  })
  public Response getClickStrategyById(
      @ApiParam(value = "ID of the Click Strategy to be fetched", required = true) @PathParam("id")
          Integer id) throws AppException {
    ClickStrategy clickStrategy = this.clickStrategyService.getClickStrategy(id);
    return Response.status(200).entity(clickStrategy).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new click strategy entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateClickStrategy(ClickStrategy clickStrategy)
      throws AppException {
    ClickStrategy databaseCS = this.clickStrategyService.postCreateClickStrategy(clickStrategy);
    return Response.status(201).entity(databaseCS).build();
  }

}
