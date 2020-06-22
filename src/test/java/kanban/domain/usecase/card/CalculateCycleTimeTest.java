package kanban.domain.usecase.card;

import kanban.domain.Utility;
import kanban.domain.adapter.presenter.card.cycleTime.CalculateCycleTimePresenter;
import kanban.domain.adapter.repository.board.InMemoryBoardRepository;
import kanban.domain.adapter.repository.card.InMemoryCardRepository;
import kanban.domain.adapter.repository.domainEvent.InMemoryDomainEventRepository;
import kanban.domain.adapter.repository.flowEvent.InMemoryFlowEventRepository;
import kanban.domain.adapter.repository.workflow.InMemoryWorkflowRepository;
import kanban.domain.model.DomainEventBus;
import kanban.domain.model.common.DateProvider;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.card.cycleTime.CalculateCycleTimeInput;
import kanban.domain.usecase.card.cycleTime.CalculateCycleTimeOutput;
import kanban.domain.usecase.card.cycleTime.CalculateCycleTimeUseCase;
import kanban.domain.usecase.card.cycleTime.CycleTime;
import kanban.domain.usecase.domainEvent.IDomainEventRepository;
import kanban.domain.usecase.flowEvent.IFlowEventRepository;
import kanban.domain.usecase.handler.card.CardEventHandler;
import kanban.domain.usecase.handler.domainEvent.DomainEventHandler;
import kanban.domain.usecase.handler.flowEvent.FlowEventHandler;
import kanban.domain.usecase.handler.workflow.WorkflowEventHandler;
import kanban.domain.usecase.workflow.IWorkflowRepository;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class CalculateCycleTimeTest {

    private String cardId;

    private IBoardRepository boardRepository;
    private IWorkflowRepository workflowRepository;
    private IDomainEventRepository domainEventRepository;
    private IFlowEventRepository flowEventRepository;
    private ICardRepository cardRepository;

    private DomainEventBus eventBus;
    private Utility utility;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");


    @Before
    public void setUp() throws Exception {
        boardRepository = new InMemoryBoardRepository();
        workflowRepository = new InMemoryWorkflowRepository();
        domainEventRepository = new InMemoryDomainEventRepository();
        cardRepository = new InMemoryCardRepository();
        flowEventRepository = new InMemoryFlowEventRepository();

        eventBus = new DomainEventBus();
        eventBus.register(new WorkflowEventHandler(boardRepository, eventBus));
        eventBus.register(new CardEventHandler(boardRepository, workflowRepository, eventBus));
        eventBus.register(new DomainEventHandler(domainEventRepository));
        eventBus.register(new FlowEventHandler(flowEventRepository));

        DateProvider.setDate(dateFormat.parse("2020/5/20 09:00:00"));

        utility = new Utility(boardRepository, workflowRepository, flowEventRepository, cardRepository, eventBus);
        utility.createDefaultKanbanBoard();
        cardId = utility.createCardInDefaultKanbanBoard("First");
    }

    @Test
    public void Calculate_CycleTime_Should_Be_Correct_Without_Moving_Card() throws ParseException {

        DateProvider.setDate(dateFormat.parse("2020/5/21 10:00:00"));

        CalculateCycleTimeUseCase calculateCycleTimeUseCase = new CalculateCycleTimeUseCase(workflowRepository, flowEventRepository);
        CalculateCycleTimeInput input = calculateCycleTimeUseCase;
        input.setWorkflowId(utility.getDefaultWorkflowId());
        input.setCardId(cardId);
        input.setBeginningStageId(utility.getReadyStageId());
        input.setEndingStageId(utility.getDeployedStageId());

        CalculateCycleTimeOutput output = new CalculateCycleTimePresenter();

        calculateCycleTimeUseCase.execute(input, output);

        assertEquals(1, output.getCycleTime().getDiffDays());
        assertEquals(1, output.getCycleTime().getDiffHours());
        assertEquals(0, output.getCycleTime().getDiffMinutes());
        assertEquals(0, output.getCycleTime().getDiffSeconds());


    }

    @Test
    public void Calculate_CycleTime_Should_Be_Correct_With_Moving_Card_From_Ready_To_ReadyToDeploy() throws ParseException {
        DateProvider.setDate(dateFormat.parse("2020/5/21 10:00:00"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getAnalysisStageId());
        CycleTime cycleTime = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getAnalysisStageId());
        assertEquals(1, cycleTime.getDiffDays());
        assertEquals(1, cycleTime.getDiffHours());
        assertEquals(0, cycleTime.getDiffMinutes());
        assertEquals(0, cycleTime.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/22 11:00:00"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getAnalysisStageId(), utility.getDevelopmentStageId());

        cycleTime = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getDevelopmentStageId());
        assertEquals(2, cycleTime.getDiffDays());
        assertEquals(2, cycleTime.getDiffHours());
        assertEquals(0, cycleTime.getDiffMinutes());
        assertEquals(0, cycleTime.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/23 12:00:00"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getDevelopmentStageId(), utility.getTestStageId());
        cycleTime = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getTestStageId());
        assertEquals(3, cycleTime.getDiffDays());
        assertEquals(3, cycleTime.getDiffHours());
        assertEquals(0, cycleTime.getDiffMinutes());
        assertEquals(0, cycleTime.getDiffSeconds());
        //---------------------------- Move card backward

        DateProvider.setDate(dateFormat.parse("2020/5/24 13:30:30"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getTestStageId(), utility.getDevelopmentStageId());
        cycleTime = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getTestStageId());
        assertEquals(4, cycleTime.getDiffDays());
        assertEquals(4, cycleTime.getDiffHours());
        assertEquals(30, cycleTime.getDiffMinutes());
        assertEquals(30, cycleTime.getDiffSeconds());

        //---------------------------- Move card forward

        DateProvider.setDate(dateFormat.parse("2020/5/25 14:25:50"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getDevelopmentStageId(), utility.getTestStageId());
        cycleTime = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getTestStageId());
        assertEquals(5, cycleTime.getDiffDays());
        assertEquals(5, cycleTime.getDiffHours());
        assertEquals(25, cycleTime.getDiffMinutes());
        assertEquals(50, cycleTime.getDiffSeconds());

        DateProvider.setDate(dateFormat.parse("2020/5/26 18:10:15"));
        utility.moveCard(utility.getDefaultWorkflowId(), cardId, utility.getTestStageId(), utility.getReadyToDeployStageId());
        cycleTime = utility.calculateCycleTime(utility.getDefaultWorkflowId(), cardId, utility.getReadyStageId(), utility.getReadyToDeployStageId());
        assertEquals(6, cycleTime.getDiffDays());
        assertEquals(9, cycleTime.getDiffHours());
        assertEquals(10, cycleTime.getDiffMinutes());
        assertEquals(15, cycleTime.getDiffSeconds());
    }



}
