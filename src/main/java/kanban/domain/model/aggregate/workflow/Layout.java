package kanban.domain.model.aggregate.workflow;

public class Layout {
    private String layout;

    public Layout(String layout) {
        this.layout = layout;
    }

    @Override
    public String toString(){
        return layout;
    }
}
