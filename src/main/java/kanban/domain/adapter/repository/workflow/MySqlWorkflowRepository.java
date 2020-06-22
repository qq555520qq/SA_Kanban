package kanban.domain.adapter.repository.workflow;

import kanban.domain.adapter.database.MySqlDatabaseHelper;
import kanban.domain.adapter.database.table.StageCardTable;
import kanban.domain.adapter.database.table.StageTable;
import kanban.domain.adapter.database.table.WorkflowTable;
import kanban.domain.adapter.repository.workflow.data.StageData;
import kanban.domain.adapter.repository.workflow.data.WorkflowData;
import kanban.domain.adapter.repository.workflow.mapper.WorkflowEntityDataMapper;
import kanban.domain.usecase.stage.StageEntity;
import kanban.domain.usecase.workflow.WorkflowEntity;
import kanban.domain.usecase.workflow.IWorkflowRepository;

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
    public void add(WorkflowEntity workflowEntity) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            WorkflowData workflowData = WorkflowEntityDataMapper.transformEntityToData(workflowEntity);

            for(StageEntity stageEntity : workflowEntity.getStageEntities()) {
                addStage(stageEntity);
            }

            String sql = String.format("Insert Into %s Values (? , ?)",
                    WorkflowTable.tableName, WorkflowTable.title);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, workflowData.getWorkflowId());
            preparedStatement.setString(2, workflowData.getName());
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
    public WorkflowEntity getWorkflowById(String workflowId) {
        if(!sqlDatabaseHelper.isTransacting()) {
            sqlDatabaseHelper.connectToDatabase();
        }
        ResultSet resultSet = null;
        WorkflowData workflowData = null;
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    WorkflowTable.tableName,
                    WorkflowTable.workflow_id,
                    workflowId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            if (resultSet.first()) {
                String name = resultSet.getString(WorkflowTable.title);

                workflowData = new WorkflowData();
                workflowData.setWorkflowId(workflowId);
                workflowData.setName(name);
                workflowData.setStageDatas(getStagesByWorkflowId(workflowId));
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

        if(workflowData == null) {
            throw new RuntimeException("Workflow is not found,id=" + workflowId);
        }

        return WorkflowEntityDataMapper.transformDataToEntity(workflowData);
    }

    @Override
    public void save(WorkflowEntity workflowEntity) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            for(StageEntity stageEntity : workflowEntity.getStageEntities()) {
                addStage(stageEntity);
            }

            String sql = String.format("Insert Into %s Values (? , ?) On Duplicate Key Update %s=?",
                    WorkflowTable.tableName, WorkflowTable.title);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, workflowEntity.getWorkflowId());
            preparedStatement.setString(2, workflowEntity.getName());
            preparedStatement.setString(3, workflowEntity.getName());
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


    private void addStage(StageEntity stageEntity) throws SQLException {

        for(String cardId: stageEntity.getCardIds()) {
            addCard(cardId, stageEntity.getStageId());
        }

        String sql = String.format("Insert Into %s Values ( ?, ?, ?) On Duplicate Key Update %s=?",
                StageTable.tableName, StageTable.name);
        PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
        preparedStatement.setString(1, stageEntity.getStageId());
        preparedStatement.setString(2, stageEntity.getWorkflowId());
        preparedStatement.setString(3, stageEntity.getName());
        preparedStatement.setString(4, stageEntity.getName());

        preparedStatement.executeUpdate();
        sqlDatabaseHelper.closePreparedStatement(preparedStatement);
    }

    private List<StageData> getStagesByWorkflowId(String workflowId) {
        ResultSet resultSet = null;
        List<StageData> stageDatas = new ArrayList<>();
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    StageTable.tableName,
                    StageTable.workflowId,
                    workflowId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            while (resultSet.next()) {
                String stageId = resultSet.getString(StageTable.stage_id);
                String name = resultSet.getString(StageTable.name);
                StageData stageData = new StageData();
                stageData.setWorkflowId(workflowId);
                stageData.setStageId(stageId);
                stageData.setName(name);
                stageData.setCardIds(getCardIdsByStageId(stageId));
                stageDatas.add(stageData);
            }
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
        }
        return stageDatas;
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
