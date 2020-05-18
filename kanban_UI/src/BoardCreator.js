import React from 'react';
import axios from 'axios';
import { Button, Modal, Form, FormControl, FormGroup, Col, Badge } from 'react-bootstrap';



class BoardCreator extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            show: false,
            boardName: ''
        };

        this.handleShow = this.handleShow.bind(this);
        this.handleClose = this.handleClose.bind(this);
        this.nameOnChange = this.nameOnChange.bind(this);
        this.submitBoard = this.submitBoard.bind(this);
    }
    handleShow() {
        this.setState({ show: true });
    }

    handleClose() {
        this.setState({ show: false });
    }

    nameOnChange(event) {
        console.log(event.target.value);
        this.setState({ boardName: event.target.value });
    }

    submitBoard() {
        let self = this;
        axios.post("http://localhost:8080/SA1321/users/1/boards", {
            name: this.state.boardName
        }).then(function (response) {
            self.props.getBoardByUserId("1");
            self.handleClose();
        });
    }

    render() {
        return (
            <div>
                <div className="button-position"  style={{ display: 'flex', 'cursor': 'pointer','margin': '10px' }}>
                    <Button onClick={this.handleShow}>
                        add Board
                    </Button>
                </div>
                <Modal show={this.state.show} onHide={this.handleClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>New Board</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form horizontal="true">
                            <FormGroup>
                                <Col componentclass={Form.Label} sm={2}>
                                    *name:
                        </Col>
                                <Col sm={10}>
                                    <FormControl componentclass="textarea" placeholder="input board name..." onInput={this.nameOnChange}/>
                                </Col>
                            </FormGroup>
                            <Col componentclass={Form.Label}>
                                (Note: * denotes a required field)
                    </Col>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button onClick={this.submitBoard}>Submit</Button>
                        <Button onClick={this.handleClose}>Cancel</Button>
                    </Modal.Footer>
                </Modal>
            </div>
        )
    }
}

export default BoardCreator;