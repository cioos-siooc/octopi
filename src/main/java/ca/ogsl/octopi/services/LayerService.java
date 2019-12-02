/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.ClientPresentationDao;
import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.dao.LayerDao;
import ca.ogsl.octopi.dao.LayerDescriptionDao;
import ca.ogsl.octopi.dao.LayerInfoDao;
import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.ClickFormatter;
import ca.ogsl.octopi.models.ClickStrategy;
import ca.ogsl.octopi.models.ClientPresentation;
import ca.ogsl.octopi.models.Layer;
import ca.ogsl.octopi.models.LayerDescription;
import ca.ogsl.octopi.models.LayerInfo;
import ca.ogsl.octopi.util.AppConstants;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import java.util.List;
import javax.validation.groups.Default;
import javax.ws.rs.core.Response;

/**
 * Created by desjardisna on 2017-02-28.
 */
public class LayerService {

  private final LayerInfoDao layerInfoDao = new LayerInfoDao();
  private GenericDao genericDao = new GenericDao();
  private LayerDao layerDao = new LayerDao();
  private ClientPresentationDao clientPresentationDao = new ClientPresentationDao();
  private LayerDescriptionDao layerDescriptionDao = new LayerDescriptionDao();

  public List<Layer> listLayers() throws AppException {
    return this.genericDao.getAllEntities(Layer.class);
  }

  public Layer postCreateLayer(Layer layer) throws AppException {
    ValidationUtil.validateBean(layer, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(layer);
  }

  public Layer getLayerForId(Integer id) throws AppException {
    Layer layer = this.genericDao.getEntityFromId(id, Layer.class);
    if (layer == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return layer;
  }

  public Layer getlayerForCode(String code) throws AppException {
    return this.genericDao.getEntityFromCode(code, Layer.class);
  }

  public String getLayerInformation(Integer layerId) throws AppException {
    LayerDescription layerDescription;
    List<LayerInfo> layerInfos;
    layerDescription = this.layerDescriptionDao.getLayerDescriptionForLayerId(layerId);
    String layerTitle = this.layerDao.getLayerTitle(layerId);
    if (layerDescription == null) {
      throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 404,
          "No layer information for given layer", AppConstants.PORTAL_URL);
    }
    layerInfos = layerInfoDao.getLayerInfosForLayerIdOrdered(layerId);
    return this.buildLayerInformationHtml(layerDescription, layerTitle, layerInfos);
  }

  private String buildLayerInformationHtml(LayerDescription layerDescription, String title,
      List<LayerInfo> layerInfos) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<div class='layerInfo'>");
    stringBuilder.append("<h4 class='layerDescriptionTitle'>");
    stringBuilder.append(title).append("</h4>");
    stringBuilder.append("<div class='layerDescription'>");
    stringBuilder.append(layerDescription.getDescription());
    stringBuilder.append("</div>");
    stringBuilder.append(
        "<div class='layerInformationsContainer'><h5 class='layerInformationsTitle'>Informations</h5>");
    stringBuilder.append("<table class='layerInformations'><tbody>");
    for (LayerInfo layerInfo : layerInfos) {
      stringBuilder.append("<tr class='layerInformation'><td class='layerInformationTdLeft'>")
          .append(layerInfo.getLabel()).append("</td><td class='layerInformationTdRight'>");
      if (layerInfo.getUrl() != null && !layerInfo.getUrl().equals("")) {
        stringBuilder.append("<a class='layerInformationLink' target='_blank' href='")
            .append(layerInfo.getUrl()).append("'>")
            .append(layerInfo.getValue()).append("</a></td>");
      } else {
        stringBuilder.append(layerInfo.getValue()).append("</td>");
      }
      stringBuilder.append("</tr>");
    }
    stringBuilder.append("</tbody></table>");
    stringBuilder.append("</div>");
    stringBuilder.append("</div>");
    return stringBuilder.toString();
  }

  public List<ClientPresentation> listClientPresentations(int layerId) {
    return this.clientPresentationDao.listClientPresentations(layerId);
  }

  public LayerDescription getLayerDescription(Integer layerId) throws AppException {
    LayerDescription layerDescription = this.layerDescriptionDao
        .getLayerDescriptionForLayerId(layerId);
    if (layerDescription == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return layerDescription;
  }

  public List<LayerInfo> getLayerInfo(Integer layerId) throws AppException {
    return this.layerInfoDao.getLayerInfosForLayerIdOrdered(layerId);
  }

  public Layer getLayerForCode(String code, String languageCode) throws AppException {
    Layer layer = this.layerDao.getLayerFromCode(code, languageCode);
    if (layer == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return layer;
  }

  public ClickStrategy getClickStrategy(Integer layerId) throws AppException {
    Layer layer = this.genericDao.getEntityFromId(layerId, Layer.class);
    if (layer == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    ClickStrategy clickStrategy = this.genericDao
        .getEntityFromId(layer.getClickStrategyId(), ClickStrategy.class);
    if (clickStrategy == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return clickStrategy;
  }

  public ClickFormatter getClickFormatter(Integer layerId) throws AppException {
    Layer layer = this.genericDao.getEntityFromId(layerId, Layer.class);
    if (layer == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    ClickFormatter clickFormatter = this.genericDao
        .getEntityFromId(layer.getClickFormatterId(), ClickFormatter.class);
    if (clickFormatter == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return clickFormatter;
  }
}
