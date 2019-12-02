/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.CategoryRelation;
import ca.ogsl.octopi.services.CategoryRelationService;
import ca.ogsl.octopi.util.AppConstants;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("category-relations")
@Api(tags = {"Category Relation"})
@Produces(MediaType.APPLICATION_JSON)
public class CategoryRelationResource {

  private CategoryRelationService categoryRelationService = new CategoryRelationService();

  @GET
  @ApiOperation(
      value = "Get a list of all category relations",
      response = CategoryRelation.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listCategories() throws AppException {
    List<CategoryRelation> categoryRelations = this.categoryRelationService.listCategoryRelations();
    return Response.status(200).entity(categoryRelations).build();
  }

  @GET
  @Path("{id}")
  @ApiOperation(
      value = "Get category relation by ID",
      response = CategoryRelation.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "category relation not found")
  })
  public Response getCategoryRelation(
      @ApiParam(value = "ID of category relation to be fetched", required = true) @PathParam("id") Integer id
  ) throws AppException {
    CategoryRelation category = this.categoryRelationService.getCategoryRelation(id);
    return Response.status(200).entity(category).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new category relation entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateCategoryRelation(CategoryRelation categoryRelation)
      throws AppException {
    CategoryRelation databaseCategoryRelation = this.categoryRelationService
        .postCreateCategoryRelation(categoryRelation);
    return Response.status(201).entity(databaseCategoryRelation).build();
  }


  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Update a category relation entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "successful operation"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response putUpdateCategoryRelation(@PathParam("id") Integer categoryRelationId,
      CategoryRelation categoryRelation)
      throws AppException {
    CategoryRelation oldCategoryRelation = this.categoryRelationService
        .retrieveCategoryRelation(categoryRelationId);
    if (oldCategoryRelation == null) {
      throw new AppException(400, 400,
          "Use post to create entity", AppConstants.PORTAL_URL);
    } else {
      this.categoryRelationService.putUpdateCategoryRelation(categoryRelation, oldCategoryRelation);
      return Response.status(200).build();
    }
  }
}
