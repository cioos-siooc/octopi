/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.LayerInfo;
import ca.ogsl.octopi.services.LayerInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("layer-info")
@Api(tags = {"Layer Info"})
@Produces(MediaType.APPLICATION_JSON)
public class LayerInfoResource {

  private LayerInfoService layerInfoService = new LayerInfoService();

  @GET
  @ApiOperation(
      value = "Get all layer information entries",
      response = LayerInfo.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listLayerInfo() {
    List<LayerInfo> layerInfo = this.layerInfoService.listLayerInfos();
    return Response.status(200).entity(layerInfo).build();
  }

  @GET
  @Path("{id}")
  @ApiOperation(
      value = "Find layer info by ID",
      response = LayerInfo.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "layer info not found")
  })
  public Response getLayerInfoForId(
      @ApiParam(value = "ID of the layer info to be fetched", required = true) @PathParam("id") Integer id
  ) throws AppException {
    LayerInfo layerInfo = this.layerInfoService.getLayerInfoForId(id);
    return Response.status(200).entity(layerInfo).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new layer info entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateLayerInfo(LayerInfo layerInfo)
      throws AppException {
    LayerInfo databaseLayerInfo = this.layerInfoService.postCreateLayerInfo(layerInfo);
    return Response.status(201).entity(databaseLayerInfo).build();
  }

  @POST
  @Path("postCreateMultipleLayerInfos")
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create multiple layer info entries. LEGACY",
      authorizations = {
          @Authorization(value = "basicAuth")
      },
      hidden = true
  )
  @RolesAllowed("ADMIN")
  public Response postCreateMultipleLayerInfos(List<LayerInfo> layerInfos,
      @HeaderParam("role") String role) throws
      AppException {
    List<LayerInfo> databaseLayerInfo = this.layerInfoService
        .postCreateMultipleLayerInfos(layerInfos);
    return Response.status(201).entity(databaseLayerInfo).build();
  }

  @DELETE
  @Path("{id}")
  @ApiOperation(value = "Delete layer info entry. LEGACY", hidden = true)
  @RolesAllowed("ADMIN")
  public Response deleteLayerInfoForId(@PathParam("id") Integer id,
      @HeaderParam("role") String role) throws
      AppException {
    this.layerInfoService.deleteLayerInfoForId(id);
    return Response.status(204).build();
  }
}
