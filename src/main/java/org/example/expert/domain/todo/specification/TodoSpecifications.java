package org.example.expert.domain.todo.specification;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TodoSpecifications {

    public static Specification<Todo> weatherFilter(String weather) {
        return (root, query, cb)
                -> (weather == null) ?
                cb.conjunction()
                : cb.equal(root.get("weather"), weather);
    }

    public static Specification<Todo> modifiedAtFilter(LocalDateTime startedAt, LocalDateTime endedAt) {
        return (root, query, cb)
                -> {
            if (startedAt == null && endedAt == null) {
                return cb.conjunction();
            }
            return cb.between(root.get("modifiedAt"), startedAt, endedAt);
        };

    }



}
