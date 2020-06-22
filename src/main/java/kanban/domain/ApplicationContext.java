package kanban.domain;

import kanban.domain.adapter.repository.board.MySqlBoardRepository;
import kanban.domain.adapter.repository.workflow.MySqlWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.board.create.CreateBoardUseCase;
import kanban.domain.usecase.board.get.GetBoardsUseCase;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.workflow.IWorkflowRepository;

public class ApplicationContext {

    private static ApplicationContext applicationContext;
    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private DomainEventBus eventBus;

    public ApplicationContext() {
        boardRepository = new MySqlBoardRepository();
        workflowRepository = new MySqlWorkflowRepository();

        eventBus = new DomainEventBus();
        DomainEventHandler domainEventHandler = new DomainEventHandler(null);
        eventBus.register(domainEventHandler);
    }

    public static ApplicationContext getInstance() {
        if(applicationContext == null) {
            applicationContext = new ApplicationContext();
        }

        return applicationContext;
    }

    public CreateBoardUseCase getCreateBoardUseCase() {
        return new CreateBoardUseCase(boardRepository, eventBus);
    }

    public GetBoardsUseCase getGetBoardsUseCase() {
        return new GetBoardsUseCase(boardRepository);
    }
}
