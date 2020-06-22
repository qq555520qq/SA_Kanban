package kanban.domain.adapter.presenter.stage.create;

import kanban.domain.adapter.presenter.stage.viewmodel.CreateStageViewModel;
import kanban.domain.usecase.stage.create.CreateStageOutput;

public class CreateStagePresenter implements CreateStageOutput {
    private String stageId;

    public CreateStageViewModel build() {
        CreateStageViewModel viewModel = new CreateStageViewModel();
        viewModel.setStageId(stageId);
        return viewModel;
    }

    @Override
    public String getStageId() {
        return stageId;
    }

    @Override
    public void setStageId(String stageId) {
        this.stageId = stageId;
    }
}
