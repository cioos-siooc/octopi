/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.dao.LayerInfoDao;
import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.Layer;
import ca.ogsl.octopi.models.LayerInfo;
import ca.ogsl.octopi.util.AppConstants;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import java.util.List;
import javax.validation.groups.Default;

public class LayerInfoService {

  private GenericDao genericDao = new GenericDao();
  private LayerInfoDao layerInfoDao = new LayerInfoDao();

  public LayerInfo getLayerInfoForId(Integer id) throws AppException {
    LayerInfo layerInfo = this.genericDao.getEntityFromId(id, LayerInfo.class);
    if (layerInfo == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return layerInfo;
  }

  public LayerInfo postCreateLayerInfo(LayerInfo layerInfo) throws AppException {
    ValidationUtil.validateBean(layerInfo, PostCheck.class, Default.class);
    Layer targetLayer = this.genericDao.getEntityFromId(layerInfo.getLayerId(),
        Layer.class);
    if (targetLayer == null) {
      throw new AppException(400, 400,
          "Specified layer does not exist. Layer info must target an existing layer",
          AppConstants.PORTAL_URL);
    }
    return this.genericDao.persistEntity(layerInfo);
  }

  public List<LayerInfo> postCreateMultipleLayerInfos(List<LayerInfo> layerInfos)
      throws AppException {
    return this.layerInfoDao.createMultipleLayerInfos(layerInfos);
  }

  public void deleteLayerInfoForId(Integer id) throws AppException {
    this.genericDao.deleteEntityFromId(id, LayerInfo.class);
  }

  public List<LayerInfo> listLayerInfos() {
    return this.genericDao.getAllEntities(LayerInfo.class);
  }
}
