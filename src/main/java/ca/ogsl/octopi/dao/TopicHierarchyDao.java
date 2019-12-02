/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.models.TopicHierarchy;
import ca.ogsl.octopi.models.TopicHierarchy_;
import ca.ogsl.octopi.util.GenericsUtil;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

public class TopicHierarchyDao {

  public TopicHierarchy getTopicHierarchyById(Integer id) throws Exception {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    TopicHierarchy topicHierarchy;
    try {
      CriteriaQuery<TopicHierarchy> cq = cb.createQuery(TopicHierarchy.class);
      Root<TopicHierarchy> root = cq.from(TopicHierarchy.class);
      Join categoryJoin = (Join) root.fetch("root", JoinType.LEFT);
      cq.where(cb.equal(root.get(TopicHierarchy_.id), id));
      TypedQuery<TopicHierarchy> tq = em.createQuery(cq);
      topicHierarchy = GenericsUtil.getSingleResultOrNull(tq);
      GenericsUtil gu = new GenericsUtil();
      if (topicHierarchy != null) {
        gu.recursiveInitialize(topicHierarchy, TopicHierarchy.class);
      }
    } finally {
      em.close();
    }
    return topicHierarchy;
  }

}
