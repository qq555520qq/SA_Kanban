import React from 'react'
import { Card, Button } from 'react-bootstrap';
import './Board.css'
class Board extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            
        }

        this.goToBoard = this.goToBoard.bind(this);
    }


    goToBoard() {
        alert('go to board');
    }
    


    render() {

        return(
            <div >
                <Card>
                    <Card.Header>

                    </Card.Header>
                    <Card.Body>
                        <Card.Text>
                            {this.props.board.boardName}
                        </Card.Text>
                        <Button variant="primary" onClick={this.goToBoard}>Go to board</Button>
                    </Card.Body>
                </Card>
            </div>
        );
    }
}

export default Board;