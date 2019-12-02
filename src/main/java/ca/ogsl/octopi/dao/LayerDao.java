/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.Layer;
import ca.ogsl.octopi.models.Layer_;
import ca.ogsl.octopi.util.GenericsUtil;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class LayerDao {

  public String getLayerTitle(Integer id) throws AppException {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    String title;
    try {
      CriteriaQuery<String> cq = cb.createQuery(String.class);
      Root<Layer> root = cq.from(Layer.class);
      cq.where(cb.equal(root.get(Layer_.id), id));
      cq.select(cb.construct(String.class, root.get(Layer_.title))).distinct(true);
      TypedQuery<String> tq = em.createQuery(cq);
      title = GenericsUtil.getSingleResultOrNull(tq);
    } finally {
      em.close();
    }
    return title;
  }

  public Layer getLayerFromCode(String code, String languageCode) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    Layer layer;
    try {
      CriteriaQuery<Layer> cq = cb.createQuery(Layer.class);
      Root<Layer> root = cq.from(Layer.class);
      Predicate codePredicate = cb.equal(root.get(Layer_.code), code);
      Predicate langPredicate = cb.equal(root.get(Layer_.languageCode), languageCode);
      cq.where(cb.and(codePredicate, langPredicate));
      TypedQuery<Layer> tq = em.createQuery(cq);
      layer = GenericsUtil.getSingleResultOrNull(tq);
    } finally {
      em.close();
    }
    return layer;
  }
}
