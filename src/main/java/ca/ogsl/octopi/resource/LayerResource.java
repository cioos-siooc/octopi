/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.ClickFormatter;
import ca.ogsl.octopi.models.ClickStrategy;
import ca.ogsl.octopi.models.ClientPresentation;
import ca.ogsl.octopi.models.Layer;
import ca.ogsl.octopi.models.LayerDescription;
import ca.ogsl.octopi.models.LayerInfo;
import ca.ogsl.octopi.services.LayerService;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("layers")
@Api(tags = {"Layer"})
@Produces(MediaType.APPLICATION_JSON)
public class LayerResource {

  private LayerService layerService = new LayerService();

  @GET
  @ApiOperation(
      value = "Get all layers",
      response = Layer.class,
      responseContainer = "List",
      notes = "Returns a list objects with the base type: Layer. Each layer will be a child type(such as WMSLayer or WFSLayer)"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listLayers() throws AppException {
    List<Layer> layerList = this.layerService.listLayers();
    return Response.status(200).entity(layerList).build();
  }

  @GET
  @Path("{id}")
  @ApiOperation(
      value = "Find layer by ID",
      response = Layer.class,
      notes = "Returns an object of base type Layer. The Layer will be a child type such as WMSLayer or WFSLayer"
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "layer not found")
  })
  public Response getLayerForId(
      @ApiParam(value = "ID of layer to be fetched", required = true) @PathParam("id") Integer id
  ) throws AppException {
    Layer databaseLayer = this.layerService.getLayerForId(id);
    return Response.status(200).entity(databaseLayer).build();
  }

  @GET
  @Path("getLayerForCode")
  @ApiOperation(
      value = "Get layer by code and language code",
      response = Layer.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "layer not found")
  })
  public Response getLayerForCode(
      @ApiParam(value = "Code of layer to be fetched", required = true) @QueryParam("code") String code,
      @ApiParam(value = "Code of the language needed", required = true)
      @QueryParam("language-code") String languageCode) throws AppException {
    Layer layer = this.layerService.getLayerForCode(code, languageCode);
    return Response.status(200).entity(layer).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new layer entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateLayer(Layer layer)
      throws AppException {
    Layer databaseLayer = this.layerService.postCreateLayer(layer);
    return Response.status(201).entity(databaseLayer).build();
  }

  @GET
  @Path("{id}/getLayerInformation")
  @ApiOperation(
      value = "Get an HTML segment for displaying the information about a layer based on ID",
      response = String.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "layer information not found")
  })
  @Produces(MediaType.TEXT_HTML)
  public Response getLayerInformation(
      @ApiParam(value = "ID of layer to be fetched", required = true) @PathParam("id") Integer layerId
  ) throws AppException {
    String htmlContent = this.layerService.getLayerInformation(layerId);
    return Response.status(200).entity(htmlContent).build();
  }

  @GET
  @Path("{id}/client-presentations")
  @ApiOperation(
      value = "Gets all of the client presentations associated with a given layer",
      response = ClientPresentation.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listClientPresentations(
      @ApiParam(value = "ID of layer to be fetched", required = true) @PathParam("id") Integer layerId
  ) throws AppException {
    List<ClientPresentation> clientPresentations = this.layerService
        .listClientPresentations(layerId);
    return Response.status(200).entity(clientPresentations).build();
  }

  @GET
  @Path("{id}/layer-descriptions")
  @ApiOperation(
      value = "Gets the description for a specific layer based on layer ID",
      response = LayerDescription.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "layer description not found")
  })
  public Response getLayerDescription(
      @ApiParam(value = "ID of the layer", required = true) @PathParam("id") Integer layerId
  ) throws AppException {
    LayerDescription layerDescription = this.layerService.getLayerDescription(layerId);
    return Response.status(200).entity(layerDescription).build();
  }

  @GET
  @Path("{id}/layer-info")
  @ApiOperation(
      value = "Gets the a collection of layerInfo entries for a layer based on layer ID",
      response = LayerInfo.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "layer info not found")
  })
  public Response getLayerInfo(
      @ApiParam(value = "ID of the layer") @PathParam("id") Integer layerId
  ) throws AppException {
    List<LayerInfo> layerInfo = this.layerService.getLayerInfo(layerId);
    return Response.status(200).entity(layerInfo).build();
  }

  @GET
  @Path("{id}/click-strategies")
  @ApiOperation(
      value = "Gets the Click Strategy for a specific layer based on layer ID",
      response = ClickStrategy.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "Click Strategy not found")
  })
  public Response getClickStrategy(
      @ApiParam(value = "ID of the layer", required = true) @PathParam("id") Integer layerId
  ) throws AppException {
    ClickStrategy clickStrategy = this.layerService.getClickStrategy(layerId);
    return Response.status(200).entity(clickStrategy).build();
  }

  @GET
  @Path("{id}/click-formatters")
  @ApiOperation(
      value = "Gets the Click Formatter for a specific layer based on layer ID",
      response = ClickFormatter.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "Click Formatter not found")
  })
  public Response getClickFormatter(
      @ApiParam(value = "ID of the layer", required = true) @PathParam("id") Integer layerId
  ) throws AppException {
    ClickFormatter clickFormatter = this.layerService.getClickFormatter(layerId);
    return Response.status(200).entity(clickFormatter).build();
  }
}
