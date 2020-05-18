import React from 'react';
import axios from 'axios';
import Board from './Board';
import BoardCreator from './BoardCreator';

class User extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            boards:[]
        };
        this.getBoardByUserId = this.getBoardByUserId.bind(this);


        this.getBoardByUserId(1);
    }

    
    getBoardByUserId(userId) {
        let self = this;
        axios.get("http://localhost:8080/SA1321/users/" + userId + "/boards")
            .then(function (response) {
                self.setState({ boards: response.data.boardEntities });
            }).catch(function (error) {
                console.log(error);
            });
    }

    render() {
        return (
            <div>
                    <BoardCreator getBoardByUserId={this.getBoardByUserId} />
                    <div style={{"display":'flex',"flexWrap":"wrap"}}>
                        {
                            this.state.boards && this.state.boards.length > 0 ?
                                this.state.boards.map(board => {
                                    return (
                                        <Board board={board}/>
                                    )
                                })
                            :'You dont have board!'
                        }
                    </div>
            </div>
        )
    }
}

export default User;