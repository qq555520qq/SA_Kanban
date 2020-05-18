import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route ,Switch} from 'react-router-dom';
import User from './User'
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";

ReactDOM.render(
    (
        <BrowserRouter>
            <Switch>
                <Route exact path="/" component={User}></Route>
                {/* <Route path="*" component={Error404}/> */}
            </Switch>
        </BrowserRouter>
    ),
    document.getElementById('root')
);
