/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.Category;
import ca.ogsl.octopi.services.CategoryService;
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

@Path("categories")
@Api(tags = {"Category"})
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {

  private CategoryService categoryService = new CategoryService();

  @GET
  @ApiOperation(
      value = "Get a list of all categories",
      response = Category.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listCategories() throws AppException {
    List<Category> categories = this.categoryService.listCategories();
    return Response.status(200).entity(categories).build();
  }

  @GET
  @Path("{id}")
  @ApiOperation(
      value = "Get category by ID",
      response = Category.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "category not found")
  })
  public Response getCategory(
      @ApiParam(value = "ID of category to be fetched", required = true) @PathParam("id") Integer id
  ) throws AppException {
    Category category = this.categoryService.getCategory(id);
    return Response.status(200).entity(category).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new category entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateCategory(Category category)
      throws AppException {
    Category databaseCategory = this.categoryService.postCreateCategory(category);
    return Response.status(201).entity(databaseCategory).build();
  }


  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Update a category entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "successful operation"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response putUpdateCategory(@PathParam("id") Integer categoryId, Category category)
      throws AppException {
    Category oldCategory = this.categoryService.retrieveCategory(categoryId);
    if (oldCategory == null) {
      throw new AppException(400, 400,
          "Use post to create entity", AppConstants.PORTAL_URL);
    } else {
      this.categoryService.putUpdateCategory(category, oldCategory);
      return Response.status(200).build();
    }
  }

}
