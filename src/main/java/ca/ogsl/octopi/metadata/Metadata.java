/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.metadata;

import io.swagger.annotations.BasicAuthDefinition;
import io.swagger.annotations.Info;
import io.swagger.annotations.SecurityDefinition;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.SwaggerDefinition.Scheme;
import javax.ws.rs.ext.Provider;

@SwaggerDefinition(
    info = @Info(
        title = "Octopi",
        description = "An API for storing and accessing the configuration necessary for displaying "
            + "map layers",
        version = "1.0.0"
    ),
    basePath = "/octopi/api",
    schemes = {Scheme.HTTP},
    securityDefinition = @SecurityDefinition(
        basicAuthDefinitions = {
            @BasicAuthDefinition(key = "basicAuth")
        }
    )
)
@Provider
public interface Metadata {

}
