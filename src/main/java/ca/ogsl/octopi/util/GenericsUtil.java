/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;
import javax.persistence.TypedQuery;
import org.apache.commons.lang3.ClassUtils;
import org.hibernate.Hibernate;

/**
 * Created by desjardisna on 2017-02-28.
 */
public class GenericsUtil {

  /**
   * Obtient un résultat unique ou null si aucun résultat n'est présent pour la présente TypedQuery
   *
   * @param query la requête à évaluer
   * @param <T> Type de la requête
   * @return le résultat unique ou null si aucun
   */
  public static <T> T getSingleResultOrNull(TypedQuery<T> query) {
    query.setMaxResults(1);
    List<T> list = query.getResultList();
    if (list.isEmpty()) {
      return null;
    }
    return list.get(0);
  }

  public <T> T recursiveInitialize(T obj, Class clazz) throws Exception {
    Set<Object> dejaVu = Collections.newSetFromMap(new IdentityHashMap<Object, Boolean>());
    recursiveInitialize(obj, dejaVu, clazz);
    return obj;
  }

  private void recursiveInitialize(Object obj, Set<Object> dejaVu, Class clazz)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    if (dejaVu.contains(obj)) {
      return;
    } else {
      dejaVu.add(obj);

      if (!Hibernate.isInitialized(obj)) {
        Hibernate.initialize(obj);
      }
      for (Field f : clazz.getDeclaredFields()) {
        f.setAccessible(true);
        Object value = f.get(obj);
        if (value != null && !ClassUtils.isPrimitiveOrWrapper(f.getType())) {
          this.recursiveInitialize(value, dejaVu, f.getType());
          if (value instanceof Collection) {
            ParameterizedType collectionType = (ParameterizedType) f.getGenericType();
            Class<?> collectionClass = (Class<?>) collectionType.getActualTypeArguments()[0];
            for (Object item : (Collection<?>) value) {
              this.recursiveInitialize(item, dejaVu, collectionClass);
            }
          }
        }
      }
    }
  }
}
