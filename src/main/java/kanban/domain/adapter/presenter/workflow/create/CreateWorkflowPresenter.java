package kanban.domain.adapter.presenter.workflow.create;

import kanban.domain.adapter.presenter.workflow.viewmodel.CreateWorkflowViewModel;
import kanban.domain.usecase.workflow.create.CreateWorkflowOutput;

public class CreateWorkflowPresenter implements CreateWorkflowOutput {

    private String workflowId;

    public CreateWorkflowViewModel build() {
        CreateWorkflowViewModel viewModel = new CreateWorkflowViewModel();
        viewModel.setWorkflowId(workflowId);
        return viewModel;
    }


    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}
