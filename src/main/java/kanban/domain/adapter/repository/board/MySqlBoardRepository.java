package kanban.domain.adapter.repository.board;

import kanban.domain.adapter.database.MySqlDatabaseHelper;
import kanban.domain.adapter.database.table.BoardTable;
import kanban.domain.adapter.database.table.BoardWorkflowTable;
import kanban.domain.adapter.repository.board.data.BoardData;
import kanban.domain.adapter.repository.board.mapper.BoardEntityDataMapper;
import kanban.domain.usecase.board.IBoardRepository;
import kanban.domain.usecase.board.BoardEntity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlBoardRepository implements IBoardRepository {

    private MySqlDatabaseHelper sqlDatabaseHelper;

    public MySqlBoardRepository() {
        sqlDatabaseHelper = new MySqlDatabaseHelper();
    }

    @Override
    public void add(BoardEntity boardEntity) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            BoardData boardData = BoardEntityDataMapper.transformEntityToData(boardEntity);

            for (String workflowId : boardData.getWorkflowIds()) {
                addBoardWorkflow(workflowId, boardData.getBoardId());
            }

            String sql = String.format("Insert Into %s Values (?, ?, ?)",
                    BoardTable.tableName);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, boardData.getUserId());
            preparedStatement.setString(2, boardData.getBoardId());
            preparedStatement.setString(3, boardData.getBoardName());
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
    public BoardEntity getBoardById(String boardId) {
        if (!sqlDatabaseHelper.isTransacting()) {
            sqlDatabaseHelper.connectToDatabase();
        }
        ResultSet resultSet = null;
        BoardData boardData = null;
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    BoardTable.tableName,
                    BoardTable.boardId,
                    boardId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            if (resultSet.first()) {
                String userId = resultSet.getString(BoardTable.userId);
                String _boardId = resultSet.getString(BoardTable.boardId);
                String name = resultSet.getString(BoardTable.name);

                boardData = new BoardData();
                boardData.setUserId(userId);
                boardData.setBoardId(_boardId);
                boardData.setBoardName(name);
                boardData.setWorkflowIds(getWorkflowIdsByBoardId(boardId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
            if (!sqlDatabaseHelper.isTransacting()) {
                sqlDatabaseHelper.closeConnection();
            }
        }
        return BoardEntityDataMapper.transformDataToEntity(boardData);
    }

    @Override
    public void save(BoardEntity boardEntity) {
        sqlDatabaseHelper.connectToDatabase();
        PreparedStatement preparedStatement = null;
        try {
            sqlDatabaseHelper.transactionStart();

            BoardData boardData = BoardEntityDataMapper.transformEntityToData(boardEntity);

            for (String workflowId : boardData.getWorkflowIds()) {
                addBoardWorkflow(workflowId, boardData.getBoardId());
            }

            String sql = String.format("Insert Into %s Values (?, ?, ?) On Duplicate Key Update %s=?",
                    BoardTable.tableName, BoardTable.name);
            preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
            preparedStatement.setString(1, boardData.getUserId());
            preparedStatement.setString(2, boardData.getBoardId());
            preparedStatement.setString(3, boardData.getBoardName());
            preparedStatement.setString(4, boardData.getBoardName());
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
    public List<BoardEntity> getBoardsByUserId(String userId) {
        if (!sqlDatabaseHelper.isTransacting()) {
            sqlDatabaseHelper.connectToDatabase();
        }

        ResultSet resultSet = null;
        List<BoardEntity> boardEntities = new ArrayList<>();
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    BoardTable.tableName,
                    BoardTable.userId,
                    userId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            while (resultSet.next()) {
                String boardId = resultSet.getString(BoardTable.boardId);
                String name = resultSet.getString(BoardTable.name);

                BoardData boardData = new BoardData();
                boardData.setUserId(userId);
                boardData.setBoardId(boardId);
                boardData.setBoardName(name);
                boardData.setWorkflowIds(getWorkflowIdsByBoardId(boardId));

                boardEntities.add(BoardEntityDataMapper.transformDataToEntity(boardData));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
            if (!sqlDatabaseHelper.isTransacting()) {
                sqlDatabaseHelper.closeConnection();
            }
        }
        return boardEntities;
    }


    private void addBoardWorkflow(String workflowId, String boardId) throws SQLException {
        String sql = String.format("Insert Ignore Into %s Values (?, ?)",
                BoardWorkflowTable.tableName);
        PreparedStatement preparedStatement = sqlDatabaseHelper.getPreparedStatement(sql);
        preparedStatement.setString(1, workflowId);
        preparedStatement.setString(2, boardId);
        preparedStatement.executeUpdate();
        sqlDatabaseHelper.closePreparedStatement(preparedStatement);
    }

    private List<String> getWorkflowIdsByBoardId(String boardId) {
        ResultSet resultSet = null;
        List<String> workflowIds = new ArrayList<>();
        try {
            String query = String.format("Select * From %s Where %s = '%s'",
                    BoardWorkflowTable.tableName,
                    BoardWorkflowTable.boardId,
                    boardId);
            resultSet = sqlDatabaseHelper.getResultSet(query);
            while (resultSet.next()) {
                String workFlowId = resultSet.getString(BoardWorkflowTable.workflowId);

                workflowIds.add(workFlowId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            sqlDatabaseHelper.closeResultSet(resultSet);
        }

        return workflowIds;
    }


}
