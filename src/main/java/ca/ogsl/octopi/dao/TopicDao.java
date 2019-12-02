/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.models.Topic;
import ca.ogsl.octopi.models.Topic_;
import ca.ogsl.octopi.util.GenericsUtil;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TopicDao {

  public void fullUpdateTopic(Topic topic, Topic oldTopic) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    EntityTransaction et = em.getTransaction();
    try {
      et.begin();
      Topic persistedTopic = em.find(Topic.class, oldTopic.getId());
      em.remove(persistedTopic);
      em.flush();
      em.merge(topic);
      et.commit();
    } finally {
      em.close();
    }
  }

  public Topic getTopicFromCode(String code, String languageCode) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    Topic topic;
    try {
      CriteriaQuery<Topic> cq = cb.createQuery(Topic.class);
      Root<Topic> root = cq.from(Topic.class);
      Predicate codePredicate = cb.equal(root.get(Topic_.code), code);
      Predicate langPredicate = cb.equal(root.get(Topic_.languageCode), languageCode);
      cq.where(cb.and(codePredicate, langPredicate));
      TypedQuery<Topic> tq = em.createQuery(cq);
      topic = GenericsUtil.getSingleResultOrNull(tq);
    } finally {
      em.close();
    }
    return topic;
  }
}
