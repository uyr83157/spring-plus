package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.entity.QComment;
import org.example.expert.domain.manager.entity.QManager;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;
import static org.example.expert.domain.user.entity.QUser.user;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        Todo findTodo = queryFactory
                .select(todo)
                .from(todo)
                .leftJoin(todo.user, user).fetchJoin()
                .where(todo.id.eq(todoId))
                .fetchOne();
        return Optional.ofNullable(findTodo);
    }

    @Override
    public Page<TodoSearchResponse> findByTitleAndNicknameAndDate(Pageable pageable, String title, String nickname, LocalDate startedAt, LocalDate endedAt) {
        QTodo todo = QTodo.todo;
        QUser user = QUser.user;
        QManager manager = QManager.manager;
        QComment comment = QComment.comment;

        List<TodoSearchResponse> list = queryFactory
                .select(Projections.constructor(TodoSearchResponse.class,
                                todo.title,
                                manager.id.count(),
                                comment.id.count()
                        )
                )
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .leftJoin(todo.comments, comment)
                .where(titleLike(title),
                        nicknameLike(nickname),
                        dateBetween(startedAt, endedAt)
                )
                .groupBy(todo.id)
                .orderBy(todo.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long count = queryFactory
                .select(todo.count())
                .from(todo)
                .leftJoin(todo.managers, manager)
                .leftJoin(manager.user, user)
                .where(
                        titleLike(title),
                        nicknameLike(nickname),
                        dateBetween(startedAt, endedAt)
                )
                .fetchOne();

        return new PageImpl<>(list, pageable, count != null ? count : 0L);

    }

    // 타이틀, 닉네임이 부분적으로 일치해도 검색 => like => contains
    private BooleanExpression titleLike(String title) {
        return title != null ? todo.title.contains(title) : null;
    }

    private BooleanExpression nicknameLike(String nickname) {
        return nickname != null ? user.nickname.contains(nickname) : null;
    }

    private BooleanExpression dateBetween(LocalDate startedAt, LocalDate endedAt) {
        if (startedAt == null && endedAt == null) {
            return null;
        }
        // 타겟 <= endedAt
        if (startedAt == null) {
            return todo.createdAt.loe(endedAt.atStartOfDay());
        }
        // startedAt <= 타겟
        if (endedAt == null) {
            return todo.createdAt.goe(startedAt.atStartOfDay());
        }

        // startedAt <= 타겟 <= endedAt
        return todo.createdAt.between(startedAt.atStartOfDay(), endedAt.atStartOfDay()); // startedAt ~ endedAt
    }

}
