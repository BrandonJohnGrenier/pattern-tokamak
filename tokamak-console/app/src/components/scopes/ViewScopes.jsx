import React from "react";
import MuiThemeProvider from "material-ui/styles/MuiThemeProvider";
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from "material-ui/Table";

const Timestamp = require('react-timestamp');

class ViewScopes extends React.Component {

    render() {
        return (
            <MuiThemeProvider>
                <div className="table-container">
                <h1>App Scopes</h1>
                <Table className="display-table" showCheckboxes={false} selectable={false}>
                  <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                    <TableRow>
                      <TableHeaderColumn className="dth left-pad-0">Scope ID</TableHeaderColumn>
                      <TableHeaderColumn className="dth">Name</TableHeaderColumn>
                      <TableHeaderColumn className="dth">Description</TableHeaderColumn>
                      <TableHeaderColumn className="dth right-pad-0">Created</TableHeaderColumn>
                    </TableRow>
                  </TableHeader>
                  
                  <TableBody displayRowCheckbox={false}>
                    {this.props.scopes.map((scope) => 
                     <TableRow key={scope.id}>
                       <TableRowColumn className="dtr left-pad-0">{scope.id}</TableRowColumn>
                       <TableRowColumn className="dtr">{scope.name}</TableRowColumn>
                       <TableRowColumn className="dtr">{scope.description}</TableRowColumn>
                       <TableRowColumn className="dtr right-pad-0"><Timestamp time={scope.created/1000} format="full" /></TableRowColumn>
                     </TableRow>
                    )}
                  </TableBody>
                </Table>
                </div>
            </MuiThemeProvider>
        );
    }
  
}

export default ViewScopes;