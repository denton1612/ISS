package ubb.scs.domain.validator;

import ubb.scs.domain.Entity;

public interface Validator<ID, E extends Entity<ID>> {
    void validate(E entity);
}
