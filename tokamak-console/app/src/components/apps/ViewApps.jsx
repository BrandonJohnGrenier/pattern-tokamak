import React from "react";
import MuiThemeProvider from "material-ui/styles/MuiThemeProvider";
import AppTable from "./AppTable.jsx";

class ViewApps extends React.Component {

    render() {
        let moreThanOneResult = this.props.apps && this.props.apps.length > 0;
        
        return (
            <div className="animated fadeIn">
            {moreThanOneResult  &&
              <MuiThemeProvider>
                <div>
                  <div id="header">
                    <div className="title">OAuth Apps</div>
                    <p className="overview">Register new apps to interact with OAuth 2 protected endpoints.</p>
                    <button className="tok-button fixed-top">+ Create App</button>
                  </div>
                  <div className="table-container">
                    <AppTable apps={this.props.apps} />
                  </div>
                </div>
              </MuiThemeProvider>
            }
            {!moreThanOneResult &&
              <div className="error-page">
                <h2 className="error-title">You haven't created any apps yet</h2>
                <button className="tok-button center margin-top-50">Create App</button>
              </div>
            }
            </div>
        );
    }
  
}

export default ViewApps;