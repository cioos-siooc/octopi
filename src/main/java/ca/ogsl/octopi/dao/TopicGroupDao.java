/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.TopicGroup;
import ca.ogsl.octopi.models.TopicGroup_;
import ca.ogsl.octopi.util.GenericsUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.transform.DistinctResultTransformer;

public class TopicGroupDao {

  public List<TopicGroup> listTopicGroups() throws AppException {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    List<TopicGroup> topicGroups;
    try {
      CriteriaQuery<TopicGroup> cq = cb.createQuery(TopicGroup.class);
      Root<TopicGroup> root = cq.from(TopicGroup.class);
      Join topicGroupMemberJoin = (Join) root.fetch(TopicGroup_.topicIds, JoinType.LEFT);
      TypedQuery<TopicGroup> tq = em.createQuery(cq);
      topicGroups = tq.getResultList();
    } finally {
      em.close();
    }
    return DistinctResultTransformer.INSTANCE.transformList(topicGroups);
  }

  public TopicGroup getTopicGroup(Integer id) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    TopicGroup topicGroup;
    try {
      CriteriaQuery<TopicGroup> cq = cb.createQuery(TopicGroup.class);
      Root<TopicGroup> root = cq.from(TopicGroup.class);
      Join topicGroupMemberJoin = (Join) root.fetch(TopicGroup_.topicIds, JoinType.LEFT);
      cq.where(cb.equal(root.get(TopicGroup_.id), id));
      TypedQuery<TopicGroup> tq = em.createQuery(cq);
      topicGroup = GenericsUtil.getSingleResultOrNull(tq);
    } finally {
      em.close();
    }
    return topicGroup;
  }

  public void fullUpdateTopicGroup(TopicGroup topicGroup, TopicGroup oldTopicGroup) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    EntityTransaction et = em.getTransaction();
    try {
      et.begin();
      TopicGroup persistedTopicGroup = em.find(TopicGroup.class, oldTopicGroup.getId());
      em.remove(persistedTopicGroup);
      em.flush();
      em.merge(topicGroup);
      et.commit();
    } finally {
      em.close();
    }
  }

  public TopicGroup getTopicGroupFromCode(String code, String languageCode) {
    EntityManager em = OctopiEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = OctopiEntityManagerFactory.getCriteriaBuilder();
    TopicGroup topicGroup;
    try {
      CriteriaQuery<TopicGroup> cq = cb.createQuery(TopicGroup.class);
      Root<TopicGroup> root = cq.from(TopicGroup.class);
      Join topicGroupMemberJoin = (Join) root.fetch(TopicGroup_.topicIds, JoinType.LEFT);
      Predicate codePredicate = cb.equal(root.get(TopicGroup_.code), code);
      Predicate langPredicate = cb.equal(root.get(TopicGroup_.languageCode), languageCode);
      cq.where(cb.and(codePredicate, langPredicate));
      TypedQuery<TopicGroup> tq = em.createQuery(cq);
      topicGroup = GenericsUtil.getSingleResultOrNull(tq);
    } finally {
      em.close();
    }
    return topicGroup;
  }
}
