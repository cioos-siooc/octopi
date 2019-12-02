/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.ClientPresentationDao;
import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.ClientPresentation;
import ca.ogsl.octopi.util.AppConstants;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import java.util.List;
import javax.validation.groups.Default;

public class ClientPresentationService {

  private GenericDao genericDao = new GenericDao();
  private ClientPresentationDao clientPresentationDao = new ClientPresentationDao();

  public List<ClientPresentation> listClientPresentations() {
    return this.genericDao.getAllEntities(ClientPresentation.class);
  }

  public ClientPresentation getClientPresentationForId(Integer id) throws AppException {
    ClientPresentation clientPresentation = this.genericDao
        .getEntityFromId(id, ClientPresentation.class);
    if (clientPresentation == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return clientPresentation;
  }

  public ClientPresentation postCreateClientPresentation(ClientPresentation clientPresentation)
      throws AppException {
    ValidationUtil.validateBean(clientPresentation, PostCheck.class, Default.class);
    checkIfDefaultClientPresentationExist(clientPresentation);
    return this.genericDao.persistEntity(clientPresentation);
  }

  private void checkIfDefaultClientPresentationExist(ClientPresentation clientPresentation)
      throws AppException {
    ClientPresentation defaultClientPresentation = this.clientPresentationDao
        .getDefaultClientPresentation(
            clientPresentation.getLayerId());
    if (defaultClientPresentation != null) {
      throw new AppException(400, 400,
          "Duplicate default client presentation. One layer cannot have multiple default"
              + " client presentations.",
          AppConstants.PORTAL_URL);
    }
  }
}
