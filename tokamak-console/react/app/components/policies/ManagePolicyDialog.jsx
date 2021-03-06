import React from 'react';
import Dialog from 'material-ui/Dialog';
import PasswordPolicyService from "../../services/PasswordPolicyService";

class CreatePolicyForm extends React.Component {
    propTypes: {
        roleCreated: React.PropTypes.func,
        roleUpdated: React.PropTypes.func
    }
    
    constructor(props) {
        super(props);
        
        this.state = {
            name: "",
            description: "",
            error: "",
            role_id: "",
            loading: false,
            update: false,
            open: false
        };
    }

    show(role) {
        if(role) {
            this.setState({ role_id:role.id, name:role.name, description:role.description, update:true });
        }
        this.setState({ open:true });
    }

    hide() {
        this.setState({ error:null });
        this.setState({open:false});
    }

    nameChanged(event) {
        this.setState({ name:event.target.value });
    }

    descriptionChanged(event) {
        this.setState({ description:event.target.value });
    }

    create() {        
        this.setState({ loading:true });
        
        let self = this;
        PasswordPolicyService.create(this.state.name, this.state.description).then(function(result) {
            if(result.status === "accepted") {
                self.props.roleCreated(result.instance);
                self.hide();
                self.setState({ role_id:"" });
                self.setState({ name:"" });
                self.setState({ description:"" });
            }
            if(result.status === "rejected") {
                self.setState({ error:result.errors[0].message });
            }
            self.setState({ loading:false });
        });
    }

    update() {        
        this.setState({ loading:true });

        let self = this;
        PasswordPolicyService.update(this.state.role_id, this.state.name, this.state.description).then(function(result) {
            if(result.status === "accepted") {
                self.props.roleUpdated(result.instance);
                self.hide();
                self.setState({ role_id:"" });
                self.setState({ name:"" });
                self.setState({ description:"" });
            }
            if(result.status === "rejected") {
                self.setState({ error:result.errors[0].message });
            }
            self.setState({ loading:false });
        });
    }


    render() {
        let title = this.state.update ? "Update Policy" : "Create Policy";
        let button = this.state.update ? <button className="tok-button center" style={{marginRight:"10px"}} onClick={ () => this.update() }>Update</button> : <button className="tok-button center" style={{marginRight:"10px"}} onClick={() => this.create()}>Create</button>;

        return (
            <Dialog modal={true} open={this.state.open}>
              <div className="modal-title">{title}</div>
              
              {this.state.error && this.state.error.length > 0 &&
                 <div className="validation-error">{this.state.error}</div>
              }        
              
              <table style={{width:"100%", padding:"0 60px 30px 60px"}}>
                <tr>
                  <td className="form-key">Policy Name</td>
                  <td className="form-value"><input autoFocus className="tok-textfield" type="text" name="name" value={this.state.name} onChange={this.nameChanged.bind(this)} autoComplete="off" /></td>
                </tr>
                <tr>
                  <td className="form-key">Description</td>
                  <td><textarea className="tok-textfield" name="description" value={this.state.description} onChange={this.descriptionChanged.bind(this)} autoComplete="off" /></td>
                </tr>                
              </table>
              
              <div style={{textAlign:"center", paddingBottom:"30px"}}>
                {button}
                <button className="tok-button tok-cancel center" onClick={() => this.hide()}>Cancel</button>
              </div>
            </Dialog>
        );
    }
    
}

export default CreatePolicyForm;