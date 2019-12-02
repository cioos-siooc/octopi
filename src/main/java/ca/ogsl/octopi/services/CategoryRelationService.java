/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.services;

import ca.ogsl.octopi.dao.CategoryRelationDao;
import ca.ogsl.octopi.dao.GenericDao;
import ca.ogsl.octopi.errorhandling.AppException;
import ca.ogsl.octopi.models.CategoryRelation;
import ca.ogsl.octopi.util.AppConstants;
import ca.ogsl.octopi.util.ValidationUtil;
import ca.ogsl.octopi.validation.tagging.PostCheck;
import ca.ogsl.octopi.validation.tagging.PutCheck;
import java.util.List;
import javax.validation.groups.Default;

public class CategoryRelationService {

  private GenericDao genericDao = new GenericDao();
  private CategoryRelationDao categoryRelationDao = new CategoryRelationDao();

  public List<CategoryRelation> listCategoryRelations() {
    return this.genericDao.getAllEntities(CategoryRelation.class);
  }

  public CategoryRelation getCategoryRelation(Integer id) throws AppException {
    CategoryRelation categoryRelation = this.genericDao.getEntityFromId(id, CategoryRelation.class);
    if (categoryRelation == null) {
      throw new AppException(404, 404,
          "Not found", AppConstants.PORTAL_URL);
    }
    return categoryRelation;
  }

  public CategoryRelation postCreateCategoryRelation(CategoryRelation categoryRelation)
      throws AppException {
    ValidationUtil.validateBean(categoryRelation, PostCheck.class, Default.class);
    return this.genericDao.persistEntity(categoryRelation);
  }

  public CategoryRelation retrieveCategoryRelation(Integer categoryRelationId) {
    return this.genericDao.getEntityFromId(categoryRelationId, CategoryRelation.class);
  }

  public void putUpdateCategoryRelation(CategoryRelation categoryRelation,
      CategoryRelation oldCategoryRelation) throws AppException {
    ValidationUtil.validateBean(categoryRelation, PutCheck.class, Default.class);
    checkIfUpdatingToExistingEntity(categoryRelation, oldCategoryRelation);
    this.categoryRelationDao.fullUpdateTopic(categoryRelation, oldCategoryRelation);
  }

  private void checkIfUpdatingToExistingEntity(CategoryRelation categoryRelation,
      CategoryRelation oldCategoryRelation) throws AppException {
    if (!categoryRelation.getId().equals(oldCategoryRelation.getId())) {
      CategoryRelation databaseTopic = this.genericDao
          .getEntityFromId(categoryRelation.getId(), CategoryRelation.class);
      if (databaseTopic != null) {
        throw new AppException(400, 400,
            "Cannot update with id from another existing entity", AppConstants.PORTAL_URL);
      }
    }
  }
}
