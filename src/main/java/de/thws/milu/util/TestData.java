package de.thws.milu.util;

import com.google.inject.Inject;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaAccount;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaBoard;
import de.thws.milu.adapter.out.persistence.jpa.entity.JpaTodo;
import de.thws.milu.application.service.AccountService;
import de.thws.milu.application.service.BoardService;
import de.thws.milu.application.service.TodoService;
import de.thws.milu.core.domain.model.Todo;
import jakarta.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Singleton
public class TestData {

    private final AccountService accountService;
    private final BoardService boardService;
    private final TodoService todoService;
    private final Random random = new Random();

    @Inject
    public TestData(AccountService accountService, BoardService boardService, TodoService todoService) {
        this.accountService = accountService;
        this.boardService = boardService;
        this.todoService = todoService;
    }

    public void seed() {

        int accountCount = 20;
        for (int i = 1; i <= accountCount; i++) {
            JpaAccount account = new JpaAccount();
            account.setId(UUID.randomUUID());
            account.setName("Account " + i);

            accountService.save(account);

            int boardCount = random.nextInt(3) + 1;
            List<JpaBoard> boardsForAccount = new ArrayList<>();
            for (int j = 1; j <= boardCount; j++) {
                JpaBoard board = new JpaBoard();
                board.setId(UUID.randomUUID());
                board.setName("Board " + i + "." + j);
                board.setDescription("Description for Board " + i + "." + j);

                boardService.save(board);

                int todoCount = random.nextInt(4) + 3;
                List<JpaTodo> todosForBoard = new ArrayList<>();
                for (int k = 1; k <= todoCount; k++) {
                    JpaTodo todo = new JpaTodo();
                    todo.setId(UUID.randomUUID());
                    todo.setName("Todo " + i + "." + j + "." + k);
                    todo.setDescription("Description for Todo " + i + "." + j + "." + k);

                    Todo.State[] states = Todo.State.values();
                    todo.setState(states[random.nextInt(states.length)]);

                    todo.setParentBoard(board);

                    todoService.save(todo);
                    todosForBoard.add(todo);
                }
                board.setTodos(todosForBoard);
                boardService.update(board.getId(), board);

                boardsForAccount.add(board);
            }
            account.setBoards(boardsForAccount);
            accountService.update(account.getId(), account);
        }
    }
}