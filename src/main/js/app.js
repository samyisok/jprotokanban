import React from 'react'
import ReactDOM from 'react-dom';
import Dashboard from './components/Dashboard.jsx'
import Login from './components/Login.jsx'

import {
  BrowserRouter as Router,
  Switch,
  Route
} from 'react-router-dom'


function App() {
  return (
    <div className='App'>
      <Router>
        <Switch>
          <Route path="/login" exact>
            <Login />
          </Route>
          <Route path="/" exact>
            <Dashboard />
          </Route>
        </Switch>
      </Router>
    </div>
  )

}

ReactDOM.render(<App />, document.getElementById('react'))