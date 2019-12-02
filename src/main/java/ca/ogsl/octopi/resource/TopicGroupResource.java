/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.resource;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.TopicGroup;
import ca.ogsl.octopi.services.TopicGroupService;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("topic-groups")
@Api(tags = {"Topic Group"})
@Produces(MediaType.APPLICATION_JSON)
public class TopicGroupResource {

  private TopicGroupService topicGroupService = new TopicGroupService();

  @GET
  @ApiOperation(
      value = "Get a list of all topic groups",
      response = TopicGroup.class,
      responseContainer = "List"
  )
  @ApiResponses(value = {@ApiResponse(code = 200, message = "successful operation")})
  public Response listTopicGroups() throws AppException {
    List<TopicGroup> topicGroups = this.topicGroupService.listTopicGroups();
    return Response.status(200).entity(topicGroups).build();
  }

  @GET
  @Path("{id}")
  @ApiOperation(
      value = "Get topic group by ID",
      response = TopicGroup.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "topic group not found")
  })
  public Response getTopicGroup(
      @ApiParam(value = "ID of topic group to be fetched", required = true) @PathParam("id") Integer id
  ) throws AppException {
    TopicGroup topicGroup = this.topicGroupService.getTopicGroup(id);
    return Response.status(200).entity(topicGroup).build();
  }

  @GET
  @Path("getTopicGroupForCode")
  @ApiOperation(
      value = "Get topic group by code and language code",
      response = TopicGroup.class
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "operation successful"),
      @ApiResponse(code = 404, message = "topic group not found")
  })
  public Response getTopicGroupForCode(
      @ApiParam(value = "Code of topic group to be fetched", required = true) @QueryParam("code") String code,
      @ApiParam(value = "Code of the language needed", required = true)
      @QueryParam("language-code") String languageCode) throws AppException {
    TopicGroup topicGroup = this.topicGroupService.getTopicGroupForCode(code, languageCode);
    return Response.status(200).entity(topicGroup).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Create a new TopicGroup entry",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 201, message = "resource created"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response postCreateTopicGroup(TopicGroup topicGroup)
      throws AppException {
    TopicGroup databaseTopicGroup = this.topicGroupService.postCreateTopicGroup(topicGroup);
    return Response.status(201).entity(databaseTopicGroup).build();
  }

  @PUT
  @Path("{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @ApiOperation(
      value = "Update a TopicGroup entry.",
      authorizations = {
          @Authorization(value = "basicAuth")
      }
  )
  @ApiResponses(value = {
      @ApiResponse(code = 200, message = "successful operation"),
      @ApiResponse(code = 400, message = "invalid request")
  })
  @RolesAllowed("ADMIN")
  public Response putTopicGroup(@PathParam("id") Integer topicGroupId, TopicGroup topicGroup)
      throws AppException {
    TopicGroup oldTopicGroup = this.topicGroupService.retrieveTopicGroup(topicGroupId);
    if (oldTopicGroup == null) {
      throw new AppException(400, 400,
          "Use post to create entity", AppConstants.PORTAL_URL);
    } else {
      this.topicGroupService.putUpdateTopicGroup(topicGroup, oldTopicGroup);
      return Response.status(200).build();
    }
  }
}
