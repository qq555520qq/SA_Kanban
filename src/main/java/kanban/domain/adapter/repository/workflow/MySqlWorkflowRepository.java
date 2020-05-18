package kanban.domain.adapter.repository.workflow;

import kanban.domain.adapter.database.MySqlDatabaseHelper;
import kanban.domain.adapter.database.table.StageCardTable;
import kanban.domain.adapter.database.table.StageTable;
import kanban.domain.adapter.database.table.WorkflowTable;
import kanban.domain.model.aggregate.workflow.Stage;
import kanban.domain.model.aggregate.workflow.Workflow;
import kanban.domain.usecase.workflow.repository.IWorkflowRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlWorkflowRepository implements IWorkflowRepository {

    private MySqlDatabaseHelper sqlDatabaseHelper;

    public MySqlWorkflowRepository() {
        sqlDatabaseHelper = new MySqlDatabaseHelper();
    }


    @Override
    public void add(Workflow workflow) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            for(Stage stage : workflow.getStages()) {
                addStage(stage);
            }

            String sql = String.format("Insert Into %s Values (? , ?)",
                    WorkflowTable.tableName, WorkflowTable.title);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, workflow.getWorkflowId());
            preparedStatement.setString(2, workflow.getName());
            preparedStatement.executeUpdate();
            sqlDatabaseHelper.transactionEnd();
        } catch (SQLException e) {
            sqlDatabaseHelper.transactionError();
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closePreparedStatement(preparedStatement);
            sqlDatabaseHelper.closeConnection();
        }
    }

    @Override
    public Workflow getWorkflowById(String workflowId) {
        if(!sqlDatabaseHelper.isTransacting()) {
            sqlDatabaseHelper.connectToDatabase();
        }
        ResultSet resultSet = null;
        Workflow workflow = null;
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    WorkflowTable.tableName,
                    WorkflowTable.workflow_id,
                    workflowId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            if (resultSet.first()) {
                String name = resultSet.getString(WorkflowTable.title);

                workflow = new Workflow();
                workflow.setWorkflowId(workflowId);
                workflow.setName(name);
                workflow.setStages(getStagesByWorkflowId(workflowId));
             }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
            if(!sqlDatabaseHelper.isTransacting()) {
                sqlDatabaseHelper.closeConnection();
            }
        }

        if(workflow == null) {
            throw new RuntimeException("Workflow is not found,id=" + workflowId);
        }

        return workflow;
    }

    @Override
    public void save(Workflow workflow) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            for(Stage stage : workflow.getStages()) {
                addStage(stage);
            }

            String sql = String.format("Insert Into %s Values (? , ?) On Duplicate Key Update %s=?",
                    WorkflowTable.tableName, WorkflowTable.title);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, workflow.getWorkflowId());
            preparedStatement.setString(2, workflow.getName());
            preparedStatement.setString(3, workflow.getName());
            preparedStatement.executeUpdate();
            sqlDatabaseHelper.transactionEnd();
        } catch (SQLException e) {
            sqlDatabaseHelper.transactionError();
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closePreparedStatement(preparedStatement);
            sqlDatabaseHelper.closeConnection();
        }
    }


    private void addStage(Stage stage) throws SQLException {

        for(String cardId: stage.getCardIds()) {
            addCard(cardId, stage.getStageId());
        }

        String sql = String.format("Insert Into %s Values ( ?, ?, ?) On Duplicate Key Update %s=?",
                StageTable.tableName, StageTable.name);
        PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
        preparedStatement.setString(1, stage.getStageId());
        preparedStatement.setString(2, stage.getWorkflowId());
        preparedStatement.setString(3, stage.getName());
        preparedStatement.setString(4, stage.getName());

        preparedStatement.executeUpdate();
        sqlDatabaseHelper.closePreparedStatement(preparedStatement);
    }

    private List<Stage> getStagesByWorkflowId(String workflowId) {
        ResultSet resultSet = null;
        List<Stage> stages = new ArrayList<>();
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    StageTable.tableName,
                    StageTable.workflowId,
                    workflowId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            while (resultSet.next()) {
                String stageId = resultSet.getString(StageTable.stage_id);
                String name = resultSet.getString(StageTable.name);
                Stage stage = new Stage();
                stage.setWorkflowId(workflowId);
                stage.setStageId(stageId);
                stage.setName(name);
                stage.setCardIds(getCardIdsByStageId(stageId));
                stages.add(stage);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
        }
        return stages;
    }

    private void addCard(String cardId, String stageId) throws SQLException {
        String sql = String.format("Insert Into %s Values ( ?, ?)",
                StageCardTable.tableName);
        PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
        preparedStatement.setString(1, cardId);
        preparedStatement.setString(2, stageId);

        preparedStatement.executeUpdate();
        sqlDatabaseHelper.closePreparedStatement(preparedStatement);
    }

    private List<String> getCardIdsByStageId(String stageId) {
        ResultSet resultSet = null;
        List<String> cardIds = new ArrayList<>();
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    StageCardTable.tableName,
                    StageCardTable.stageId,
                    stageId);
            resultSet = sqlDatabaseHelper.getResultSet(query);

            while (resultSet.next()) {
                String _cardId = resultSet.getString(StageCardTable.cardId);
                cardIds.add(_cardId);
            }

            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
        }
        return cardIds;
    }

}
