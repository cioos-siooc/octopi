/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.filter;

import ca.ogsl.octopi.models.auth.Role;
import ca.ogsl.octopi.services.AuthService;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.internal.util.Base64;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  private static final Logger logger = LogManager.getLogger();
  private static final String AUTHORIZATION_PROPERTY = "Authorization";
  private static final String AUTHENTICATION_SCHEME = "Basic";
  private static final ResponseBuilder ACCESS_DENIED_RESPONSE = Response
      .status(Response.Status.UNAUTHORIZED)
      .entity("");
  private static final ResponseBuilder ACCESS_FORBIDDEN_RESPONSE = Response
      .status(Response.Status.FORBIDDEN)
      .entity("");
  @Context
  private ResourceInfo resourceInfo;

  @Override
  public void filter(ContainerRequestContext requestContext) {
    Method method = resourceInfo.getResourceMethod();
    if (!method.isAnnotationPresent(PermitAll.class)) {
      if (method.isAnnotationPresent(DenyAll.class)) {
        throw new WebApplicationException(ACCESS_FORBIDDEN_RESPONSE.build());
      }
      final MultivaluedMap<String, String> headers = requestContext.getHeaders();

      if (method.isAnnotationPresent(RolesAllowed.class)) {
        validateUserRoleForMethod(method, headers);
      }
    }
  }

  private void validateUserRoleForMethod(Method method,
      MultivaluedMap<String, String> headers) {
    final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);
    if (authorization == null || authorization.isEmpty()) {
      throw new WebApplicationException(ACCESS_DENIED_RESPONSE.build());
    }
    final StringTokenizer tokenizer = getUsernamePasswordTokenizer(authorization);
    final String username = tokenizer.nextToken();
    final String password = tokenizer.nextToken();

    RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
    Set<String> annotatedRoles = new HashSet<>(Arrays.asList(rolesAnnotation.value()));

    if (!isUserAllowed(username, password, annotatedRoles)) {
      throw new WebApplicationException(ACCESS_DENIED_RESPONSE.build());
    }
  }

  private StringTokenizer getUsernamePasswordTokenizer(List<String> authorization) {
    final String encodedUserPassword = authorization.get(0)
        .replaceFirst(AUTHENTICATION_SCHEME + " ", "");
    String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));
    return new StringTokenizer(usernameAndPassword, ":");
  }

  private boolean isUserAllowed(final String username, final String password,
      final Set<String> annotatedRoles) {
    List<Role> roles = getRoles(username, password);
    return !roles.isEmpty() && areUserRolesAdequate(annotatedRoles, roles);
  }

  private List<Role> getRoles(String username, String password) {
    AuthService authService = new AuthService();
    List<Role> roles;
    try {
      roles = authService.getUserRoles(username, password);
    } catch (NoSuchAlgorithmException e) {
      logger.error("Password hashing error", e);
      throw new WebApplicationException(ACCESS_DENIED_RESPONSE.build());
    }
    return roles;
  }

  private boolean areUserRolesAdequate(Set<String> annotatedRoles, List<Role> roles) {
    Set<String> rolesToSet = roles.stream()
        .map(Role::getCode)
        .collect(Collectors.toSet());
    return !Collections.disjoint(rolesToSet, annotatedRoles);
  }
}

