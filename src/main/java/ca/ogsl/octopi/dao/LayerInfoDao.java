/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.LayerInfo;
import ca.ogsl.octopi.models.LayerInfo_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class LayerInfoDao {

  public List<LayerInfo> createMultipleLayerInfos(List<LayerInfo> layerInfos) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    EntityTransaction et = em.getTransaction();
    List<LayerInfo> databaseLayerInfos = new ArrayList<>();
    try {
      et.begin();
      for (LayerInfo layerInfo : layerInfos) {
        databaseLayerInfos.add(em.merge(layerInfo));
      }
      et.commit();
      return databaseLayerInfos;
    } finally {
      em.close();
    }
  }

  public List<LayerInfo> getLayerInfosForLayerIdOrdered(Integer layerId)
      throws AppException {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    List<LayerInfo> databaseLayerInfos;
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    try {
      CriteriaQuery<LayerInfo> cq = cb.createQuery(LayerInfo.class);
      Root<LayerInfo> root = cq.from(LayerInfo.class);
      cq.where(cb.equal(root.get(LayerInfo_.layerId), layerId));
      cq.orderBy(cb.asc(root.get(LayerInfo_.label)));
      TypedQuery<LayerInfo> tq = em.createQuery(cq);
      databaseLayerInfos = tq.getResultList();
    } finally {
      em.close();
    }
    return databaseLayerInfos;

  }
}
