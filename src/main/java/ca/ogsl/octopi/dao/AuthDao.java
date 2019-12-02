/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.dao;

import ca.ogsl.octopi.models.auth.Role;
import ca.ogsl.octopi.models.auth.Role_;
import ca.ogsl.octopi.models.auth.User;
import ca.ogsl.octopi.models.auth.User_;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AuthDao {

  public List<Role> getUserRoles(String username, String hashedPassword) {
    EntityManager em = AuthEntityManagerFactory.createEntityManager();
    CriteriaBuilder cb = AuthEntityManagerFactory.getCriteriaBuilder();
    List<Role> roles;
    try {
      CriteriaQuery<Role> cq = cb.createQuery(Role.class);
      Root<Role> root = cq.from(Role.class);
      Join<Role, User> userJoin = root.join(Role_.users);
      Predicate usernamePredicate = cb.equal(userJoin.get(User_.username), username);
      Predicate passwordPredicate = cb.equal(userJoin.get(User_.password), hashedPassword);
      cq.where(cb.and(usernamePredicate, passwordPredicate));
      TypedQuery<Role> tq = em.createQuery(cq);
      roles = tq.getResultList();
    } finally {
      em.close();
    }
    return roles;
  }
}
