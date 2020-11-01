import React from 'react'

import TablePage from "../../../common/TablePage";

import {deleteUser, restoreUser} from "../../../services/SuperAdminService";
import {getDoctors} from "../../../services/MedAdminService";
import {bindActionCreators} from "redux";
import {withRouter} from "react-router-dom";
import {withSnackbar} from "notistack";
import connect from "react-redux/es/connect/connect";
import strings from "../../../localization";
import * as Actions from "../../../actions/Actions";

import IconButton from "@material-ui/core/IconButton";
import MoreVert from '@material-ui/icons/MoreVert';
import UndoIcon from '@material-ui/icons/Undo';
import DeleteIcon from '@material-ui/icons/Delete';

import {ListItemIcon, ListItemText, Menu, MenuItem, TableCell} from "@material-ui/core";

import AddDoctor from './AddDoctor';

class DoctorList extends TablePage {

    tableDescription = [
        { key: 'email', label: strings.doctorList.email },
        { key: 'firstName', label: strings.doctorList.firstName },
        { key: 'lastName', label: strings.doctorList.lastName },
        { key: 'role', label: strings.doctorList.role },
        { key: 'occupation', label: strings.doctorList.occupation },
        { key: 'activeSince', label: strings.doctorList.dateCreated, transform: 'renderColumnDate' },
        { key: 'enabled', label: strings.doctorList.enabled, transform: 'renderColumnDeleted' }
    ];

    fetchData() {

        this.setState({
            lockTable: true
        });

        getDoctors({
            
        }).then(response => {

            if(!response.ok) {
                return;
            }

            const doctors = response.data;
            const doctorsTotal = doctors.length;
            
            this.setState({
                tableData: doctors,
                total: doctorsTotal,
                lockTable: false,
                medInstitutionName: doctorsTotal > 0 ? doctors[0].medInstitution.name : '',
                medInstitutionId: doctorsTotal > 0 ? doctors[0].medInstitution.id : undefined,
                showAdd: doctorsTotal > 0 ? true : false,
            });
        });
    }

    componentDidMount() {
        this.fetchData();
    }

    getPageHeader() {
        return <h1>{ strings.doctorList.pageTitle } in {this.state.medInstitutionName}</h1>;
    }

    renderAddContent() {
        return <AddDoctor 
                    onCancel={ this.onCancel } 
                    onFinish={ this.onFinish } 
                    medInstitution={this.state.medInstitutionId}
                    medInstitutionName={this.state.medInstitutionName} />
    }

    delete(item) {

        this.setState({
            lockTable: true
        });

        deleteUser(item.id).then(response => {

            if(response && !response.ok) {
                this.onFinish(null);
                return;
            }

            this.props.enqueueSnackbar(strings.medInstitutionList.userDelete, { variant: 'success' });

            this.onFinish(item);
            this.cancelDelete();

            this.setState({
                lockTable: false
            });
        });
    }

    restore(item) {

        this.setState({
            lockTable: true
        });

        restoreUser(item.id).then(response => {

            if(response && !response.ok) {
                this.onFinish(null);
                return;
            }

            this.props.enqueueSnackbar(strings.userList.userRestored, { variant: 'success' });

            this.onFinish(item);

            this.setState({
                lockTable: false
            });
        });
    }

    renderRowMenu(index, item) {

        let ariaOwns = 'action-menu-' + index;

        return(
            <TableCell>
                <IconButton
                    aria-owns={ this.state.anchorEl ? ariaOwns : undefined }
                    aria-haspopup="true"
                    onClick={ (event) => this.handleMenuClick(event, ariaOwns) }
                >
                    <MoreVert/>
                </IconButton>
                {
                    ariaOwns === this.state.ariaOwns &&
                    <Menu
                        id={ ariaOwns }
                        anchorEl={ this.state.anchorEl }
                        open={ Boolean(this.state.anchorEl) }
                        onClose={ () => this.handleMenuClose() }
                    >
                        {
                            !item[this.deletedField] &&
                            <MenuItem onClick={ () => this.handleMenuDelete(item) }>
                                <ListItemIcon>
                                    <DeleteIcon/>
                                </ListItemIcon>
                                <ListItemText inset primary={ strings.table.delete }/>
                            </MenuItem>
                        }
                        {
                            item[this.deletedField] &&
                            <MenuItem onClick={ () => this.handleRestore(item) }>
                                <ListItemIcon>
                                    <UndoIcon/>
                                </ListItemIcon>
                                <ListItemText inset primary={ strings.table.undo }/>
                            </MenuItem>
                        }

                    </Menu>
                }

            </TableCell>
        );
    }
}

function mapDispatchToProps(dispatch)
{
    return bindActionCreators({
        changeFullScreen: Actions.changeFullScreen
    }, dispatch);
}

function mapStateToProps({ menuReducers })
{
    return { menu: menuReducers };
}

export default withSnackbar(withRouter(connect(mapStateToProps, mapDispatchToProps)(DoctorList)));