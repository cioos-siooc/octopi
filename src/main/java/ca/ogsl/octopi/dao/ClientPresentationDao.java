/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.models.ClientPresentation;
import ca.ogsl.octopi.models.ClientPresentation_;
import ca.ogsl.octopi.util.GenericsUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ClientPresentationDao {

  public List<ClientPresentation> listClientPresentations(int layerId) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    List<ClientPresentation> clientPresentations;
    try {
      CriteriaQuery<ClientPresentation> cq = cb.createQuery(ClientPresentation.class);
      Root<ClientPresentation> root = cq.from(ClientPresentation.class);
      cq.where(cb.equal(root.get(ClientPresentation_.layerId), layerId));
      TypedQuery<ClientPresentation> tq = em.createQuery(cq);
      clientPresentations = tq.getResultList();
    } finally {
      em.close();
    }
    return clientPresentations;
  }

  public ClientPresentation getDefaultClientPresentation(Integer layerId) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    ClientPresentation clientPresentation;
    try {
      CriteriaQuery<ClientPresentation> cq = cb.createQuery(ClientPresentation.class);
      Root<ClientPresentation> root = cq.from(ClientPresentation.class);
      Predicate layerIdPredicate = cb.equal(root.get(ClientPresentation_.layerId), layerId);
      Predicate defaultPredicate = cb.equal(root.get(ClientPresentation_.isDefault), true);
      cq.where(cb.and(layerIdPredicate, defaultPredicate));
      TypedQuery<ClientPresentation> tq = em.createQuery(cq);
      clientPresentation = GenericsUtil.getSingleResultOrNull(tq);
    } finally {
      em.close();
    }
    return clientPresentation;
  }
}
