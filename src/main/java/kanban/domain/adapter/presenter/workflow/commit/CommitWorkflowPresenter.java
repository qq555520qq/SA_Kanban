package kanban.domain.adapter.presenter.workflow.commit;

import kanban.domain.adapter.presenter.workflow.viewmodel.CommitWorkflowViewModel;
import kanban.domain.usecase.workflow.commit.CommitWorkflowOutput;

public class CommitWorkflowPresenter implements CommitWorkflowOutput {
    private String workflowId;

    public CommitWorkflowViewModel build() {
        CommitWorkflowViewModel viewModel = new CommitWorkflowViewModel();
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
