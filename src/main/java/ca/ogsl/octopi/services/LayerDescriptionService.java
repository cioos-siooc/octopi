/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.dao.LayerDescriptionDao;
import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.LayerDescription;
import ca.ogsl.octopi.util.AppConstants;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import java.util.List;
import javax.validation.groups.Default;

public class LayerDescriptionService {

  private GenericDao genericDao = new GenericDao();
  private LayerDescriptionDao layerDescriptionDao = new LayerDescriptionDao();

  public List<LayerDescription> listLayerDescriptions() throws AppException {
    return this.genericDao.getAllEntities(LayerDescription.class);
  }

  public LayerDescription getLayerDescriptionForId(Integer id) throws AppException {
    LayerDescription layerDescription = this.genericDao.getEntityFromId(id, LayerDescription.class);
    if (layerDescription == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return layerDescription;
  }

  public void deleteLayerDescriptionForId(Integer id, String role) throws AppException {
    this.genericDao.deleteEntityFromId(id, LayerDescription.class);
  }

  public LayerDescription postCreateLayerDescription(LayerDescription layerDescription)
      throws AppException {
    ValidationUtil.validateBean(layerDescription, PostCheck.class, Default.class);
    checkIfLayerDescriptionExist(layerDescription);
    return this.genericDao.persistEntity(layerDescription);
  }

  private void checkIfLayerDescriptionExist(LayerDescription layerDescription) throws AppException {
    LayerDescription currLayerDescription = this.layerDescriptionDao.getLayerDescriptionForLayerId(
        layerDescription.getLayerId());
    if (currLayerDescription != null) {
      throw new AppException(400, 400,
          "Duplicate layerId. One layer cannot have multiple descriptions.",
          AppConstants.PORTAL_URL);
    }
  }
}
