/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package ca.ogsl.octopi.util;

import ca.ogsl.octopi.errorhandling.AppException;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;


public class ValidationUtil {

  private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  public static <T> void validateBean(T objectToValidate, Class... groupsToValidate)
      throws AppException {
    Set<ConstraintViolation<T>> constraintViolations = validator
        .validate(objectToValidate, groupsToValidate);
    if (constraintViolations.size() > 0) {
      throwValidationError(constraintViolations);
    }
  }

  private static <T> void throwValidationError(Set<ConstraintViolation<T>> constraintViolations)
      throws AppException {
    ConstraintViolation<T> firstViolation = constraintViolations.iterator().next();
    throw new AppException(400, 400,
        firstViolation.getPropertyPath() + " " + firstViolation.getMessage(),
        AppConstants.PORTAL_URL);
  }
}
