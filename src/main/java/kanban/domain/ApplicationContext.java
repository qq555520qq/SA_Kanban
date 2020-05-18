package kanban.domain;

import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.usecase.board.create.CreateBoardUseCase;
import kanban.domain.usecase.board.get.GetBoardsUseCase;

public class ApplicationContext {

    private static ApplicationContext applicationContext;


    public ApplicationContext() {}

    public static ApplicationContext getInstance() {
        if(applicationContext == null) {
            applicationContext = new ApplicationContext();
        }

        return applicationContext;
    }

    public CreateBoardUseCase getCreateBoardUseCase() {
        return new CreateBoardUseCase(new MySqlBoardRepository());
    }

    public GetBoardsUseCase getGetBoardsUseCase() {
        return new GetBoardsUseCase(new MySqlBoardRepository());
    }
}
