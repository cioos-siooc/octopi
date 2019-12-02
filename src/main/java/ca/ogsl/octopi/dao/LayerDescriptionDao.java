/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.LayerDescription;
import ca.ogsl.octopi.models.LayerDescription_;
import ca.ogsl.octopi.util.GenericsUtil;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class LayerDescriptionDao {

  public LayerDescription getLayerDescriptionForLayerId(Integer layerId)
      throws AppException {
    LayerDescription layerDescription;
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    try {
      CriteriaQuery<LayerDescription> cq = cb.createQuery(LayerDescription.class);
      Root<LayerDescription> root = cq.from(LayerDescription.class);
      cq.where(cb.equal(root.get(LayerDescription_.layerId), layerId));
      TypedQuery<LayerDescription> tq = em.createQuery(cq);
      layerDescription = GenericsUtil.getSingleResultOrNull(tq);
    } finally {
      em.close();
    }
    return layerDescription;
  }
}
